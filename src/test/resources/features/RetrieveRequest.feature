Feature: Retrieve a Request
  In order to use the app
  As a user
  I want to retrieve requests

#ver las request de alguien que no tenga nada
  #ver requests que no existen
  #ver requests de un usuario que no existe
  Background:
    Given There is a registered user with username "user" and password "password" and email "user@sample.app"
    And There is a registered user with username "Antonio" and password "123" and email "Antonio@gmail.com"
    And I login as "user" with password "password"
    And There is a request created with name "croquetas", price 50, description "las mejores cocretas de la mama" by "user"
    And There is a request created with name "croquetas 2.0", price 100, description "ahora con más jamón" by "Antonio"
    And There are 2 request created
    And The response code is 201


  Scenario: Retrieve my own requests successfully
    Given I can login with username "user" and password "password"
    And The response code is 200
    When I retrieve my own created requests
    Then The response code is 200
    And I see 1 requests from "user"

  Scenario: Retrieve other user's requests
    Given I can login with username "user" and password "password"
    And The response code is 200
    When I retrieve requests from user "Antonio"
    Then The response code is 200
    And I see 1 requests from "Antonio"

  Scenario: Retrieve user's own requests but user is not logged in
    Given I'm not logged in
    When I retrieve my own created requests
    Then The response code is 401
    #And I can't see any request
    And I'm not allowed to see any request

  Scenario: Retrieve other user's requests but user is not logged in
    Given I'm not logged in
    When I retrieve requests from user "Antonio"
    Then The response code is 401
    #And I can't see any request
    And I'm not allowed to see any request


