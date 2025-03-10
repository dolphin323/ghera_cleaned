$buildToolsVersion=$(gc ../../Misc/sdkFlavors.gradle | `
    ? { $_ -like "*buildTools*" } | `
    % { ($_.Trim() -split ' ')[1] -replace '"', '' })
$APK_SIGNER="$ANDROID_HOME/build-tools/$buildToolsVersion/apksigner.bat"

function before_install_vuln() {
  & $APK_SIGNER sign `
    --ks ..\..\Misc\android_keys\keyOne-pass_keyOne-alias_keyOne-validity_10May2019-storepass_keyOne.jks `
    --ks-pass pass:keyOne `
    Benign\app\build\outputs\apk\$api_version\debug\app-$api_version-debug.apk

  & $APK_SIGNER sign `
    --ks ..\..\Misc\android_keys\keyTwo-pass_keyTwo-alias_keyTwo-validity_10May2019-storepass_keyTwo.jks `
    --ks-pass pass:keyTwo `
    Malicious\app\build\outputs\apk\$api_version\debug\app-$api_version-debug.apk

  & $APK_SIGNER sign `
    --ks ..\..\Misc\android_keys\keyOne-pass_keyOne-alias_keyOne-validity_10May2019-storepass_keyOne.jks `
    --ks-pass pass:keyOne `
    Testing\benign_app\build\outputs\apk\androidTest\$api_version\debug\benign_app-$api_version-debug-androidTest.apk
}

function before_install_secure() {
  & $APK_SIGNER sign `
    --ks ..\..\Misc\android_keys\keyOne-pass_keyOne-alias_keyOne-validity_10May2019-storepass_keyOne.jks `
    --ks-pass pass:keyOne `
    Secure\app\build\outputs\apk\$api_version\debug\app-$api_version-debug.apk

  & $APK_SIGNER sign `
    --ks ..\..\Misc\android_keys\keyOne-pass_keyOne-alias_keyOne-validity_10May2019-storepass_keyOne.jks `
    --ks-pass pass:keyOne `
    Testing\secure_app\build\outputs\apk\androidTest\$api_version\debug\secure_app-$api_version-debug-androidTest.apk
}
