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


    @BeforeAll
    public static void beforeAll() {
        //1. Runs once before All scenarios
        System.out.println("*** Inside BeforeAll hook ***");



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

    }
}

