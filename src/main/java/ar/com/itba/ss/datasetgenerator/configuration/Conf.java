package ar.com.itba.ss.datasetgenerator.configuration;

import java.util.List;
import java.util.Random;

import ar.com.itba.ss.datasetgenerator.model.config.CameraHeight;
import ar.com.itba.ss.datasetgenerator.model.config.PixelMultiplier;
import ar.com.itba.ss.datasetgenerator.model.config.UniformBackground;
import ar.com.itba.ss.datasetgenerator.model.imagegeneration.ImageResource;

public class Conf {
	
	// --------------------------------------------------------------------------------------------
	// Properties loaded from configuration file.
	// --------------------------------------------------------------------------------------------
	
	private long randomSeed;
	
	private int width;
	private int height;
	
	private double cellIndexGridSide;
	private int cellIndexGridSize;
	
	private double mass;
	private double kn;
	private double gamma;
	private double dt;
	private double startVelocity;
	
	private double driveSpeed;
	private double tau;
	private double socialA;
	private double socialB;

	private int epochs;
	private int saveRate;
	
	private int particleInsertionRetries;
	
	private List<CameraHeight> cameraHeights;
	private List<UniformBackground> uniformBackgrounds;
	
	private List<Double> irMultipliers;
	private List<PixelMultiplier> rgbMultipliers;
	
	private boolean cleanImagesDirectories;
	
	private int simulationStartNumber;
	private int simulationIterations;
	
	// --------------------------------------------------------------------------------------------
	// Properties configured within the execution.
	// --------------------------------------------------------------------------------------------
	
	private List<ImageResource> backgrounds;
	
	private Random random = new Random(randomSeed);
	private CameraHeight cameraHeight;
	private ImageResource background;
	private List<ImageResource> people;
	private PixelMultiplier rgbMultiplier;
	private double irMultiplier;
	
	// --------------------------------------------------------------------------------------------
	// getters and setters.
	// --------------------------------------------------------------------------------------------
	
	public long getRandomSeed() {
		return randomSeed;
	}
	
	public void setRandomSeed(long randomSeed) {
		this.randomSeed = randomSeed;
		this.random = new Random(randomSeed);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public double getCellIndexGridSide() {
		return cellIndexGridSide;
	}

	public void setCellIndexGridSide(double cellIndexGridSide) {
		this.cellIndexGridSide = cellIndexGridSide;
	}

	public int getCellIndexGridSize() {
		return cellIndexGridSize;
	}

	public void setCellIndexGridSize(int cellIndexGridSize) {
		this.cellIndexGridSize = cellIndexGridSize;
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public double getKn() {
		return kn;
	}

	public void setKn(double kn) {
		this.kn = kn;
	}

	public double getGamma() {
		return gamma;
	}

	public void setGamma(double gamma) {
		this.gamma = gamma;
	}

	public double getDt() {
		return dt;
	}

	public void setDt(double dt) {
		this.dt = dt;
	}

	public double getStartVelocity() {
		return startVelocity;
	}

	public void setStartVelocity(double startVelocity) {
		this.startVelocity = startVelocity;
	}

	public double getDriveSpeed() {
		return driveSpeed;
	}

	public void setDriveSpeed(double driveSpeed) {
		this.driveSpeed = driveSpeed;
	}

	public double getTau() {
		return tau;
	}

	public void setTau(double tau) {
		this.tau = tau;
	}

	public double getSocialA() {
		return socialA;
	}

	public void setSocialA(double socialA) {
		this.socialA = socialA;
	}

	public double getSocialB() {
		return socialB;
	}

	public void setSocialB(double socialB) {
		this.socialB = socialB;
	}

	public int getEpochs() {
		return epochs;
	}

	public void setEpochs(int epochs) {
		this.epochs = epochs;
	}

	public int getSaveRate() {
		return saveRate;
	}

	public void setSaveRate(int saveRate) {
		this.saveRate = saveRate;
	}

	public int getParticleInsertionRetries() {
		return particleInsertionRetries;
	}

	public void setParticleInsertionRetries(int particleInsertionRetries) {
		this.particleInsertionRetries = particleInsertionRetries;
	}

	public List<CameraHeight> getCameraHeights() {
		return cameraHeights;
	}

	public void setCameraHeights(List<CameraHeight> cameraHeights) {
		this.cameraHeights = cameraHeights;
	}

	public List<UniformBackground> getUniformBackgrounds() {
		return uniformBackgrounds;
	}

	public void setUniformBackgrounds(List<UniformBackground> uniformBackgrounds) {
		this.uniformBackgrounds = uniformBackgrounds;
	}

	public List<Double> getIrMultipliers() {
		return irMultipliers;
	}

	public void setIrMultipliers(List<Double> irMultipliers) {
		this.irMultipliers = irMultipliers;
	}

	public List<PixelMultiplier> getRgbMultipliers() {
		return rgbMultipliers;
	}

	public void setRgbMultipliers(List<PixelMultiplier> rgbMultipliers) {
		this.rgbMultipliers = rgbMultipliers;
	}

	public boolean getCleanImagesDirectories() {
		return cleanImagesDirectories;
	}

	public void setCleanImagesDirectories(boolean cleanImagesDirectories) {
		this.cleanImagesDirectories = cleanImagesDirectories;
	}

	public int getSimulationStartNumber() {
		return simulationStartNumber;
	}

	public void setSimulationStartNumber(int simulationStartNumber) {
		this.simulationStartNumber = simulationStartNumber;
	}
	
	public int getSimulationIterations() {
		return simulationIterations;
	}
	
	public void setSimulationIterations(int simulationIterations) {
		this.simulationIterations = simulationIterations;
	}
	
	// --------------------------------------------------------------------------------------------		

	public List<ImageResource> getBackgrounds() {
		return backgrounds;
	}
	
	public void setBackgrounds(List<ImageResource> backgrounds) {
		this.backgrounds = backgrounds;
	}
	
	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public CameraHeight getCameraHeight() {
		return cameraHeight;
	}

	public void setCameraHeight(CameraHeight cameraHeight) {
		this.cameraHeight = cameraHeight;
	}

	public ImageResource getBackground() {
		return background;
	}

	public void setBackground(ImageResource background) {
		this.background = background;
	}

	public List<ImageResource> getPeople() {
		return people;
	}

	public void setPeople(List<ImageResource> people) {
		this.people = people;
	}

	public PixelMultiplier getRgbMultiplier() {
		return rgbMultiplier;
	}

	public void setRgbMultiplier(PixelMultiplier rgbMultiplier) {
		this.rgbMultiplier = rgbMultiplier;
	}

	public double getIrMultiplier() {
		return irMultiplier;
	}

	public void setIrMultiplier(double irMultiplier) {
		this.irMultiplier = irMultiplier;
	}
	
}
