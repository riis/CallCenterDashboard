@wip
Feature: Calculate Your Property Tax Savings Form
   As a customer I want to calculate how much I could potentially save
   off of my property taxes.  The scenarios will include submitting an empty form,
   Submitting a form for each field that can be left blank (a total of 10), and
   submitting a completed form.


Scenario: I submit an empty form
  Given I am about to complete the form
    When I submit the form for calculation
    Then I will see a message indicating the required fields need to be entered