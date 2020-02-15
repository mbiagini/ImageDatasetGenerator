package ar.com.itba.ss.datasetgenerator.engine.imagegeneration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ar.com.itba.ss.datasetgenerator.configuration.Config;
import ar.com.itba.ss.datasetgenerator.engine.utils.FileUtils;
import ar.com.itba.ss.datasetgenerator.engine.utils.ImageUtils;
import ar.com.itba.ss.datasetgenerator.engine.utils.RandomUtils;
import ar.com.itba.ss.datasetgenerator.model.ImageGrid;
import ar.com.itba.ss.datasetgenerator.model.SSImage;
import ar.com.itba.ss.datasetgenerator.model.cellindexmethod.Particle;
import ar.com.itba.ss.datasetgenerator.model.imagegeneration.ImageGenerationTrunk;
import ar.com.itba.ss.datasetgenerator.model.imagegeneration.ImageResource;

import static java.lang.String.format;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ImageGenerator {
	
	private static Logger log = LoggerFactory.getLogger(ImageGenerator.class);

	public void generate() {
				
		log.info("Generating images.");
		
		ImageGenerationTrunk trunk = new ImageGenerationTrunk();
		
		generatePeople(trunk);
		
		generateBackground(trunk);
		
		log.info("Loading map.");
		loadMap(trunk);
		
		log.info("Generating initial state.");
		generateInitialState(trunk);
		
		for (int instant = 0; instant < Config.generationCount; instant ++) {
			
			log.info("Generating image: " + instant);
			List<Particle> particles = loadParticles(instant);
			ImageUtils.saveImage(trunk, particles, instant);
			
		}
		
		log.info("------------------------------------------------------------------------------------------");
				
	}
	
	private void generatePeople(ImageGenerationTrunk trunk) {
		
		log.info("Generating people.");
		log.info("Reading people.");
		
		List<ImageResource> people = FileUtils.readAllImages(Config.peopleRgbBasepath, Config.peopleRgbRegex).stream()
				.map(rgbImg -> {
					
					SSImage irImg = FileUtils.readImage(Config.peopleIrBasepath, rgbImg.getFilename().replace("rgb", "ir"));
					return ImageResourceManager.initialize(rgbImg, irImg);
					
				})
				.collect(Collectors.toList());
		
		trunk.setPeople(people);
		
		log.info(format("Finished reading and saving %d people.", people.size()));
		
	}
	
	private void generateBackground(ImageGenerationTrunk trunk) {
		
		log.info("Generating background.");
		log.info("Reading all backgrounds.");
				
		List<SSImage> backgroundImgs = FileUtils.readAllImages(Config.backgroundsRgbBasepath, Config.backgroundsRgbRegex);
		
		log.info(format("Finished reading %d backgrounds.", backgroundImgs.size()));
		
		SSImage rgbImg = backgroundImgs.get(RandomUtils.randomIntBetween(0, backgroundImgs.size()));
		SSImage irImg = FileUtils.readImage(Config.backgroundsIrBasepath, rgbImg.getFilename().replace("rgb", "ir"));
		
		trunk.setBackground(ImageResourceManager.initialize(rgbImg, irImg));
		
		log.info(format("Background selected: %s.", rgbImg.getFilename()));
				
	}
		
	private void generateInitialState(ImageGenerationTrunk trunk) {
		
		log.info("Generating initial state.");
		
		ImageGrid initialState = ImageGridManager.generateGrid(trunk.getBackground(), trunk.getPeople());
		trunk.setInitialState(initialState);
		
		log.debug(format("Initial ImageGrid created: %s.", initialState.toString()));
				
	}
	
	private List<Particle> loadParticles(int instant) {
		
		String filename = format(Config.simulationDataBasepath + "/particles/particles_%07d.json", instant);
		File file = new File(filename);
		
		String json = FileUtils.readStringFromFile(file);
		Type listType = new TypeToken<ArrayList<Particle>>() {}.getType();
		
		return new Gson().fromJson(json, listType);
		
	}
	
	private void loadMap(ImageGenerationTrunk trunk) {
		
		String filename = Config.simulationDataBasepath + "/particle_to_person_map.json";
		File file = new File(filename);
		
		Gson gson = new Gson();
		
		@SuppressWarnings("unchecked")
		Map<String, Double> map = gson.fromJson(FileUtils.readStringFromFile(file), Map.class);
		
		List<ImageResource> people = trunk.getPeople();
		Map<Long, ImageResource> aux = new HashMap<>();
		Map<Long, ImageResource> particleToPersonMap = new HashMap<>();
		
		for (ImageResource p : people) {
			aux.put(p.getId(), p);
		}
		
		for (String particle : map.keySet()) {
			particleToPersonMap.put(Long.parseLong(particle), aux.get(map.get(particle).longValue()));
		}
		
		trunk.setParticleToPersonMap(particleToPersonMap);
		
	}

}
