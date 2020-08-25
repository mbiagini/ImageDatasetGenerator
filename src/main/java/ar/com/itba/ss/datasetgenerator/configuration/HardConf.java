package ar.com.itba.ss.datasetgenerator.configuration;

/**
 * This class contains hard configuration that cannot be modified.
 */
public class HardConf {
	
	private String rgbImagesDirectory = "src/main/resources/data/images/rgb";
	private String rgbImagesFormat = "rgb_image_%07d_%s.jpg";
	
	private String irImagesDirectory = "src/main/resources/data/images/ir";
	private String irImagesFormat = "ir_image_%07d_%s.jpg";
	
	private String rgbPeopleDirectory = "src/main/resources/data/people/rgb";
	private String irPeopleDirectory = "src/main/resources/data/people/ir";
	private String rgbPeopleRegex = "person_([0-9]+)_rgb.bmp";
	
	private String rgbBackgroundsDirectory = "src/main/resources/data/backgrounds/rgb";
	private String irBackgroundsDirectory = "src/main/resources/data/backgrounds/ir";
	private String rgbBackgroundsRegex = "background_([0-9]+)_rgb.bmp";
	
	private String particlesDirectory = "src/main/resources/data/simulation/particles";
	private String particlesFormat = "particles_%07d_%s.json";
	private String particlesToPersonMapFilename = "particles_to_people_map.json";
	
	private String configFilePath = "src/main/resources/configuration.json";
	
	// --------------------------------------------------------------------------------------------
	// getters
	// --------------------------------------------------------------------------------------------

	public String getRgbImagesDirectory() {
		return rgbImagesDirectory;
	}
	
	public String getRgbImagesFormat() {
		return rgbImagesFormat;
	}
	
	public String getIrImagesDirectory() {
		return irImagesDirectory;
	}
	
	public String getIrImagesFormat() {
		return irImagesFormat;
	}
	
	public String getRgbPeopleDirectory() {
		return rgbPeopleDirectory;
	}
	
	public String getIrPeopleDirectory() {
		return irPeopleDirectory;
	}
	
	public String getRgbPeopleRegex() {
		return rgbPeopleRegex;
	}
	
	public String getRgbBackgroundsDirectory() {
		return rgbBackgroundsDirectory;
	}
	
	public String getIrBackgroundsDirectory() {
		return irBackgroundsDirectory;
	}
	
	public String getRgbBackgroundsRegex() {
		return rgbBackgroundsRegex;
	}
	
	public String getParticlesDirectory() {
		return particlesDirectory;
	}
	
	public String getParticlesFormat() {
		return particlesFormat;
	}
	
	public String getParticlesToPersonMapFilename() {
		return particlesToPersonMapFilename;
	}
	
	public String getConfigFilePath() {
		return configFilePath;
	}

}
