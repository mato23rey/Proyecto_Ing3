function getLocation() {
	if (navigator.geolocation) {
	//	navigator.geolocation.getCurrentPosition(showPosition);
	} else {
		alert("Geolocation is not supported by this browser.");
	}
}

function showPosition(position) {
	var pos = position.coords.latitude +";"+ position.coords.longitude;
	updatePosition(pos);
}

function onSearch(){

}

function find(address){
	var url = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20geo.placefinder%20where%20text%3D%22"+address+"%2CMontevideo%2CUruguay%22&format=json";

	$.getJSON(url, function(r){
		var pos = r.query.results.Result.latitude+";"+r.query.results.Result.longitude;
		console.log(r);
		console.log(pos);
		updatePosition(pos);
	});
}

function updatePosition(pos){
	document.getElementById("cordsForm:cords").value = pos;
	document.getElementById("cordsForm:cordsButton").click();
}