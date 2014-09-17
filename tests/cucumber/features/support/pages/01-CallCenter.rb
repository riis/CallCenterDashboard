class Calculator
	include PageObject
	include DataMagic

	page_url "https://1800lawfirm.riis.com/"

	text_field(:address, :name => "address")
	text_field(:city, :name => "city")
	select_list(:state, :name => "state")
	text_field(:zip, :name => "zipcode")
	select_list(:condition, :name => "condition")
	select_list(:propertytype, :name => "type")
	text_field(:firstname, :name => "firstName")
	text_field(:lastname, :name => "lastName")
	text_field(:email, :name => "email")
	text_field(:phone, :name => "phone")
	button(:submit, :id => "page_1_continue")


def fill_out_form(data = {})
	populate_page_with data_for(:qualifying_information, data)
	submit
end

end