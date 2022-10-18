Feature: Delete review


  Background:
    Given There is a registered user with username "user0" and password "password0" and email "user0@sample.app"
    Given There is a registered user with username "user1" and password "password1" and email "user1@sample.app"


  Scenario: Delete review as a user
    Given I can login with username "user0" and password "password0"
    And I submit a new review with username "user0", number of stars 4 and message "Great" to a seller "user1"
    When I delete the last created rating
    Then The response code is 204
    And The rating was deleted

  Scenario: Delete rating when not authenticated
    Given I'm not logged in
    When I delete a review with id 3
    Then The response code is 401
    And The rating with id 3 was not deleted