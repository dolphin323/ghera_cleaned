function before_install_vuln() {
  cd ../../Misc/LocalServer/
  python index_http.py &
  echo "benchmark path = $1"
  cd $1
}

function after_uninstall_secure() {
  curl http://localhost:5000/shutdown
}
