package ar.com.itba.ss.datasetgenerator.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.itba.ss.datasetgenerator.model.ImageGrid;
import ar.com.itba.ss.datasetgenerator.model.Person;
import ar.com.itba.ss.datasetgenerator.model.Point;
import ar.com.itba.ss.datasetgenerator.model.SSImage;
import ar.com.itba.ss.datasetgenerator.configuration.Config;
import ar.com.itba.ss.datasetgenerator.engine.utils.RandomUtils;

import static java.lang.String.format;

import java.awt.Color;
import java.util.List;

public class ImageGridManager {
	
	private static Logger log = LoggerFactory.getLogger(ImageGridManager.class);
	
	public static ImageGrid generateGrid(SSImage img, List<Person> people, Integer temperature) {
		
		log.info(format("Generating ImageGrid with default temperature value: %d.", temperature));
		
		Integer width = img.getWidth();
		Integer height = img.getHeight();
		
		Integer[][] colorCanvas = new Integer[width][height];
		Integer[][] irCanvas = new Integer[width][height];
				
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				
				colorCanvas[x][y] = Color.WHITE.getRGB();
				irCanvas[x][y] = temperature;
				
			}
		}
		
		return new ImageGrid(img, people, colorCanvas, irCanvas);
		
	}
	
	public static void initializeGrid(ImageGrid grid, Integer[][] irCanvas) {
		
		log.info("Initializing grid with IR background.");
		
		Integer[][] colorCanvas = grid.getColorCanvas();
		
		for (int x = 0; x < grid.getWidth(); x++) {
			for (int y = 0; y < grid.getHeight(); y++) {
				
				colorCanvas[x][y] = Color.WHITE.getRGB();
				
			}
		}
		
		grid.setColorCanvas(colorCanvas);
		grid.setIrCanvas(irCanvas);
		
	}
	
	public static Point addPerson(ImageGrid grid, Person person) {
				
		Point start = null;
		boolean canOccupy = false;
		int counter = 0;
		
		do {
			
			start = RandomUtils.randomPoint(grid.getWidth() - person.getWidth(), grid.getHeight() - person.getHeight());
			if (!isGridOccupied(grid, person, start)) {
				canOccupy = true;
			}
			counter ++;
			
		} while (counter < Config.maxOccupationTests && !canOccupy);
		
		if (canOccupy) {
			occupyGrid(grid, person, start);
			return start;
		}
		
		if (counter >= Config.maxOccupationTests) {
			log.info("Max occupation tests made. Person could not be added");
		}
		
		return null;
				
	}
	
	public static boolean isGridOccupied(ImageGrid grid, Person person, Point start) {
		
		if (!validDimensions(grid, person, start)) {
			log.error(format("Img: %s, Start: %s.", person.toString(), start.toString()));
			throw new RuntimeException("Person out of bounds");
		}
		
		for (int x = 0; x < person.getWidth(); x++) {
			for (int y = 0; y < person.getHeight(); y++) {
				if (person.getImg().getBufferedImage().getRGB(x, y) != Color.WHITE.getRGB()
						&& grid.getColorCanvas()[x + start.getX()][y + start.getY()] != Color.WHITE.getRGB()) {
					return true;
				}
			}
		}
		
		return false;
		
	}
	
	public static void occupyGrid(ImageGrid grid, Person person, Point start) {
		
		if (!validDimensions(grid, person, start)) {
			log.error(format("Img: %s, Start: %s.", person.toString(), start.toString()));
			throw new RuntimeException("Person out of bounds");
		}
		
		Integer[][] colorCanvas = grid.getColorCanvas();
		Integer[][] irCanvas = grid.getIrCanvas();
		
		Integer[][] personColorCanvas = person.getColorCanvas();
		Integer[][] personIRCanvas = person.getIRCanvas();
		
		for (int x = 0; x < person.getWidth(); x++) {
			for (int y = 0; y < person.getHeight(); y++) {
				
				int pixel = personColorCanvas[x][y];
								
				if (pixel != Color.WHITE.getRGB()) {
					
					if (colorCanvas[x + start.getX()][y + start.getY()] != Color.WHITE.getRGB()) {
						log.error("Cannot occupy an already occupied position.");
						throw new RuntimeException("Inconsistency between isOccupiedGrid and occupyGrid");
					}
					
					colorCanvas[x + start.getX()][y + start.getY()] = pixel;
					irCanvas[x + start.getX()][y + start.getY()] = personIRCanvas[x][y];
					
				}

			}
		}
		
		grid.setColorCanvas(colorCanvas);
		grid.setIrCanvas(irCanvas);
		
		grid.getPeoplePositions().get(person.getId()).add(start);
						
	}
	
	private static Boolean validDimensions(ImageGrid grid, Person person, Point start) {
		
		int xStart = start.getX();
		int yStart = start.getY();
		
		int xEnd = xStart + person.getWidth();
		int yEnd = yStart + person.getHeight();
		
		if (xStart < 0 || xStart > grid.getWidth() || yStart < 0 || yStart > grid.getHeight()
				|| xEnd < 0 || xEnd > grid.getWidth() || yEnd < 0 || yEnd > grid.getHeight()) {
			
			return false;
		
		}
		
		return true;
		
	}
	
}
