Feature: Delete a Service Request

  Background:
    Given There is a registered user with username "user" and password "password" and email "user@sample.app"
    And There is a registered user with username "nene" and password "password" and email "nene@gmail.com"
    And I can login with username "user" and password "password"
    And There is an offer created with name "croqueta2", price 100, description "le hago la competencia a la mama" and offerer named "Paco"
    And There are 1 offer created
    And There is a service request created with name "croqueta2", price 100, description "le hago la competencia a la mama" by "user"
    And There is a service request created with name "croquetas 2.0", price 200, description "mama soy tu mayor fan" by "nene"
    And There are 2 service request created

  Scenario: Delete own service requests successfully
    When I delete my own created service requests
    Then The response code is 204

    When I retrieve my own created service requests
    Then I can't see any service request

    When I retrieve service requests from user "nene"
    Then I see 1 service request from "nene"


  Scenario: Delete other user's service requests sucessfully
    When I delete service requests from user "nene"
    Then The response code is 403
    And There are 2 service request created

    When I retrieve service requests from user "nene"
    Then I see 1 service request from "nene"


  Scenario: Delete service request that doesn't exist
  #todo tendria que repetir este test para retrieve
    When I delete a service request with name "request inventada", price 37, description "fake request" by "user"
    Then The service request is not found
    And There are 2 service request created

  Scenario: Delete a specific service request
    When I delete a service request with name "croqueta2", price 100, description "le hago la competencia a la mama" by "user"
    Then The response code is 204
    And I want to check that the service request with name "croqueta2", price 100, description "le hago la competencia a la mama" by "user" doesn't exist

  Scenario: Delete service requests but user is not logged in
    Given I'm not logged in
    When I delete a service request with name "croqueta2", price 100, description "le hago la competencia a la mama" by "user"
    Then The response code is 401
    And I want to check that the service request still exist

  Scenario: Delete service requests from a non existing user
    When I delete service requests from user "Mondongo"
    Then The response code is 404
    And There are 2 service request created