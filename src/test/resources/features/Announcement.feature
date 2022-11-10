Feature: Announcement tests
  In order to sell a product
  As a user
  I want to create a new announcement, retrieve announcements, modify announcements and delete announcements.

  Scenario: Create new announcement
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    Then The announcement should be created
    And said announcement has a name "product1", description "product description" and a price "10".
    And After all the steps I can retrieve this announcement which should have the name "product1".

  Scenario: Modify an announcement
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    Then The announcement should be created
    And said announcement has a name "product1", description "product description" and a price "10".
    And After all the steps I can retrieve this announcement which should have the name "product1".
    Then I want to modify this announcement's name to "Laptop1".
    And After all the steps I can retrieve this announcement which should have the name "Laptop1".

  Scenario: Delete an announcement
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    Then The announcement should be created
    And said announcement has a name "product1", description "product description" and a price "10".
    And After all the steps I can retrieve this announcement which should have the name "product1".
    Then I want to delete the announcement with id "1".
    And I want to check that the announcement doesn't exist anymore.

  Scenario: Cannot create an announcement if i'm not logged in
    Given I'm not logged in
    Then I shouldn't be able to create any announcement.

  Scenario: Cannot modify an announcement if I'm not logged in.
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    Then The announcement should be created
    And said announcement has a name "product1", description "product description" and a price "10".
    Given I'm not logged in
    Then I shouldn't be able to modify any announcement.

  Scenario: Cannot delete an announcement if I'm not logged in.
    Given There is a registered user with username "user" and password "password" and email "example@gmail.com"
    And I login as "user" with password "password"
    Then The announcement should be created
    And said announcement has a name "product1", description "product description" and a price "10".
    Given I'm not logged in
    Then I shouldn't be able to delete any announcement.
