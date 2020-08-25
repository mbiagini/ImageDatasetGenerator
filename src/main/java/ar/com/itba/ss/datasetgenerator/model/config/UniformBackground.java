package ar.com.itba.ss.datasetgenerator.model.config;

public class UniformBackground {
	
	private int minPixelValue;
	private int maxPixelValue;
	
	public UniformBackground(int minPixelValue, int maxPixelValue) {
		this.minPixelValue = minPixelValue;
		this.maxPixelValue = maxPixelValue;
	}

	public int getMinPixelValue() {
		return minPixelValue;
	}

	public void setMinPixelValue(int minPixelValue) {
		this.minPixelValue = minPixelValue;
	}

	public int getMaxPixelValue() {
		return maxPixelValue;
	}

	public void setMaxPixelValue(int maxPixelValue) {
		this.maxPixelValue = maxPixelValue;
	}

}
