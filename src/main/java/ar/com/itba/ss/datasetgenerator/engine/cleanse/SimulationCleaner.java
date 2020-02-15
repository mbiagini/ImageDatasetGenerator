package ar.com.itba.ss.datasetgenerator.engine.cleanse;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.itba.ss.datasetgenerator.configuration.Config;
import ar.com.itba.ss.datasetgenerator.engine.utils.FileUtils;

public class SimulationCleaner implements Cleaner {
	
	private static Logger log = LoggerFactory.getLogger(SimulationCleaner.class);

	@Override
	public void clean() {
		
		log.info("Deleting particles map.");
		
		String filename = Config.simulationDataBasepath + "/particle_to_person_map.json";
		File file = new File(filename);
		
		file.delete();
		
		log.info("Deleting particles files.");
		
		FileUtils.deleteAllFiles(Config.simulationParticlesBasepath);
		
	}

}
