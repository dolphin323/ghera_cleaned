buildToolsVersion=`grep buildToolsVersion ../../Misc/sdkFlavors.gradle | sed -E -e 's/.*"(.*)"/\1/'`
APK_SIGNER=$ANDROID_HOME/build-tools/$buildToolsVersion/apksigner

function before_install_vuln() {
  $APK_SIGNER sign \
    --ks ../../Misc/android_keys/keyOne-pass_keyOne-alias_keyOne-validity_10May2019-storepass_keyOne.jks \
    --ks-pass pass:keyOne Benign/app/build/outputs/apk/$2/debug/app-$2-debug.apk

  $APK_SIGNER sign \
    --ks ../../Misc/android_keys/keyTwo-pass_keyTwo-alias_keyTwo-validity_10May2019-storepass_keyTwo.jks \
    --ks-pass pass:keyTwo Malicious/app/build/outputs/apk/$2/debug/app-$2-debug.apk

  $APK_SIGNER sign \
    --ks ../../Misc/android_keys/keyOne-pass_keyOne-alias_keyOne-validity_10May2019-storepass_keyOne.jks \
    --ks-pass pass:keyOne Testing/benign_app/build/outputs/apk/androidTest/$2/debug/benign_app-$2-debug-androidTest.apk
}

function before_install_secure() {
  $APK_SIGNER sign \
    --ks ../../Misc/android_keys/keyOne-pass_keyOne-alias_keyOne-validity_10May2019-storepass_keyOne.jks \
    --ks-pass pass:keyOne Secure/app/build/outputs/apk/$2/debug/app-$2-debug.apk

  $APK_SIGNER sign \
    --ks ../../Misc/android_keys/keyOne-pass_keyOne-alias_keyOne-validity_10May2019-storepass_keyOne.jks \
    --ks-pass pass:keyOne Testing/secure_app/build/outputs/apk/androidTest/$2/debug/secure_app-$2-debug-androidTest.apk
}
