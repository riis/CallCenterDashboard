@wip
Feature: Call Center Agent Status List
   As a user I want to see a list of agents and their
   statuses displayed on the dashboard

Scenario: I look at the list of agents
  	Given I am on the dashboard page
    When The page contains the text "Agents"
    Then I will see "3210"
    And I will see "3211"
    And I will see "3212"
    And I will see "1160"
    And I will see "1161"
    And I will see "1162"
    And I will see "1163"
    And I will see "1164"
    And I will see "1165"
    And I will see "1166"
    And I will see "1167"
    And I will see "1168"
    And I will see "1169"