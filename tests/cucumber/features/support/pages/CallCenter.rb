class CallCenter
	include PageObject

	page_url "https://1800lawfirm.riis.com/"

def check_agents(num)
	@browser.divs(:class => "tech_oncall").length.should == num
end

end