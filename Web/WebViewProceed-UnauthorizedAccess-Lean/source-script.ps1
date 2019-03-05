function before_install_vuln() {
  $webServer = Start-Process powershell "cd ..\..\Misc\LocalServer; python.exe .\index_http.py" -PassThru
}

function after_uninstall_vuln() {
  after_uninstall_secure
}

function before_install_secure() {
  before_install_vuln
}

function after_uninstall_secure() {
  $web = New-Object Net.WebClient
  [System.Net.ServicePointManager]::ServerCertificateValidationCallback = { $true }
  $web.DownloadString("http://localhost:5000/shutdown")
  [System.Net.ServicePointManager]::ServerCertificateValidationCallback = $null
  echo "killed the web server"
}
