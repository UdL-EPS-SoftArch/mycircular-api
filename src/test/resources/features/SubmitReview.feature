Feature: Submit review
  In order to use the App
  As a buyer
  I want to submit a review to a seller

  Background:
    Given There is a registered user with username "user0" and password "password0" and email "user0@sample.app"
    Given There is a registered user with username "user1" and password "password1" and email "user1@sample.app"
    Given There is a registered admin with username "admin" and password "password" and email "admin@local.com"


  Scenario: Submit a new review
    Given I can login with username "user0" and password "password0"
    When The buyer submits a new review with username "user0", number of stars 5 and message "Great" to a seller with username "user1"
    Then The response code is 201
    And It has been submitted a review by buyer with username "user0"

  Scenario: Submit a new review without a message
    Given I can login with username "user0" and password "password0"
    When The buyer submits a new review with username "user0", number of stars 5 to a seller with username "user1"
    Then The response code is 201
    And It has been submitted a review by buyer with username "user0"

  Scenario: Submit a review when I'm not authenticated
    Given I'm not logged in
    When The buyer submits a new review with username "user0", number of stars 5 and message "Great" to a seller with username "user1"
    Then The response code is 401
    And A new review has not been created

  Scenario: Submit a review with number of stars equals 0
    Given I can login with username "user0" and password "password0"
    When The buyer submits a new review with username "user0", number of stars 0 and message "Great" to a seller with username "user1"
    Then The response code is 400
    And The error message is "The number of stars must be greater than or equal 1"
    And A new review has not been created

  Scenario: Submit a review with overvalued number of stars
    Given I can login with username "user0" and password "password0"
    When The buyer submits a new review with username "user0", number of stars 6 and message "Great" to a seller with username "user1"
    Then The response code is 400
    And The error message is "The number of stars must be less than or equal 5"
    And A new review has not been created

  Scenario: Submit a review to a seller that already has one review submitted by the same buyer
    Given I can login with username "user0" and password "password0"
    And There is already a review submitted by a buyer with username "user0" to a seller with username "user1"
    When The buyer submits a new review with username "user0", number of stars 5 and message "Great" to a seller with username "user1"
    Then The response code is 409
    And A duplicated review has not been created

  Scenario: Submit a review as admin
    Given I login as "admin" with password "password"
    When The buyer submits a new review with username "user0", number of stars 5 and message "Great" to a seller with username "user1"
    Then The response code is 403
    And A new review has not been created
