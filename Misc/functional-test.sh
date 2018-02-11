# The acceptance tests should follow the structure of
# ConstantKey-ForgeryAttack-Lean benchmark.
#
# Also, the AVDs should be named Nexus_5X_API_<api #>, e.g., Nexus_5X_API_23.
#
# If there are no changes to the structure, then execute this script from the
# benchmark folder
# If there are changes to the structure, then copy this script into the benchmark
# folder, make changes, and execute the modified script.  REMEMBER TO ADD THE
# SCRIPT TO VCS.
# Make sure to add $ANDROID_HOME/build-tools/<build-tools-version>/apksigner
# to your class path

# Here are resources to help understand various tool features used in this script.
# - https://stuff.mit.edu/afs/sipb/project/android/docs/tools/testing/testing_ui.html
# - https://developer.android.com/training/testing/ui-testing/uiautomator-testing.html
# - https://developer.android.com/studio/test/command-line.html
# - https://developer.android.com/studio/command-line/adb.html
# - https://developer.android.com/studio/run/emulator-commandline.html

function before_install_vuln() {
  #put any task that needs to be performed before installing vulnerable
  :
}

function before_test_vuln() {
  #put any task that needs to be performed before testing vulnerable
  :
}

function after_uninstall_vuln() {
  #put any task that needs to be performed after uninstalling vulnerable
  :
}

function before_install_secure() {
  #put any task that needs to be performed before installing secure
  :
}

function before_test_secure() {
  #put any task that needs to be performed before testing secure
  :
}

function after_uninstall_secure() {
  #put any task that needs to be performed after uninstalling secure
  :
}

execute_gradle () {
    pushd $1
    ./gradlew clean $2 --build-cache
    if [ $? -ne 0 ] ; then
        exit $?
    fi
    popd
}

if [ -z $ANDROID_HOME ] ; then
  echo "Please set ANDROID_HOME env variable."
  exit -1
fi

if [ -d "BenignPartner" ]; then
  execute_gradle BenignPartner assembleDebug
fi
execute_gradle Benign assembleDebug
if [ -d "Malicious/" ]; then
    execute_gradle Malicious assembleDebug
fi
execute_gradle Secure assembleDebug
execute_gradle Testing assembleAndroidTest

wait_for_boot_completion () {
    $ANDROID_HOME/platform-tools/adb wait-for-device
    while [ "`$ANDROID_HOME/platform-tools/adb shell getprop sys.boot_completed | tr -d '\r' `" != "1" ] ; do
        sleep 1
    done
}

clean_data() {
    # add all data clean up commands here
    # e.g., $ANDROID_HOME/platform-tools/adb shell rm -f /sdcard/*
    :
}

strict=0
for i in 19 21 22 23 24 25 ; do
    echo "Testing against API $i"
    os=`uname -o`
    if [[ $os = *"Linux"* ]]; then
        $ANDROID_HOME/tools/emulator -avd Nexus_5X_API_$i -wipe-data -use-system-libs -gpu off &
    else
        $ANDROID_HOME/tools/emulator -avd Nexus_5X_API_$i -wipe-data &
    fi
    emulator_pid=$!
    wait_for_boot_completion
    api_version="api$i"

    if [ -f "./source-script.sh" ] ; then
        . ./source-script.sh
    fi
    echo "Testing Vulnerable"
    clean_data
    before_install_vuln $(pwd) $api_version

    # installing apps
    $ANDROID_HOME/platform-tools/adb install \
        Benign/app/build/outputs/apk/$api_version/debug/app-$api_version-debug.apk
    if [ -d "BenignPartner" ]; then
        $ANDROID_HOME/platform-tools/adb install \
           BenignPartner/app/build/outputs/apk/$api_version/debug/app-$api_version-debug.apk
    fi
    if [ -d "Malicious/" ]; then
        $ANDROID_HOME/platform-tools/adb install \
            Malicious/app/build/outputs/apk/$api_version/debug/app-$api_version-debug.apk
    fi
    $ANDROID_HOME/platform-tools/adb install -t \
        Testing/benign_app/build/outputs/apk/androidTest/$api_version/debug/benign_app-$api_version-debug-androidTest.apk

    before_test_vuln $(pwd) $api_version

    # testing
    $ANDROID_HOME/platform-tools/adb shell am instrument -w \
        edu.ksu.cs.benign.test/android.support.test.runner.AndroidJUnitRunner \
        > vulnerable-$api_version-test.log

    # uninstalling apps
    $ANDROID_HOME/platform-tools/adb shell pm uninstall edu.ksu.cs.benign
    if [ -d "BenignPartner" ]; then
        $ANDROID_HOME/platform-tools/adb shell pm uninstall edu.ksu.cs.benignpartner
    fi
    if [ -d "Malicious/" ]; then
        $ANDROID_HOME/platform-tools/adb shell pm uninstall edu.ksu.cs.malicious
    fi
    $ANDROID_HOME/platform-tools/adb shell pm uninstall edu.ksu.cs.benign.test

    after_uninstall_vuln $(pwd) $api_version

    #reboot
    if [ $strict -ne 0 ] ; then
        $ANDROID_HOME/platform-tools/adb reboot
        wait_for_boot_completion
    fi

    echo "Testing Secure"
    clean_data
    before_install_secure $(pwd) $api_version

    # installing apps
    $ANDROID_HOME/platform-tools/adb install \
        Secure/app/build/outputs/apk/$api_version/debug/app-$api_version-debug.apk
    if [ -d "BenignPartner" ]; then
        $ANDROID_HOME/platform-tools/adb install \
            BenignPartner/app/build/outputs/apk/$api_version/debug/app-$api_version-debug.apk
    fi
    if [ -d "Malicious/" ]; then
        $ANDROID_HOME/platform-tools/adb install \
            Malicious/app/build/outputs/apk/$api_version/debug/app-$api_version-debug.apk
    fi
    $ANDROID_HOME/platform-tools/adb install -t \
        Testing/secure_app/build/outputs/apk/androidTest/$api_version/debug/secure_app-$api_version-debug-androidTest.apk

    before_test_secure $(pwd) $api_version

    # testing
    $ANDROID_HOME/platform-tools/adb shell am instrument -w \
        edu.ksu.cs.benign.test/android.support.test.runner.AndroidJUnitRunner \
        > secure-$api_version-test.log

    # uninstalling apps
    $ANDROID_HOME/platform-tools/adb shell pm uninstall edu.ksu.cs.benign
    if [ -d "BenignPartner" ]; then
        $ANDROID_HOME/platform-tools/adb shell pm uninstall edu.ksu.cs.benignpartner
    fi
    if [ -d "Malicious/" ]; then
        $ANDROID_HOME/platform-tools/adb shell pm uninstall edu.ksu.cs.malicious
    fi
    $ANDROID_HOME/platform-tools/adb shell pm uninstall edu.ksu.cs.benign.test

    after_uninstall_secure $(pwd) $api_version

    $ANDROID_HOME/platform-tools/adb emu kill
done


#$ANDROID_HOME/platform-tools/adb shell pm list packages | grep edu
#$ANDROID_HOME/platform-tools/adb shell pm list instrumentation | grep edu
#$ANDROID_HOME/tools/bin/apkanalyzer apk summary app/build/outputs/apk/$api_version/debug/app-$api_version-debug.apk
