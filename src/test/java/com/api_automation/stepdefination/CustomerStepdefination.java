package com.api_automation.stepdefination;

import com.api_automation.Hooks;
import com.api_automation.config.ApiRequestBuilder;
import com.api_automation.config.PropertyHandler;
import com.api_automation.pojo.CustomerPojo;
import com.github.javafaker.Faker;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class CustomerStepdefination {
    PropertyHandler propertyHandler = new PropertyHandler("endpoints.properties");
    ApiRequestBuilder apiRequestBuilder = ApiRequestBuilder.getInstance();

    CustomerPojo customerPojo;

    @Given("I setup request to create an customer")
    public void setUp() {
        Random random = new Random();
        int randomNumber = 1000 + random.nextInt(9000);
        String body = "{\n" +
                "    \"name\": \"Sample Project" + randomNumber + "\",\n" +
                "    \"archived\": false\n" +
                "}";
        String endPoint = propertyHandler.getProperty("customers");
        apiRequestBuilder.setPostRequest(body, endPoint);

    }


    @When("I hit an api and verify status code {int}")
    public void iHitAnApiAndVerifyStatusCode(int statusCode) {
        System.out.println("Actual Status Code: " + apiRequestBuilder.response.getStatusCode());
        Assert.assertEquals(statusCode, apiRequestBuilder.response.statusCode());
    }

    @Then("I verify in the response")
    public void iVerifyInTheResponse() {
        System.out.println(apiRequestBuilder.response.jsonPath().getString("name"));


    }

    @Given("I setup request to create an customer by json file")
    public void iSetupRequestToCreateAnCustomerByJsonFile() {
        String filePath = "src/main/resources/CreateUser.json";

        String body = String.valueOf(apiRequestBuilder.setRequestBodyWithFile(filePath));
        String endPoint = propertyHandler.getProperty("customers");
        apiRequestBuilder.setPostRequest(body, endPoint);
    }

    @Given("I setup request to get all customers")
    public void iSetupRequestToGetAllCustomers() {
        apiRequestBuilder.getRequestWithOutPathParam(propertyHandler.getProperty("customers"));
    }

    @Then("I verify list of  all customers")
    public void iVerifyListOfAllCustomers() {
        System.out.println(apiRequestBuilder.response.prettyPrint());
        List<String> listOfCustomers = apiRequestBuilder.response.jsonPath().getList("items.name");
//        listOfCustomers.forEach(System.out::println);
    }

    @Given("I setup request to create an customer by pojo class")
    public void iSetupRequestToCreateAnCustomerByPojoClass(DataTable data) {
        Map<String, String> dataTable = data.asMaps().get(0);

        customerPojo = new CustomerPojo();
        Faker faker = new Faker();
        String name = null;

        if (dataTable.get("name").equals("Random")) {
            name = faker.company().name();
        } else {
            name = dataTable.get("name");
        }
        customerPojo.setName(name);

        boolean archived = Boolean.parseBoolean(dataTable.get("archived"));
        customerPojo.setArchived(archived);
        /*if (data.asMaps().isEmpty()) {
            throw new RuntimeException("DataTable is empty. Cannot proceed.");
        }*/

        String description = null;
        if (dataTable.get("description").equals("Random")) {
            description = faker.lorem().sentence();
        } else {
            description = dataTable.get("description");
        }
        customerPojo.setDescription(description);

        String endPoint = propertyHandler.getProperty("customers");
        apiRequestBuilder.setPostRequest(customerPojo, endPoint);

    }

    @Then("I verify created customer in the list")
    public void iVerifyCreatedCustomerInTheList(DataTable table) {
        Map<String, String> dataTable = table.asMaps().get(0);
        int statusCode = apiRequestBuilder.response.statusCode();
        Assert.assertEquals(String.valueOf(statusCode), dataTable.get("statusCode"));
        String expectedName = customerPojo.getName();
        String actualName = apiRequestBuilder.response.jsonPath().get("name");
        Assert.assertEquals(expectedName, actualName);
    }

    @When("I hit an api and verify status code")
    public void iHitAnApiAndVerifyStatusCode(DataTable table) {
        Map<String, String> dataTable = table.asMaps().get(0);
        Assert.assertEquals(apiRequestBuilder.response.statusCode(), Integer.parseInt(dataTable.get("statusCode")));
        apiRequestBuilder.response.prettyPrint();
    }

    @Given("I setup request to create an customer to create multiple customers")
    public void iSetupRequestToCreateAnCustomerToCreateMultipleCustomers(Map<String, Object> dataTable) {
        String name;
        if (dataTable.get("name").equals("Random")) {
            Faker faker = new Faker();
            name = faker.company().name();
        } else {
            name = (String) dataTable.get("name");
        }
        customerPojo = new CustomerPojo();
        customerPojo.setName(name);

        Boolean archived = Boolean.parseBoolean(dataTable.get("archived").toString());
        customerPojo.setArchived(archived);

        String description = null;
        if (dataTable.get("description").equals("Random")) {
            Faker faker = new Faker();
            description = faker.lorem().sentence();
        } else {
            description = (String) dataTable.get("description");
        }
        customerPojo.setDescription(description);
    }

    @When("I hit an post api to create customers")
    public void iHitAnPostApiToCreateCustomers(Map<String, Object> dataTable) {
        String endPoint = (String) dataTable.get("endPoint");
        apiRequestBuilder.setPostRequest(customerPojo, endPoint);
        int statusCode = Integer.parseInt(dataTable.get("statusCode").toString());
        Assert.assertEquals(statusCode, apiRequestBuilder.response.statusCode());
    }

    @And("I hit an api to get all customers")
    public void iHitAnApiToGetAllCustomers(Map<String, Object> dataTable) {
        String endPoint = (String) dataTable.get("endPoint");
        apiRequestBuilder.getRequestWithOutPathParam(endPoint);
        int statusCode = Integer.parseInt(dataTable.get("statusCode").toString());
        Assert.assertEquals(statusCode, apiRequestBuilder.response.statusCode());
    }

    @Then("I verify created customer is present in the list of customers")
    public void iVerifyCreatedCustomerIsPresentInTheListOfCustomers(Map<String, String> dataTable) {
        List<String> namesOfCustomers = apiRequestBuilder.response.jsonPath().getList("items.name");
        Assert.assertTrue(namesOfCustomers.contains(customerPojo.getName()));
    }
}
