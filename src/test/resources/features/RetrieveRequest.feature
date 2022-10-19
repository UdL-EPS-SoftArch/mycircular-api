Feature: Retrieve a Request
  In order to use the app
  As a user
  I want to retrieve requests


  Background:
    Given There is a registered user with username "user" and password "password" and email "user@sample.app"
    And There is a request created with name "croquetas", price 50, description "las mejores cocretas de la mama" by "user"

    And There are 1 request created
    And The response code is 201


    Scenario: Retrieve a list of request successfully
      Given I can login with username "user" and password "password"
      And The response code is 200
      When I retrieve a list of requests