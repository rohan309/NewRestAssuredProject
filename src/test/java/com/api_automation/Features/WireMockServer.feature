Feature: Verify connection with the wire mock server
  @TestMockData
  Scenario: Verify aadhar number is valid
    Given I start the mock server
    When I test the aadhar number
    Then I verify the server response with statusCode 200