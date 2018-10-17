package org.jab.microservices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path="/")
public class BasicController {

    private static Logger LOGGER = LoggerFactory.getLogger(BasicController.class);

    private RestTemplate restTemplate;

    private String endpoint;

    BasicController(
            RestTemplate restTemplate,
            @Value("${stubs.api.endpoint}") String endpoint) {

        this.restTemplate = restTemplate;
        this.endpoint = endpoint;
        LOGGER.info(endpoint);
    }

    @GetMapping(path="entrypoint")
    public ConceptReponse getConcept1() throws Exception {

        final String result = getResult(endpoint);
        LOGGER.info(result);

        return new ConceptReponse("Hello World");
    }

    private String getResult(String endpoint) {
        ResponseEntity<String> response = restTemplate.getForEntity(endpoint, String.class);
        return response.toString();
    }

}