class CallCenter
	include PageObject

	page_url "http://ec2-54-205-41-129.compute-1.amazonaws.com:8080/CallCenterManager/"
	
def check_agents(num)
	@browser.divs(:class => "tech_oncall").length.should == num
end

end