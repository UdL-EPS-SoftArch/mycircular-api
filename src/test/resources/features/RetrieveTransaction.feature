Feature: Retrieve Transaction
  We need to be able to see the created transactions
  As a certain user or admin
  I want to see all my transactions

  Background:
    Given There is a registered user with username "user1" and password "password" and email "user0@sample.app"
    Given There is a registered user with username "user2" and password "password" and email "user1@sample.app"
    And There is an announcement with id 1, name "test1", description "description for test1" and price 20.00
    And There is an announcement with id 2, name "test2", description "description for test2" and price 20.00
    And There is a transaction created with id 1 Buyer "user1" and Seller "user2" and announcement 1
    And There is a transaction created with id 2 Buyer "user2" and Seller "user1" and announcement 2

  Scenario: List all transactions as an user is forbidden
    Given I can login with username "user1" and password "password"
    When I list the transactions of all users
    Then The response code is 403

  Scenario: List all transactions with a buyer username
    Given I can login with username "user1" and password "password"
    When I list the transactions with buyer "user1"
    And The number of transactions is 1

  Scenario: List all transactions with forbidden buyer username
    Given I can login with username "user1" and password "password"
    When I list the transactions with buyer "user2"
    And The number of transactions is 0

  Scenario: List all transactions with a seller username
    Given I can login with username "user1" and password "password"
    When I list the transactions with seller "user1"
    And The number of transactions is 1

  Scenario: List all transactions with forbidden seller username
    Given I can login with username "user1" and password "password"
    When I list the transactions with seller "user2"
    And The number of transactions is 0
