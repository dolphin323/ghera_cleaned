function before_benign() {
$PORT = 5000
$id = Start-Process -FilePath "C:\Windows\System32\WindowsPowerShell\v1.0\powershell.exe" -ArgumentList "cd ..\..\Misc\LocalServer\java; javac WriteToSocket.java; java WriteToSocket $PORT" -PassThru
}

function after_benign() {
  Stop-Process -ProcessName java* -Force
}

function before_secure() {
  before_benign
}

function after_secure {
  after_benign
}
