function getKey() {
	document.getElementById("demo").innerHTML = Util.readKey();
}

function setKey(){
	Util.writeKey("Some key");
}
