package com.api_automation.stepdefination;

import com.api_automation.config.ApiRequestBuilder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CustomerStepdefination {
    ApiRequestBuilder apiRequestBuilder=ApiRequestBuilder.getInstance();;
    @Given("I setup request to create an customer")
    public void setUp(){
//        apiRequestBuilder.setPostRequest();
    }

    @When("I hit an api")
    public void iHitAnApi() {
    }

    @Then("I verify status code code in the response")
    public void iVerifyStatusCodeCodeInTheResponse() {
    }
}
