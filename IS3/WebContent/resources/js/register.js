function onRegister(xhr, status, args) {
	if (!args.validationFailed && args.register) {
		setTimeout(function() {
			window.location = args.target;
		}, 1000);
	}
}