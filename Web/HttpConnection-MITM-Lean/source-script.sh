id=0
function before_install_vuln() {
  cd ../../Misc/LocalServer/
  python index_http.py &
  id=`ps | grep -in "index_http.py" | grep -v "grep" | cut -d ' ' -f2`
  echo "benchmark path = $1"
  cd $1
}

function before_test_vuln() {
  #put any task that needs to be performed before testing vulnerable
  :
}

function after_uninstall_vuln() {
  #put any task that needs to be performed after uninstalling vulnerable
  :
}

function before_install_secure() {
  #put any task that needs to be performed before installing secure
  :
}

function before_test_secure() {
  #put any task that needs to be performed before testing secure
  :
}

function after_uninstall_secure() {
  kill $id
  wait $id
  echo "killed web server at $id"
}
