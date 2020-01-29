package ar.com.itba.ss.datasetgenerator.model.cellindexmethod;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ar.com.itba.ss.datasetgenerator.model.Point;

public class Cell {
	
	private Long id;
	private Point gridPosition;
	private List<Particle> particles;
	private List<Cell> neighborCells;
	
	public Cell(Long id, Point gridPosition) {
		this.id = id;
		this.gridPosition = gridPosition;
		this.particles = new ArrayList<>();
		this.neighborCells = new ArrayList<>();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Point getGridPosition() {
		return gridPosition;
	}
	
	public void setGridPosition(Point gridPosition) {
		this.gridPosition = gridPosition;
	}
	
	public boolean contains(Particle p) {
		return particles.contains(p);
	}
	
	public void addParticle(Particle p) {
		particles.add(p);
	}
	
	public List<Particle> getParticles() {
		return particles;
	}
	
	public List<Cell> getNeighborCells() {
		return neighborCells;
	}
	
	public void setNeighborCells(List<Cell> neighborCells) {
		this.neighborCells = neighborCells;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		
		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    gridPosition: ").append(toIndentedString(gridPosition)).append("\n");
		sb.append("    particles: ").append(toIndentedString(particles)).append("\n");
		sb.append("    neighborCells: ").append(toIndentedString(neighborCells.stream().map(n -> n.getId()).collect(Collectors.toList()))).append("\n");
		
		sb.append("}");
		return sb.toString();
	}
	
	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
	
}
