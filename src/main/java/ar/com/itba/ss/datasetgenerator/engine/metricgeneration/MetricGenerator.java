package ar.com.itba.ss.datasetgenerator.engine.metricgeneration;

import static java.lang.String.format;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ar.com.itba.ss.datasetgenerator.configuration.Config;
import ar.com.itba.ss.datasetgenerator.engine.utils.FileUtils;
import ar.com.itba.ss.datasetgenerator.engine.utils.MetricUtils;
import ar.com.itba.ss.datasetgenerator.model.cellindexmethod.Particle;

public class MetricGenerator {

	private static Logger log = LoggerFactory.getLogger(MetricGenerator.class);
	
	public void generate() {
		
		log.info("Generating metrics.");
		
		for (int instant = 0; instant < Config.generationCount; instant ++) {
			
			log.info(format("Generating metrics: %d.", instant));
			List<Particle> particles = loadParticles(instant);
			
			double meanDistanceToDrive = MetricUtils.getDriveDistanceMetric(particles, Config.drivePointx, Config.drivePointy);
			double populationDensity = MetricUtils.getPopulationDensity(particles, Config.densityXStart, Config.densityXEnd, Config.densityYStart, Config.densityYEnd);
			
			log.info(format("Mean distance to drive: %f. Density: %f.", meanDistanceToDrive, populationDensity));
			
		}
		
	}
	
	private List<Particle> loadParticles(int instant) {
		
		String filename = format(Config.simulationDataBasepath + "/particles/particles%07d.json", instant);
		File file = new File(filename);
		
		String json = FileUtils.readStringFromFile(file);
		Type listType = new TypeToken<ArrayList<Particle>>() {}.getType();
		
		return new Gson().fromJson(json, listType);
		
	}
	
}
