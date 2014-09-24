Given(/^I am on the dashboard page$/) do
  visit(CallCenter)
end

When(/^I create six incoming calls to "(.*?)"$/) do |number|
  on(CallCenter).create_calls(number)
end

Then(/^I will see the dashboard refresh$/) do
  
end

Then(/^There will be six agents on a call$/) do
	on(CallCenter).check_agents()
end

When(/^The page contains the text "(.*?)"$/) do |arg1|
	Watir::Wait.until { @browser.text.include? arg1 }
end

Then(/^I will see "(.*?)"$/) do |arg1|
	Watir::Wait.until { @browser.text.include? arg1 }
end