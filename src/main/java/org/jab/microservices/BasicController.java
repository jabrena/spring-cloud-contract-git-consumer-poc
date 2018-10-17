package org.jab.microservices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path="/")
public class BasicController {

    private static Logger LOGGER = LoggerFactory.getLogger(BasicController.class);

    private RestTemplate restTemplate;

    private String domain;
    private int port;
    private String endpoint;

    BasicController(
            RestTemplate restTemplate,
            @Value("${subsystem1.api.domain}") String domain,
            @Value("${subsystem1.api.port}") int port,
            @Value("${subsystem1.api.endpoint}") String endpoint) {

        this.restTemplate = restTemplate;
        this.domain = domain;
        this.port = port;
        this.endpoint = endpoint;

        LOGGER.info("Subsystem 1: {}:{}{}", domain, port, endpoint);
    }

    @GetMapping(path="entrypoint")
    public ConceptReponse getConcept1() throws Exception {

        final String result = getResult();
        LOGGER.info(result);

        return new ConceptReponse("Hello World");
    }

    public void setPort(int port) {
        this.port = port;
    }

    private String getResult() {

        final String address = domain + ":" + port + endpoint;
        LOGGER.info(address);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<String> response = restTemplate.exchange(
                address,
                HttpMethod.GET,
                new HttpEntity<String>(headers),
                String.class
        );

        return response.toString();
    }

}