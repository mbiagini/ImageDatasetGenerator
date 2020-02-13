package ar.com.itba.ss.datasetgenerator.engine.utils;

import java.util.List;

import ar.com.itba.ss.datasetgenerator.model.cellindexmethod.Particle;

public class MetricUtils {
	
	public static double getPopulationDensity(List<Particle> particles, double xstart, double xend, double ystart, double yend) {
		
		double accumulator = 0.0;
		
		for (Particle p : particles) {
			
			if (p.getX() >= xstart && p.getX() < xend && p.getY() >= ystart && p.getY() < yend) {
				accumulator += 1;
			}
			
		}
		
		return accumulator / (Math.abs(xend - xstart) * Math.abs(yend - ystart));
		
	}
	
	public static double getDriveDistanceMetric(List<Particle> particles, double drivePointx, double drivePointy) {
		
		double accumulator = 0.0;
		
		for (Particle p : particles) {
			
			double dx = Math.abs(drivePointx - p.getX());
			double dy = Math.abs(drivePointy - p.getY());
			
			double d = Math.hypot(dx, dy);
			
			accumulator += d;
			
		}
		
		return accumulator / particles.size();
		
	}
	
	public static double getKyneticEnergy(List<Particle> particles) {
		
		double energy = 0.0;
		
		for (Particle p : particles) {
			double v = Math.hypot(p.getVx(), p.getVy());
			energy += (1.0 / 2) * p.getM() * v * v;
		}
		
		return energy;
		
	}
	
	public static <E extends Number> Double getMean(List<E> values) {
		
		Double sum = 0.0;
		
		for (E value : values) {
			sum += value.doubleValue();
		}
		
		return sum / values.size();
		
	}
	
	public static <E extends Number> Double getVariance(List<E> values) {
		
		Double mean = getMean(values);
		Double aux = 0.0;
		
		for (E value : values) {
			aux += (value.doubleValue() - mean) * (value.doubleValue() - mean);
		}
		
		return aux / (values.size() - 1);
		
	}

}
