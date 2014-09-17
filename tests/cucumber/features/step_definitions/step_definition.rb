###########################################
########                                  #
########    01-CaculateSavings.feature    #
########                                  #
###########################################

Given(/^I am about to complete the form$/) do
	visit(Calculator)
	Watir::Wait.until { @browser.text.should include 'Calculate Your Property Tax Savings' }
end

When(/^I submit the form for calculation$/) do
	on(Calculator).submit_form
end

Then(/^I will see a message indicating the required fields need to be entered$/) do
  @browser.text.should include "Required"
end

When(/^I complete the Property Tax Savings form$/) do
  on(Calculator).fill_out_form
end

Then(/^I will be placed on the Automation Valuation screen$/) do
  Watir::Wait.until { @browser.text.should include 'Automated Valuation Model Calculation' }
end