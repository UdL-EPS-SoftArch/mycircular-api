Feature: Create a Offer
  In order to use the app
  As a user
  I want to create a new offer

  Scenario: Register new offer
    Given There is no registered user with username "user"
    And I'm not logged in
    When I register a new user with username "user", email "user@sample.app" and password "password"
    Then The response code is 201
    And It has been created a user with username "user" and email "user@sample.app", the password is not returned
    And I can login with username "user" and password "password"