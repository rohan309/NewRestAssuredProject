package com.api_automation.stepdefination;

import com.api_automation.config.ApiRequestBuilder;
import com.api_automation.config.PropertyHandler;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class CustomerStepdefination {
    PropertyHandler propertyHandler=new PropertyHandler("endpoints.properties");
    ApiRequestBuilder apiRequestBuilder=ApiRequestBuilder.getInstance();;
    @Given("I setup request to create an customer")
    public void setUp(){
        String body="{\n" +
                "    \"name\": \"Sample Projects1\",\n" +
                "    \"archived\": false\n" +
                "}";
        String endPoint= propertyHandler.getProperty("customers");
        apiRequestBuilder.setPostRequest(body,endPoint);

    }


    @When("I hit an api and verify status code {int}")
    public void iHitAnApiAndVerifyStatusCode(int statusCode) {
        Assert.assertEquals(statusCode,apiRequestBuilder.response.statusCode());
    }

    @Then("I verify in the response")
    public void iVerifyInTheResponse() {
        System.out.println(apiRequestBuilder.response.jsonPath().getString("name"));
    }
}
