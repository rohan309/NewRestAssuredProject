Feature: Verify customer functionality
  Scenario: Verify user is able to create customer
    Given I setup request to create an customer
    When I hit an api
    Then I verify status code code in the response