package ar.com.itba.ss.datasetgenerator.engine.utils;

import java.util.Random;

import ar.com.itba.ss.datasetgenerator.model.Point;

public class RandomUtils {

	/**
	 * Random Integer between "from" (inclusive) and "to" (exclusive).
	 */
	public static int randomIntBetween(Random random, int from, int to) {
		if (from >= to) {
			throw new RuntimeException("Cannot generate random between " + from + " and " + to);
		}
		return random.nextInt(to - from) + from;
	}
	
	/**
	 * Random Double between "from" (inclusive) and "to" (exclusive).
	 */
	public static double randomDoubleBetween(Random random, double from, double to) {
		if (from >= to) {
			throw new RuntimeException("Cannot generate random between " + from + "and " + to);
		}
		return from + (to - from) * randomDouble(random);
	}
	
	/**
	 * Random Point within a rectangle.
	 */
	public static Point randomPoint(Random random, int width, int height) {
		return new Point(randomIntBetween(random, 0, width), randomIntBetween(random, 0, height));
	}
	
	public static Double randomDouble(Random random) {
		return random.nextDouble();
	}

}
