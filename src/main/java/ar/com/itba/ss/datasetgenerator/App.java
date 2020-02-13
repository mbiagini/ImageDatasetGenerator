package ar.com.itba.ss.datasetgenerator;

import org.apache.cxf.spring.boot.autoconfigure.CxfAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ar.com.itba.ss.datasetgenerator.configuration.Config;
import ar.com.itba.ss.datasetgenerator.engine.imagegeneration.ImageGenerator;
import ar.com.itba.ss.datasetgenerator.engine.metricgeneration.MetricGenerator;
import ar.com.itba.ss.datasetgenerator.engine.simulation.Simulator;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = CxfAutoConfiguration.class)
@EnableSwagger2
public class App {

	public static void main(String[] args) {
		
		switch (Config.executionType) {
		
			case RUN_SIMULATION: {
				Simulator simulator = new Simulator();
				simulator.simulate();
				break;
			}
		
			case GENERATE_IMAGES: {		
				ImageGenerator generatorService = new ImageGenerator();
				generatorService.generate();
				break;
			}
			
			case GENERATE_METRICS: {
				MetricGenerator metricGenerator = new MetricGenerator();
				metricGenerator.generate();
				break;
			}
		
			default:
				break;
		
		}
		
	}
	
}
