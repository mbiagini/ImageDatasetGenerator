package ar.com.itba.ss.datasetgenerator.engine.cleanse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.itba.ss.datasetgenerator.configuration.Config;
import ar.com.itba.ss.datasetgenerator.engine.utils.FileUtils;

public class ImagesCleaner implements Cleaner {
	
	private static Logger log = LoggerFactory.getLogger(ImagesCleaner.class);

	@Override
	public void clean() {
		
		log.info("Deleting RGB images.");
		FileUtils.deleteAllFiles(Config.imagesBasepath);
		
		log.info("Deleting IR images.");
		FileUtils.deleteAllFiles(Config.infraredBasepath);
		
	}

}
