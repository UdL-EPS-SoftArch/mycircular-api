Feature: Delete a Product Request

  Background:
    Given There is a registered user with username "user" and password "password" and email "user@sample.app"
    And There is a registered user with username "nene" and password "password" and email "nene@gmail.com"
    And I can login with username "user" and password "password"
    And There is an offer created with name "croqueta2", price 100, description "le hago la competencia a la mama" and offerer named "Paco"
    And There are 1 offer created
    And There is a product request created with name "croqueta2", price 100, description "le hago la competencia a la mama" by "user"
    And There is a product request created with name "croquetas 2.0", price 200, description "mama soy tu mayor fan" by "nene"
    And There are 2 product request created

  Scenario: Delete own product requests successfully
    When I delete my own created product requests
    Then The response code is 204

    When I retrieve my own created product requests
    Then I can't see any product request

    When I retrieve product requests from user "nene"
    Then I see 1 product request from "nene"


  Scenario: Delete other user's product requests sucessfully
    When I delete product requests from user "nene"
    Then The response code is 403
    And There are 2 product request created

    When I retrieve product requests from user "nene"
    Then I see 1 product request from "nene"


  Scenario: Delete product request that doesn't exist
  #todo tendria que repetir este test para retrieve
    When I delete a product request with name "request inventada", price 37, description "fake request" by "user"
    Then The product request is not found
    And There are 2 product request created

  Scenario: Delete a specific product request
    When I delete a product request with name "croqueta2", price 100, description "le hago la competencia a la mama" by "user"
    Then The response code is 204
    And I want to check that the product request with name "croqueta2", price 100, description "le hago la competencia a la mama" by "user" doesn't exist

  Scenario: Delete product requests but user is not logged in
    Given I'm not logged in
    When I delete a product request with name "croqueta2", price 100, description "le hago la competencia a la mama" by "user"
    Then The response code is 401
    And I want to check that the product request still exist

  Scenario: Delete product requests from a non existing user
    When I delete product requests from user "Mondongo"
    Then The response code is 404
    And There are 2 product request created