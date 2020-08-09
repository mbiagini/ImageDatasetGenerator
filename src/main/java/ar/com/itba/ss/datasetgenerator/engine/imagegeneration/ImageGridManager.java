package ar.com.itba.ss.datasetgenerator.engine.imagegeneration;

import ar.com.itba.ss.datasetgenerator.configuration.Conf;
import ar.com.itba.ss.datasetgenerator.engine.utils.ImageUtils;
import ar.com.itba.ss.datasetgenerator.engine.utils.RandomUtils;
import ar.com.itba.ss.datasetgenerator.model.ImageGrid;
import ar.com.itba.ss.datasetgenerator.model.imagegeneration.ImageResource;

import java.util.List;

public class ImageGridManager {
		
	public static ImageGrid generateGrid(Conf conf, ImageResource background, List<ImageResource> people) {
		
		int width = background.getWidth();
		int height = background.getHeight();
		
		Integer[][] colorCanvas = new Integer[width][height];
		Integer[][] irCanvas = new Integer[width][height];
		
		Integer[][] backgroundColorCanvas = background.getColorCanvas();
		Integer[][] backgroundIrCanvas = background.getIRCanvas();
		
		double irMultiplier = conf.getIrMultipliers().get(
				RandomUtils.randomIntBetween(conf.getRandom(), 0, conf.getIrMultipliers().size()));
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				
				colorCanvas[x][y] = backgroundColorCanvas[x][y];
				irCanvas[x][y] = ImageUtils.bright(backgroundIrCanvas[x][y], irMultiplier);
				
			}
		}
		
		return new ImageGrid(background, people, colorCanvas, irCanvas);
		
	}
	
}
