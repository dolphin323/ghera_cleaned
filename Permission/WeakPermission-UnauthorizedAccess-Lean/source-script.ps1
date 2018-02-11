function before_install_vuln() {
  $apksigner =( iex '(get-item env:APKSIGNER).Value')
  & "$apksigner" "sign" "--ks" "..\..\Misc\android_keys\key0.jks" `
    "--ks-pass" "pass:ghera1234" "Benign\app\build\outputs\apk\$api_version\debug\app-$api_version-debug.apk"

  & "$apksigner" "sign" "--ks" "..\..\Misc\android_keys\key1.jks" `
    "--ks-pass" "pass:ghera1234" "Malicious\app\build\outputs\apk\$api_version\debug\app-$api_version-debug.apk"

  & "$apksigner" "sign" "--ks" "..\..\Misc\android_keys\key0.jks" `
    "--ks-pass" "pass:ghera1234" "Testing\benign_app\build\outputs\apk\androidTest\$api_version\debug\benign_app-$api_version-debug-androidTest.apk"
}

function before_test_vuln() {
  #put any task that needs to be performed before testing vulnerable
}

function after_uninstall_vuln() {
  #put any task that needs to be performed after testing vulnerable
}

function before_install_secure() {
  $apksigner =( iex '(get-item env:APKSIGNER).Value')
  & "$apksigner" "sign" "--ks" "..\..\Misc\android_keys\key0.jks" `
    "--ks-pass" "pass:ghera1234" "Secure\app\build\outputs\apk\$api_version\debug\app-$api_version-debug.apk"

  & "$apksigner" "sign" "--ks" "..\..\Misc\android_keys\key0.jks" `
    "--ks-pass" "pass:ghera1234" "Testing\secure_app\build\outputs\apk\androidTest\$api_version\debug\secure_app-$api_version-debug-androidTest.apk"
}

function before_test_secure() {
  #put any task that needs to be performed before testing secure
}

function after_uninstall_secure() {
  #put any task that needs to be performed after testing secure
}
