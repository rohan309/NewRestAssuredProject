package com.api_automation.pojo;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TaskPogo {
    private String name;
    private String description;
    private String status;
    private int projectId;
    private int typeOfWorkId;
    private int estimatedTime;
}
