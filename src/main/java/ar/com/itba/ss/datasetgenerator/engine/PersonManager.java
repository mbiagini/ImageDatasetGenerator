package ar.com.itba.ss.datasetgenerator.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.itba.ss.datasetgenerator.engine.utils.RandomUtils;
import ar.com.itba.ss.datasetgenerator.model.Person;
import ar.com.itba.ss.datasetgenerator.model.SSImage;

import static java.lang.String.format;

import java.awt.image.BufferedImage;

public class PersonManager {
	
	private static Logger log = LoggerFactory.getLogger(PersonManager.class);

	public static Person initializePerson(SSImage personImg) {
		
		log.debug(format("Initializing person from filename: %s.", personImg.getFilename()));
		String[] nameInfo = extractFilenameInfo(personImg.getFilename());

		return new Person(
				Long.parseLong(nameInfo[1]), 
				extractColorCanvas(personImg), 
				generateRandomIRCanvas(personImg.getWidth(), personImg.getHeight()), 
				personImg);
				
	}
		
	private static Integer[][] extractColorCanvas(SSImage img) {
		
		Integer[][] colorCanvas = new Integer[img.getWidth()][img.getHeight()];
		BufferedImage bi = img.getBufferedImage();
		
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				
				colorCanvas[x][y] = bi.getRGB(x, y);
				
			}
		}
		
		return colorCanvas;
		
	}
	
	private static Integer[][] generateRandomIRCanvas(Integer width, Integer height) {
		
		Integer[][] irCanvas = new Integer[width][height];
		
		int randomTemperature = RandomUtils.randomIntBetween(0, 255);
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				
				irCanvas[x][y] = randomTemperature;
				
			}
		}
		
		return irCanvas;
		
	}

	private static String[] extractFilenameInfo(String filename) {

		if (filename == null || !filename.contains(".") || !filename.contains("_")) {
			log.error("Could not extract filename info from " + filename);
			throw new RuntimeException("Runtime Error");
		}
		
		return filename.split("\\.")[0].split("_");
		
	}

}
