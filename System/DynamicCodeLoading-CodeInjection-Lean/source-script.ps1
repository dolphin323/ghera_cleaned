function before_any_test() {
    Push-Location UnsignedHelper
    & .\gradlew.bat clean dexify --build-cache
    if ( $? -ne $true) {
        "Not equal to 0"
        exit $?
    }
    Pop-Location
}

function before_test_vuln() {
    $ext_storage = & $ANDROID_HOME/platform-tools/adb.exe 'shell' `
        'echo $EXTERNAL_STORAGE'
    if (-not ($ext_storage -is [String])) {
        $ext_storage = $ext_storage[0]
    }

    echo $ext_storage
    & $ANDROID_HOME/platform-tools/adb.exe 'push' `
        'UnsignedHelper/build/libs/unsigned-greetings.jar' `
        "$($ext_storage.Trim())/Android/data/edu.ksu.cs.benign/files/greetings.jar"
}

function before_test_secure() {
    before_test_vuln
}
