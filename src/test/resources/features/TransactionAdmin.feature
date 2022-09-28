Feature: AdminTransaction
  In order buy or sell a product or service
  As a admin
  I want to create a transaction.

  Scenario: Create new transaction.
    Given There is no transaction created with identifyer 1
    When I Create a new Transaction with Identifyer 1, price 50, Status "Alive"
