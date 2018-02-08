id=0
function before_benign() {
  PORT=5000
  cd ../../Misc/LocalServer/java/
  javac WriteToSocket.java
  java WriteToSocket $PORT &
  id=$!
  echo "benchmark path = $1"
  cd $1
}

function after_benign() {
  #put any task that needs to be performed after benign
  :
}

function before_secure() {
  before_benign $1
}

function after_secure() {
  kill $id
  wait $id
  echo "killed java server at $id"
}
