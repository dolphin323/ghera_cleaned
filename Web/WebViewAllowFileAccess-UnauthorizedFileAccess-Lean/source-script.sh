function before_benign() {
  if [ -z $ANDROID_HOME ] ; then
    echo "Please set ANDROID_HOME env variable."
    exit -1
  fi
  $ANDROID_HOME/platform-tools/adb root
  $ANDROID_HOME/platform-tools/adb push demo1.html /data/data/edu.ksu.cs.benign/files/demo1.html
  $ANDROID_HOME/platform-tools/adb push File2 /data/data/edu.ksu.cs.benign/files/File2
}

function after_benign() {
  #put any task that needs to be performed after benign
  :
}

function before_secure() {
  if [ -z $ANDROID_HOME ] ; then
    echo "Please set ANDROID_HOME env variable."
    exit -1
  fi
  $ANDROID_HOME/platform-tools/adb root
  $ANDROID_HOME/platform-tools/adb push demo1.html /data/data/edu.ksu.cs.benign/files/demo1.html
  $ANDROID_HOME/platform-tools/adb push File2 /data/data/edu.ksu.cs.benign/files/File2
}

function after_secure() {
  #put any task that needs to be performed after secure
  :
}
