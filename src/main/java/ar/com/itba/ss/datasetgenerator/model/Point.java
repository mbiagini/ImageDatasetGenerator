package ar.com.itba.ss.datasetgenerator.model;

public class Point {
	
	private Integer x;
	private Integer y;
	
	public Point clone() {
		return new Point(getX(), getY());
	}
		
	public Point(final Integer x, final Integer y) {
		this.x = x;
		this.y = y;
	}

	public Integer getX() {
		return x;
	}

	public void setX(final Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(final Integer y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		
		sb.append("    x: ").append(toIndentedString(x)).append("\n");
		sb.append("    y: ").append(toIndentedString(y)).append("\n");

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
