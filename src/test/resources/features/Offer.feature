Feature: Offer tests
  In order to sell a product
  As a user
  I want to create a new offer, retrieve offers, modify offers and delete offers.

  Scenario: Create new offer
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    And There is no offer created from the user "user"
    Then The offer should be created together with the announcement
    And which has a name "product1", description "product description" and a price "10".
    And a ZoneDateTime "2018-02-12T12:08:23Z" and a offerer user "user"
    And After all the steps I can retrieve this offer which should have the name "product1".

  Scenario: Modify a created Offer
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    And There is no offer created from the user "user"
    Then The offer should be created together with the announcement
    And which has a name "product1", description "product description" and a price "10".
    And a ZoneDateTime "2018-02-12T12:08:23Z" and a offerer user "user"
    And After all the steps I can retrieve this offer which should have the name "product1".
    Then I want to modify this product's name to "Laptop1".
    And After all the steps I can retrieve this offer which should have the name "Laptop1".

  Scenario: Delete a created Offer
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    And There is no offer created from the user "user"
    Then The offer should be created together with the announcement
    And which has a name "product1", description "product description" and a price "10".
    And a ZoneDateTime "2018-02-12T12:08:23Z" and a offerer user "user"
    And After all the steps I can retrieve this offer which should have the name "product1".
    Then I want to delete the offer with id "1".
    And I want to check that the offer doesn't exist anymore.

  Scenario: Cannot create an offer if i'm not logged in
    Given I'm not logged in
    Then I shouldn't be able to create any offer.

  Scenario: Cannot modify an offer if i'm not logged in
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    And There is no offer created from the user "user"
    Then The offer should be created together with the announcement
    And which has a name "product1", description "product description" and a price "10".
    And a ZoneDateTime "2018-02-12T12:08:23Z" and a offerer user "user"
    Given I'm not logged in
    Then I shouldn't be able to modify any offer.

  Scenario: Cannot delete an offer if i'm not logged in
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    And There is no offer created from the user "user"
    Then The offer should be created together with the announcement
    And which has a name "product1", description "product description" and a price "10".
    And a ZoneDateTime "2018-02-12T12:08:23Z" and a offerer user "user"
    Given I'm not logged in
    Then I shouldn't be able to delete any offer.