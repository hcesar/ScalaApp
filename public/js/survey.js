function submitSurvey() {
	var survey = {
		"userId" : $('#userId').val(),
		"name" : $('#name').val(),
		"email" : $('#email').val(),
		"city" : $('#city').val(),
		"state" : $('#state').val(),
		"isCertified" : eval($('input[name=isCertified]:checked').val()),
		"occupation" : $('#occupation').val(),
		"tripKinds" : getList("#divTripKinds input:checked"),
		"regions" : getList("#divRegions input:checked"),
		"message" : $('#message').val()
	};

	$.ajax({
		url : "/api/subscriptions/" + survey.userId + "/survey",
		type : 'POST',
		data : JSON.stringify(survey)
	}).done(function() {
		$('#successModal').modal();
	}).fail(function(xhr) {
		$('#errorMessage').html(xhr.responseText);
		$('#errorModal').modal();
	});
}

function getList(selector){
	return $(selector)
			.map(function(idx, e){return e.id})
			.get()
			.join();
}

$('#btnSubmit').click(submitSurvey);

$('[name=empregado]').click(function(){
	var value = $('[name=empregado]').parent().find(':checked').val();
	if(value == 1)
		$('#divOccupation').show();
	else
		$('#divOccupation').hide();
})
$('#divOccupation').hide();
