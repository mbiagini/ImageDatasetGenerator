package ar.com.itba.ss.datasetgenerator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageGrid {
	
	private int width;
	private int height;
	
	private Integer[][] colorCanvas;
	private Integer[][] irCanvas;
	
	private List<Person> people;
	
	private Map<Long, List<Point>> peoplePositions;
	
	private SSImage background;
		
	public ImageGrid(SSImage background, List<Person> people, Integer[][] colorCanvas, Integer[][] irCanvas) {
		
		this.width = background.getWidth();
		this.height = background.getHeight();
		this.background = background;
		this.colorCanvas = colorCanvas;
		this.irCanvas = irCanvas;	
		this.people = people;
		this.peoplePositions = new HashMap<>();
		
		people.forEach(p -> {
			peoplePositions.put(p.getId(), new ArrayList<>());
		});
		
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Integer[][] getColorCanvas() {
		return colorCanvas;
	}
	
	public Integer[][] getIrCanvas() {
		return irCanvas;
	}
	
	public SSImage getBackground() {
		return background;
	}
	
	public List<Person> getPeople() {
		return people;
	}
	
	public Map<Long, List<Point>> getPeoplePositions() {
		return peoplePositions;
	}
	
	public void setColorCanvas(Integer[][] colorCanvas) {
		this.colorCanvas = colorCanvas;
	}
	
	public void setIrCanvas(Integer[][] irCanvas) {
		this.irCanvas = irCanvas;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		
		sb.append("    width: ").append(toIndentedString(width)).append("\n");
		sb.append("    height: ").append(toIndentedString(height)).append("\n");
		sb.append("    people: ").append(toIndentedString(people.size())).append("\n");
		
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
