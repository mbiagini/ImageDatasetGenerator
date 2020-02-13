package ar.com.itba.ss.datasetgenerator.model.imagegeneration;

import java.util.List;
import java.util.Map;

import ar.com.itba.ss.datasetgenerator.model.ImageGrid;
import ar.com.itba.ss.datasetgenerator.model.cellindexmethod.Particle;

public class ImageGenerationTrunk {
	
	private List<ImageResource> people;
	private ImageResource background;
	
	private ImageGrid initialState;
	
	private List<Particle> particles;
	private Map<Long, ImageResource> particleToPersonMap;
	
	public ImageGenerationTrunk() {}
	
	public List<ImageResource> getPeople() {
		return people;
	}
	
	public void setPeople(List<ImageResource> people) {
		this.people = people;
	}
	
	public ImageResource getBackground() {
		return background;
	}
	
	public void setBackground(ImageResource background) {
		this.background = background;
	}
	
	public ImageGrid getInitialState() {
		return initialState;
	}
	
	public void setInitialState(ImageGrid initialState) {
		this.initialState = initialState;
	}

	public List<Particle> getParticles() {
		return particles;
	}

	public void setParticles(List<Particle> particles) {
		this.particles = particles;
	}

	public Map<Long, ImageResource> getParticleToPersonMap() {
		return particleToPersonMap;
	}

	public void setParticleToPersonMap(Map<Long, ImageResource> particleToPersonMap) {
		this.particleToPersonMap = particleToPersonMap;
	}

}
