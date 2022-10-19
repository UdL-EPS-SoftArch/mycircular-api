Feature: AdminTransaction
  In order buy or sell a product or service
  As a admin
  I want to create a transaction.

  Background:
    Given There is a registered user with username "user1" and password "password" and email "user0@sample.app"
    Given There is a registered user with username "user2" and password "password" and email "user0@sample.app"
    And There is a transaction created with Buyer "user1" and Seller "user2"

  Scenario: List all transactions of a User



