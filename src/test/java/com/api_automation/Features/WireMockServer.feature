Feature: Verify connection with the wire mock server
  @TestMockData
  Scenario: Verify aadhar number is valid
    Given I hit an api to get countries from wiremock server
    Then I verify the response of wiremock server
