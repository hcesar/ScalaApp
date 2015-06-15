


function submitUser(){
	var user = { name: $('#name').val(), email:$('#email').val() };
	$.ajax({url: "/api/subscriptions", type: 'POST', data: JSON.stringify(user)})
		  .done(function() {
			  $('#nameSubscription').text(user.name)
			  $('#successModal').modal();
		  })
		  .fail(function(xhr) {
			  $('#errorMessage').html(xhr.responseText);
			  $('#errorModal').modal();
		  });
}

$('#btnEnviarEmail').click(submitUser)