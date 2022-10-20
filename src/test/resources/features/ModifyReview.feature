Feature: Modify review


  Background:
    Given There is a registered user with username "user0" and password "password0" and email "user0@sample.app"
    Given There is a registered user with username "user1" and password "password1" and email "user1@sample.app"

  Scenario: Modify number of stars in a review
    Given I can login with username "user0" and password "password0"
    And I submit new review with username "user0", number of stars 2 and message "Not good" to a seller "user1"
    And It has been submitted a review by buyer with username "user0"
    When The buyer modifies the last created review with changing number of stars to 3
    Then The response code is 200

  Scenario: Modify message in a review
    Given I can login with username "user0" and password "password0"
    And I submit new review with username "user0", number of stars 2 and message "Not good" to a seller "user1"
    And It has been submitted a review by buyer with username "user0"
    When The buyer modifies the last created review with changing message to "I don't like it!"
    Then The response code is 200

  Scenario: Modify number of stars and message in a review
    Given I can login with username "user0" and password "password0"
    And I submit new review with username "user0", number of stars 2 and message "Not good" to a seller "user1"
    And It has been submitted a review by buyer with username "user0"
    When The buyer modifies the last created review with changing message to "I don't like it!" and number of stars to 1
    Then The response code is 200

  Scenario: Modify review when not authenticated
    Given I'm not logged in
    When I modify a review with id 3
    Then The response code is 401



