package ar.com.itba.ss.datasetgenerator.model;

public class PixelMultiplier {
	
	private double redScale;
	private double blueScale;
	private double greenScale;
	
	public PixelMultiplier(double scale) {
		this(scale, scale, scale);
	}
	
	public PixelMultiplier(double redScale, double blueScale, double greenScale) {
		this.redScale = redScale;
		this.blueScale = blueScale;
		this.greenScale = greenScale;
	}

	public double getRedScale() {
		return redScale;
	}

	public void setRedScale(double redScale) {
		this.redScale = redScale;
	}

	public double getBlueScale() {
		return blueScale;
	}

	public void setBlueScale(double blueScale) {
		this.blueScale = blueScale;
	}

	public double getGreenScale() {
		return greenScale;
	}

	public void setGreenScale(double greenScale) {
		this.greenScale = greenScale;
	} 

}
