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
  :
}

function before_test_vuln() {
  #tasks that needs to be performed before testing vulnerable
  :
}

function after_uninstall_vuln() {
  #tasks that needs to be performed after uninstalling vulnerable
  :
}

function before_install_secure() {
  #tasks that needs to be performed before installing secure
  :
}

function before_test_secure() {
  #tasks that needs to be performed before testing secure
  :
}

function after_uninstall_secure() {
  # tasks that needs to be performed after uninstalling secure
  :
}

function clean_data() {
    # add all data clean up commands here
    # e.g., $ANDROID_HOME/platform-tools/adb shell rm -f /sdcard/*
    :
}

function before_any_test() {
  # tasks that need to be performed before any tests
  :
}

function after_all_tests() {
  # tasks that need to be performed after all tests
  :
}

function before_any_app_build() {
  # tasks that need to be performed before building any app
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

if [ -f "./source-script.sh" ] ; then
    . ./source-script.sh
fi

before_any_app_build

if [ -d "BenignPartner" ]; then
    execute_gradle BenignPartner assembleDebug
fi

allVariants=`ls -1d Benign* | grep -v Partner`
for b in $allVariants ; do
  execute_gradle $b assembleDebug
done

if [ -d "Malicious/" ]; then
    execute_gradle Malicious assembleDebug
fi

if [ -d "Secure/" ]; then
  execute_gradle Secure assembleDebug
fi

execute_gradle Testing assembleAndroidTest

wait_for_boot_completion () {
    $ANDROID_HOME/platform-tools/adb wait-for-device
    while [ "`$ANDROID_HOME/platform-tools/adb shell getprop sys.boot_completed | tr -d '\r' `" != "1" ] ; do
        sleep 1
    done
}

before_any_test

strict=0
headless="-no-window"
for i in 22 23 24 25 26 27 'DIST' ; do
    echo "Testing against API $i"
    os=`uname -s`
    if [[ $os = "Linux" ]]; then
        $ANDROID_HOME/emulator/emulator -avd Nexus_5X_API_$i -wipe-data \
            -no-boot-anim -no-snapshot -selinux permissive $headless \
            -use-system-libs -gpu off &
    fi
    if [[ $os = "Darwin" ]]; then
        $ANDROID_HOME/emulator/emulator -avd Nexus_5X_API_$i -wipe-data \
            -no-boot-anim -no-snapshot -selinux permissive $headless &
    fi
    emulator_pid=$!
    wait_for_boot_completion
    api_version="api$i"

    echo "Testing Vulnerable"
    clean_data
    before_install_vuln $(pwd) $api_version

    for variant in $allVariants ; do
        echo "Start test process for $variant"
        # installing apps
        $ANDROID_HOME/platform-tools/adb install -t \
            $variant/app/build/outputs/apk/$api_version/debug/app-$api_version-debug.apk
        if [ -d "BenignPartner" ]; then
            $ANDROID_HOME/platform-tools/adb install -t \
               BenignPartner/app/build/outputs/apk/$api_version/debug/app-$api_version-debug.apk
        fi
        if [ -d "Malicious/" ]; then
            $ANDROID_HOME/platform-tools/adb install -t \
                Malicious/app/build/outputs/apk/$api_version/debug/app-$api_version-debug.apk
        fi
        $ANDROID_HOME/platform-tools/adb install -t \
            Testing/benign_app/build/outputs/apk/androidTest/$api_version/debug/benign_app-$api_version-debug-androidTest.apk

        before_test_vuln $(pwd) $api_version

        # testing
        $ANDROID_HOME/platform-tools/adb shell am instrument -w \
            edu.ksu.cs.benign.test/android.support.test.runner.AndroidJUnitRunner \
            > vulnerable-$variant-$api_version-test.log

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
    done
    #reboot
    if [ $strict -ne 0 ] ; then
        $ANDROID_HOME/platform-tools/adb reboot
        wait_for_boot_completion
    fi

    if [ -d "Secure" ]; then
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
    fi

    $ANDROID_HOME/platform-tools/adb emu kill
    wait $emulator_pid

    ps -p $emulator_pid
    if [ $? -ne 1 ] ; then
        kill -9 $emulator_pid
    fi

    $ANDROID_HOME/platform-tools/adb kill-server 
done

after_all_tests


#$ANDROID_HOME/platform-tools/adb shell pm list packages | grep edu
#$ANDROID_HOME/platform-tools/adb shell pm list instrumentation | grep edu
#$ANDROID_HOME/tools/bin/apkanalyzer apk summary app/build/outputs/apk/$api_version/debug/app-$api_version-debug.apk
