Feature: AdminTransaction
  In order buy or sell a product or service
  As a admin
  I want to create a transaction.

  Background:
    Given There is a registered user with username "user0" and password "password0" and email "user0@sample.app"
    And I login as "demo" with password "password"
    And There is an announcement with id 1, name "test1", description "test1" and price 20.00

  Scenario: Create new transaction.
    Given I Create a new Transaction with price "20.5", the buyer is "user0", the seller is "demo" and announcement id is 1
    Then The response code is 201

  Scenario: Change the status of a transaction
    Given I login as "demo" with password "password"
    And I Create a new Transaction with price "20.5", the buyer is "user0", the seller is "demo" and announcement id is 1
    And I change the status of the transaction to "CLOSED"
    Then The response code is 200
    Then The transaction status is "CLOSED"


