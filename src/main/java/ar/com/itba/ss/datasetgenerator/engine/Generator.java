package ar.com.itba.ss.datasetgenerator.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import ar.com.itba.ss.datasetgenerator.configuration.Config;
import ar.com.itba.ss.datasetgenerator.engine.cellindexmethod.CellIndexManager;
import ar.com.itba.ss.datasetgenerator.engine.movement.MovementManager;
import ar.com.itba.ss.datasetgenerator.engine.utils.FileUtils;
import ar.com.itba.ss.datasetgenerator.engine.utils.GenericUtils;
import ar.com.itba.ss.datasetgenerator.engine.utils.MetricUtils;
import ar.com.itba.ss.datasetgenerator.engine.utils.RandomUtils;
import ar.com.itba.ss.datasetgenerator.model.ImageGrid;
import ar.com.itba.ss.datasetgenerator.model.Person;
import ar.com.itba.ss.datasetgenerator.model.Point;
import ar.com.itba.ss.datasetgenerator.model.SSImage;
import ar.com.itba.ss.datasetgenerator.model.SimulationTrunk;
import ar.com.itba.ss.datasetgenerator.model.cellindexmethod.Grid;
import ar.com.itba.ss.datasetgenerator.model.cellindexmethod.Particle;

import static java.lang.String.format;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class Generator {
	
	private static Logger log = LoggerFactory.getLogger(Generator.class);

	public void generate() {
		
		log.info("******************************************************************************************");
		log.info("GENERATING IMAGES");
		
		log.info("Reading people.");
		List<Person> people = FileUtils.readAllImages(Config.peopleBasepath, Config.peopleRegex).stream()
				.map(img -> {
					return PersonManager.initializePerson(img);
				})
				.collect(Collectors.toList());
		log.info(format("Finished reading %d people.", people.size()));
		
		log.info("Reading all backgrounds.");
		List<SSImage> backgrounds = FileUtils.readAllImages(Config.backgroundsBasepath, Config.backgroundsRegex);
		log.info(format("Finished reading %d backgrounds.", backgrounds.size()));
		
		log.info("Generating simulation.");
		SimulationTrunk trunk = generateSimulation(people, backgrounds);
				
		log.info("Running simulation.");
		runSimulation(trunk, Config.simulationEphocs, Config.simulationSaveRate);
		
		log.info("------------------------------------------------------------------------------------------");
				
	}
	
	private SimulationTrunk generateSimulation(List<Person> people, List<SSImage> backgrounds) {
		
		SimulationTrunk trunk = new SimulationTrunk();
		
		trunk.setPeople(new ArrayList<>(people));
		trunk.setBackground(backgrounds.get(RandomUtils.randomIntBetween(0, backgrounds.size())));
		
		log.info("Generating Image Grid from background and people.");
		generateInitialState(trunk);
		
		log.info("Generating Particles to simulate movement using Cell Index Method");
		generateParticles(trunk, Config.simulationParticles);
						
		return trunk;
		
	}
	
	private void runSimulation(SimulationTrunk trunk, Integer epochs, Integer saveRate) {
		
		List<Particle> particles = trunk.getParticles();
		
		for (int epoch = 0; epoch < epochs; epoch++) {
			
			log.info(format("Running ephoc: %d. KyneticEnergy: %f.", epoch, MetricUtils.getKyneticEnergy(particles)));
			
			log.debug("Generating Cell Index Method's grid.");
			Grid grid = generateCellIndexGrid(particles);
			
			log.debug("Executing Cell Index Method.");
			CellIndexManager.computeParticlesInteraction(grid, particles, Config.cellIndexInteractionRadius);
			
			if (saveRate != 0 && epoch % saveRate == 0) {
				
				//log.debug("Saving particles");
				//saveParticles(particles, epoch);
				
				log.debug("Saving image.");
				saveImageOfInstant(trunk, particles, epoch);
				
			}
			
//			for (Particle p : particles) {
//				if (p.getId().equals(Config.debugParticleId) || p.getId().equals(Config.debugParticleId2)) {
//					log.debug("Updating particle: " + p.toString());
//				}
//			}
			
			MovementManager.moveParticles(
					particles,
					trunk.getBackground().getWidth(),
					trunk.getBackground().getHeight(),
					Config.kn,
					Config.gamma,
					Config.dt);
						
		}
		
	}
	
	private void saveParticles(List<Particle> particles, Integer instant) {
		
		Gson gson = new Gson();
		String json = gson.toJson(particles);
		String filename = format(Config.simulationDataBasepath + "particles%07d.json", instant);
		
		File file = new File(filename);
		FileUtils.saveStringToFile(file, json);
		
	}
	
	private void saveImageOfInstant(SimulationTrunk trunk, List<Particle> particles, Integer instant) {
		
		SSImage img = trunk.getInitialState().getBackground().clone();
		img.setBasepath(Config.imagesBasepath);
		img.setFilename(format("image%07d.jpg", instant));
		
		ImageGrid imgGrid = ImageGridManager.generateGrid(img, trunk.getPeople(), Config.infraredBackground);
				
		Integer[][] imgGridColorCanvas = imgGrid.getColorCanvas();
		Integer[][] imgGridIRCanvas = imgGrid.getIrCanvas();
		
		for (Particle p : particles) {
			
			Person person = trunk.getParticleToPersonMap().get(p.getId());
			
			if (person == null) {
				log.error(format("No person found for particle %d.", p.getId()));
				throw new RuntimeException("Particle with no person mapping error.");
			}
			
			Integer[][] personColorCanvas = person.getColorCanvas();
			Integer[][] personIRCanvas = person.getIRCanvas();
			
			Point start = new Point((int) (p.getX() - person.getWidth() / 2.0), (int) (p.getY() - person.getHeight() / 2.0));
									
			for (int x = 0; x < person.getWidth(); x++) {
				for (int y = 0; y < person.getHeight(); y++) {
					
					Integer color = personColorCanvas[x][y];
					Integer ir = personIRCanvas[x][y];
					
					if (color != Color.WHITE.getRGB()) {
						
						if (! (x + start.getX() >= imgGrid.getWidth()) && ! (y + start.getY() >= imgGrid.getHeight())
								&& ! (x + start.getX() < 0) && ! (y + start.getY() < 0)) {
							
							imgGridColorCanvas[x + start.getX()][y + start.getY()] = color;
							//imgIRCanvas[x + start.getX()][y + start.getY()] = ir;
							
						}
						
					} 
					
				}
			}
			
		}
		
		BufferedImage bi = img.getBufferedImage();
		BufferedImage initialStateBackgroundBi = trunk.getInitialState().getBackground().getBufferedImage();
		
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				
				if (imgGridColorCanvas[x][y] == Color.WHITE.getRGB()) {
					
					bi.setRGB(x, y, initialStateBackgroundBi.getRGB(x, y));
					
				} else {
					
					bi.setRGB(x, y, imgGridColorCanvas[x][y]);
					
				}
				
			}
		}
		
		FileUtils.saveImage(img);
		
	}
		
	private void generateInitialState(SimulationTrunk trunk) {
		
		SSImage img = trunk.getBackground().clone();
		
		ImageGrid initialState = ImageGridManager.generateGrid(img, trunk.getPeople(), Config.infraredBackground);
		
		log.debug(format("Initial ImageGrid created: %s.", initialState.toString()));
		
		trunk.setInitialState(initialState);
				
	}
	
	private void generateParticles(SimulationTrunk trunk, Integer count) {
		
		ImageGrid initialState = trunk.getInitialState();
	
		Map<Long, Person> particleToPersonMap = new HashMap<>();
		List<Particle> particles = new ArrayList<>();
		
		List<Person> people = initialState.getPeople();
		
		for (Long particleId = 0L; particleId < count; particleId ++) {
			
			log.debug(format("Trying to add particle %d.", particleId));
			
			Person person = people.get(RandomUtils.randomIntBetween(0, people.size()));
			
			Point start = ImageGridManager.addPerson(initialState, person);
			
			Double radius = new Double(Math.min(person.getWidth(),  person.getHeight())) / 2;
			Double x = new Double(start.getX()) + person.getWidth() / 2.0;
			Double y = new Double(start.getY()) + person.getHeight() / 2.0;
			Double theta = RandomUtils.randomDouble() * (2 * Math.PI) - Math.PI;
			Double speed = Config.simulationStartVelocity;
			Double acc = 0.0;
			
			particleToPersonMap.put(particleId, person);
			particles.add(new Particle(
					particleId, x, y, radius, Config.mass, speed * Math.cos(theta), speed * Math.sin(theta), acc, acc, theta));
			
		}
		
		trunk.setParticles(particles);
		trunk.setParticleToPersonMap(particleToPersonMap);
		
	}
	
	private Grid generateCellIndexGrid(List<Particle> particles) {
				
		Grid grid = new Grid(Config.cellIndexGridSide, Config.cellIndexGridSize);
		
		CellIndexManager.allocateParticlesInGrid(particles, grid);
				
		return grid;
		
	}

}
