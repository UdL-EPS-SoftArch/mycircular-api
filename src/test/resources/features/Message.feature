Feature: Message
  In order to use the app
  As a user
  I must be able to send and receive messages

  Background:
    Given There is a registered user with username "send1" and password "password" and email "send1@circule.cat"

  Scenario: Send a message without being logged in
    Given I'm not logged in
    When I send a message with date "2022-04-12T12:08:23Z", text "Hello" and author "send1"
    Then The response code is 401
    And The error message is "Unauthorized"

  Scenario: Send a message while logged in
    Given I login as "send1" with password "password"
    And I don't have any messages
    When I send a message with date "2022-04-12T12:08:23Z", text "Hello" and author "send1"
    Then The response code is 201


  Scenario: Send an empty message while logged in
    Given I login as "send1" with password "password"
    And I don't have any messages
    When I send a message with date "2022-04-12T12:08:23Z", text "" and author "send1"
    Then The response code is 400

  Scenario: Send a large message while logged in
    Given I login as "send1" with password "password"
    And I don't have any messages
    When I send a message with date "2022-04-12T12:08:23Z", text "Lorem ipsum sagittis neque, blandit aliquam sem sapien nec leo. Vivamus pharetra finibus lacus eu convallis. Morbi augue sapien, iaculis sit amet diam eu, condimentum sollicitudin leo. Mauris nec ullamcorper felis. Sed id volutpat eros. Fusce a nibh id risus." and author "send1"
    Then The response code is 400
