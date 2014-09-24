class LoginPage

	include PageObject

	page_url "https://1800lawfirm.riis.com/"

def newWindows(sipUser, authUser, sipUser2, authUser2)


	@browser2 = Watir::Browser.new :firefox, :profile => 'Cucumber'
	@browser2.goto("http://gunifysip.appspot.com/newserver/index.html")
	@browser2.text_field(:id => "display_name").when_present(10) {
		@browser2.text_field(:id => "sip_uri").set sipUser
		@browser2.text_field(:id => "sip_auth").set authUser
		@browser2.text_field(:id => "sip_domain").set "xdp.broadsoft.com"
		@browser2.text_field(:id => "sip_realm").set "xdp.broadsoft.com"
		@browser2.text_field(:id => "sip_proxy").set "tcp://199.19.193.9"
		@browser2.text_field(:id => "sip_password").set "welcome1"
		@browser2.button(:id => 'btnRegister').click
		@browser2.text_field(:id => "destination").when_present(20) {
			@browser3 = Watir::Browser.new :firefox, :profile => 'Cucumber'
			@browser3.goto("http://gunifysip.appspot.com/newserver/index.html")
			@browser3.text_field(:id => "display_name").when_present(10) {
				@browser3.text_field(:id => "sip_uri").set sipUser2
				@browser3.text_field(:id => "sip_auth").set authUser2
				@browser3.text_field(:id => "sip_domain").set "xdp.broadsoft.com"
				@browser3.text_field(:id => "sip_realm").set "xdp.broadsoft.com"
				@browser3.text_field(:id => "sip_proxy").set "tcp://199.19.193.9"
				@browser3.text_field(:id => "sip_password").set "welcome1"
				@browser3.button(:id => 'btnRegister').click
				@browser3.text_field(:id => "destination").when_present(20) {
					@browser2.text_field(:id => "destination").set sipUser2
					@browser2.div(:class => "button call").fire_event :click
						@browser3.button(:class => "yes").when_present(30) {
						sleep 2
						@browser3.button(:class => "yes").click
						sleep 15
						Watir::Wait.until { @browser.divs(:class => "tech_oncall").length.should == 2 }
						@browser2.div(:class => "button hangup").when_present(2) {
							@browser2.div(:class => "button hangup").fire_event :click
							sleep 5
							@browser2.close
							@broswer3.close
						}
					}
				}
			}
		}
	}
end

end	