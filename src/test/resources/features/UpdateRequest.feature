Feature: Update Request
  In order to use the app
  As a user
  I want to update requests


  Background:
    Given There is a registered user with username "user" and password "password" and email "user@sample.app"
    And There is a registered user with username "nene" and password "password" and email "nene@gmail.com"
    And I can login with username "user" and password "password"
    And There is an offer created with name "croqueta2", price 100, description "le hago la competencia a la mama" and offerer named "Paco"
    And There are 1 offer created
    And There is a request created with name "croqueta2", price 100, description "le hago la competencia a la mama" by "user"
    And There is a request created with name "croquetas 2.0", price 200, description "mama soy tu mayor fan" by "nene"
    And There are 2 request created

  Scenario: Modify own request (patch)
    When I modify my own created requests with price 300
    Then The response code is 403
    When I retrieve my own created requests
    Then The price of my request I tried to change is not 300

  Scenario: Modify other's user request (patch)
    When I modify "nene"'s requests with price 300
    Then The response code is 403
    When I retrieve my own created requests
    Then The price of "nene"'s request I tried to change is not 300

  Scenario: Modify request that doesn't exist (patch)
    When I modify a request with name "request inventada", price 37, description "fake request" by "user" with new price 300
    Then The request to modify is not found

  Scenario: Modify request being not logged in (patch)
    Given I'm not logged in
    When I modify a request with name "croqueta2", price 100, description "le hago la competencia a la mama" by "user" with new price 300
    Then The response code is 401

  Scenario: Modify own request (put)
    When I modify my own created requests with price 300 (put)
    Then The response code is 403
    When I retrieve my own created requests
    Then The price of my request I tried to change is not 300

  Scenario: Modify other's user request (put)
    When I modify "nene"'s requests with price 300 (put)
    Then The response code is 403
    When I retrieve my own created requests
    Then The price of "nene"'s request I tried to change is not 300

  Scenario: Modify request that doesn't exist (put)
    When I modify a request with name "request inventada", price 37, description "fake request" by "user" with new price 300 (put)
    Then The request to modify is not found

  Scenario: Modify request being not logged in (put)
    Given I'm not logged in
    When I modify a request with name "croqueta2", price 100, description "le hago la competencia a la mama" by "user" with new price 300 (put)
    Then The response code is 401
