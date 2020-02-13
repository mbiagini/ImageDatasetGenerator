package ar.com.itba.ss.datasetgenerator.engine.simulation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.itba.ss.datasetgenerator.model.cellindexmethod.Particle;
import ar.com.itba.ss.datasetgenerator.model.simulation.GearVariables;

public class GearAlgorithm {
	
	private Map<Long, GearVariables> gvxMap;
	private Map<Long, GearVariables> gvyMap;
	
	public GearAlgorithm() {
		this.gvxMap = new HashMap<>();
		this.gvyMap = new HashMap<>();
	}
	
	public void initialize(List<Particle> particles) {
		for (Particle p : particles) {
			gvxMap.put(p.getId(), new GearVariables(p.getX(), p.getVx(), p.getAx(), 0, 0, 0));
			gvyMap.put(p.getId(), new GearVariables(p.getY(), p.getVy(), p.getAy(), 0, 0, 0));
		}
	}
	
	public void computePredictions(List<Particle> particles, double dt) {
		for (Particle p : particles) {
			
			GearVariables gvx = gvxMap.get(p.getId());
			GearVariables gvy = gvyMap.get(p.getId());
			
			computePrediction(gvx, dt);
			computePrediction(gvy, dt);
			
			updateParticle(p, gvx, gvy);
			
		}
	}
	
	public void fixPredictions(List<Particle> particles, double dt) {
		
		for (Particle p : particles) {
			
			GearVariables gvx = gvxMap.get(p.getId());
			GearVariables gvy = gvyMap.get(p.getId());
			
			fixPrediction(gvx, p.getAx(), dt);
			fixPrediction(gvy, p.getAy(), dt);
			
			updateParticle(p, gvx, gvy);
			
		}
		
	}
	
	private void fixPrediction(GearVariables gv, double ap, double dt) {
		
		double da = ap - gv.getR2();
		double dR2 = da*dt*dt/2;
		
		// powers of dt
		double dt2 =  dt*dt;
		double dt3 = dt2*dt;
		double dt4 = dt3*dt;
		double dt5 = dt4*dt;
				
		// factorial
		double f2 =    2;
		double f3 = 3*f2;
		double f4 = 4*f3;
		double f5 = 5*f4;
				
		// coefficients
		double a0 = 3.0/20.0;
		double a1 = 251.0/360.0;
		double a2 = 1.0;
		double a3 = 11.0/18.0;
		double a4 = 1.0/6.0;
		double a5 = 1.0/60.0;
				
		// fix predictions
		gv.setR0(gv.getR0() + a0*dR2);
		gv.setR1(gv.getR1() + a1*dR2*(1.0/dt));
		gv.setR2(gv.getR2() + a2*dR2*(f2/dt2));
		gv.setR3(gv.getR3() + a3*dR2*(f3/dt3));
		gv.setR4(gv.getR4() + a4*dR2*(f4/dt4));
		gv.setR5(gv.getR5() + a5*dR2*(f5/dt5));
		
	}
	
	private void updateParticle(Particle p, GearVariables gvx, GearVariables gvy) {
		
		p.setX(gvx.getR0());
		p.setVx(gvx.getR1());
		p.setAx(gvx.getR2());
		
		p.setY(gvy.getR0());
		p.setVy(gvy.getR1());
		p.setAy(gvy.getR2());
		
	}
	
	private void computePrediction(GearVariables gv, double dt) {
		
		// last variable values
		double r  = gv.getR0();
		double r1 = gv.getR1();
		double r2 = gv.getR2();
		double r3 = gv.getR3();
		double r4 = gv.getR4();
		double r5 = gv.getR5();
		
		// powers of dt
		double dt2 =  dt * dt;
		double dt3 = dt2 * dt;
		double dt4 = dt3 * dt;
		double dt5 = dt4 * dt;
		
		// factorial
		double f2 = 2;
		double f3 = 3 * f2;
		double f4 = 4 * f3;
		double f5 = 5 * f4;
		
		// put predictions in the variables
		gv.setR0( r + r1*dt + r2*dt2/f2 + r3*dt3/f3 + r4*dt4/f4 + r5*dt5/f5);
		gv.setR1(r1 + r2*dt + r3*dt2/f2 + r4*dt3/f3 + r5*dt4/f4);
		gv.setR2(r2 + r3*dt + r4*dt2/f2 + r5*dt3/f3);
		gv.setR3(r3 + r4*dt + r5*dt2/f2);
		gv.setR4(r4 + r5*dt);
		gv.setR5(r5);
		
	}

}
