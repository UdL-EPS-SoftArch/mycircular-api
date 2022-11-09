Feature: Delete a Request

  Background:
    Given There is a registered user with username "user" and password "password" and email "user@sample.app"
    And There is a registered user with username "nene" and password "password" and email "nene@gmail.com"

  Scenario: Delete own requests successfully
    Given I can login with username "user" and password "password"
    And There is an offer created with name "croqueta2", price 100, description "le hago la competencia a la mama" and offerer named "Paco"
    And There are 1 offer created
    And There is a request created with name "croqueta2", price 100, description "gimme dat croquetas" by "user"
    And There is a request created with name "croquetas 2.0", price 200, description "mama soy tu mayor fan" by "nene"
    And There are 2 request created

    When I delete my own created requests
    Then The response code is 204

    When I retrieve my own created requests
    Then I can't see any request

    When I retrieve requests from user "nene"
    Then I see 1 request from "nene"


  Scenario: Delete other user's requests sucessfully
  Scenario: Delete requests that don't exist
  #tendria que repetir este test para retrieve

  Scenario: Delete a specific request
    Given I can login with username "user" and password "password"
    And There is an offer created with name "croqueta2", price 100, description "le hago la competencia a la mama" and offerer named "Paco"
    And There are 1 offer created
    And There is a request created with name "croqueta2", price 100, description "le hago la competencia a la mama" by "user"
    Then There are 1 request created
    When I delete a request with name "croqueta2", price 100, description "le hago la competencia a la mama" by "user"
    And The response code is 204
    And I want to check that the request with name "croqueta2", price 100, description "le hago la competencia a la mama" by "user" doesn't exist anymore
    #Si no hardcodeamos la id, no podemos buscar por atributos porque peta
    #And The response code is 404

  Scenario: Delete requests but user is not logged in
    Given I'm not logged in
    And There is an offer already created
    And There is a request already created
    When I delete a request with id "2"
    Then The response code is 401
    And I want to check that the request still exist

  Scenario: Forbidden delete
    Given I can login with username "user" and password "password"
    And There is an offer already created
    And There is a request already created
    When I delete a request with id "2"
    Then The response code is 403
    And I want to check that the request still exist