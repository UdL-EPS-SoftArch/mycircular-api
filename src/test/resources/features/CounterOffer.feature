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
    And After all the steps I can retrieve this counter offer which should have the name "50".

  Scenario: Modify a created Counter Offer
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    Then The Counter offer should be created.
    And Counter offer has a name "product1", description "product description" and a price "10".
    And counter offer has ZoneDateTime "2018-02-12T12:08:23Z" and a offerer user "user"
    And After all the steps I can retrieve this Counter offer which should have the name "product1".
    Then I want to modify this Counter offer price to "50" tokens.
    And After all the steps I can retrieve this Counter offer which should have the name "Laptop1".

  Scenario: Delete a created Counter Offer
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    Then The Counter offer should be created.
    And Counter offer has a name "product1", description "product description" and a price "10".
    And Counter offer has ZoneDateTime "2018-02-12T12:08:23Z" and a offerer user "user"
    And After all the steps I can retrieve this Counter offer which should have the name "product1".
    Then I want to delete the Counter offer with id "1".
    And I want to check that the Counter offer doesn't exist anymore.

  Scenario: Cannot create an Counter offer if i'm not logged in
    Given I'm not logged in
    Then I shouldn't be able to create any Counter offer.

  Scenario: Cannot modify an Counter offer if i'm not logged in
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    And There is no Counter offer created from the user "user"
    Then The Counter offer should be created together with the announcement
    And Counter offer has a name "product1", description "product description" and a price "10".
    And Counter offer has ZoneDateTime "2018-02-12T12:08:23Z" and a offerer user "user"
    Given I'm not logged in
    Then I shouldn't be able to modify any counter offer.

  Scenario: Cannot delete an Counter offer if i'm not logged in
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    And There is no Counter offer created from the user "user"
    Then The Counter offer should be created together with the announcement
    And Counter offer has a name "product1", description "product description" and a price "10".
    And Counter offer has ZoneDateTime "2018-02-12T12:08:23Z" and a offerer user "user"
    Given I'm not logged in
    Then I shouldn't be able to delete any Counter offer.