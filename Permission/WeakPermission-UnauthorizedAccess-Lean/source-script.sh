buildToolsVersion=`grep buildToolsVersion ../../Misc/sdkFlavors.gradle | sed -E -e 's/.*"(.*)"/\1/'`
APK_SIGNER=$ANDROID_HOME/build-tools/$buildToolsVersion/apksigner

function before_install_vuln() {
  $APK_SIGNER sign --ks ../../Misc/android_keys/key0.jks \
    --ks-pass pass:ghera1234 Benign/app/build/outputs/apk/$2/debug/app-$2-debug.apk

  $APK_SIGNER sign --ks ../../Misc/android_keys/key1.jks \
    --ks-pass pass:ghera1234 Malicious/app/build/outputs/apk/$2/debug/app-$2-debug.apk

  $APK_SIGNER sign --ks ../../Misc/android_keys/key0.jks \
    --ks-pass pass:ghera1234 Testing/benign_app/build/outputs/apk/androidTest/$2/debug/benign_app-$2-debug-androidTest.apk
}

function before_install_secure() {
  $APK_SIGNER sign --ks ../../Misc/android_keys/key0.jks \
    --ks-pass pass:ghera1234 Secure/app/build/outputs/apk/$2/debug/app-$2-debug.apk

  $APK_SIGNER sign --ks ../../Misc/android_keys/key0.jks \
    --ks-pass pass:ghera1234 Testing/secure_app/build/outputs/apk/androidTest/$2/debug/secure_app-$2-debug-androidTest.apk
}
