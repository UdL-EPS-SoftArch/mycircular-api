Feature: Create a ServRequest
  In order to use the app
  As a user
  I want to create a new Request


  #Scenario: Register new request
    # Given I'm not logged in
    # When I register a new request with name "croquetas", price "50", description "tremenda croqueta bro, por la gloria de mi mama" and requester "user"
    # Then Server responds with page containing "You are not logged in"
    # And There is "Log in" link available

  Background:
    Given There is a registered user with username "user" and password "password" and email "user@sample.app"

  Scenario: Create new service request successfully.
    Given I can login with username "user" and password "password"
    And The response code is 200
    And There is an offer created
    And There are 1 offer created
    When I Create a new service request
    Then There are 1 service request created
    And The response code is 201

  Scenario: Create new Service Request but not logged in.
    Given I'm not logged in
    And There is an offer created with name "croqueta2", price 100, description "le hago la competencia a la mama" and offerer named "Paco"
    And There are 1 offer created
    When I Create a new service request
    Then The response code is 401
    And There are 0 service request created

  Scenario: I create new Service Request but it already exists
    Given I can login with username "user" and password "password"
    And The response code is 200
    And There is an offer created with name "croqueta2", price 100, description "le hago la competencia a la mama" and offerer named "Paco"
    And There are 1 offer created
    And There is a service request created with name "croqueta2", price 100, description "le hago la competencia a la mama" by "user"
    And There are 1 service request created
    When I Create a new service request with name "croqueta2", price 100, description "le hago la competencia a la mama"
    Then The response code is 403
    And There are 1 offer created
    And There are 1 service request created