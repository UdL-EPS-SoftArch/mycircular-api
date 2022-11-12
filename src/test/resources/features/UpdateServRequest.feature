Feature: Update Service Request
  In order to use the app
  As a user
  I want to update requests

  Background:
    Given There is a registered user with username "user" and password "password" and email "user@sample.app"
    And There is a registered user with username "nene" and password "password" and email "nene@gmail.com"
    And I can login with username "user" and password "password"
    And There is an offer created with name "croqueta2", price 100, description "le hago la competencia a la mama" and offerer named "Paco"
    And There are 1 offer created
    And There is a service request created with name "croqueta2", price 100, description "le hago la competencia a la mama" by "user"
    And There is a service request created with name "croquetas 2.0", price 200, description "mama soy tu mayor fan" by "nene"
    And There are 2 service request created

  Scenario: Modify own service request (patch)
    When I modify my own created service requests with price 300
    Then The response code is 403
    When I retrieve my own created service requests
    Then The price of my service request I tried to change is not 300

  Scenario: Modify other's user service request (patch)
    When I modify "nene"'s service requests with price 300
    Then The response code is 403
    When I retrieve my own created service requests
    Then The price of "nene"'s service request I tried to change is not 300

  Scenario: Modify service request that doesn't exist (patch)
    When I modify a service request with name "request inventada", price 37, description "fake request" by "user" with new price 300
    Then The service request to modify is not found

  Scenario: Modify service request being not logged in (patch)
    Given I'm not logged in
    When I modify a service request with name "croqueta2", price 100, description "le hago la competencia a la mama" by "user" with new price 300
    Then The response code is 401

  Scenario: Modify own service request (put)
    When I modify my own created service requests with price 300 (put)
    Then The response code is 403
    When I retrieve my own created service requests
    Then The price of my service request I tried to change is not 300

  Scenario: Modify other's user service request (put)
    When I modify "nene"'s service requests with price 300 (put)
    Then The response code is 403
    When I retrieve my own created service requests
    Then The price of "nene"'s service request I tried to change is not 300

  Scenario: Modify service request that doesn't exist (put)
    When I modify a service request with name "request inventada", price 37, description "fake request" by "user" with new price 300 (put)
    Then The service request to modify is not found

  Scenario: Modify service request being not logged in (put)
    Given I'm not logged in
    When I modify a service request with name "croqueta2", price 100, description "le hago la competencia a la mama" by "user" with new price 300 (put)
    Then The response code is 401