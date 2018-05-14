function before_test_vuln() {
    Push-Location UnsignedHelper
    & ".\gradlew.bat" "clean" "dexify" "--build-cache"
    if ( $? -ne "True") {
        "Not equal to 0"
        exit $?
    }
    Pop-Location
  
    $ext_storage = & "$env:ANDROID_HOME/platform-tools/adb" 'shell' `
        'echo $EXTERNAL_STORAGE'
    echo $ext_storage
    & "$env:ANDROID_HOME/platform-tools/adb" 'push' `
        'UnsignedHelper/build/libs/unsigned-greetings.jar' `
        "$($ext_storage.Trim())/Android/data/edu.ksu.cs.benign/files/greetings.jar"
}

function before_test_secure() {
    before_test_vuln
}
