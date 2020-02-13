package ar.com.itba.ss.datasetgenerator.engine.imagegeneration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.itba.ss.datasetgenerator.model.SSImage;
import ar.com.itba.ss.datasetgenerator.model.imagegeneration.ImageResource;

import static java.lang.String.format;

import java.awt.image.BufferedImage;

public class ImageResourceManager {
	
	private static Logger log = LoggerFactory.getLogger(ImageResourceManager.class);

	public static ImageResource initialize(SSImage rgbImg, SSImage irImg) {
		
		log.debug(format("Initializing resource from rgb: %s, ir: %s.", rgbImg.getFilename(), irImg.getFilename()));
		String[] nameInfo = extractFilenameInfo(rgbImg.getFilename());
		
		return new ImageResource(
				Long.parseLong(nameInfo[1]),
				rgbImg.getWidth(),
				rgbImg.getHeight(),
				extractCanvas(rgbImg),
				extractCanvas(irImg));
				
	}
		
	private static Integer[][] extractCanvas(SSImage img) {
		
		Integer[][] canvas = new Integer[img.getWidth()][img.getHeight()];
		BufferedImage bi = img.getBufferedImage();
		
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				
				canvas[x][y] = bi.getRGB(x, y);
				
			}
		}
		
		return canvas;
		
	}

	private static String[] extractFilenameInfo(String filename) {

		if (filename == null || !filename.contains(".") || !filename.contains("_")) {
			log.error("Could not extract filename info from " + filename);
			throw new RuntimeException("Runtime Error");
		}
		
		return filename.split("\\.")[0].split("_");
		
	}

}
