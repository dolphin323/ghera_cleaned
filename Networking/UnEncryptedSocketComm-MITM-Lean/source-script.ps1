function test_current_benchmark() {
$PORT = 5000
$id = Start-Process -FilePath "C:\Windows\System32\WindowsPowerShell\v1.0\powershell.exe" -ArgumentList "cd ..\..\Misc\LocalServer\java; javac WriteToSocket.java; java WriteToSocket $PORT" -PassThru
}

function clean_current_benchmark_data(){
  Stop-Process -ProcessName java* -Force
}
