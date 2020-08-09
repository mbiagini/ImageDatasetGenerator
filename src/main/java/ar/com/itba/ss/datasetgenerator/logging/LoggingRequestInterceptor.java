package ar.com.itba.ss.datasetgenerator.logging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {
	
	private static final Logger log = LoggerFactory.getLogger(LoggingRequestInterceptor.class);
	private Integer loggingMaxLength = 1000;

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		traceRequest(request, body);
		ClientHttpResponse response = execution.execute(request, body);
		traceResponse(response);
		return response;
	}

	private void traceRequest(HttpRequest request, byte[] body) throws IOException {
		log.info("=================================INNER CALL REQUEST BEGIN=================================");
		log.info("URI         : {}", request.getURI());
		log.info("Method      : {}", request.getMethod());
		log.info("Headers     : {}", request.getHeaders());
		log.info("Request body: {}", new String(body, "UTF-8"));
		log.info("=================================INNER CALL REQUEST END===================================");
	}

	private void traceResponse(ClientHttpResponse response) throws IOException {
		StringBuilder inputStringBuilder = new StringBuilder();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"));
		String line = bufferedReader.readLine();
		while (line != null) {
			if (inputStringBuilder.length() != 0) {
				inputStringBuilder.append('\n');
			}
			inputStringBuilder.append(line);
			line = bufferedReader.readLine();
		}
		log.info("=================================INNER CALL RESPONSE BEGIN================================");
		log.info("Status code  : {}", response.getStatusCode());
		log.info("Status text  : {}", response.getStatusText());
		log.info("Headers      : {}", response.getHeaders());
		log.info("Response body: {}",
				inputStringBuilder.toString().length() > loggingMaxLength
						? inputStringBuilder.toString().substring(0, loggingMaxLength)
								+ "\nResponse body continues..."
						: inputStringBuilder.toString());
		log.info("=================================INNER CALL RESPONSE END==================================");
	}

}