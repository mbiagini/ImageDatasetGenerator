package ar.com.itba.ss.datasetgenerator.engine.utils;

import java.awt.Color;

import java.awt.image.BufferedImage;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.itba.ss.datasetgenerator.configuration.Config;
import ar.com.itba.ss.datasetgenerator.engine.imagegeneration.ImageGridManager;
import ar.com.itba.ss.datasetgenerator.model.ImageGrid;
import ar.com.itba.ss.datasetgenerator.model.Point;
import ar.com.itba.ss.datasetgenerator.model.SSImage;
import ar.com.itba.ss.datasetgenerator.model.cellindexmethod.Particle;
import ar.com.itba.ss.datasetgenerator.model.imagegeneration.ImageGenerationTrunk;
import ar.com.itba.ss.datasetgenerator.model.imagegeneration.ImageResource;

import static java.lang.String.format;

public class ImageUtils {

	private static Logger log = LoggerFactory.getLogger(ImageUtils.class);
	
	public static void saveImage(ImageGenerationTrunk trunk, List<Particle> particles, int instant) {
		
		ImageGrid initialState = trunk.getInitialState();
		
		// RGB Image
		BufferedImage rgbBi = new BufferedImage(initialState.getWidth(), initialState.getHeight(), BufferedImage.TYPE_INT_RGB);
		SSImage rgbImg = new SSImage()
				.bufferedImage(rgbBi)
				.basepath(Config.imagesBasepath)
				.filename(format("image%07d.jpg", instant))
				.extension(Config.datasetExtension);
		
		// IR Image
		BufferedImage irBi = new BufferedImage(initialState.getWidth(), initialState.getHeight(), BufferedImage.TYPE_INT_RGB);
		SSImage irImg = new SSImage()
				.bufferedImage(irBi)
				.basepath(Config.infraredBasepath)
				.filename(format("infrared%07d.jpg", instant))
				.extension(Config.datasetExtension);
		
		ImageGrid imgGrid = ImageGridManager.generateGrid(trunk.getBackground(), trunk.getPeople());
		
		Integer[][] imgGridColorCanvas = imgGrid.getColorCanvas();
		Integer[][] imgGridIRCanvas = imgGrid.getIrCanvas();
		
		for (Particle p : particles) {
			
			ImageResource person = trunk.getParticleToPersonMap().get(p.getId());
			
			if (person == null) {
				log.error(format("No person found for particle %d.", p.getId()));
				throw new RuntimeException("Particle with no person mapping error.");
			}
			
			Integer[][] personColorCanvas = person.getColorCanvas();
			Integer[][] personIRCanvas = person.getIRCanvas();
			
			Point start = new Point((int) (p.getX() - person.getWidth() / 2.0), (int) (p.getY() - person.getHeight() / 2.0));
									
			for (int x = 0; x < person.getWidth(); x++) {
				for (int y = 0; y < person.getHeight(); y++) {
					
					Integer color = personColorCanvas[x][y];
					Integer ir = personIRCanvas[x][y];
					
					if (color != Color.WHITE.getRGB()) {
						
						if (! (x + start.getX() >= imgGrid.getWidth()) && ! (y + start.getY() >= imgGrid.getHeight())
								&& ! (x + start.getX() < 0) && ! (y + start.getY() < 0)) {
							
							imgGridColorCanvas[x + start.getX()][y + start.getY()] = color;
							imgGridIRCanvas[x + start.getX()][y + start.getY()] = ir;
							
						}
						
					} 
					
				}
			}
			
		}
		
		Integer[][] initialColorCanvas = initialState.getColorCanvas();
		Integer[][] initialIrCanvas = initialState.getIrCanvas();
				
		for (int x = 0; x < rgbImg.getWidth(); x++) {
			for (int y = 0; y < rgbImg.getHeight(); y++) {
				
				if (imgGridColorCanvas[x][y] == Color.WHITE.getRGB()) {
					
					rgbBi.setRGB(x, y, initialColorCanvas[x][y]);
					irBi.setRGB(x, y, initialIrCanvas[x][y]);
					
				} else {
					
					rgbBi.setRGB(x, y, imgGridColorCanvas[x][y]);
					irBi.setRGB(x, y, imgGridIRCanvas[x][y]);
					
				}
				
			}
		}
		
		FileUtils.saveImage(rgbImg);
		FileUtils.saveImage(irImg);
		
	}

}
