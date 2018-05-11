function before_test_vuln() {
    Push-Location UnsignedHelper
    & ".\gradlew.bat" "clean" "dexify" "--build-cache"
    if ( $? -ne "True") {
        "Not equal to 0"
        exit $?
    }
    Pop-Location
  
# & "$ANDROID_HOME/platform-tools/adb" "shell" "am" "start" \
    #"-n" "edu.ksu.cs.benign/.MainActivity"
    $ext_storage = & "$ANDROID_HOME/platform-tools/adb" "shell" "'echo $EXTERNAL_STORAGE'"
    echo $ext_storage
    & "$ANDROID_HOME/platform-tools/adb" "push" \
        "UnsignedHelper/build/libs/unsigned-greetings.jar" \
        "$($ext_storage.Trim())/Android/data/edu.ksu.cs.benign/files/greetings.jar"
# & "$ANDROID_HOME/platform-tools/adb" "shell" "am" "force-stop edu.ksu.cs.benign" 
}

function before_test_secure() {
    before_test_vuln
}
