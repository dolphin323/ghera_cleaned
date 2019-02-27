function before_install_vuln() {
  $webServer = Start-Process powershell "cd ..\..\Misc\LocalServer; python.exe .\index_http.py" -PassThru
}

function after_uninstall_secure() {
  Invoke-WebRequest -Uri "http://localhost:5000/shutdown"
  echo "killed the web server"
}
