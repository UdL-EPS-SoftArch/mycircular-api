Feature: Product offer tests
  In order to sell a product
  As a user
  I want to create a new product offer, retrieve, modify and delete it.

  Scenario: Create new Product Offer
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    Then The product offer should be created.
    And the product offer has a name "product1", description "product description" and a price "10".
    And the product offer has a ZoneDateTime "2018-02-12T12:08:23Z" and a offerer user "user"
    And a manufacturer "manufacturer1" a band "brand1" and a product code "X-01"
    And After all the steps I can retrieve this product offer which should have the product code "X-01".

  Scenario: Modify a created Product Offer
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    Then The product offer should be created.
    And the product offer has a name "product1", description "product description" and a price "10".
    And the product offer has a ZoneDateTime "2018-02-12T12:08:23Z" and a offerer user "user"
    And a manufacturer "manufacturer1" a band "brand1" and a product code "X-01"
    And After all the steps I can retrieve this product offer which should have the product code "X-01".
    Then I want to modify this product's brand to "Toshota".
    And After all the steps I can retrieve this product offer which should have the brand "Toshota".

  Scenario: Delete a created Product Offer
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    Then The product offer should be created.
    And the product offer has a name "product1", description "product description" and a price "10".
    And the product offer has a ZoneDateTime "2018-02-12T12:08:23Z" and a offerer user "user"
    And a manufacturer "manufacturer1" a band "brand1" and a product code "X-01"
    And After all the steps I can retrieve this product offer which should have the product code "X-01".
    Then I want to delete the product offer with id "1".
    And I want to check that the product offer doesn't exist anymore.

  Scenario: Cannot create a product offer if i'm not logged in
    Given I'm not logged in
    Then I shouldn't be able to create any product offer.

  Scenario: Cannot modify a product offer if i'm not logged in
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    Then The product offer should be created.
    And the product offer has a name "product1", description "product description" and a price "10".
    And the product offer has a ZoneDateTime "2018-02-12T12:08:23Z" and a offerer user "user"
    And a manufacturer "manufacturer1" a band "brand1" and a product code "X-01"
    Given I'm not logged in
    Then I shouldn't be able to modify any product offer.

  Scenario: Cannot delete a product offer if i'm not logged in
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    Then The product offer should be created.
    And the product offer has a name "product1", description "product description" and a price "10".
    And the product offer has a ZoneDateTime "2018-02-12T12:08:23Z" and a offerer user "user"
    And a manufacturer "manufacturer1" a band "brand1" and a product code "X-01"
    Given I'm not logged in
    Then I shouldn't be able to delete any product offer.