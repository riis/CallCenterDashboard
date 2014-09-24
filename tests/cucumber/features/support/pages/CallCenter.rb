class CallCenter
	include PageObject

	page_url "https://1800lawfirm.riis.com/"


def create_calls(num)
	for i in 0..6
		# put your own credentials here - from twilio.com/user/account
		account_sid = 'AC884700d962ee1a7bbd6fc52f8999f658'
		auth_token = '92d0192e9cd60092232ee6de705e0f5b'
		 
		# set up a client to talk to the Twilio REST API
		@client = Twilio::REST::Client.new account_sid, auth_token
		 
		@call = @client.account.calls.create(
		 :from => '+16164997845',   # From your Twilio number
		 :to => num,     # To any number
		 # Fetch instructions from this URL when the call connects
		 :url => 'http://demo.twilio.com/welcome/voice/'
		)
		sleep 2
	end
end

def check_agents()
	@browser.divs(:class => "tech_oncall").length.should == 6
end

end