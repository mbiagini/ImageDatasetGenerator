package ar.com.itba.ss.datasetgenerator.model.cellindexmethod;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Particle implements Serializable {
	
	private static final long serialVersionUID = 2793512693176081070L;
	
	private Long id;
	private Double x;
	private Double y;
	private Double r;
	private Double m;
	private Double vx;
	private Double vy;
	private Double ax;
	private Double ay;
	private Double theta;
		
	private transient List<Particle> neighbors;
		
	public Particle(Long id, Double x, Double y, Double r, Double m, Double vx, Double vy, Double ax, Double ay, Double theta) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.r = r;
		this.m = m;
		this.vx = vx;
		this.vy = vy;
		this.ax = ax;
		this.ay = ay;
		this.theta = theta;
		this.neighbors = new ArrayList<>();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public Double getR() {
		return r;
	}

	public void setR(Double r) {
		this.r = r;
	}

	public Double getM() {
		return m;
	}

	public void setM(Double m) {
		this.m = m;
	}

	public Double getVx() {
		return vx;
	}

	public void setVx(Double vx) {
		this.vx = vx;
	}

	public Double getVy() {
		return vy;
	}

	public void setVy(Double vy) {
		this.vy = vy;
	}

	public Double getAx() {
		return ax;
	}

	public void setAx(Double ax) {
		this.ax = ax;
	}

	public Double getAy() {
		return ay;
	}

	public void setAy(Double ay) {
		this.ay = ay;
	}

	public Double getTheta() {
		return theta;
	}

	public void setTheta(Double theta) {
		this.theta = theta;
	}

	public List<Particle> getNeighbors() {
		return this.neighbors;
	}
	
	public void setNeighbors(List<Particle> neighbors) {
		this.neighbors = neighbors;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		
		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    x: ").append(toIndentedString(x)).append("\n");
		sb.append("    y: ").append(toIndentedString(y)).append("\n");
		sb.append("    r: ").append(toIndentedString(r)).append("\n");
		sb.append("    m: ").append(toIndentedString(m)).append("\n");
		sb.append("    vx: ").append(toIndentedString(vx)).append("\n");
		sb.append("    vy: ").append(toIndentedString(vy)).append("\n");
		sb.append("    ax: ").append(toIndentedString(ax)).append("\n");
		sb.append("    ay: ").append(toIndentedString(ay)).append("\n");
		sb.append("    theta: ").append(toIndentedString(theta)).append("\n");
		sb.append("    neighbors: ").append(toIndentedString(neighbors.stream().map(n -> n.getId()).collect(Collectors.toList()))).append("\n");
		
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
