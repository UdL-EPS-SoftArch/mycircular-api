Feature: AdminTransaction
  In order buy or sell a product or service
  As a admin
  I want to create a transaction.

  Scenario: Create new transaction.
    Given I login as "demo" with password "password"
    When I Create a new Transaction with price 20.05
    Then The response code is 201

  Scenario: Change the status of a transaction
    Given I login as "demo" with password "password"
    When I Create a new Transaction with price 20.05
    And I change the status of the transaction to "CLOSED"
    Then The response code is 200
    Then The transaction status is "CLOSED"


