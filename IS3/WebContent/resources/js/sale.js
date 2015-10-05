function onSaleComplete(xhr, status, args) {
	if (!args.validationFailed) {

		if((args.scored && args.logued) && args.completed){
			setTimeout(function() {
				window.location = args.target;
			}, 500);
		}
	}
}
