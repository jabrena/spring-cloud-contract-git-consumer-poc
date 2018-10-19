package org.jab.microservices;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerPort;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureStubRunner(
        stubsMode = StubRunnerProperties.StubsMode.REMOTE,
        repositoryRoot = "git://https://github.com/jabrena/spring-cloud-contract-git.git",
        ids = {
                "org.jab.microservices:spring-cloud-contract-git-producer-poc:0.1.0-SNAPSHOT",
                "org.jab.microservices:spring-cloud-contract-git-producer-poc2:0.1.0-SNAPSHOT"
        })

@DirtiesContext
public class IntegrationTests {

    private static Logger LOGGER = LoggerFactory.getLogger(IntegrationTests.class);

    @Autowired
    private MockMvc mvc;

    @StubRunnerPort("spring-cloud-contract-git-producer-poc") int producerPort;

    @Autowired
    private BasicController basicController;

    @Before
    public void setupPort() {
        basicController.setPort(producerPort);
    }

    @Test
    public void whenGetConcept1_thenReturnOk() throws Exception {

        mvc.perform(get("/entrypoint")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetConcept1_thenReturnOk_testingDynamicPort() throws Exception {

        mvc.perform(get("/entrypoint")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
