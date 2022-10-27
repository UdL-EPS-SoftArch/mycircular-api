Feature: Submit review
  In order to use the app
  As a buyer
  I want to submit a review to a seller

  Background:
    Given There is a registered user with username "user0" and password "password0" and email "user0@sample.app"
    Given There is a registered user with username "user1" and password "password1" and email "user1@sample.app"

  Scenario: Submit a new review
    Given I can login with username "user0" and password "password0"
    When The buyer submits a new review with username "user0", number of stars 5 and message "Great" to a seller "user1"
    Then The response code is 201
    And It has been submitted a review by buyer with username "user0"

  Scenario: Submit review when I am not authenticated
    Given I'm not logged in
    When The buyer submits a new review with username "user0", number of stars 5 and message "Great" to a seller "user1"
    Then The response code is 401
    And A new review has not been created

  Scenario: Submit review with empty field stars
    Given I can login with username "user0" and password "password0"
    When The buyer submits a new review with username "user0", number of stars 0 and message "Great" to a seller "user1"
    Then The response code is 400
    And The error message is "must be greater than or equal to 1"
    And A new review has not been created


    