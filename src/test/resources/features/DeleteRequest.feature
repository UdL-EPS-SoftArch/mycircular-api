Feature: Delete a Request
  In order to use the app
  As a user
  I want to delete requests

  Background:
    Given There is a registered user with username "user" and password "password" and email "user@sample.app"
    And There is a registered user with username "nene" and password "password" and email "nene@gmail.com"
    And I can login with username "user" and password "password"
    And There is an offer created with name "croqueta2", price 100, description "le hago la competencia a la mama" and offerer named "Paco"
    And There are 1 offer created
    And There is a request created with name "croqueta2", price 100, description "le hago la competencia a la mama" by "user"
    And There is a request created with name "croquetas 2.0", price 200, description "mama soy tu mayor fan" by "nene"
    And There are 2 request created

  Scenario: Delete own requests successfully
    When I delete my own created requests
    Then The response code is 204

    When I retrieve my own created requests
    Then I can't see any request

    When I retrieve requests from user "nene"
    Then I see 1 request from "nene"


  Scenario: Delete other user's requests sucessfully
    When I delete requests from user "nene"
    Then The response code is 403
    And There are 2 request created

    When I retrieve requests from user "nene"
    Then I see 1 request from "nene"


  Scenario: Delete request that doesn't exist
  #todo tendria que repetir este test para retrieve
    When I delete a request with name "request inventada", price 37, description "fake request" by "user"
    Then The request is not found
    And There are 2 request created

  Scenario: Delete a specific request
    When I delete a request with name "croqueta2", price 100, description "le hago la competencia a la mama" by "user"
    Then The response code is 204
    And I want to check that the request with name "croqueta2", price 100, description "le hago la competencia a la mama" by "user" doesn't exist

  Scenario: Delete requests but user is not logged in
    Given I'm not logged in
    When I delete a request with name "croqueta2", price 100, description "le hago la competencia a la mama" by "user"
    Then The response code is 401
    And I want to check that the request still exist

    Scenario: Delete requests from a non existing user
      When I delete requests from user "Mondongo"
      Then The response code is 404
      And There are 2 request created