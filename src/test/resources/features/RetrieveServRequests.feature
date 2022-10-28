Feature: Retrieve a Service Request
  In order to use the app
  As a user
  I want to retrieve service requests


  Background:
    Given There is a registered user with username "user" and password "password" and email "user@sample.app"
    And There is a registered user with username "Antonio" and password "123" and email "Antonio@gmail.com"
    And I login as "user" with password "password"
    And There is a service request created with name "croquetas", price 50, description "las mejores cocretas de la mama" by "user"
    And There is a service request created with name "croquetas 2.0", price 100, description "ahora con más jamón" by "Antonio"
    And There are 2 service request created
    And The response code is 201


  Scenario: Retrieve my own service requests successfully
    Given I can login with username "user" and password "password"
    And The response code is 200
    When I retrieve my own created service requests
    Then The response code is 200
    And I see 1 service requests from "user"

  Scenario: Retrieve other user's service requests
    Given I can login with username "user" and password "password"
    And The response code is 200
    When I retrieve service requests from user "Antonio"
    Then The response code is 200
    And I see 1 service requests from "Antonio"

  Scenario: Retrieve user's own service requests but user is not logged in
    Given I'm not logged in
    When I retrieve my own created service requests
    Then The response code is 401
    And I can't see any service request

  Scenario: Retrieve other user's service requests but user is not logged in
    Given I'm not logged in
    When I retrieve service requests from user "Antonio"
    Then The response code is 401
    And I can't see any service request