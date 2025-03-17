Feature: Verify customer functionality

  @CreateCustomer
  Scenario: Verify user is able to create customer
    Given I setup request to create an customer
    When I hit an api and verify status code 200
    Then I verify in the response

  @CreateCustomerByJson
  Scenario: Verify user is able to create customer by json file
    Given I setup request to create an customer by json file
    When I hit an api and verify status code 200
    Then I verify in the response