package ar.com.itba.ss.datasetgenerator.engine.utils;

import static java.lang.String.format;

public class GenericUtils {

	public static String matrixToString(Integer[][] matrix, int width, int height) {

		StringBuilder sb = new StringBuilder();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				sb.append(format("%4d", matrix[x][y]));
			}
			sb.append("\n");
		}
		
		return sb.toString();
		
	}

}
