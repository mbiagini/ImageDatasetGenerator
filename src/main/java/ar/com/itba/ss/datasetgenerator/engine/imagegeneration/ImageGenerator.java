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
import ar.com.itba.ss.datasetgenerator.model.ImageGrid;
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

@Service
public class ImageGenerator {
	
	private static Logger log = LoggerFactory.getLogger(ImageGenerator.class);

	public void generate(Conf conf, HardConf hardConf, int simulationStartNumber, int generationCount) {
				
		log.info(format("Generating images. SimulationStartNumber: %d, GenerationCount: %d.)", 
				simulationStartNumber, generationCount));
		
		ImageGenerationTrunk trunk = new ImageGenerationTrunk(conf, hardConf);
		trunk.setPeople(conf.getPeople());
		trunk.setBackground(conf.getBackground());
		
		log.info("Loading map.");
		loadMap(trunk);
		
		log.info("Generating initial state.");
		generateInitialState(trunk);
		
		for (int instant = simulationStartNumber; instant < simulationStartNumber + generationCount; instant ++) {
			
			log.info("Generating image: " + instant);
			List<Particle> particles = loadParticles(
					hardConf.getParticlesDirectory(),
					hardConf.getParticlesFormat(), 
					instant,
					conf.getCameraHeight().getLabel());
			
			ImageUtils.saveImage(trunk, particles, instant, conf.getCameraHeight().getLabel());
			
		}
		
		log.info("------------------------------------------------------------------------------------------");
				
	}
		
	private void generateInitialState(ImageGenerationTrunk trunk) {
		
		log.info("Generating initial state.");
		
		ImageGrid initialState = ImageGridManager.generateGrid(trunk.getConf(), trunk.getBackground(), trunk.getPeople());
		trunk.setInitialState(initialState);
		
		log.debug(format("Initial ImageGrid created: %s.", initialState.toString()));
		
	}
	
	private List<Particle> loadParticles(String particlesDirectory, String particlesFormat, int instant, String label) {
		
		String filename = FileUtils.getPath(particlesDirectory, particlesFormat);
		String json = FileUtils.readStringFromFile(new File(format(filename, instant, label)));
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
