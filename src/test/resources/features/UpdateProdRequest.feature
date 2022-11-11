Feature: Update Request
  In order to use the app
  As a user
  I want to update requests

  Background:
    Given There is a registered user with username "user" and password "password" and email "user@sample.app"
    And There is a registered user with username "nene" and password "password" and email "nene@gmail.com"
    And I can login with username "user" and password "password"
    And There is an offer created with name "croqueta2", price 100, description "le hago la competencia a la mama" and offerer named "Paco"
    And There are 1 offer created
    And There is a product request created with name "croqueta2", price 100, description "le hago la competencia a la mama" by "user"
    And There is a product request created with name "croquetas 2.0", price 200, description "mama soy tu mayor fan" by "nene"
    And There are 2 product request created

  Scenario: Modify own product request (patch)
    When I modify my own created product requests with price 300
    Then The response code is 403
    When I retrieve my own created product requests
    Then The price of my product request I tried to change is not 300

  Scenario: Modify other's user product request (patch)
    When I modify "nene"'s product requests with price 300
    Then The response code is 403
    When I retrieve my own created product requests
    Then The price of "nene"'s product request I tried to change is not 300