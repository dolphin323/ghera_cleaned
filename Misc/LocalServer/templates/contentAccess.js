function uploadFile() {
    var file = "content://edu.ksu.cs.MyCP/File2";
    var rawFile = new XMLHttpRequest();
    rawFile.open("GET", file, false);
    rawFile.onreadystatechange = function() {
        if (rawFile.readyState === 4) {
            if (rawFile.status === 200 || rawFile.status == 0) {
                var allText = rawFile.responseText;
                document.getElementById("demo").innerHTML = allText;
                alert(allText);
            }
        }
    }
    rawFile.send(null);
}
