Feature: Create an ServiceOffer
  In order to sell a product
  As a user
  I want to create a new offer

  Scenario: Create new Service offer
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    And There is no offer created from the user "user"
    Then The Service offer should be created
    And the Service Offer has a name "product1", description "product description" and a price "10".
    And the Service Offer has a ZoneDateTime "01/02/2018" and a offerer user "user"
    And service are available "True" and duration is "2" hours.
    And After all the steps I can retrieve this offer which should have the name "product1".

  Scenario: Modify a created Offer
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    And There is no offer created from the user "user"
    Then The Service offer should be created
    And the Service Offer has a name "product1", description "product description" and a price "10".
    And the Service Offer has a ZoneDateTime "01/02/2018" and a offerer user "user"
    And After all the steps I can retrieve this offer which should have the name "product1".
    Then I want to modify this product's name to "Laptop1" and Service duration to "5" hours.
    And After all the steps I can retrieve this offer which should have the name "Laptop1".

  Scenario: Delete a created Offer
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    And There is no offer created from the user "user"
    Then The Service offer should be created
    And the Service Offer has a name "product1", description "product description" and a price "10".
    And the Service Offer has a ZoneDateTime "01/02/2018" and a offerer user "user"
    And After all the steps I can retrieve this offer which should have the name "product1".
    Then I want to delete the offer with id "1".
    And I want to check that the offer doesn't exist anymore.
