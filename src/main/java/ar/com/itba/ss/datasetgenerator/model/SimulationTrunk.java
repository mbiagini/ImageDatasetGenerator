package ar.com.itba.ss.datasetgenerator.model;

import java.util.List;
import java.util.Map;

import ar.com.itba.ss.datasetgenerator.model.cellindexmethod.Particle;

public class SimulationTrunk {
	
	private List<Person> people;
	private SSImage background;
	private ImageGrid initialState;
	private List<Particle> particles;
	private Map<Long, Person> particleToPersonMap;
	
	public SimulationTrunk() {}

	public List<Person> getPeople() {
		return people;
	}

	public void setPeople(List<Person> people) {
		this.people = people;
	}

	public SSImage getBackground() {
		return background;
	}

	public void setBackground(SSImage background) {
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

	public Map<Long, Person> getParticleToPersonMap() {
		return particleToPersonMap;
	}

	public void setParticleToPersonMap(Map<Long, Person> particleToPersonMap) {
		this.particleToPersonMap = particleToPersonMap;
	}

}
