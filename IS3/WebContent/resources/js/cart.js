function onRequest(xhr, status, args) {
	if (args.requested) {
		setTimeout(function() {
			location.reload();
		}, 1000);
	}
}

function onNewItem(xhr, status, args){
	if (!args.validationFailed){
		location.reload();
	}

}