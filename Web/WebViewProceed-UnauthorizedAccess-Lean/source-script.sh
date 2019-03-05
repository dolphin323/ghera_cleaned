function before_install_vuln() {
  cd ../../Misc/LocalServer/
  python index_http.py &
  echo "benchmark path = $1"
  cd $1
}

function after_uninstall_vuln() {
  #put any task that needs to be performed after uninstalling vulnerable
  after_uninstall_secure
}

function before_install_secure() {
  #put any task that needs to be performed before installing secure
  before_install_vuln
}

function after_uninstall_secure() {
  curl -k http://localhost:5000/shutdown
}
