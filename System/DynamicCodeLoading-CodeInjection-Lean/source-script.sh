function before_any_test() {
    pushd UnsignedHelper
    ./gradlew clean dexify --build-cache
    if [ $? -ne 0 ] ; then
        exit $?
    fi
    popd
}
  
function before_test_vuln() {
    ext_storage=`$ANDROID_HOME/platform-tools/adb shell 'echo $EXTERNAL_STORAGE'`
    echo $ext_storage
    $ANDROID_HOME/platform-tools/adb push \
        UnsignedHelper/build/libs/unsigned-greetings.jar \
        ${ext_storage%[[:space:]]}/Android/data/edu.ksu.cs.benign/files/greetings.jar
}

function before_test_secure() {
    before_test_vuln
}
