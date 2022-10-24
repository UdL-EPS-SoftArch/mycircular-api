Feature: Delete a Request

  Background:
    Given There is a registered user with username "user" and password "password" and email "user@sample.app"

  Scenario:
    Given I can login with username "user" and password "password"
    And There is an offer created with name "croqueta2", price 100, description "le hago la competencia a la mama" and offerer named "Paco"
    And There are 1 offer created
    And There is a request created with name "croqueta2", price 100, description "le hago la competencia a la mama" by "user"
    Then There are 1 request created
    When I delete a request with id "2"
    And The response code is 204
    And I want to check that the request doesn't exist anymore

  Scenario:
    Given I'm not logged in
    And There is an offer already created
    And There is a request already created
    When I delete a request with id "2"
    Then The response code is 401
    And I want to check that the request still exist
