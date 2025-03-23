package com.api_automation.stepdefination;

import com.api_automation.config.ApiRequestBuilder;
import com.api_automation.pojo.TaskPogo;
import com.api_automation.pojo.TaskResponsePojo;
import com.github.javafaker.Faker;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class TaskStepdefination {
    ApiRequestBuilder apiRequestBuilder = ApiRequestBuilder.getInstance();
    TaskPogo taskPogo;

    @Given("I setup request to create a task")
    public void setUp(List<Map<String, Object>> data) {
        Map<String, Object> dataTable = data.get(0);
        taskPogo = new TaskPogo();
        Faker faker = new Faker();
        String name = null;
        if (dataTable.get("name").equals("Random")) {
            name = faker.job().title();
        } else {
            name = (String) dataTable.get("name");
        }
        taskPogo.setName(name);

       /*String description = null;
        if (dataTable.get("description").equals("Random")) {
            description = faker.lorem().sentence();
        } else {
            description = (String) dataTable.get("description");
        }*/
        String description = dataTable.get("description").equals("Random") ? faker.lorem().sentence() : (String) dataTable.get("description");
        taskPogo.setDescription(description);
        taskPogo.setStatus((String) dataTable.get("status"));
        taskPogo.setProjectId(Integer.parseInt(dataTable.get("projectId").toString()));
        taskPogo.setTypeOfWorkId(Integer.parseInt(dataTable.get("typeOfWorkId").toString()));
        taskPogo.setEstimatedTime(Integer.parseInt(dataTable.get("estimatedTime").toString()));

    }

    @When("I hit an api to create task")
    public void iHitAnApiToCreateTask(Map<String, String> dataTable) {
        String endPoint = dataTable.get("endpoint");
        apiRequestBuilder.setPostRequest(taskPogo, endPoint);

    }

    @Then("I verify the created task")
    public void iVerifyTheCreatedTask(Map<String, Object> dataTable) {
        TaskResponsePojo taskResponsePojo = apiRequestBuilder.response.as(TaskResponsePojo.class);
        int expectedStatusCode = Integer.parseInt(dataTable.get("statusCode").toString());
        int actualStatusCode = apiRequestBuilder.response.statusCode();
        Assert.assertEquals(expectedStatusCode, actualStatusCode);
        Assert.assertEquals(taskPogo.getName(), taskResponsePojo.getName());
        Assert.assertEquals(taskPogo.getDescription(),taskResponsePojo.getDescription());
        Assert.assertEquals(taskPogo.getStatus(),taskResponsePojo.getStatus());
        Assert.assertEquals(taskPogo.getProjectId(),taskResponsePojo.getProjectId());
        Assert.assertEquals(taskPogo.getTypeOfWorkId(),taskResponsePojo.getTypeOfWorkId());
        Assert.assertEquals(taskPogo.getEstimatedTime(),taskResponsePojo.getEstimatedTime());

    }
}
