id=0
function before_install_vuln() {
  PORT=5000
  cd ../../Misc/LocalServer/java/
  javac WriteToSocket.java
  java WriteToSocket $PORT &
  id=$!
  echo "benchmark path = $1"
  cd $1
}

function before_test_vuln() {
  #put any task that needs to be performed before testing vulnerable
  :
}

function after_uninstall_vuln() {
  #put any task that needs to be performed after testing vulnerable
  :
}

function before_install_secure() {
  before_install_vuln $1
}

function after_test_secure() {
  #put any task that needs to be performed before testing secure
  :
}

function after_uninstall_secure() {
  kill $id
  wait $id
  echo "killed java server at $id"
}
