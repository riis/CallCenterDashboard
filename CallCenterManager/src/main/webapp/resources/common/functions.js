function startLoader(element) {
	$(element).append('<i class="fa fa-spinner fa-spin"></i>');
}

function endLoader(element) {
	$(element).find('.fa-spinner').remove();
}