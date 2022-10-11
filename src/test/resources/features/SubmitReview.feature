Feature: Submit Review
  In order to use the App
  As a buyer
  I want to submit a review to a seller

  Background: I'm logged in
    Given There is no registered user with username "user0"
    And I'm not logged in
    When I register a new user with username "user0", email "user@sample.app" and password "password"
    Then The response code is 201
    And It has been created a user with username "user0" and email "user@sample.app", the password is not returned
    And I can login with username "user0" and password "password"

  Scenario: Submit a new review
    Given There is no review submitted by a buyer with username "user0" to a seller with username "user1"
    And Exists a transaction between the buyer with username "user0" and the seller with username "user1"
    When The buyer submits a new review with username "user0", number of stars 5 and message "Great"
    Then The response code is 201
    And It has been submitted a review for the seller with username "user1", number of stars 5 and message "Great"
    And It has been submitted a review by buyer with username "user0"

  Scenario: Submit a review without being logged in
    Given I'm not logged in
    When The buyer submits a new review with username "user0", number of stars 5 and message "Great!"
    Then The response code is 401
    And The review has not been created

  Scenario: Submit a review to user that already has one review submitted by the same user
    Given There is already a review submitted by a buyer with username "user0" to a seller with username "user1"
    When The buyer submits a new review with username "user0", number of stars 5 and message "Great!"
    Then The response code is 401
    And The review has not been created

  Scenario: Submit a review with empty field stars
    When The buyer submits a new review with username "user0" and message "Great!"
    Then The response code is 400
    And The error message is "You must to provide a number of stars in your review"
    And The review has not been created

  Scenario: Submit a review before having a transaction with user
    Given There is no any transaction between the buyer with username "user0" and the seller with username "user1"
    When The buyer submits a new review with username "user0", number of stars 5 and message "Great!"
    Then The response code is 400
    And The error message is "There was no transaction between users, you can't submit a review!"
    And The review has not been created