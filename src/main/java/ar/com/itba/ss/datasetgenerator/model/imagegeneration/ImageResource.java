package ar.com.itba.ss.datasetgenerator.model.imagegeneration;

public class ImageResource {
	
	private Long id;
	
	private Integer width;
	private Integer height;
			
	private Integer[][] colorCanvas;
	private Integer[][] irCanvas;
	
	public ImageResource(Long id, Integer width, Integer height, Integer[][] colorCanvas, Integer[][] irCanvas) {
		this.id = id;
		this.width = width;
		this.height = height;
		this.colorCanvas = colorCanvas;
		this.irCanvas = irCanvas;
	}
	
	public Long getId() {
		return id;
	}

	public Integer getWidth() {
		return width;
	}

	public Integer getHeight() {
		return height;
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
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		
		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    width: ").append(toIndentedString(width)).append("\n");
		sb.append("    height: ").append(toIndentedString(height)).append("\n");

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
