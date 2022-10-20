Feature: Message
  In order to use the app
  As a user
  I must be able to send and receive messages

  Scenario: Send a message without being logged in
    Given I'm not logged in
    When I send a message with date "2022-04-12T12:08:23Z" and text "Hello"
    Then The response code is 401
    And The error message is "Unauthorized"

  Scenario: Send a message while logged in
    Given I login as "demo" with password "password"
    And I don't have any messages
    When I send a message with date "2022-04-12T12:08:23Z" and text "Hello"
    Then The response code is 201


  Scenario: Send an empty message while logged in
    Given I login as "demo" with password "password"
    And I don't have any messages
    When I send a message with date "2022-04-12T12:08:23Z" and text ""
    Then The response code is 400

  Scenario: Send a large message while logged in
    Given I login as "demo" with password "password"
    And I don't have any messages
    When I send a message with date "2022-04-12T12:08:23Z" and text "Lorem ipsum sagittis neque, blandit aliquam sem sapien nec leo. Vivamus pharetra finibus lacus eu convallis. Morbi augue sapien, iaculis sit amet diam eu, condimentum sollicitudin leo. Mauris nec ullamcorper felis. Sed id volutpat eros. Fusce a nibh id risus."
    Then The response code is 400
