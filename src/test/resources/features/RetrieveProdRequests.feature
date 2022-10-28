Feature: Retrieve a product Request
  In order to use the app
  As a user
  I want to retrieve product requests


  Background:
    Given There is a registered user with username "user" and password "password" and email "user@sample.app"
    And There is a registered user with username "Antonio" and password "123" and email "Antonio@gmail.com"
    And I login as "user" with password "password"
    And There is a product request created with name "croquetas", price 50, description "las mejores cocretas de la mama" by "user"
    And There is a product request created with name "croquetas 2.0", price 100, description "ahora con más jamón" by "Antonio"
    And There are 2 product request created
    And The response code is 201


  Scenario: Retrieve my own product requests successfully
    Given I can login with username "user" and password "password"
    And The response code is 200
    When I retrieve my own created product requests
    Then The response code is 200
    And I see 1 requests from "user"

  Scenario: Retrieve other user's product requests
    Given I can login with username "user" and password "password"
    And The response code is 200
    When I retrieve product requests from user "Antonio"
    Then The response code is 200
    And I see 1 product requests from "Antonio"

  Scenario: Retrieve user's own product requests but user is not logged in
    Given I'm not logged in
    When I retrieve my own created product requests
    Then The response code is 401
    And I can't see any product request

  Scenario: Retrieve other user's product requests but user is not logged in
    Given I'm not logged in
    When I retrieve product requests from user "Antonio"
    Then The response code is 401
    And I can't see any product request