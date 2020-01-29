package ar.com.itba.ss.datasetgenerator.model.cellindexmethod;

import java.util.ArrayList;
import java.util.List;

import ar.com.itba.ss.datasetgenerator.model.Point;

public class Grid {

	private Double side;
	private Integer size;
	private Cell[][] matrix;
	
	public Grid(Double side, Integer size) {
		
		this.side = side;
		this.size = size;
		this.matrix = new Cell[size][size];
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
								
				matrix[i][j] = new Cell(Long.parseLong("" + i + j), new Point(i, j));
				
			}
		}
		
	}
	
	public Cell[][] getMatrix() {
		return matrix;
	}
	
	public List<Cell> getMatrixCellsAsList() {
				
		List<Cell> cells = new ArrayList<>();
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				
				cells.add(matrix[i][j]);
				
			}
		}
		
		return cells;
		
	}
	
	public Integer getSize() {
		return size;
	}
	
	public void setSize(Integer size) {
		this.size = size;
	}
	
	public Double getSide() {
		return side;
	}
	
	public void setSide(Double side) {
		this.side = side;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		
		sb.append("    side: ").append(toIndentedString(side)).append("\n");
		sb.append("    size: ").append(toIndentedString(size)).append("\n");
		sb.append("    matrix: ").append(toIndentedString(getMatrixCellsAsList())).append("\n");
		
		sb.append("    }\n");

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
