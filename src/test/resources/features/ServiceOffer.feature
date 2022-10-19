Feature: Create an ServiceOffer
  In order to sell a product
  As a user
  I want to create a new Service offer

  Scenario: Create new Service offer
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    Then The Service offer should be created
    And the Service Offer has a name "product1", description "product description" and a price "10".
    And the Service Offer has a ZoneDateTime "01/02/2018" and a offerer user "user"
    And service are available "True" and duration is "2" hours.
    And After all the steps I can retrieve this Service offer which should have the available "True".

  Scenario: Modify a created Service Offer
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    Then The Service offer should be created
    And the Service Offer has a name "product1", description "product description" and a price "10".
    And the Service Offer has a ZoneDateTime "01/02/2018" and a offerer user "user"
    And service are available "True" and duration is "2" hours.
    And After all the steps I can retrieve this Service offer which should have the available "True".
    Then I want to modify this Service Offer duration to "5" hours.
    And After all the steps I can retrieve this Service Offer which should have a duration off "5" hours.

  Scenario: Delete a created Offer
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    Then The Service offer should be created
    And the Service Offer has a name "product1", description "product description" and a price "10".
    And the Service Offer has a ZoneDateTime "01/02/2018" and a offerer user "user"
    And service are available "True" and duration is "2" hours.
    And After all the steps I can retrieve this Service offer which should have the available "True".
    Then i want to delete the Service Offer with id "1"
    And i want to check that the Service Offer doesn't exist anymore.

