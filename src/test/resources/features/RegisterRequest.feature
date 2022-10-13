Feature: Create a Request
  In order to use the app
  As a user
  I want to create a new Request


  #Scenario: Register new request
    # Given I'm not logged in
    # When I register a new request with name "croquetas", price "50", description "tremenda croqueta bro, por la gloria de mi mama" and requester "user"
    # Then Server responds with page containing "You are not logged in"
    # And There is "Log in" link available

  Background:
  Given There is a registered user with username "user" and password "password" and email "user@sample.app"

  Scenario: Create new request successfully.
    Given I can login with username "user" and password "password"
    And The response code is 200
    And There is an offer created
    When I Create a new request
    Then There is a request created
    And The response code is 201

  Scenario: Create new Request but not logged in.
    Given I'm not logged in
    And There is an offer created with name "croqueta2", price 100, description "le hago la competencia a la mama" and offerer named "Paco"
    When I Create a new request
    Then The response code is 401