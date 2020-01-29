package ar.com.itba.ss.datasetgenerator.engine.utils;

import java.awt.Color;

import java.awt.image.BufferedImage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.itba.ss.datasetgenerator.model.ImageGrid;
import ar.com.itba.ss.datasetgenerator.model.SSImage;

import static java.lang.String.format;

public class ImageUtils {

	private static Logger log = LoggerFactory.getLogger(ImageUtils.class);
	
	public static SSImage copyGridPixelsToImg(ImageGrid grid, SSImage img) {
		
		if (grid.getWidth() != img.getWidth() || grid.getHeight() != img.getHeight()) {
			log.error(format("Cannot paste grid to image when dimensions don't match. Grid: %s, img: %s.", grid.toString(), img.toString()));
			throw new RuntimeException("Runtime Error");
		}
		
		Integer[][] colorCanvas = grid.getColorCanvas();
		
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				
				// get pixel color from image to paste
				int rgb = colorCanvas[x][y];

				// paint pixel in the image if not white (255, 255, 255)
				if (!(new Color(rgb).equals(Color.WHITE))) {
					img.getBufferedImage().setRGB(x, y, rgb);
				}
				
			}
		}
		
		return img;
				
	}

	public static SSImage generateBackgroundImage(SSImage img, Color color) {

		BufferedImage bi = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {

				bi.setRGB(x, y, color.getRGB());

			}
		}

		img.setBufferedImage(bi);

		return img;

	}

}
