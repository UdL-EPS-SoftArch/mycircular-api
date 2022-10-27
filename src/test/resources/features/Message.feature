Feature: Message
  In order to use the app
  As a user
  I must be able to send and receive messages

  Background:
    Given There is a registered user with username "send1" and password "password" and email "send1@circule.cat"
    Given There is a registered user with username "prod1" and password "password" and email "prod1@circule.cat"
    #El que rep el missatge

  Scenario: Send a message without being logged in
    Given I'm not logged in
    When I send a message with date "2022-04-12T12:08:23Z", text "Hello"
    Then The response code is 401
    And The error message is "Unauthorized"

  Scenario: Send a message while logged in
    Given I login as "send1" with password "password"
    And I don't have any messages
    When I send a message with date "2022-04-12T12:08:23Z", text "Hello"
    Then The response code is 201

  Scenario: For an Announcement create a Message
    Given I login as "prod1" with password "password"
    Then The product offer should be created.
    And the product offer has a name "product1", description "product description" and a price "10".
    And the product offer has a ZoneDateTime "2018-02-12T12:08:23Z" and a offerer user "prod1"
    And a manufacturer "manufacturer1" a band "brand1" and a product code "X-01"

  Scenario: Send a message for product without being logged in and
    Given I login as "send1" with password "password"
    When I send a message with date "2022-04-12T12:08:23Z", text "Hello" and for 1
    # The Message is associated with the Product Offer 1



  Scenario: Send an empty message while logged in
    Given I login as "send1" with password "password"
    And I don't have any messages
    When I send a message with date "2022-04-12T12:08:23Z", text ""
    Then The response code is 400

  Scenario: Send a large message while logged in
    Given I login as "send1" with password "password"
    And I don't have any messages
    When I send a message with date "2022-04-12T12:08:23Z", text "Lorem ipsum sagittis neque, blandit aliquam sem sapien nec leo. Vivamus pharetra finibus lacus eu convallis. Morbi augue sapien, iaculis sit amet diam eu, condimentum sollicitudin leo. Mauris nec ullamcorper felis. Sed id volutpat eros. Fusce a nibh id risus."
    Then The response code is 400
