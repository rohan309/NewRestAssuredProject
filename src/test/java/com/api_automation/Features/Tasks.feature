Feature: Verify task functionality

  @SerializationDeserialization
  Scenario: Verify user is able to create the task
    Given I setup request to create a task
      | name   | description | status | projectId | typeOfWorkId | estimatedTime |
      | Random | Random      | open   | 16        | 2            | 120           |
    When I hit an api to create task
      | endpoint | tasks |

    Then I verify the created task
      | statusCode | 200 |
