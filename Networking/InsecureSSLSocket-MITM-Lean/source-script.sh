id=0
function before_install_vuln() {
  :
}

function before_test_vuln() {
  PORT=5000
  cd ../../Misc/LocalServer/java/
  javac WriteToSocket.java
  java WriteToSocket $PORT &
  id=$!
  echo "benchmark path = $1"
  cd $1
}

function after_uninstall_vuln() {
  kill $id
  wait $id
  echo "killed java server at $id"
}

function before_install_secure() {
  :
}

function before_test_secure() {
  before_test_vuln $1
}

function after_test_secure() {
  kill $id
  wait $id
  echo "killed java server at $id"
}

function after_uninstall_secure() {
  :
}
