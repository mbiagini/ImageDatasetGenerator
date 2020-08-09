package ar.com.itba.ss.datasetgenerator.engine.simulation;

import java.util.List;

import ar.com.itba.ss.datasetgenerator.model.cellindexmethod.Particle;

public class ModifiedEulerAlgorithm {
	
	public void updateParticles(List<Particle> particles, double dt) {
		
		for (Particle p : particles) {
			
			//v(t + dt) = v(t) + a(t) * (dt / m)
			//x(t + dt) = x(t) + v(t + dt) * dt + a(t) * dt * dt / (2 * m)
			
			double ax = p.getAx();
			double ay = p.getAy();
			double m = p.getM();
			
			p.setVx(p.getVx() + ax * (dt / m));
			p.setVy(p.getVy() + ay * (dt / m));
			
			p.setX(p.getX() + p.getVx() * dt + ax * dt * dt / (2 * m));
			p.setY(p.getY() + p.getVy() * dt + ay * dt * dt / (2 * m));
			
		}
		
	}

}
