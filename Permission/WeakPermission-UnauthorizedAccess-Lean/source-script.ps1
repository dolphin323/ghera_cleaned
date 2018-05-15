$buildToolsVersion=$(gc ../../Misc/sdkFlavors.gradle | `
    ? { $_ -like "*buildTools*" } | `
    % { ($_.Trim() -split ' ')[1] -replace '"', '' })
$APK_SIGNER="$env:ANDROID_HOME/build-tools/$buildToolsVersion/apksigner.bat"

function before_install_vuln() {
  & "$APK_SIGNER" "sign" "--ks" "..\..\Misc\android_keys\key0.jks" `
    "--ks-pass" "pass:ghera1234" "Benign\app\build\outputs\apk\$api_version\debug\app-$api_version-debug.apk"

  & "$APK_SIGNER" "sign" "--ks" "..\..\Misc\android_keys\key1.jks" `
    "--ks-pass" "pass:ghera1234" "Malicious\app\build\outputs\apk\$api_version\debug\app-$api_version-debug.apk"

  & "$APK_SIGNER" "sign" "--ks" "..\..\Misc\android_keys\key0.jks" `
    "--ks-pass" "pass:ghera1234" "Testing\benign_app\build\outputs\apk\androidTest\$api_version\debug\benign_app-$api_version-debug-androidTest.apk"
}

function before_install_secure() {
  $APK_SIGNER =( iex '(get-item env:APKSIGNER).Value')
  & "$APK_SIGNER" "sign" "--ks" "..\..\Misc\android_keys\key0.jks" `
    "--ks-pass" "pass:ghera1234" "Secure\app\build\outputs\apk\$api_version\debug\app-$api_version-debug.apk"

  & "$APK_SIGNER" "sign" "--ks" "..\..\Misc\android_keys\key0.jks" `
    "--ks-pass" "pass:ghera1234" "Testing\secure_app\build\outputs\apk\androidTest\$api_version\debug\secure_app-$api_version-debug-androidTest.apk"
}
