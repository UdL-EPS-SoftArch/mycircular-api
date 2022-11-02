Feature: Retrieve review
  In order to see reviews
  As a user
  I want to list all the reviews

  Background:
    Given There is a registered user with username "user0" and password "password0" and email "user0@sample.app"
    Given There is a registered user with username "user1" and password "password1" and email "user1@sample.app"
    Given There is a registered admin with username "admin" and password "password" and email "admin@local.com"
    And There is a review with id 1, number of stars 5 and message "Fantastic!" from user "user0" to user "user1"
    And There is a review with id 2, number of stars 2 and message "Terrible!" from user "user1" to user "user0"

  Scenario: List all reviews as a user
    Given I can login with username "user0" and password "password0"
    When I list all the reviews
    Then The response code is 200
    And The number of reviews are 2

  Scenario: Show a review by id as a user
    Given I can login with username "user0" and password "password0"
    When I list the review with id 2
    Then The response code is 200
    And A review with number of stars 2 and message "Terrible!" is returned

  Scenario: Show review by number of stars
    Given  I can login with username "user0" and password "password0"
    When I list the review with number of stars 5
    Then The response code is 200
    And The number of reviews are 1

  Scenario: Show review by message
    Given  I can login with username "user0" and password "password0"
    When I list the review with message "Terrible!"
    Then The response code is 200
    And The number of reviews are 1

  Scenario: Show all reviews for one user
    Given I can login with username "user0" and password "password0"
    When I list all reviews for user "user0"
    Then The response code is 200
    And The number of reviews are 1

  Scenario: Show all reviews that one user has made
    Given I can login with username "user0" and password "password0"
    When I list all reviews that user "user1" has already made
    Then The response code is 200
    And The number of reviews are 1

  Scenario: List all reviews as admin
    Given I can login with username "admin" and password "password"
    When I list all the reviews
    Then The response code is 200
    And The number of reviews are 2





