function getLat(){
	var lat_val = Benign.getLatitude();
	if (lat_val != "0"){
		document.getElementById("lat").innerHTML = "Current Latitude: ".concat(lat_val);
	}
	else {
		document.getElementById("lat").innerHTML = "Permission Denied";
	}
}

function execute(){
	document.getElementById("rexec").innerHTML = Benign.getClass().forName("edu.ksu.cs.benign.GPSTracker").getMethod("testMethod",null).invoke(Benign,null);
}
