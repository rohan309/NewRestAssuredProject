/*
package com.api_automation.stepdefination;

import com.api_automation.config.MockServer;
import com.api_automation.config.PropertyHandler;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;

public class AadharTest {
    Response mockServerResponse;
    PropertyHandler propertyHandler = new PropertyHandler("config.properties");

    @Given("I start the mock server")
    public void setUp() {
        MockServer.startMockServer();
    }

    @When("I test the aadhar number")
    public void iTestTheAadharNumber() {
        String aadharNumber = propertyHandler.getProperty("AadharNumber");

        mockServerResponse = RestAssured
                .given()
                .queryParam("aadhar_number", aadharNumber)
                .when()
                .get("http://localhost:8080/aadhar/verify");
    }

    @Then("I verify the server response with statusCode {int}")
    public void iVerifyTheServerResponseWithStatusCode(int statusCode) {
        Assert.assertEquals(statusCode, mockServerResponse.getStatusCode());
        System.out.println("✅ Response:");
        mockServerResponse.prettyPrint();
        MockServer.stopMockServer();
    }
}
*/
