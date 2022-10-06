Feature: AdminTransaction
  In order buy or sell a product or service
  As a admin
  I want to create a transaction.

  Scenario: Create new transaction.
    Given I login as "demo" with password "password"
    And There is no transaction created
    When I Create a new Transaction
    Then There is a transaction created

    Scenario: New transaction status is INITIALIZED
    Given I login as "demo" with password "password"
    And There is no transaction created
    When I Create a new Transaction
    Then The transaction status is "INITIALIZED"
    
    Scenario: Create new transaction with invalid status.
    Given There is no transaction created with identifier 1
    When I Create a new Transaction with Identifier 1, status "INVALID"
    Then There is no transaction created with identifier 1