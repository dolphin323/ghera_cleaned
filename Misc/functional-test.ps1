# The acceptance tests should follow the structure of
# ConstantKey-ForgeryAttack-Lean benchmark.
#
# Also, the AVDs should be named Nexus_5X_API_<api #>, e.g., Nexus_5X_API_23.
# Create an AVD named Nexus_5X_API_DIST based on the highest supported API.
#
# If there are no changes to the structure, then execute this script from the
# benchmark folder
# If there are changes to the structure, then copy this script into the benchmark
# folder, make changes, and execute the modified script.  REMEMBER TO ADD THE
# SCRIPT TO VCS.

# Change the value of headless to an empty string to run in head mode.

# Here are resources to help understand various tool features used in this script.
# - https://stuff.mit.edu/afs/sipb/project/android/docs/tools/testing/testing_ui.html
# - https://developer.android.com/training/testing/ui-testing/uiautomator-testing.html
# - https://developer.android.com/studio/test/command-line.html
# - https://developer.android.com/studio/command-line/adb.html
# - https://developer.android.com/studio/run/emulator-commandline.html

function before_install_vuln() {
  #tasks that needs to be performed before installing vulnerable
}

function before_test_vuln() {
  #tasks that needs to be performed before testing vulnerable
}

function after_uninstall_vuln() {
  #tasks that needs to be performed after uninstalling vulnerable
}

function before_install_secure() {
  #tasks that needs to be performed before installing secure
}

function before_test_secure() {
  #tasks that needs to be performed before testing secure
}

function after_uninstall_secure() {
  #tasks that needs to be performed after uninstalling secure
}

function clean_data () {
    # add all data clean up commands here
    # e.g., $ANDROID_HOME\platform-tools\adb shell rm -f \sdcard\*
}

function before_any_test() {
  # tasks that need to be performed before any tests
}

function after_all_tests() {
  # tasks that need to be performed after all tests
}

function before_any_app_build() {
  # tasks that need to be performed before building any app
}

function execute_gradle ($x, $y) {
    Push-Location $x
    & .\gradlew.bat clean $y --build-cache
    if ( $? -ne $true) {
        "Not equal to 0"
        exit $?
    }
    Pop-Location
}

$ANDROID_HOME =( iex '(get-item env:ANDROID_HOME).Value')
$sleep_time = 7

$source_script_exists = Test-Path .\source-script.ps1
if ($source_script_exists) {
  . .\source-script.ps1
}

before_any_app_build

$allVariants = Get-ChildItem -Path * -Directory Benign* | where {$_.Name -ne "BenignPartner"}
ForEach($variant in $allVariants) {
  execute_gradle $variant.Name assembleDebug
}

$BenignPartner_Path_Exists = Test-Path .\BenignPartner
if ($BenignPartner_Path_Exists) {
  execute_gradle BenignPartner assembleDebug
}

$Mal_Path_Exists = Test-Path .\Malicious
if ($Mal_Path_Exists) {
  execute_gradle Malicious assembleDebug
}

$Sec_Path_Exists = Test-Path .\Secure
if ($Sec_Path_Exists) {
    execute_gradle Secure assembleDebug
}

execute_gradle Testing assembleAndroidTest

function wait_for_boot_completion () {
    & $ANDROID_HOME\platform-tools\adb.exe wait-for-device
    $v = & $ANDROID_HOME\platform-tools\adb.exe shell getprop sys.boot_completed
    while ($v -ne 1) {
      Start-Sleep -s 1
      $v = & $ANDROID_HOME\platform-tools\adb.exe shell getprop sys.boot_completed
    }
}

before_any_test

$strict = 0
$headless = "-no-window"
ForEach ($i in 22,23,24,25,26,27,'DIST') {
  echo "Testing against API $i"
  $emulator_process = Start-Process -FilePath `
    $ANDROID_HOME\emulator\emulator.exe -ArgumentList "-port", "5554", `
    "-avd", "Nexus_5X_API_$i", "-wipe-data", "-no-boot-anim", "-no-snapshot", `
    "-selinux", "permissive", $headless `
    -PassThru

  wait_for_boot_completion
  $api_version="api$i"
  echo "Testing Vulnerable"

  Foreach ($variant in $allVariants)
  {
    $variantName = $variant.Name
    echo "Start test process for $variantName"

    clean_data
    before_install_vuln

    & $ANDROID_HOME\platform-tools\adb.exe install `
        .\$variantName\app\build\outputs\apk\$api_version\debug\app-$api_version-debug.apk
    if($BenignPartner_Path_Exists) {
      & $ANDROID_HOME\platform-tools\adb.exe install `
          .\BenignPartner\app\build\outputs\apk\$api_version\debug\app-$api_version-debug.apk
    }
    if ($Mal_Path_Exists) {
      & $ANDROID_HOME\platform-tools\adb.exe install `
          .\Malicious\app\build\outputs\apk\$api_version\debug\app-$api_version-debug.apk
    }
    & $ANDROID_HOME\platform-tools\adb.exe install -t `
        Testing\benign_app\build\outputs\apk\androidTest\$api_version\debug\benign_app-$api_version-debug-androidTest.apk

    before_test_vuln

    $result_vulnerable = & $ANDROID_HOME\platform-tools\adb.exe shell am instrument -w `
        edu.ksu.cs.benign.test/android.support.test.runner.AndroidJUnitRunner
    echo $result_vulnerable > vulnerable-$variantName-$api_version-test.log

    & $ANDROID_HOME\platform-tools\adb.exe shell pm uninstall edu.ksu.cs.benign
    if ($BenignPartner_Path_Exists) {
      & $ANDROID_HOME\platform-tools\adb.exe shell pm uninstall edu.ksu.cs.benignpartner
    }
    if ($Mal_Path_Exists) {
      & $ANDROID_HOME\platform-tools\adb.exe shell pm uninstall edu.ksu.cs.malicious
    }
    & $ANDROID_HOME\platform-tools\adb.exe shell pm uninstall edu.ksu.cs.benign.test

    after_uninstall_vuln
  }

  if ( $strict -ne 0 ) {
      & $ANDROID_HOME\platform-tools\adb.exe reboot
      wait_for_boot_completion
  }

  if ($Sec_Path_Exists) {
    echo "Testing Secure"
    clean_data
    before_install_secure

    & $ANDROID_HOME\platform-tools\adb.exe install `
        Secure\app\build\outputs\apk\$api_version\debug\app-$api_version-debug.apk
    if($BenignPartner_Path_Exists) {
      & $ANDROID_HOME\platform-tools\adb.exe install `
          .\BenignPartner\app\build\outputs\apk\$api_version\debug\app-$api_version-debug.apk
    }
    if ($Mal_Path_Exists) {
    & $ANDROID_HOME\platform-tools\adb.exe install `
        Malicious\app\build\outputs\apk\$api_version\debug\app-$api_version-debug.apk
    }
    & $ANDROID_HOME\platform-tools\adb.exe install -t `
        Testing\secure_app\build\outputs\apk\androidTest\$api_version\debug\secure_app-$api_version-debug-androidTest.apk

    before_test_secure

    $result_secure = & $ANDROID_HOME\platform-tools\adb.exe shell am instrument -w `
        edu.ksu.cs.benign.test/android.support.test.runner.AndroidJUnitRunner
    echo $result_secure > "secure-$api_version-test.log"
    & $ANDROID_HOME\platform-tools\adb.exe shell pm uninstall edu.ksu.cs.benign
    if ($BenignPartner_Path_Exists) {
      & $ANDROID_HOME\platform-tools\adb.exe shell pm uninstall edu.ksu.cs.benignpartner
    }
    if ($Mal_Path_Exists) {
      & $ANDROID_HOME\platform-tools\adb.exe shell pm uninstall edu.ksu.cs.malicious
    }
    & $ANDROID_HOME\platform-tools\adb.exe shell pm uninstall edu.ksu.cs.benign.test

    after_uninstall_secure
  }

  while (!$emulator_process.HasExited) {
    & $ANDROID_HOME\platform-tools\adb.exe -s emulator-5554 emu kill
  }
  & $ANDROID_HOME\platform-tools\adb.exe kill-server 

  Start-Sleep -s $sleep_time
}


after_all_tests
