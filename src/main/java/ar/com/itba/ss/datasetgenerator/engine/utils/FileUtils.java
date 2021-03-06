package ar.com.itba.ss.datasetgenerator.engine.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.itba.ss.datasetgenerator.model.IRMatrix;
import ar.com.itba.ss.datasetgenerator.model.SSImage;

import static java.lang.String.format;

public class FileUtils {
	
	private static Logger log = LoggerFactory.getLogger(FileUtils.class);
	
	public static String getPath(String... args) {
		Path path = null;
		for (String arg : args) {
			if (path == null) {
				path = Paths.get(arg);
			} else {
				path = Paths.get(path.toString(), arg);
			}
		}
		return path.toString();
	}
	
	/**
	 * Saves a string to a given file.
	 */
	public static void saveStringToFile(File file, String data) {
		try (PrintWriter pw = new PrintWriter(file)) {
			pw.print(data);
			pw.close();
		} catch (FileNotFoundException exception) {
			throw new RuntimeException("Error while writing to file " + file.getName());
		}
	}
	
	/**
	 * Reads the given file as a string.
	 */
	public static String readStringFromFile(File file) {
		try {
			InputStream is = new FileInputStream(file);
			BufferedReader buf = new BufferedReader(new InputStreamReader(is));
			String line = buf.readLine(); 
			StringBuilder sb = new StringBuilder();
			while(line != null) { 
				sb.append(line).append("\n");
				line = buf.readLine();
			} 
			buf.close();
			return sb.toString();
		} catch (Exception exception) {
			throw new RuntimeException("Exception while reading from file " + file.getName());
		}
	}
	
	/**
	 * Saves the given image to a file.
	 */
	public static void saveImage(SSImage image) {
		File file = new File(getPath(image.getBasepath(), image.getFilename()));
		try {
			ImageIO.write(image.getBufferedImage(), image.getExtension(), file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads image from file.
	 */
	public static BufferedImage readBufferedImageFromFile(File file) {
		if (file == null || !file.exists()) {
			log.error("Cannot read image from inexistent file.");
			throw new RuntimeException("Runtime Error");
		}	
		try {
			return ImageIO.read(file);
		} catch (IOException exception) {
			log.error("IOException while reading bufferedImage from " + file.getName());
			throw new RuntimeException("Runtime Error");
		}
	}
	
	public static File[] readAllFilesMatching(String basepath, String regex) {	
		
		File directory = new File(basepath);

		if (!directory.isDirectory()) {
			log.error(format("%s is not a directory.", basepath));
			throw new RuntimeException("Not a directory error");
		}
		
		File[] files = directory.listFiles(new FilenameFilter() {
			public boolean accept(File directory, String filename) {
				
				log.info(format("Checking filename: %s agains regex: %s.", filename, regex));
				return filename.matches(regex);
				
			}
		});
		
		log.info(format("%d files after filter.", files.length));
		
		return files;
		
	}
	
	/**
	 * Deletes all files in the given path recursively, leaving only empty directories.
	 */
	public static void deleteFiles(String path) {
		File file = new File(path);
		if (file.isDirectory())
	        for (File f : file.listFiles()) {
	        	deleteFiles(f.getAbsolutePath());
	        }
	    else {
	    	file.delete();
	    }		
	}
	
	public static List<SSImage> readAllImages(String basepath, String regex) {
		
		log.info(format("Reading all images from %s matching regex ", basepath, regex));
		
		List<SSImage> imageList = new ArrayList<>();
		
		File[] imageFiles = readAllFilesMatching(basepath, regex);
		
		for (File file : imageFiles) {
			imageList.add(fileToSSImage(basepath, file));
		}
		
		return imageList;
		
	}
	
	public static SSImage readImage(String basepath, String filename) {
		
		File file = new File(format("%s/%s", basepath, filename));
		
		return fileToSSImage(basepath, file);
		
	}
	
	public static List<IRMatrix> readAllMatrices(String basepath, String regex) {
		
		List<IRMatrix> matrixList = new ArrayList<>();
		
		File[] matrixFiles = readAllFilesMatching(basepath, regex);
		
		for (File file : matrixFiles) {
			matrixList.add(fileToIRMatrix(file));
		}
		
		return matrixList;
		
	}
	
	private static SSImage fileToSSImage(String basepath, File file) {
		
		SSImage image = new SSImage()
				.basepath(basepath)
				.filename(file.getName())
				.extension(extractExtension(file.getName()));
				
		BufferedImage buffi = readBufferedImageFromFile(file);
		
		image = image.bufferedImage(buffi);
		
		return image;
		
	}
	
	private static IRMatrix fileToIRMatrix(File file) {
		
		try {
						
			Integer id = extractId(file.getName());
			
			Scanner input = new Scanner(file);
			Scanner colReader = null;
			
			int height = 0;
			int width = 0;
			
			boolean first = true;
			
			while (input.hasNextLine()) {
				
				height++;
			    colReader = new Scanner(input.nextLine());
			    
			    while (first && colReader.hasNextInt()) {
			    	colReader.nextInt();
			    	width++;
			    }
			    
			    first = false;
			    
			}
			
			log.info(format("Width: %d, height: %d.", width, height));
			
			Integer[][] matrix = new Integer[width][height];

			colReader.close();
			input.close();
			
			input = new Scanner(file);
			
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (input.hasNextInt()) {
						matrix[x][y] = input.nextInt();
					}
				}
			}
			
			input.close();
			
			return new IRMatrix(id, width, height, matrix);
			
		} catch (Exception exception) {
			log.error("Exception while reading matrix.", exception);
			throw new RuntimeException("Error reading matrix");
		}
		
	}
	
	private static Integer extractId(String filename) {
		
		if (filename == null || !filename.contains(".") || !filename.contains("_")) {
			log.error(format("Cannot extract id from %s.", filename));
			throw new RuntimeException("Incorrect filename error");
		}
		
		return Integer.parseInt(filename.split("\\.")[0].split("_")[0]);
		
	}
	
	private static String extractExtension(String filename) {
		
		if (filename == null || !filename.contains(".")) {
			log.error("Could not extract extension from " + filename);
			throw new RuntimeException("Incorrect filename error");
		}
		
		String[] parts = filename.split("\\.");
		
		return parts[parts.length - 1];
		
	}

}
