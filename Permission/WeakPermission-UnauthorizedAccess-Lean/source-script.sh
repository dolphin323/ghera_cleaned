id=0
function before_benign() {
  apksigner sign --ks ../../Misc/android_keys/key0.jks \
    --ks-pass pass:ghera1234 Benign/app/build/outputs/apk/$2/debug/app-$2-debug.apk
}

function after_benign() {
  #put any task that needs to be performed after benign
  :
}

function before_secure() {
  apksigner sign --ks ../../Misc/android_keys/key0.jks \
    --ks-pass pass:ghera1234 Secure/app/build/outputs/apk/$2/debug/app-$2-debug.apk
}

function after_secure() {
  #put any task that needs to be performed after Secure
  :
}

function before_malicious() {
  apksigner sign --ks ../../Misc/android_keys/key1.jks \
    --ks-pass pass:ghera1234 Malicious/app/build/outputs/apk/$2/debug/app-$2-debug.apk
}

function after_malicious() {
  #put any task that needs to be performed after Malicious
  :
}

function before_benign_test() {
  apksigner sign --ks ../../Misc/android_keys/key0.jks \
    --ks-pass pass:ghera1234 Testing/benign_app/build/outputs/apk/androidTest/$2/debug/benign_app-$2-debug-androidTest.apk
}

function after_benign_test() {
  #put any task that needs to be performed after benign_test
  :
}

function before_secure_test() {
  apksigner sign --ks ../../Misc/android_keys/key0.jks \
    --ks-pass pass:ghera1234 Testing/secure_app/build/outputs/apk/androidTest/$2/debug/secure_app-$2-debug-androidTest.apk
}

function after_secure_test() {
  #put any task that needs to be performed after benign_test
  :
}
