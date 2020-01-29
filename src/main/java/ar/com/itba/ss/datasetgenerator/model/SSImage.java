package ar.com.itba.ss.datasetgenerator.model;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

public class SSImage {
		
	private BufferedImage bufferedImage;
	
	private String basepath;
	private String filename;
	private String extension;
		
	public SSImage bufferedImage(final BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
		return this;
	}
	
	public SSImage basepath(final String basepath) {
		this.basepath = basepath;
		return this;
	}
	
	public SSImage filename(final String filename) {
		this.filename = filename;
		return this;
	}
	
	public SSImage extension(final String extension) {
		this.extension = extension;
		return this;
	}

	public Integer getWidth() {
		return bufferedImage.getWidth();
	}

	public Integer getHeight() {
		return bufferedImage.getHeight();
	}

	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	public void setBufferedImage(final BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}
	
	public String getBasepath() {
		return basepath;
	}
	
	public void setBasepath(final String basepath) {
		this.basepath = basepath;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(final String filename) {
		this.filename = filename;
	}
	
	public String getExtension() {
		return extension;
	}
	
	public void setExtension(final String extension) {
		this.extension = extension;
	}
	
	public SSImage clone() {
		
		SSImage clone = new SSImage()
				.basepath(basepath)
				.filename(filename)
				.extension(extension);
		
		ColorModel cm = bufferedImage.getColorModel();
		return clone.bufferedImage(new BufferedImage(cm, bufferedImage.copyData(null), cm.isAlphaPremultiplied(), null));
		
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		
		sb.append("    width: ").append(toIndentedString(bufferedImage.getWidth())).append("\n");
		sb.append("    height: ").append(toIndentedString(bufferedImage.getHeight())).append("\n");
		sb.append("    basepath: ").append(toIndentedString(basepath)).append("\n");
		sb.append("    filename: ").append(toIndentedString(filename)).append("\n");
		sb.append("    extension: ").append(toIndentedString(extension)).append("\n");
		
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
