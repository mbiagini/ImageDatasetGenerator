package ar.com.itba.ss.datasetgenerator.model.imagegeneration;

import java.util.List;
import java.util.Map;

import ar.com.itba.ss.datasetgenerator.configuration.Conf;
import ar.com.itba.ss.datasetgenerator.configuration.HardConf;
import ar.com.itba.ss.datasetgenerator.model.ImageGrid;
import ar.com.itba.ss.datasetgenerator.model.cellindexmethod.Particle;

public class ImageGenerationTrunk {
	
	private List<ImageResource> people;
	private ImageResource background;
	
	private ImageGrid initialState;
	
	private List<Particle> particles;
	private Map<Long, ImageResource> particleToPersonMap;
	
	private Conf conf;
	private HardConf hardConf;
	
	// --------------------------------------------------------------------------------------------
	
	public ImageGenerationTrunk(Conf conf, HardConf hardConf) {
		this.conf = conf;
		this.hardConf = hardConf;
	}
	
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

	public Conf getConf() {
		return conf;
	}

	public void setConf(Conf conf) {
		this.conf = conf;
	}

	public HardConf getHardConf() {
		return hardConf;
	}

	public void setHardConf(HardConf hardConf) {
		this.hardConf = hardConf;
	}

}
