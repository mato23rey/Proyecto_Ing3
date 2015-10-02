function onRequest(xhr, status, args) {
	if (args.requested) {
		setTimeout(function() {
			location.reload();
		}, 1000);
	}
}