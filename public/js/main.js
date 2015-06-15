function submitUser() {
	var user = {
		name : $('#name').val(),
		email : $('#email').val()
	};

	if (!user.name || !user.email) {
		$('#errorMessage').html('Todos os campos são obrigatórios');
		$('#errorModal').modal();
		return;
	}

	$('#waitingModal').modal();
	$.ajax({
		url : "/api/subscriptions",
		type : 'POST',
		data : JSON.stringify(user)
	}).done(function() {
		$('#nameSubscription').text(user.name)
		$('#successModal').modal();
		$('#waitingModal').modal('hide');
	}).fail(function(xhr) {
		$('#errorMessage').html(xhr.responseText);
		$('#errorModal').modal();
		$('#waitingModal').modal('hide');
	});
}

$('#btnSubmitEmail').click(submitUser)

$("#btnMore").click(function() {
	$('html, body').animate({
		scrollTop : $("#divMore").offset().top
	}, 1000);
});