function before_install_vuln() {
$PORT = 5000
$id = Start-Process -FilePath "C:\Windows\System32\WindowsPowerShell\v1.0\powershell.exe" -ArgumentList "cd ..\..\Misc\LocalServer\java; javac WriteToSocket.java; java WriteToSocket $PORT" -PassThru
}

function before_test_vuln() {

}

function after_uninstall_vuln() {
  Stop-Process -ProcessName java* -Force
}

function before_install_secure() {
  before_install_vuln
}

function before_test_secure() {

}

function after_uninstall_secure {
  after_uninstall_vuln
}
