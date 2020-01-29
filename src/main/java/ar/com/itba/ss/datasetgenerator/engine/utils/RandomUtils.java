package ar.com.itba.ss.datasetgenerator.engine.utils;

import java.util.Random;

import ar.com.itba.ss.datasetgenerator.configuration.Config;
import ar.com.itba.ss.datasetgenerator.model.Point;

public class RandomUtils {
	
	private static Random random = new Random(Config.randomSeed);

	/**
	 * Random Integer between "from" (inclusive) and "to" (exclusive).
	 */
	public static int randomIntBetween(int from, int to) {
		if (from >= to) {
			throw new RuntimeException("Cannot generate random between " + from + " and " + to);
		}
		return random.nextInt(to - from) + from;
	}
	
	/**
	 * Random Point within a rectangle.
	 */
	public static Point randomPoint(int width, int height) {
		return new Point(randomIntBetween(0, width), randomIntBetween(0, height));
	}
	
	public static Double randomDouble() {
		return random.nextDouble();
	}

}
