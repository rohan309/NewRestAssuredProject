package com.api_automation.stepdefination;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

public class WireMockServer {

    static com.github.tomakehurst.wiremock.WireMockServer wireMockServer;
    Response response;

    @Before
    public void setUp() {
        if (wireMockServer == null || !wireMockServer.isRunning()) {
            wireMockServer = new com.github.tomakehurst.wiremock.WireMockServer(8080);
            wireMockServer.start();
            configureFor("localhost", 8080);

            String mockData=System.getProperty("user.dir")+"/src/main/resources/MockServerData.json";

            String responseBody=null;

            try {
                responseBody = new String(Files.readAllBytes(Paths.get(mockData)));
            } catch (IOException e) {
                throw new RuntimeException("Failed to load mock JSON data: " + e.getMessage());
            }


            stubFor(get(urlEqualTo("/countries"))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", ContentType.JSON.toString())
                            .withBody(responseBody)));
        }
    }

    @After
    public void tearDown() {
        if (wireMockServer != null && wireMockServer.isRunning()) {
            wireMockServer.stop();
        }
    }

    @Given("I hit an api to get countries from wiremock server")
    public void getWiremockContent() {
        response = given()
                .baseUri("http://localhost:8080")
                .log().all()
                .header("Accept", ContentType.JSON)
                .when().get("/countries");

//        response.prettyPrint();
    }

    @Then("I verify the response of wiremock server")
    public void iVerifyTheResponseOfWiremockServer() {

        System.out.println(response.getStatusCode());
        Map<String,List<Map<String,Object>>> aadharData=response.as(new TypeRef<Map<String, List<Map<String, Object>>>>() {});

        List<Map<String, Object>> listOfNames=aadharData.get("aadhar_cards");
        String status=testAadharNumber(listOfNames,"Priya Sharma",234567890124l);
        System.out.println(status);

    }

    public String testAadharNumber(List<Map<String, Object>> listOfNames, String nameToMatch, long expectedAadharNumber) {
        for (Map<String, Object> record : listOfNames) {
            String name = (String) record.get("name");
            String aadharStr = (String) record.get("aadhar_number");

            if (name.equals(nameToMatch)) {
                long actualAadharNumber = Long.parseLong(aadharStr);
                if (actualAadharNumber == expectedAadharNumber) {
                    return "Aadhar number exists";
                } else {
                    return "Name found but Aadhar number does not match";
                }
            }
        }
        return "Name not found";
    }


}
