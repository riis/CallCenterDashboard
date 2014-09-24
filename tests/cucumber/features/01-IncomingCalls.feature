@wip
Feature: Incoming callcenter calls
   As a user I want the call center dashboard to function
   with multiple incoming calls, for obvious reasons

Scenario: I create six incoming calls to the callcenter
  	Given I am on the dashboard page
    When I create six incoming calls to "6165940021"
    Then I will see the dashboard refresh
    And There will be six agents on a call