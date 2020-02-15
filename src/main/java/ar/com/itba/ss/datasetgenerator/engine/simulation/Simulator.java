package ar.com.itba.ss.datasetgenerator.engine.simulation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import ar.com.itba.ss.datasetgenerator.configuration.Config;
import ar.com.itba.ss.datasetgenerator.engine.cellindexmethod.CellIndexManager;
import ar.com.itba.ss.datasetgenerator.engine.imagegeneration.ImageResourceManager;
import ar.com.itba.ss.datasetgenerator.engine.utils.FileUtils;
import ar.com.itba.ss.datasetgenerator.engine.utils.MetricUtils;
import ar.com.itba.ss.datasetgenerator.engine.utils.RandomUtils;
import ar.com.itba.ss.datasetgenerator.model.SSImage;
import ar.com.itba.ss.datasetgenerator.model.cellindexmethod.Grid;
import ar.com.itba.ss.datasetgenerator.model.cellindexmethod.Particle;
import ar.com.itba.ss.datasetgenerator.model.imagegeneration.ImageResource;
import ar.com.itba.ss.datasetgenerator.model.simulation.SimulationTrunk;

import static java.lang.String.format;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Simulator {
	
	private static Logger log = LoggerFactory.getLogger(Simulator.class);

	public void simulate() {
		
		SimulationTrunk trunk = generateSimulation();
		runSimulation(trunk, Config.simulationEphocs, Config.simulationSaveRate);
		
	}
	
	public SimulationTrunk generateSimulation() {
		
		log.info("Generating simulation.");
		
		log.info("Reading people.");
		List<ImageResource> people = FileUtils.readAllImages(Config.peopleRgbBasepath, Config.peopleRgbRegex).stream()
				.map(rgbImg -> {
					
					SSImage irImg = FileUtils.readImage(Config.peopleIrBasepath, rgbImg.getFilename().replace("rgb", "ir"));
					return ImageResourceManager.initialize(rgbImg, irImg);
					
				})
				.collect(Collectors.toList());
		log.info(format("Finished reading %d people.", people.size()));
	
		SimulationTrunk trunk = new SimulationTrunk();
		trunk.setPeople(new ArrayList<>(people));
		
		log.info("Generating random particles.");
		generateRandomParticles(trunk);
		saveMap(trunk.getParticleToPersonMap());
		
		return trunk;
			
	}
	
	public void runSimulation(SimulationTrunk trunk, int epochs, int saveRate) {
		
		log.info("Running simulation.");
		
		List<Particle> particles = trunk.getParticles();
		
		GearAlgorithm ga = new GearAlgorithm();
		ga.initialize(particles);
						
		for (int epoch = 0; epoch < epochs; epoch++) {
			
			log.info(format("Running epoch: %d, KE: %f.", epoch, MetricUtils.getKyneticEnergy(particles)));
			
			log.debug("Generating cell-index-method's grid.");
			Grid grid = generateCellIndexGrid(particles);
			
			log.debug("Executing cell-index-method.");
			CellIndexManager.computeParticlesInteraction(grid, particles, Config.cellIndexInteractionRadius);
			
			if (saveRate != 0 && epoch % saveRate == 0) {
				
				log.debug("Saving particles.");
				saveParticles(particles, epoch / saveRate);
				
			}
			
			log.debug("Moving particles.");
			
			ga.computePredictions(particles, Config.dt);
			
			MovementManager.moveParticles(particles);
			
			ga.fixPredictions(particles, Config.dt);
			
		}
				
	}
	
	public void generateRandomParticles(SimulationTrunk trunk) {
		
		List<ImageResource> people = trunk.getPeople();
		List<Particle> particles = new ArrayList<>();
		Map<Long, Long> particleToPersonMap = new HashMap<>();
		
		double h = new Double(Config.simulationHeight);
		double w = new Double(Config.simulationWidth);
				
		for (long id = 0L; id < Config.simulationParticles; id ++) {
			
			ImageResource person = people.get(RandomUtils.randomIntBetween(0, people.size()));
			
			double radius = new Double(Math.min(person.getWidth(),  person.getHeight())) / 2;
			double theta = RandomUtils.randomDouble() * (2 * Math.PI) - Math.PI;
			double speed = Config.simulationStartVelocity;
			double acc = 0.0;
			
			Particle p = null;
						
			do {
				
				double x = RandomUtils.randomDouble() * (w - 2 * radius) + radius;
				double y = RandomUtils.randomDouble() * (h - 2 * radius) + radius;
				
				p = new Particle(id, x, y, radius, Config.mass, speed * Math.cos(theta), speed * Math.sin(theta), acc, acc, theta);
								
			} while (CellIndexManager.isOverlappingAny(p, particles));
			
			particles.add(p);
			particleToPersonMap.put(p.getId(), person.getId());
			
		}
		
		trunk.setParticles(particles);
		trunk.setParticleToPersonMap(particleToPersonMap);
				
	}
		
	private Grid generateCellIndexGrid(List<Particle> particles) {
		
		Grid grid = new Grid(Config.cellIndexGridSide, Config.cellIndexGridSize);
		
		CellIndexManager.allocateParticlesInGrid(particles, grid);
				
		return grid;
		
	}
	
	private void saveParticles(List<Particle> particles, int instant) {
		
		Gson gson = new Gson();
		String json = gson.toJson(particles);
		String filename = format(Config.simulationDataBasepath + "/particles/particles_%07d.json", instant);
		
		File file = new File(filename);
		FileUtils.saveStringToFile(file, json);
		
	}
	
	private void saveMap(Map<Long, Long> particleToPersonMap) {
		
		Gson gson = new Gson();
		String json = gson.toJson(particleToPersonMap);
		String filename = Config.simulationDataBasepath + "/particle_to_person_map.json";
		
		File file = new File(filename);
		FileUtils.saveStringToFile(file, json);
		
	}
	
}
