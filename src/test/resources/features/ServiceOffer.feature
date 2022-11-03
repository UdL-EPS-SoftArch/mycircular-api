Feature: Service offer tests
  In order to sell a product
  As a user
  I want to create a new Service offer, retrieve, modify and delete it.

  Scenario: Create new Service offer
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    Then The Service offer should be created
    And the Service Offer has a name "product1", description "product description" and a price "10".
    And the Service Offer has a ZoneDateTime "2018-02-12T12:08:23Z" and a offerer user "user"
    And service are available "True" and duration is "2" hours.
    And After all the steps I can retrieve this Service offer which should have the available "True".

  Scenario: Modify a created Service Offer
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    Then The Service offer should be created
    And the Service Offer has a name "product1", description "product description" and a price "10".
    And the Service Offer has a ZoneDateTime "2018-02-12T12:08:23Z" and a offerer user "user"
    And service are available "True" and duration is "2" hours.
    And After all the steps I can retrieve this Service offer which should have the available "True".
    Then I want to modify this Service Offer duration to "5" hours.
    And After all the steps I can retrieve this Service Offer which should have a duration off "5" hours.

  Scenario: Delete a created Offer
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    Then The Service offer should be created
    And the Service Offer has a name "product1", description "product description" and a price "10".
    And the Service Offer has a ZoneDateTime "2018-02-12T12:08:23Z" and a offerer user "user"
    And service are available "True" and duration is "2" hours.
    And After all the steps I can retrieve this Service offer which should have the available "True".
    Then i want to delete the Service Offer with id "1"
    And i want to check that the Service Offer doesn't exist anymore.

  Scenario: Cannot create a service offer if i'm not logged in
    Given I'm not logged in
    Then I shouldn't be able to create any service offer.

  Scenario: Cannot modify a service offer if i'm not logged in
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    Then The Service offer should be created
    And the Service Offer has a name "product1", description "product description" and a price "10".
    And the Service Offer has a ZoneDateTime "2018-02-12T12:08:23Z" and a offerer user "user"
    And service are available "True" and duration is "2" hours.
    Given I'm not logged in
    Then I shouldn't be able to modify any service offer.

  Scenario: Cannot delete a service offer if i'm not logged in
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    Then The Service offer should be created
    And the Service Offer has a name "product1", description "product description" and a price "10".
    And the Service Offer has a ZoneDateTime "2018-02-12T12:08:23Z" and a offerer user "user"
    And service are available "True" and duration is "2" hours.
    Given I'm not logged in
    Then I shouldn't be able to delete any service offer.