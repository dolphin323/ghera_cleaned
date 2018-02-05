$ANDROID_HOME =( iex '(get-item env:ANDROID_HOME).Value')
$sleep_time = 7
function execute_gradle ($x, $y){
    Push-Location $x
    # &".\gradlew.bat" "clean" $y "--build-cache"
    &".\gradlew.bat" $y "--build-cache"
    if ( $? -ne "True") {
        "Not equal to 0"
        exit $?
    }
    Pop-Location
}

execute_gradle Benign assembleDebug
$Mal_Path_Exists = Test-Path .\Malicious
if ($Mal_Path_Exists){
  execute_gradle Malicious assembleDebug
}
execute_gradle Secure assembleDebug
execute_gradle Testing assembleAndroidTest

function wait_for_boot_completion () {
    &"$ANDROID_HOME\platform-tools\adb.exe" "wait-for-device"
    $v = &"$ANDROID_HOME\platform-tools\adb.exe" "shell" "getprop" "sys.boot_completed"
    while ($v -ne 1) {
      Start-Sleep -s 1
      $v = &"$ANDROID_HOME\platform-tools\adb.exe" "shell" "getprop" "sys.boot_completed"
  }
}

function before_benign() {
  #e.g. starting a local server
}

function after_benign(){
#e.g. stopping a local server
}

function before_secure() {
  #e.g. starting a local server
}

function after_secure(){
#e.g. stopping a local server
}
function clean_data () {
    # add all data clean up commands here
    # e.g., $ANDROID_HOME\platform-tools\adb shell rm -f \sdcard\*
}
$strict = 0
ForEach ($i in 19,21,22,23,24,25){
  echo "Testing against API $i"
  $id = Start-Process -FilePath "$ANDROID_HOME\tools\emulator.exe" -ArgumentList "-port", "5554","-avd", "Nexus_5X_API_$i", "-wipe-data" -PassThru

  $emulator_pid=$id.Id
  wait_for_boot_completion
  $api_version="api$i"

  echo "Testing Vulnerable"
  # clean_data
  . ./source-script.ps1

  before_benign

  &"$ANDROID_HOME\platform-tools\adb.exe" "install" `
      ".\Benign\app\build\outputs\apk\$api_version\debug\app-$api_version-debug.apk"
  if ($Mal_Path_Exists){
  &"$ANDROID_HOME\platform-tools\adb.exe" "install" `
      ".\Malicious\app\build\outputs\apk\$api_version\debug\app-$api_version-debug.apk"
  }
  &"$ANDROID_HOME\platform-tools\adb.exe" "install" "-t" `
      "Testing\benign_app\build\outputs\apk\androidTest\$api_version\debug\benign_app-$api_version-debug-androidTest.apk"

  $result_vulnerable = &"$ANDROID_HOME\platform-tools\adb.exe" "shell" "am" "instrument" "-w" `
      "edu.ksu.cs.benign.test/android.support.test.runner.AndroidJUnitRunner"
  echo $result_vulnerable > "vulnerable-$api_version-test.log"

  &"$ANDROID_HOME\platform-tools\adb.exe" "shell" "pm" "uninstall" "edu.ksu.cs.benign"
  if ($Mal_Path_Exists){
  &"$ANDROID_HOME\platform-tools\adb.exe" "shell" "pm" "uninstall" "edu.ksu.cs.malicious"
  }
  &"$ANDROID_HOME\platform-tools\adb.exe" "shell" "pm" "uninstall" "edu.ksu.cs.benign.test"
  if ( $strict -ne 0 ){
      &"$ANDROID_HOME\platform-tools\adb.exe" "reboot"
      wait_for_boot_completion
  }

   "Testing Secure"

  clean_data
  before_secure

  &"$ANDROID_HOME\platform-tools\adb.exe" "install" `
      "Secure\app\build\outputs\apk\$api_version\debug\app-$api_version-debug.apk"
  if ($Mal_Path_Exists){
  &"$ANDROID_HOME\platform-tools\adb.exe" "install" `
      "Malicious\app\build\outputs\apk\$api_version\debug\app-$api_version-debug.apk"
  }
  &"$ANDROID_HOME\platform-tools\adb.exe" "install" "-t" `
      "Testing\secure_app\build\outputs\apk\androidTest\$api_version\debug\secure_app-$api_version-debug-androidTest.apk"
  $result_secure = &"$ANDROID_HOME\platform-tools\adb.exe" "shell" "am" "instrument" "-w" `
      "edu.ksu.cs.benign.test/android.support.test.runner.AndroidJUnitRunner"
  echo $result_secure > "secure-$api_version-test.log"
  &"$ANDROID_HOME\platform-tools\adb.exe" "shell" "pm" "uninstall" "edu.ksu.cs.benign"
  if ($Mal_Path_Exists){
  &"$ANDROID_HOME\platform-tools\adb.exe" "shell" "pm" "uninstall" "edu.ksu.cs.malicious"
  }
  &"$ANDROID_HOME\platform-tools\adb.exe" "shell" "pm" "uninstall" "edu.ksu.cs.benign.test"

  &"$ANDROID_HOME\platform-tools\adb.exe" "-s" "emulator-5554" emu kill

  after_secure
  Start-Sleep -s $sleep_time
}
