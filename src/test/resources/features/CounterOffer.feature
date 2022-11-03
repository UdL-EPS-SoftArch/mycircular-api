Feature: Counter Offer tests
  In order to sell a product
  As a user
  I want to create a counter offer, retrieve this counter offers

  Scenario: Create new Counter offer
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    Then The Counter offer should be created.
    And Counter offer has a name "product1", description "product description" and a price "10".
    And counter offer has ZoneDateTime "2018-02-12T12:08:23Z" and a offerer user "user"
    And the counter offer price is "50" tokens.
    And After all the steps I can retrieve this counter offer which should have the counter offer price of "50.0".

  Scenario: Modify a created Counter Offer
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    Then The Counter offer should be created.
    And Counter offer has a name "product1", description "product description" and a price "10".
    And counter offer has ZoneDateTime "2018-02-12T12:08:23Z" and a offerer user "user"
    And the counter offer price is "50" tokens.
    And After all the steps I can retrieve this counter offer which should have the counter offer price of "50.0".
    Then I want to modify this Counter offer product name to "laptop".
    And After all the steps I can retrieve this counter offer which should have the counter offer name of "laptop".

  Scenario: Delete a created Counter Offer
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    Then The Counter offer should be created.
    And Counter offer has a name "product1", description "product description" and a price "10".
    And counter offer has ZoneDateTime "2018-02-12T12:08:23Z" and a offerer user "user"
    And the counter offer price is "50" tokens.
    And After all the steps I can retrieve this counter offer which should have the counter offer price of "50.0".
    Then I want to delete the Counter offer with id "1".
    And I want to check that the Counter offer doesn't exist anymore.

  Scenario: Cannot create an Counter offer if i'm not logged in
    Given I'm not logged in
    Then I shouldn't be able to create any Counter offer.

  Scenario: Cannot modify an Counter offer if i'm not logged in
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    Then The Counter offer should be created.
    And Counter offer has a name "product1", description "product description" and a price "10".
    And counter offer has ZoneDateTime "2018-02-12T12:08:23Z" and a offerer user "user"
    And the counter offer price is "50" tokens.
    Given I'm not logged in
    Then I shouldn't be able to modify any counter offer.

  Scenario: Cannot delete an Counter offer if i'm not logged in
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    Then The Counter offer should be created.
    And Counter offer has a name "product1", description "product description" and a price "10".
    And counter offer has ZoneDateTime "2018-02-12T12:08:23Z" and a offerer user "user"
    And the counter offer price is "50" tokens.
    Given I'm not logged in
    Then I shouldn't be able to delete any Counter offer.