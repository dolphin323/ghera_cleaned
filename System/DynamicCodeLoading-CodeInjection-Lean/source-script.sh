function before_test_vuln() {
    pushd UnsignedHelper
    ./gradlew clean dexify --build-cache
    if [ $? -ne 0 ] ; then
        exit $?
    fi
    popd
  
    #$ANDROID_HOME/platform-tools/adb shell am start \
        #-n edu.ksu.cs.benign/.MainActivity
    #sleep 1
    ext_storage=`$ANDROID_HOME/platform-tools/adb shell 'echo $EXTERNAL_STORAGE'`
    echo $ext_storage
    $ANDROID_HOME/platform-tools/adb push \
        UnsignedHelper/build/libs/unsigned-greetings.jar \
        ${ext_storage%[[:space:]]}/Android/data/edu.ksu.cs.benign/files/greetings.jar
    #$ANDROID_HOME/platform-tools/adb shell am force-stop edu.ksu.cs.benign 
}

function before_test_secure() {
    before_test_vuln
}
