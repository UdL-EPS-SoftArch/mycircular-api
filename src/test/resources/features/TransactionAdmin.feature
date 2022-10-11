Feature: AdminTransaction
  In order buy or sell a product or service
  As a admin
  I want to create a transaction.

  Scenario: Create new transaction.
    Given I login as "demo" with password "password"
    And There is no transaction created
    When I Create a new Transaction
    Then I put the Transaction price "20.00"
    Then There is a transaction created
    And Now the Transaction price is "20.00"

  Scenario: New transaction status is INITIALIZED
    Given I login as "demo" with password "password"
    And There is no transaction created
    When I Create a new Transaction
    Then The transaction status is "INITIALIZED"

  Scenario: Change the status of a transaction
    Given I login as "demo" with password "password"
    And There is no transaction created
    When I Create a new Transaction
    And I change the status of the transaction to "CLOSED"
    Then The transaction status is "CLOSED"

  Scenario: Delete/Cancel an existing transaction
    Given I login as "demo" with password "password"
    When I Create a new Transaction
    And I change the status of the transaction to "CANCELED"
    Then The transaction status is "CANCELED"
