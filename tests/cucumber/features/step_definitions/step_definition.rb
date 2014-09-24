Given(/^I am on the dashboard page$/) do
    visit(CallCenter)
end

Then(/^There will be (.*?) agents on a call$/) do |number|
	on(CallCenter).check_agents(number)
end

When(/^The page contains the text "(.*?)"$/) do |arg1|
	Watir::Wait.until { @browser.text.include? arg1 }
end

Then(/^I will see "(.*?)"$/) do |arg1|
	Watir::Wait.until { @browser.text.include? arg1 }
end

Given(/^I have two GUnify SIP apps open with information "(.*?)", "(.*?)", "(.*?)", "(.*?)" and a call is started$/) do |sipUser, authUser, sipUser2, authUser2|
	on(LoginPage).newWindows(sipUser, authUser, sipUser2, authUser2)
end

Then(/^I will see the dashboard refresh$/) do
  pending # express the regexp above with the code you wish you had
end