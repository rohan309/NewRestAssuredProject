package com.api_automation;

import com.api_automation.config.PropertyHandler;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.cucumber.java.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Hooks {
    public static WireMockServer wireMockServer;
    public static String responseBody;

    @BeforeAll
    public static void beforeAll() {
        //1. Runs once before All scenarios
        System.out.println("*** Inside BeforeAll hook ***");

        String jsonFilePath = "src/main/resources/MockServerData.json";
//        responseBody = null;
        try {
            responseBody = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (wireMockServer == null) {
            PropertyHandler propertyHandler = new PropertyHandler("config.properties");
            int port = Integer.parseInt(propertyHandler.getProperty("port"));
            wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(port));
            wireMockServer.start();
            WireMock.configureFor(port);
            System.out.println("WireMock server started on port " + port);

            WireMock.stubFor(WireMock.get("/aadhar-data")
                    .willReturn(WireMock.aResponse()
                            .withStatus(Integer.parseInt(propertyHandler.getProperty("mockServerStatusCode")))
                            .withHeader("Content-Type", "application/json")
                            .withBody(responseBody)));

        }

    }

    @Before
    public void beforeScenario() {
        // 2. Runs before each scenario
        System.out.println("*** Inside Before hook ***");
    }

    @BeforeStep
    public void beforeStep() {
        // 3. Runs before each step in a scenario
        System.out.println("*** Inside BeforeStep hook ***");
    }

    @AfterStep
    public void afterStep() {
        // 4. Runs after each step in a scenario
        System.out.println("*** Inside AfterStep hook ***");
    }

    @After
    public void afterScenario() {
        // 5. Runs after each scenario
        System.out.println("*** Inside After hook ***");
        System.out.flush();
    }

    @AfterAll
    public static void afterAll() {
        //6. Runs once after all scenarios
        System.out.println("*** Inside AfterAll hook ***");
        if (wireMockServer != null && wireMockServer.isRunning()) {
            wireMockServer.stop();
            System.out.println("WireMock server stopped.");
        }
    }
}

