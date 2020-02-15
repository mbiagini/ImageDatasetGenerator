package ar.com.itba.ss.datasetgenerator.engine.imagegeneration;

import ar.com.itba.ss.datasetgenerator.configuration.Config;
import ar.com.itba.ss.datasetgenerator.model.ImageGrid;
import ar.com.itba.ss.datasetgenerator.model.imagegeneration.ImageResource;

import java.awt.Color;
import java.util.List;

public class ImageGridManager {
		
	public static ImageGrid generateGrid(ImageResource background, List<ImageResource> people) {
		
		int width = background.getWidth();
		int height = background.getHeight();
		
		Integer[][] colorCanvas = new Integer[width][height];
		Integer[][] irCanvas = new Integer[width][height];
		
		Integer[][] backgroundColorCanvas = background.getColorCanvas();
		Integer[][] backgroundIrCanvas = background.getIRCanvas();
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				
				colorCanvas[x][y] = bright(backgroundColorCanvas[x][y], Config.backgroundRgbMultiplier);
				irCanvas[x][y] = bright(backgroundIrCanvas[x][y], Config.backgroundIrMultiplier);
				
			}
		}
		
		return new ImageGrid(background, people, colorCanvas, irCanvas);
		
	}
	
	private static int bright(int pixel, double multiplier) {
		
		Color color = new Color(pixel);
		
		//System.out.println("\n\n\n*****************************************************");
		//System.out.println(String.format("bright event. red: %d, green: %d, blue: %d.", color.getRed(), color.getGreen(), color.getBlue()));
		
		int red = multiplyValue(color.getRed(), multiplier);
		int green = multiplyValue(color.getGreen(), multiplier);
		int blue = multiplyValue(color.getBlue(), multiplier);
		
		//System.out.println(String.format("bright finished. red: %d, green: %d, blue: %d.", red, green, blue));
		
		//throw new RuntimeException("STOP");
		
		Color newColor = new Color(red, green, blue);
		
		return newColor.getRGB();
		
	}
	
	private static int multiplyValue(int value, double multiplier) {
		int response = value;
		if (value * multiplier > 255.0) {
			response = 255;
		} else {
			response = new Double(value * multiplier).intValue();
		}
		return response;
	}
	
}
