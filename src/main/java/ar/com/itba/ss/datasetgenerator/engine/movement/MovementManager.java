package ar.com.itba.ss.datasetgenerator.engine.movement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.itba.ss.datasetgenerator.configuration.Config;
import ar.com.itba.ss.datasetgenerator.model.cellindexmethod.Particle;
import ar.com.itba.ss.datasetgenerator.model.movement.Force;

import static java.lang.String.format;

public class MovementManager {
	
	private static Logger log = LoggerFactory.getLogger(MovementManager.class);
	
	// to prevent scenarios where x + r = side.
	final static Double EPSILON = 0.001;
	
	public static void moveParticles(List<Particle> particles, Integer width, Integer height, Double kn, Double gamma, Double dt) {
		
		// beeman prediction method
		Map<Particle, Double> axtminusdtmap = new HashMap<>();
		Map<Particle, Double> aytminusdtmap = new HashMap<>();
		Map<Particle, Double> axtmap = new HashMap<>();
		Map<Particle, Double> aytmap = new HashMap<>();
		
		for (Particle p : particles) {
			axtminusdtmap.put(p, new Double(0.0));
			aytminusdtmap.put(p, new Double(0.0));
		}
		
		for (Particle p : particles) {
			
			double xt = p.getX();
			double yt = p.getY();
			double vxt = p.getVx();
			double vyt = p.getVy();
			double axt = p.getAx();
			double ayt = p.getAy();
			
			double axtminusdt = axtminusdtmap.get(p);
			double aytminusdt = aytminusdtmap.get(p);
			
			axtmap.put(p, axt);
			aytmap.put(p, ayt);
			
			double xtplusdt = xt + vxt * dt + (2.0 / 3) * axt * dt * dt - (1.0 / 6) * axtminusdt * dt * dt;
			double ytplusdt = yt + vyt * dt + (2.0 / 3) * ayt * dt * dt - (1.0 / 6) * aytminusdt * dt * dt;
			
			double vxp = vxt + (3.0 / 2) * axt * dt - (1.0 / 2) * axtminusdt * dt;
			double vyp = vyt + (3.0 / 2) * ayt * dt - (1.0 / 2) * aytminusdt * dt;
			
			p.setX(xtplusdt);
			p.setY(ytplusdt);
			p.setVx(vxp);
			p.setVy(vyp);
			
		}
		
		// update all the accelerations
		for (Particle p : particles) {
			updateParticleAcceleration(p, width, height, kn, gamma);
		}
		
		// fix predictions
		for (Particle p : particles) {
			
			double axt = axtmap.get(p);
			double ayt = aytmap.get(p);
			
			axtminusdtmap.put(p, axt);
			aytminusdtmap.put(p, ayt);
			
		}
		
	}
	
	private static void updateParticleAcceleration(Particle p, Integer width, Integer height, Double kn, Double gamma) {
		
		double fx = 0.0;
		double fy = 0.0;
		
		// forces against other particles
		if (p.getNeighbors() != null) {
			for (Particle n : p.getNeighbors()) {
				Force f = getForceAgainstParticle(p, n, kn, gamma);
				fx += f.getFx();
				fy += f.getFy();	
			}
		}
		
		// forces against the walls
		Force f = getForceAgainstSurface(p, width, height, kn, gamma);
		fx += f.getFx();
		fy += f.getFy();
				
		p.setAx(fx / p.getM() + Config.fixedAccx);
		p.setAy(fy / p.getM() + Config.fixedAccy);
		
	}
	
	private static Force getForceAgainstParticle(Particle p1, Particle p2, Double kn, Double gamma) {
		
		double fx = 0.0;
		double fy = 0.0;
		
		double dx = p2.getX() - p1.getX();
		double dy = p2.getY() - p1.getY();
		
		double hyp = Math.hypot(dx, dy);
		
		double ex = dx/hyp;
		double ey = dy/hyp;
		
		if (hyp < p1.getR() + p2.getR()) {
			
			double overlap = p1.getR() + p2.getR() - hyp;
			
			double normalSpeed1 = p1.getVx() * ex + p1.getVy() * ey;
			double normalSpeed2 = p2.getVx() * ex + p2.getVy() * ey;
			
			double relativeSpeed = normalSpeed1 - normalSpeed2;
			
			double f = kn * overlap - gamma * relativeSpeed;
			
			fx = - f * ex;
			fy = - f * ey;
		
		}
		
		return new Force(fx, fy);
		
	}
	
	private static Force getForceAgainstSurface(Particle p, Integer width, Integer height, Double kn, Double gamma) {
		
		double fx = 0.0;
		double fy = 0.0;
		
		double x = p.getX();
		double y = p.getY();
		double r = p.getR();
		
		double relativeSpeedx = p.getVx();
		double relativeSpeedy = p.getVy();
		
		if (x - r < 0) {
			double overlap = - (x - r);
			double f = - kn * overlap + gamma * relativeSpeedx;
			fx = - f;
		}
		
		if (x + r > width) {
			double overlap = (x + r) - width;
			double f = - kn * overlap - gamma * relativeSpeedx;
			fx = f;
		}
		
		if (y - r < 0) {
			double overlap = - (y - r);
			double f = - kn * overlap + gamma * relativeSpeedy;
			fy = - f;
		}
		
		if (y + r > height) {
			double overlap = (y + r) - height;
			double f = - kn * overlap - gamma * relativeSpeedy;
			fy = f;
		}
		
		return new Force(fx, fy);
		
	}

}
