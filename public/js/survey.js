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

	$('#waitingModal').modal();
	$.ajax({
		url : "/api/subscriptions/" + survey.userId + "/survey",
		type : 'POST',
		data : JSON.stringify(survey)
	}).done(function() {
		$('#waitingModal').modal('hide');
		$('#successModal').modal();
	}).fail(function(xhr) {
		$('#waitingModal').modal('hide');
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

$('#btnSubmitSurvey').click(submitSurvey);

$('[name=rbOccupation]').click(function(){
	var value = $(this).parent().find(':checked').val();
	if(value == 1)
		$('#divOccupation').show();
	else
		$('#divOccupation').hide();
})
$('#divOccupation').hide();
