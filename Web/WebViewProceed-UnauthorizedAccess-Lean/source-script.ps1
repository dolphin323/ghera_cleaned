function before_install_vuln() {
  $webServer = Start-Process powershell "cd ..\..\Misc\LocalServer; python.exe .\index_https_webviewproceed_unauthorizedaccess.py" -PassThru
}

function after_uninstall_secure() {
  $web = New-Object Net.WebClient
  [System.Net.ServicePointManager]::ServerCertificateValidationCallback = { $true }
  $web.DownloadString("https://localhost:5000/shutdown")
  [System.Net.ServicePointManager]::ServerCertificateValidationCallback = $null
  echo "killed the web server"
}
