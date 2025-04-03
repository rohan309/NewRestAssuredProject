Feature: Verify customer functionality

  @CreateCustomer
  Scenario: Verify user is able to create customer
    Given I setup request to create an customer
    When I hit an api and verify status code 200
    Then I verify in the response

  @CreateCustomerByJson
  Scenario: Verify user is able to create customer by json file
    Given I setup request to create an customer by json file
    When I hit an api and verify status code 400
    Then I verify in the response

  @GetAllCustomer
  Scenario: Verify user is able get the list of all customer
    Given I setup request to get all customers
    When I hit an api and verify status code 200
    Then I verify list of  all customers

  @CreateCustomerByPojo
  Scenario: Verify user is able to create a customer using POJO
    Given I setup request to create an customer by pojo class
      | name   | archived | description |
      | Random | false    | Random      |

    When I hit an api and verify status code
      | method | statusCode |
      | Post   | 200        |

    Then I verify created customer in the list
      | method | statusCode |
      | Get    | 200        |

  @VerifyCustomerInList
  Scenario Outline: Verify user is able to create multiple customer
    Given I setup request to create an customer to create multiple customers
      | name        | <name>        |
      | archived    | <archived>    |
      | description | <description> |
    When I hit an post api to create customers
      | endPoint   | <endPoint>   |
      | statusCode | <statusCode> |
    And I hit an api to get all customers
      | endPoint   | <endPoint>   |
      | statusCode | <statusCode> |
    Then I verify created customer is present in the list of customers
      | endPoint   | <endPoint>   |
      | statusCode | <statusCode> |
    Examples:
      | name   | archived | description | endPoint  | statusCode |
      | Random | false    | Random      | customers | 200        |
