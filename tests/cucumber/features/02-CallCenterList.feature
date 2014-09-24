@wip
Feature: Call Center Listing
   As a user I want to see a list of call centers displayed
   on the dashboard

Scenario: I look at the list of call centers
  	Given I am on the dashboard page
    When The page contains the text "Calls in Queue"
    Then I will see "Call Std2"