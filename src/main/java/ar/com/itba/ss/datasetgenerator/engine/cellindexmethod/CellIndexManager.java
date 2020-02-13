package ar.com.itba.ss.datasetgenerator.engine.cellindexmethod;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.itba.ss.datasetgenerator.model.Point;
import ar.com.itba.ss.datasetgenerator.model.cellindexmethod.Cell;
import ar.com.itba.ss.datasetgenerator.model.cellindexmethod.Grid;
import ar.com.itba.ss.datasetgenerator.model.cellindexmethod.Particle;

import static java.lang.String.format;

public class CellIndexManager {
	
	private static Logger log = LoggerFactory.getLogger(CellIndexManager.class);
	
	public static void allocateParticlesInGrid(List<Particle> particles, Grid grid) {
		
		log.debug("Allocating particles in Cell Index Method Grid.");
		
		particles.forEach(p -> {
			
			Point cellPosition = getParticleCellPosition(grid, p);
			
			try {
				
				grid.getMatrix()[cellPosition.getX()][cellPosition.getY()].addParticle(p);
				
			} catch (Exception e) {
				
				log.error(format("Cell Index Method Grid allocation failed for particle %s: and cellPosition: %s.", 
						p.toString(), cellPosition.toString()));
				throw new RuntimeException("Runtime error");
				
			}
			
		});
		
		log.debug("Computing Neighbors for Grid Cells.");
		
		computeCellsNeighbors(grid);
		
	}
	
	public static void computeParticlesInteraction(Grid grid, List<Particle> particles, Double rc) {
		
		log.debug("Computing all particles interactions with Cell Index Method.");
		
		for (Particle p : particles) {
			p.setNeighbors(new ArrayList<>());
		}
						
		particles.forEach(p -> {
			
			List<Cell> neighborCells = getParticleNeighborCells(grid, p);
			
			neighborCells.forEach(nc -> {
				
				nc.getParticles().forEach(cellParticle -> {
					
					if (getDistance(p, cellParticle) < rc) {
						
						p.getNeighbors().add(cellParticle);
						cellParticle.getNeighbors().add(p);
						
					}
					
				});
				
			});
			
		});
		
		grid.getMatrixCellsAsList().forEach(cell -> {
			
			List<Particle> cellParticles = cell.getParticles();
			
			for (int i = 0; i < cellParticles.size(); i++) {
				for (int j = i + 1; j < cellParticles.size(); j++) {
					
					Particle p1 = cellParticles.get(i);
					Particle p2 = cellParticles.get(j);
					
					if (!p1.getId().equals(p2.getId()) && getDistance(p1, p2) < rc) {
						
						p1.getNeighbors().add(p2);
						p2.getNeighbors().add(p1);
						
					}
					
				}
			}
			
		});
		
		log.debug("Interaction computing finished.");
		
	}
	
	private static List<Cell> getParticleNeighborCells(Grid grid, Particle p) {
		
		Point cellPosition = getParticleCellPosition(grid, p);
		
		try {
			
			Cell cell = grid.getMatrix()[cellPosition.getX()][cellPosition.getY()];
			return cell.getNeighborCells();
			
		} catch (Exception e) {
			
			log.error(format("Particle %s out of grid bounds.", p.toString()));
			throw new RuntimeException("Runtime error");
			
		}
		
	}
	
	private static Point getParticleCellPosition(Grid grid, Particle p) {
		
		Double side = grid.getSide();
		Integer size = grid.getSize();
		
		Integer x = (int) Math.floor(p.getX() / (side / size));
		Integer y = (int) Math.floor(p.getY() / (side / size));
		
		return new Point(x, y);
		
	}
	
	private static void computeCellsNeighbors(Grid grid) {
		
		Cell[][] matrix = grid.getMatrix();
		int size = grid.getSize();
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				
				Cell cell = matrix[i][j];
				List<Cell> neighbors = new ArrayList<>();
				
				Integer x = cell.getGridPosition().getX();
				Integer y = cell.getGridPosition().getY();
				
				Integer right = x + 1;
				Integer up 	  = y + 1;
				Integer down  = y - 1;
				
				if (x == size - 1) {
					right = null;
				}
				if (y == size - 1) {
					up = null;
				}
				else if (y == 0) {
					down = null;
				}
				if(right != null) {
					neighbors.add(matrix[right][y]);
					if(up != null) {
						neighbors.add(matrix[x][up]);
						neighbors.add(matrix[right][up]);
					}
					if(down != null) {
						neighbors.add(matrix[right][down]);
					}
				}
				else if(up != null) {
					neighbors.add(matrix[x][up]);
				}
				
				cell.setNeighborCells(neighbors);
			}
		}
		
	}
	
	public static boolean isOverlappingAny(Particle particle, List<Particle> particles) {
		for (Particle p : particles) {
			if (!p.getId().equals(particle.getId())) {
				if (areOverlapping(particle, p)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private static boolean areOverlapping(Particle p1, Particle p2) {
		if (getCenterDistance(p1, p2) < (p1.getR() + p2.getR())) {
			return true;
		}
		return false;
	}
	
	public static double getDistance(Particle p1, Particle p2) {
		double centerDistance = Math.hypot(Math.abs(p2.getX() - p1.getX()), Math.abs(p2.getY() - p1.getY()));
		return centerDistance - p1.getR() - p2.getR();
	}
	
	private static double getCenterDistance(Particle p1, Particle p2) {
		return Math.hypot(Math.abs(p2.getX() - p1.getX()), Math.abs(p2.getY() - p1.getY()));
	}

}
