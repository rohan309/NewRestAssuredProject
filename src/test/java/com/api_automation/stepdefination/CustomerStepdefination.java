package com.api_automation.stepdefination;

import com.api_automation.config.ApiRequestBuilder;
import com.api_automation.config.PropertyHandler;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class CustomerStepdefination {
    PropertyHandler propertyHandler = new PropertyHandler("endpoints.properties");
    ApiRequestBuilder apiRequestBuilder = ApiRequestBuilder.getInstance();
    ;

    @Given("I setup request to create an customer")
    public void setUp() {
        String body = "{\n" +
                "    \"name\": \"Sample Projects2\",\n" +
                "    \"archived\": false\n" +
                "}";
        String endPoint = propertyHandler.getProperty("customers");
        apiRequestBuilder.setPostRequest(body, endPoint);

    }


    @When("I hit an api and verify status code {int}")
    public void iHitAnApiAndVerifyStatusCode(int statusCode) {
        System.out.println("Actual Status Code: " + apiRequestBuilder.response.getStatusCode());
        System.out.println(apiRequestBuilder.response.jsonPath());
        Assert.assertEquals(statusCode, apiRequestBuilder.response.statusCode());
    }

    @Then("I verify in the response")
    public void iVerifyInTheResponse() {
        System.out.println(apiRequestBuilder.response.jsonPath().getString("name"));
        System.out.println(apiRequestBuilder.response.getBody().toString());
    }

    @Given("I setup request to create an customer by json file")
    public void iSetupRequestToCreateAnCustomerByJsonFile() {
        String filePath = "src/main/resources/CreateUser.json";


        String body = String.valueOf(apiRequestBuilder.setRequestBodyWithFile(filePath));
        String endPoint = propertyHandler.getProperty("customers");
        apiRequestBuilder.setPostRequest(body, endPoint);
    }
}
