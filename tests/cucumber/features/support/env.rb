require 'rspec'
require 'page-object'

World(PageObject::PageFactory)

if ENV['HEADLESS'] == 'true'
  require 'headless'

  headless = Headless.new
  headless.start

  at_exit do
    headless.destroy
  end
end