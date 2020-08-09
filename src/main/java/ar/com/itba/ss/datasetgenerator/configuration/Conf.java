package ar.com.itba.ss.datasetgenerator.configuration;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import ar.com.itba.ss.datasetgenerator.model.PixelMultiplier;

/**
 * This class contains soft configuration that can be modified via external file. All fields are
 * initialized with default values that will be used if they are not overridden by external
 * configuration.
 */
public class Conf {
	
	private long randomSeed = 1L;
	private Random random = new Random(randomSeed);
	
	private int width = 1024;
	private int height = 768;
	
	private double cellIndexGridSide = 1024.0;
	private int cellIndexGridSize = 8;
	
	private double mass = 80.0;
	private double kn = Math.pow(10, 7);
	private double gamma = 5000.0;
	private double dt = 0.01;
	private double startVelocity = 10.0;
	
	private double driveSpeed = 20.0;
	private double tau = 0.05;
	private double socialA = 2000.0;
	private double socialB = 8.0;

	private int epochs = 2500;
	private int saveRate = 500;
	
	private boolean randomAmountOfParticles = true;
	private int minParticleCount = 100;
	private int maxParticleCount = 700;
	private int fixedParticleCount = 500;
	
	private int particleInsertionRetries = 100000;
	
	private boolean randomUniformIrBackground = true;
	private int minBackgroundValue = 100;
	private int maxBackgroundValue = 140;
	
	private List<Double> irMultipliers = new LinkedList<>(Arrays.asList(0.6, 0.8, 1.0, 1.2, 1.4));
	private List<PixelMultiplier> rgbMultipliers = new LinkedList<>(Arrays.asList(
			new PixelMultiplier(1.0, 1.0, 1.0),
			new PixelMultiplier(0.8, 0.8, 0.8),
			new PixelMultiplier(0.6, 0.6, 0.6),
			new PixelMultiplier(0.4, 0.4, 0.4),
			new PixelMultiplier(0.2, 0.2, 0.2),
			new PixelMultiplier(0.1, 0.1, 0.1),
			new PixelMultiplier(0.8, 0.1, 0.1),
			new PixelMultiplier(0.1, 0.8, 0.1),
			new PixelMultiplier(0.1, 0.1, 0.8)));
	
	private boolean cleanImagesDirectories = true;
	
	private int simulationStartNumber = 1;
	private int numberOfSimulations = 100;
	
	// --------------------------------------------------------------------------------------------
	// getters and setters
	// --------------------------------------------------------------------------------------------
	
	public long getRandomSeed() {
		return randomSeed;
	}
	
	public void setRandomSeed(long randomSeed) {
		this.randomSeed = randomSeed;
		this.random = new Random(randomSeed);
	}
	
	public Random getRandom() {
		return random;
	}
	
	public void setRandom(Random random) {
		this.random = random;
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
	
	public boolean isRandomAmountOfParticles() {
		return randomAmountOfParticles;
	}
	
	public void setRandomAmountOfParticles(boolean randomAmountOfParticles) {
		this.randomAmountOfParticles = randomAmountOfParticles;
	}
	
	public int getMinParticleCount() {
		return minParticleCount;
	}
	
	public void setMinParticleCount(int minParticleCount) {
		this.minParticleCount = minParticleCount;
	}
	
	public int getMaxParticleCount() {
		return maxParticleCount;
	}
	
	public void setMaxParticleCount(int maxParticleCount) {
		this.maxParticleCount = maxParticleCount;
	}
	
	public int getFixedParticleCount() {
		return fixedParticleCount;
	}
	
	public void setFixedParticleCount(int fixedParticleCount) {
		this.fixedParticleCount = fixedParticleCount;
	}
	
	public int getParticleInsertionRetries() {
		return particleInsertionRetries;
	}
	
	public void setParticleInsertionRetries(int particleInsertionRetries) {
		this.particleInsertionRetries = particleInsertionRetries;
	}
	
	public boolean isRandomUniformIrBackground() {
		return randomUniformIrBackground;
	}

	public void setRandomUniformIrBackground(boolean randomUniformIrBackground) {
		this.randomUniformIrBackground = randomUniformIrBackground;
	}

	public int getMinBackgroundValue() {
		return minBackgroundValue;
	}

	public void setMinBackgroundValue(int minBackgroundValue) {
		this.minBackgroundValue = minBackgroundValue;
	}

	public int getMaxBackgroundValue() {
		return maxBackgroundValue;
	}

	public void setMaxBackgroundValue(int maxBackgroundValue) {
		this.maxBackgroundValue = maxBackgroundValue;
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
	
	public int getNumberOfSimulations() {
		return numberOfSimulations;
	}
	
	public void setNumberOfSimulations(int numberOfSimulations) {
		this.numberOfSimulations = numberOfSimulations;
	}
	
}
