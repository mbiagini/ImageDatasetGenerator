package ar.com.itba.ss.datasetgenerator.model.config;

public class CameraHeight {
	
	private String label;
	private int minParticleCount;
	private int maxParticleCount;
	
	public CameraHeight(String label, int minParticleCount, int maxParticleCount) {
		this.label = label;
		this.minParticleCount = minParticleCount;
		this.maxParticleCount = maxParticleCount;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
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

}
