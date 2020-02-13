package ar.com.itba.ss.datasetgenerator.engine.imagegeneration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.itba.ss.datasetgenerator.model.ImageGrid;
import ar.com.itba.ss.datasetgenerator.model.imagegeneration.ImageResource;

import java.awt.Color;
import java.util.List;

public class ImageGridManager {
	
	private static Logger log = LoggerFactory.getLogger(ImageGridManager.class);
	
	public static ImageGrid generateGrid(ImageResource background, List<ImageResource> people) {
		
		int width = background.getWidth();
		int height = background.getHeight();
		
		Integer[][] colorCanvas = new Integer[width][height];
		Integer[][] irCanvas = new Integer[width][height];
		
		Integer[][] backgroundColorCanvas = background.getColorCanvas();
		Integer[][] backgroundIrCanvas = background.getIRCanvas();
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				
				colorCanvas[x][y] = backgroundColorCanvas[x][y];
				irCanvas[x][y] = backgroundIrCanvas[x][y];
				
			}
		}
		
		return new ImageGrid(background, people, colorCanvas, irCanvas);
		
	}
	
}
