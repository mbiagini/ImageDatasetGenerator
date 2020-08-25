package ar.com.itba.ss.datasetgenerator.engine.simulation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import ar.com.itba.ss.datasetgenerator.configuration.Conf;
import ar.com.itba.ss.datasetgenerator.configuration.HardConf;
import ar.com.itba.ss.datasetgenerator.engine.cellindexmethod.CellIndexManager;
import ar.com.itba.ss.datasetgenerator.engine.utils.FileUtils;
import ar.com.itba.ss.datasetgenerator.engine.utils.MetricUtils;
import ar.com.itba.ss.datasetgenerator.engine.utils.RandomUtils;
import ar.com.itba.ss.datasetgenerator.model.cellindexmethod.Grid;
import ar.com.itba.ss.datasetgenerator.model.cellindexmethod.Particle;
import ar.com.itba.ss.datasetgenerator.model.imagegeneration.ImageResource;
import ar.com.itba.ss.datasetgenerator.model.simulation.DrivePoint;
import ar.com.itba.ss.datasetgenerator.model.simulation.SimulationTrunk;

import static java.lang.String.format;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Simulator {
	
	private static Logger log = LoggerFactory.getLogger(Simulator.class);

	public Integer simulate(Conf conf, HardConf hardConf, int simulationStartNumber) {
		
		SimulationTrunk trunk = generateSimulation(conf, hardConf);
		return runSimulation(trunk, simulationStartNumber);
		
	}
	
	public SimulationTrunk generateSimulation(Conf conf, HardConf hardConf) {
		
		log.info("Generating simulation.");
	
		SimulationTrunk trunk = new SimulationTrunk(conf, hardConf);
		trunk.setPeople(conf.getPeople());
		
		log.info("Generating random particles.");
		generateRandomParticles(trunk);
		
		saveMap(hardConf, trunk.getParticleToPersonMap());
		
		return trunk;
			
	}
	
	public Integer runSimulation(SimulationTrunk trunk, int simulationNumber) {
		
		log.info("Running simulation.");
		
		List<Particle> particles = trunk.getParticles();
		
		ModifiedEulerAlgorithm euler = new ModifiedEulerAlgorithm();
		
		Conf conf = trunk.getConf();
		HardConf hardConf = trunk.getHardConf();
		
		DrivePoint drivePoint = new DrivePoint(
				RandomUtils.randomDoubleBetween(conf.getRandom(), 0.0, conf.getWidth()),
				RandomUtils.randomDoubleBetween(conf.getRandom(), 0.0, conf.getHeight()));
		
		Double cellIndexinteractionRadius = (conf.getCellIndexGridSide() / conf.getCellIndexGridSize()) / 4.0;
						
		int saveRate = conf.getSaveRate();
		
		for (int epoch = 0; epoch < conf.getEpochs(); epoch++) {
			
			log.info(format("Running epoch: %d, KE: %f.", epoch, MetricUtils.getKyneticEnergy(particles)));
			
			log.debug("Generating cell-index-method's grid.");
			Grid grid = generateCellIndexGrid(conf, particles);
			
			log.debug("Executing cell-index-method.");
			CellIndexManager.computeParticlesInteraction(grid, particles, cellIndexinteractionRadius);
			
			if (saveRate != 0 && epoch % saveRate == 0) {
				
				log.debug("Saving particles.");
				saveParticles(hardConf, conf, particles, simulationNumber);
				simulationNumber ++;
				
			}
			
			log.debug("Moving particles.");
			
			MovementManager.updateParticlesAcceleration(conf, particles, drivePoint);
			euler.updateParticles(particles, conf.getDt());
						
		}
		
		return simulationNumber;
				
	}
	
	public void generateRandomParticles(SimulationTrunk trunk) {
		
		Conf conf = trunk.getConf();
		
		List<ImageResource> people = trunk.getPeople();
		List<Particle> particles = new ArrayList<>();
		Map<Long, Long> particleToPersonMap = new HashMap<>();
		
		double h = new Double(conf.getHeight());
		double w = new Double(conf.getWidth());
		
		int simulationParticles = 0;
		simulationParticles = RandomUtils.randomIntBetween(
				conf.getRandom(), 
				conf.getCameraHeight().getMinParticleCount(),
				conf.getCameraHeight().getMaxParticleCount());
		
		log.info(format("Number of particles to generate: %d.", simulationParticles));
			
		for (long id = 0L; id < simulationParticles; id ++) {
			
			ImageResource person = people.get(RandomUtils.randomIntBetween(conf.getRandom(), 0, people.size()));
			
			double radius = new Double(Math.min(person.getWidth(),  person.getHeight())) / 2;
			double theta = RandomUtils.randomDouble(conf.getRandom()) * (2 * Math.PI) - Math.PI;
			double speed = conf.getStartVelocity();
			double acc = 0.0;
			
			Particle p = null;
						
			do {
				
				double x = RandomUtils.randomDouble(conf.getRandom()) * (w - 2 * radius) + radius;
				double y = RandomUtils.randomDouble(conf.getRandom()) * (h - 2 * radius) + radius;
				
				p = new Particle(id, x, y, radius, conf.getMass(), speed * Math.cos(theta), speed * Math.sin(theta), acc, acc, theta);
								
			} while (CellIndexManager.isOverlappingAny(p, particles));
			
			particles.add(p);
			particleToPersonMap.put(p.getId(), person.getId());
			
		}
		
		trunk.setParticles(particles);
		trunk.setParticleToPersonMap(particleToPersonMap);
		
	}
		
	private Grid generateCellIndexGrid(Conf conf, List<Particle> particles) {
		Grid grid = new Grid(conf.getCellIndexGridSide(), conf.getCellIndexGridSize());
		CellIndexManager.allocateParticlesInGrid(particles, grid);
		return grid;
	}
	
	private void saveParticles(HardConf hardConf, Conf conf, List<Particle> particles, int instant) {
		
		Gson gson = new Gson();
		String json = gson.toJson(particles);
		
		String filename = FileUtils.getPath(hardConf.getParticlesDirectory(), hardConf.getParticlesFormat());
		filename = format(filename, instant, conf.getCameraHeight().getLabel());
		
		File file = new File(filename);
		FileUtils.saveStringToFile(file, json);
		
	}
	
	private void saveMap(HardConf hardConf, Map<Long, Long> particleToPersonMap) {
		
		Gson gson = new Gson();
		String json = gson.toJson(particleToPersonMap);
		
		String filename = FileUtils.getPath(hardConf.getParticlesDirectory(), hardConf.getParticlesToPersonMapFilename());
		
		File file = new File(filename);
		FileUtils.saveStringToFile(file, json);
		
	}
	
}
