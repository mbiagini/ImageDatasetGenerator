package ar.com.itba.ss.datasetgenerator.model.simulation;

import java.util.List;
import java.util.Map;

import ar.com.itba.ss.datasetgenerator.configuration.Conf;
import ar.com.itba.ss.datasetgenerator.configuration.HardConf;
import ar.com.itba.ss.datasetgenerator.model.cellindexmethod.Particle;
import ar.com.itba.ss.datasetgenerator.model.imagegeneration.ImageResource;

public class SimulationTrunk {
	
	private List<ImageResource> people;
	private List<Particle> particles;
	private Map<Long, Long> particleToPersonMap;
	
	private Conf conf;
	private HardConf hardConf;
	
	// --------------------------------------------------------------------------------------------
	
	public SimulationTrunk(Conf conf, HardConf hardConf) {
		this.conf = conf;
		this.hardConf = hardConf;
	}
	
	public List<ImageResource> getPeople() {
		return people;
	}

	public void setPeople(List<ImageResource> people) {
		this.people = people;
	}
	
	public List<Particle> getParticles() {
		return particles;
	}
	
	public void setParticles(List<Particle> particles) {
		this.particles = particles;
	}

	public Map<Long, Long> getParticleToPersonMap() {
		return particleToPersonMap;
	}

	public void setParticleToPersonMap(Map<Long, Long> particleToPersonMap) {
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
