function getKey() {
	document.getElementById("demo").innerHTML = Util.readKey();
}

function setKey(){
	Util.writeKey("some key");
}

function performOp(){
	document.getElementById("demo").innerHTML = "Privileged Op performed";
	//Util.doPrivilegedOperation();
}
