package ar.com.itba.ss.datasetgenerator.engine.simulation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.itba.ss.datasetgenerator.configuration.Config;
import ar.com.itba.ss.datasetgenerator.engine.cellindexmethod.CellIndexManager;
import ar.com.itba.ss.datasetgenerator.model.cellindexmethod.Particle;
import ar.com.itba.ss.datasetgenerator.model.simulation.Force;

import static java.lang.String.format;

public class MovementManager {
	
	private static Logger log = LoggerFactory.getLogger(MovementManager.class);
	
	// to prevent scenarios where x + r = side.
	final static Double EPSILON = 0.001;
	
	public static void moveParticles(List<Particle> particles) {

		// update all the accelerations
		for (Particle p : particles) {
			updateParticleAcceleration(p);
		}
		
	}
	
	private static void updateParticleAcceleration(Particle p) {
		
		log.debug("Updating acceleration of particle: " + p.getId());
		
		double fx = 0.0;
		double fy = 0.0;
		
		// forces against other particles
		if (p.getNeighbors() != null) {
			for (Particle n : p.getNeighbors()) {
				Force f = getForceAgainstParticle(p, n, Config.kn, Config.gamma);
				fx += f.getFx();
				fy += f.getFy();	
			}
		}
		
		log.debug("forces against neighbors: " + new Force(fx,fy).toString());
		
		// forces against the walls
		Force f = getForceAgainstSurface(p, Config.simulationWidth, Config.simulationHeight, Config.kn, Config.gamma);
		fx += f.getFx();
		fy += f.getFy();
		
		log.debug("forces against walls: " + f.toString());
				
		// driving force
		Force df = getDrivingForce(p, Config.driveSpeed, Config.tau, Config.drivePointx, Config.drivePointy);
		
		fx += df.getFx();
		fy += df.getFy();
		
		log.debug("forces of driving: " + df.toString());
		
		// social force
		Force sf = getSocialForce(p, p.getNeighbors(), Config.socialA, Config.socialB);
		fx += sf.getFx();
		fy += sf.getFy();
		
		log.debug("forces of social interaction: " + sf.toString());
		
		// setting acceleration
		p.setAx(fx / p.getM());
		p.setAy(fy / p.getM());
		
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
			
			double f = kn * overlap + gamma * relativeSpeed;
			
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
	
	private static Force getSocialForce(Particle p, List<Particle> neighbors, double socialA, double socialB) {
		
		double fx = 0.0;
		double fy = 0.0;
		
		for (Particle n : neighbors) {
			
			double dx = p.getX() - n.getX();
			double dy = p.getY() - n.getY();
			double r = Math.hypot(dx, dy);
			
			double ex = dx/r;
			double ey = dy/r;
			
			double d = CellIndexManager.getDistance(n, p);
			
			fx += socialA * Math.exp(- d/socialB) * ex;
			fy += socialA * Math.exp(- d/socialB) * ey;
			
		}
		
		return new Force(fx, fy);
		
	}
	
	private static Force getDrivingForce(Particle p, double ds, double tau, double drivingPointx, double drivingPointy) {
		
		double dx = drivingPointx - p.getX();
		double dy = drivingPointy - p.getY();
		double r = Math.hypot(dx, dy);
		double ex = dx / r;
		double ey = dy / r;
		
		double m = p.getM();
		double vx = p.getVx();
		double vy = p.getVy();
		
		double fx = (m * (ds * ex - vx)) / tau;
		double fy = (m * (ds * ey - vy)) / tau;
	
		return new Force(fx, fy);
		
	}

}
