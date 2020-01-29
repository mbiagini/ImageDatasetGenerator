package ar.com.itba.ss.datasetgenerator;

import org.apache.cxf.spring.boot.autoconfigure.CxfAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ar.com.itba.ss.datasetgenerator.engine.Generator;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = CxfAutoConfiguration.class)
@EnableSwagger2
public class App {

	public static void main(String[] args) {
				
		Generator generatorService = new Generator();
		generatorService.generate();
		
	}
	
}
