function getLat(){
	document.getElementById("lat").innerHTML = Benign.getLatitude();
}

function execute(){
	document.getElementById("rexec").innerHTML = Benign.getClass().forName("edu.ksu.cs.benign.GPSTracker").getMethod("testMethod",null).invoke(Benign,null);
}
