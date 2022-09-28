Feature: AdminTransaction
  In order buy or sell a product or service
  As a admin
  I want to create a transaction.

  Scenario: Create new transaction.
    Given Theres is no transaction with the identifyer 1
    When I Create a new Transaction with Identifyer 1, price 50 and Status "ACTIVE"
