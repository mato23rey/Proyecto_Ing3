function onValidate(xhr, status, args) {
	if (!args.validationFailed && args.validated) {
		setTimeout(function() {
			window.location = args.target;
		}, 500);
	}
}