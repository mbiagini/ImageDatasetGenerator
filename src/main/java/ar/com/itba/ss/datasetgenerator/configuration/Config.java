package ar.com.itba.ss.datasetgenerator.configuration;

import ar.com.itba.ss.datasetgenerator.model.simulation.ExecutionType;

public class Config {
	
	public static Integer loggingMaxLength = 1000;
	
	public static Long randomSeed = 1L;
	
	public static ExecutionType executionType = ExecutionType.GENERATE_IMAGES;
	
	public static String imagesBasepath = "src/main/resources/data/images";
	public static String infraredBasepath = "src/main/resources/data/infrared";
	public static String datasetExtension = "jpg";
	
	public static Integer imagesWidth = 640;
	public static Integer imagesHeight = 512;
	
	public static String peopleRgbBasepath = "src/main/resources/data/people/rgb";
	public static String peopleIrBasepath = "src/main/resources/data/people/ir";
	public static String peopleRgbRegex = "person_([0-9]{1,3})_rgb.bmp";
	
	public static String backgroundsRgbBasepath = "src/main/resources/data/backgrounds/rgb";
	public static String backgroundsIrBasepath = "src/main/resources/data/backgrounds/ir";
	public static String backgroundsRgbRegex = "background_([0-9]{1,3})_rgb.jpg";
		
	public static Integer maxOccupationTests = 1000000;
	
	public static Double  cellIndexGridSide = 640.0;
	public static Integer cellIndexGridSize = 8;
	public static Double  cellIndexInteractionRadius = (cellIndexGridSide / cellIndexGridSize) / 4.0;
	
	public static Double mass = 80.0;
	public static Double kn = Math.pow(10, 7);
	public static Double gamma = 5000.0;
	public static Double dt = 0.00028;
	
	public static String  simulationDataBasepath = "src/main/resources/data/simulation";
	public static Integer simulationParticles = 100;
	public static Double  simulationStartVelocity = 0.0;
	public static Integer simulationEphocs = 200000;
	public static Integer simulationSaveRate = 200;
	public static Integer simulationWidth = 640;
	public static Integer simulationHeight = 512;
	
	public static Integer generationCount = 1000;
	
	// forces
	public static Double driveSpeed = 10.0;
	public static Double tau = 0.05;
	public static Double socialA = 40000.0;
	public static Double socialB = 8.0;
	
	public static Double drivePointx = (double)simulationWidth / 2;
	public static Double drivePointy = (double)simulationHeight;
	
	// metrics
	public static Double densityXStart = drivePointx - 100;
	public static Double densityXEnd = drivePointx  + 100;
	public static Double densityYStart = drivePointy - 200;
	public static Double densityYEnd = drivePointy;
		
}
