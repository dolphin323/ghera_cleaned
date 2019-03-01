function before_install_vuln() {
  $webServer = Start-Process powershell "cd ..\..\Misc\LocalServer; python.exe .\index_http.py" -PassThru
}

function before_test_vuln() {
  & $ANDROID_HOME\platform-tools\adb.exe root
  & $ANDROID_HOME\platform-tools\adb.exe push File2 /data/data/edu.ksu.cs.benign/files/File2
}

function before_test_secure() {
  before_test_vuln
}

function after_uninstall_secure() {
  Invoke-WebRequest -Uri "http://localhost:5000/shutdown"
  echo "killed the web server"
}
