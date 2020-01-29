package ar.com.itba.ss.datasetgenerator.model.movement;

public class Force {
	
	private Double fx;
	private Double fy;
	
	public Force(Double fx, Double fy) {
		this.fx = fx;
		this.fy = fy;
	}

	public Double getFx() {
		return fx;
	}

	public void setFx(Double fx) {
		this.fx = fx;
	}

	public Double getFy() {
		return fy;
	}

	public void setFy(Double fy) {
		this.fy = fy;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		
		sb.append("    fx: ").append(toIndentedString(fx)).append("\n");
		sb.append("    fy: ").append(toIndentedString(fy)).append("\n");
		
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
