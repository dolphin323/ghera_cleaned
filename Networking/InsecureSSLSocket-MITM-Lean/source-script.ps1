function before_install_vuln() {
$PORT = 5000
cd ..\..\Misc\LocalServer\java;
javac WriteToSocket.java;
$global:javaProcessId = Start-Process -FilePath java -ArgumentList "WriteToSocket $PORT" -PassThru
cd ..\..\..\Networking\InsecureSSLSocket-MITM-Lean
}

function before_test_vuln() {

}

function after_uninstall_vuln() {
  $javaProcessId | Stop-Process
}

function before_install_secure() {
  before_install_vuln
}

function before_test_secure() {

}

function after_uninstall_secure {
  $javaProcessId | Stop-Process
}
