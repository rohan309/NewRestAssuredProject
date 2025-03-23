package com.api_automation.pojo;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponsePojo {
    private int id;
    private String name;
    private String description;
    private Long created;
    private String status;
    private int workflowStatusId;
    private int typeOfWorkId;
    private String url;
    private String projectName;
    private String customerName;
    private String workflowStatusName;
    private String typeOfWorkName;
    private Map<String, Boolean> allowedActions;
    private Object deadline;
    private int estimatedTime;
    private int customerId;
    private int projectId;

    /*public void setProjectId(int projectId){
        this.projectId=projectId;
    }

    public int getProjectId(){
        return projectId;
    }

    public void setAllowedActions(Map<String,Boolean> allowedActions){
        this.allowedActions=allowedActions;
    }
    public Map<String,Boolean> getAllowedActions(){
        return allowedActions;
    }*/

}
