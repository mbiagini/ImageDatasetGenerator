package ar.com.itba.ss.datasetgenerator.engine.imagegeneration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ar.com.itba.ss.datasetgenerator.configuration.Conf;
import ar.com.itba.ss.datasetgenerator.configuration.HardConf;
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

	public void generate(Conf conf, HardConf hardConf, int simulationStartNumber, int generationCount) {
				
		log.info(format("Generating images. SimulationStartNumber: %d, GenerationCount: %d.)", 
				simulationStartNumber, generationCount));
		
		ImageGenerationTrunk trunk = new ImageGenerationTrunk(conf, hardConf);
		
		generatePeople(trunk);
		generateBackground(trunk);
		
		log.info("Loading map.");
		loadMap(trunk);
		
		log.info("Generating initial state.");
		generateInitialState(trunk);
		
		for (int instant = simulationStartNumber; instant < simulationStartNumber + generationCount; instant ++) {
			
			log.info("Generating image: " + instant);
			List<Particle> particles = loadParticles(
					trunk.getHardConf().getParticlesDirectory(),
					trunk.getHardConf().getParticlesFormat(), 
					instant);
			ImageUtils.saveImage(trunk, particles, instant);
			
		}
		
		log.info("------------------------------------------------------------------------------------------");
				
	}
	
	private void generatePeople(ImageGenerationTrunk trunk) {
		
		log.info("Generating people.");
		log.info("Reading people.");
		
		String irPeopleDirectory = trunk.getHardConf().getIrPeopleDirectory();
		String rgbPeopleDirectory = trunk.getHardConf().getRgbPeopleDirectory();
		String rgbPeopleRegex = trunk.getHardConf().getRgbPeopleRegex();
		
		List<ImageResource> people = FileUtils.readAllImages(rgbPeopleDirectory, rgbPeopleRegex)
				.stream()
				.map(rgbImg -> {
					
					SSImage irImg = FileUtils.readImage(irPeopleDirectory, rgbImg.getFilename().replace("rgb", "ir"));
					return ImageResourceManager.initializeWithIrImg(rgbImg, irImg);
					
				})
				.collect(Collectors.toList());
		
		trunk.setPeople(people);
		
		log.info(format("Finished reading and saving %d people.", people.size()));
		
	}
	
	private void generateBackground(ImageGenerationTrunk trunk) {
		
		log.info("Generating background.");
		log.info("Reading all backgrounds.");

		List<SSImage> backgroundImgs = FileUtils.readAllImages(
				trunk.getHardConf().getRgbBackgroundsDirectory(), 
				trunk.getHardConf().getRgbBackgroundsRegex());
		
		log.info(format("Finished reading %d backgrounds.", backgroundImgs.size()));
		
		SSImage rgbImg = backgroundImgs.get(
				RandomUtils.randomIntBetween(trunk.getConf().getRandom(), 0, backgroundImgs.size()));
				
		SSImage irImg = FileUtils.readImage(
				trunk.getHardConf().getIrBackgroundsDirectory(), 
				rgbImg.getFilename().replace("rgb", "ir"));
		
		if (trunk.getConf().isRandomUniformIrBackground()) {
			trunk.setBackground(ImageResourceManager.initializeWithIrCustom(
					rgbImg, trunk.getConf().getMinBackgroundValue(), trunk.getConf().getMaxBackgroundValue(), trunk.getConf().getRandom()));
		} else {
			trunk.setBackground(ImageResourceManager.initializeWithIrImg(rgbImg, irImg));
		}
		
		log.info(format("Background selected: %s.", rgbImg.getFilename()));
				
	}
		
	private void generateInitialState(ImageGenerationTrunk trunk) {
		
		log.info("Generating initial state.");
		
		ImageGrid initialState = ImageGridManager.generateGrid(trunk.getConf(), trunk.getBackground(), trunk.getPeople());
		trunk.setInitialState(initialState);
		
		log.debug(format("Initial ImageGrid created: %s.", initialState.toString()));
				
	}
	
	private List<Particle> loadParticles(String particlesDirectory, String particlesFormat, int instant) {
		
		String filename = FileUtils.getPath(particlesDirectory, particlesFormat);
		String json = FileUtils.readStringFromFile(new File(format(filename, instant)));
		Type listType = new TypeToken<ArrayList<Particle>>() {}.getType();
		
		return new Gson().fromJson(json, listType);
		
	}
	
	private void loadMap(ImageGenerationTrunk trunk) {
				
		String filename = FileUtils.getPath(
				trunk.getHardConf().getParticlesDirectory(),
				trunk.getHardConf().getParticlesToPersonMapFilename());
		
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
