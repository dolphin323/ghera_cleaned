function uploadFile(){
	var file = "file:///data/data/edu.ksu.cs.benign/files/File2";
	var rawFile = new XMLHttpRequest();
    	rawFile.open("GET", file, false);
    	rawFile.onreadystatechange = function ()
    	{
        	if(rawFile.readyState === 4)
        	{
            		if(rawFile.status === 200 || rawFile.status == 0)
            		{
                		var allText = rawFile.responseText;
				            document.getElementById("demo").innerHTML = allText;
                		alert(allText);
            		}
        	}
    	}
    	rawFile.send(null);
}

/*
Note: This JavaScript file is shared with following benchmarks:
1. Web/WebViewAllowFileAccess-UnauthorizedFileAccess-Lean
2. Web/WebViewLoadDataWithBaseUrl-UnauthorizedFileAccess-Lean
*/
