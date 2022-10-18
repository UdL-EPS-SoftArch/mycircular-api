Feature: Message
  In order to use the app
  As a user
  I must be able to send and receive messages

  Scenario: Send message and logged
    Given I login as "demo" with password "password"
    And The message with id 1 doesn't exist
    When I send the message with date "2022-04-12T12:08:23Z" and text "Hello"




