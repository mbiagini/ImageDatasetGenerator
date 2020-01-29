package ar.com.itba.ss.datasetgenerator.configuration;

public class Config {
	
	public static Integer loggingMaxLength = 1000;
	
	public static Long randomSeed = 1L;
	
	public static String  imagesBasepath = "src/main/resources/data/images/";
	public static String  imagesExtension = "jpg";
	public static Integer imagesWidth = 640;
	public static Integer imagesHeight = 512;
	
	public static String peopleBasepath = "src/main/resources/data/people/";
	public static String peopleRegex = "person_([0-9]{1,3}).bmp";
	
	public static String backgroundsBasepath = "src/main/resources/data/backgrounds/";
	public static String backgroundsRegex = "background_([0-9]{1,3}).jpg";
	
	public static String  infraredBasepath = "src/main/resources/data/infrared/";
	public static Integer infraredBackground = 0;
	
	public static Integer maxOccupationTests = 1000000;
			
	public static Double  cellIndexGridSide = 640.0;
	public static Integer cellIndexGridSize = 8;
	public static Double  cellIndexInteractionRadius = (cellIndexGridSide / cellIndexGridSize) / 4.0;
	
	public static Double mass = 80.0;
	public static Double kn = Math.pow(10, 3);
	public static Double gamma = 0.02;
	public static Double dt = 0.00089;
		
	public static String  simulationDataBasepath = "src/main/resources/data/simulation/";
	public static Integer simulationParticles = 281;
	public static Double  simulationStartVelocity = 0.0;
	public static Integer simulationEphocs = 500000;
	public static Integer simulationSaveRate = 500;
	
	public static Double fixedAccx = 0.0;
	public static Double fixedAccy = 0.0;
	
	// for debugging
	public static Long debugParticleId = 58L;
	public static Long debugParticleId2 = 30L;
	
}
