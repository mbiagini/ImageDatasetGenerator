package ar.com.itba.ss.datasetgenerator.engine.utils;

import java.util.List;

import ar.com.itba.ss.datasetgenerator.model.cellindexmethod.Particle;

public class MetricUtils {
	
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
