package ar.com.itba.ss.datasetgenerator.model;

public class Person {
	
	private Long id;
			
	private Integer[][] colorCanvas;
	private Integer[][] irCanvas;

	private SSImage img;
	
	public Person(Long id, Integer[][] colorCanvas, Integer[][] irCanvas, SSImage img) {
		this.id = id;
		this.colorCanvas = colorCanvas;
		this.irCanvas = irCanvas;
		this.img = img;
	}
	
	public Long getId() {
		return id;
	}

	public Integer getWidth() {
		return img.getWidth();
	}

	public Integer getHeight() {
		return img.getHeight();
	}

	public Integer[][] getColorCanvas() {
		return colorCanvas;
	}

	public void setColorCanvas(Integer[][] colorCanvas) {
		this.colorCanvas = colorCanvas;
	}

	public Integer[][] getIRCanvas() {
		return irCanvas;
	}

	public void setIRCanvas(Integer[][] irCanvas) {
		this.irCanvas = irCanvas;
	}

	public SSImage getImg() {
		return img;
	}

	public void setImg(SSImage img) {
		this.img = img;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		
		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    width: ").append(toIndentedString(img.getWidth())).append("\n");
		sb.append("    height: ").append(toIndentedString(img.getHeight())).append("\n");

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
