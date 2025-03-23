package com.api_automation.pojo;

public class CustomerPojo {
    private String name;
    private Boolean archived;
    private String description;

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName(){
        return name;
    }


}
