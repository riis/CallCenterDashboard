require 'watir-webdriver'
require 'twilio-ruby'


Before do
  @browser = Watir::Browser.new :firefox
  @browser.window.maximize

end


After do
  @browser.close
end