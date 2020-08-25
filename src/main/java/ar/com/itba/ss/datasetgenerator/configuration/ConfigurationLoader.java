package ar.com.itba.ss.datasetgenerator.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import ar.com.itba.ss.datasetgenerator.engine.imagegeneration.ImageResourceManager;
import ar.com.itba.ss.datasetgenerator.engine.utils.FileUtils;
import ar.com.itba.ss.datasetgenerator.model.SSImage;
import ar.com.itba.ss.datasetgenerator.model.config.UniformBackground;
import ar.com.itba.ss.datasetgenerator.model.imagegeneration.ImageResource;

import static java.lang.String.format;

public class ConfigurationLoader {
	
	private static Logger log = LoggerFactory.getLogger(ConfigurationLoader.class);
	
	public Conf load(HardConf hardConf) {
		log.info("Loading configuration from .json file.");
		Gson gson = new Gson();
		Conf conf = gson.fromJson(
				FileUtils.readStringFromFile(new File(hardConf.getConfigFilePath())), 
				Conf.class);
		conf.setRandom(new Random(conf.getRandomSeed()));
		return conf;
	}
	
	public void initializeBackgrounds(HardConf hardConf, Conf conf) {
		
		log.info("Initializing backgrounds for later pick.");
		List<ImageResource> backgrounds = new ArrayList<>();
		
		List<SSImage> backgroundRgbImages = FileUtils.readAllImages(
				hardConf.getRgbBackgroundsDirectory(), 
				hardConf.getRgbBackgroundsRegex());
		
		for (SSImage backgroundRgbImage : backgroundRgbImages) {
			
			SSImage backgroundIrImage = FileUtils.readImage(
					hardConf.getIrBackgroundsDirectory(),
					backgroundRgbImage.getFilename().replace("rgb", "ir"));
			backgrounds.add(ImageResourceManager.initializeWithIrImg(backgroundRgbImage, backgroundIrImage));
			
			for (UniformBackground uniformBackground : conf.getUniformBackgrounds()) {
				backgrounds.add(ImageResourceManager.initializeWithIrCustom(
						backgroundRgbImage,
						uniformBackground.getMinPixelValue(),
						uniformBackground.getMaxPixelValue(),
						conf.getRandom()));
			}
			
		}
		
		log.info(format("Finished initializing %d backgrounds.", backgrounds.size()));
		conf.setBackgrounds(backgrounds);
				
	}
	
	public void initializePeople(HardConf hardConf, Conf conf) {
		
		log.info("Initializing people with camera_height label: " + conf.getCameraHeight().getLabel());
		
		String label = conf.getCameraHeight().getLabel();
		String irPeopleDirectory = hardConf.getIrPeopleDirectory().replace("people", "people_" + label);
		String rgbPeopleDirectory = hardConf.getRgbPeopleDirectory().replace("people", "people_" + label);
		String rgbPeopleRegex = hardConf.getRgbPeopleRegex();
		
		List<ImageResource> people = FileUtils.readAllImages(rgbPeopleDirectory, rgbPeopleRegex)
				.stream()
				.map(rgbImg -> {
					
					SSImage irImg = FileUtils.readImage(irPeopleDirectory, rgbImg.getFilename().replace("rgb", "ir"));
					return ImageResourceManager.initializeWithIrImg(rgbImg, irImg);
					
				})
				.collect(Collectors.toList());
		
		log.info(format("Finished reading and saving %d people.", people.size()));
		conf.setPeople(people);
		
	}

}
