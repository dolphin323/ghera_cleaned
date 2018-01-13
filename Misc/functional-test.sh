# The acceptance tests should follow the structure of
# ConstantKey-InformationExposure-Lean benchmark.
#
# Also, the AVDs should be named Nexus_5X_API_<api #>, e.g., Nexus_5X_API_23.
#
# If there are no changes to the structure, then execute this script from the
# benchmark folder
# If there are changes to the structure, then copy this script into the benchmark
# folder, make changes, and execute the modified script.  REMEMBER TO ADD THE
# SCRIPT TO VCS.

# Here are resources to help understand various tool features used in this script.
# - https://stuff.mit.edu/afs/sipb/project/android/docs/tools/testing/testing_ui.html
# - https://developer.android.com/training/testing/ui-testing/uiautomator-testing.html
# - https://developer.android.com/studio/test/command-line.html
# - https://developer.android.com/studio/command-line/adb.html
# - https://developer.android.com/studio/run/emulator-commandline.html


execute_gradle () {
    pushd $1
    ./gradlew clean $2
    if [ $? -ne 0 ] ; then
        exit $?
    fi
    popd
}

execute_gradle Benign assembleDebug
execute_gradle Malicious assembleDebug
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
#for i in 19 21 22 23 24 25 ; do
for i in 25 ; do
    echo "Testing against API $i"
    $ANDROID_HOME/tools/emulator -avd Nexus_5X_API_$i -wipe-data &
    emulator_pid=$!
    wait_for_boot_completion
    api_version="api$i"

    echo "Testing Vulnerable"
    clean_data
    if [ -f Benign/app/build/outputs/apk/$api_version/debug/app-$api_version-debug.apk ];
    then
      $ANDROID_HOME/platform-tools/adb install \
          Benign/app/build/outputs/apk/$api_version/debug/app-$api_version-debug.apk
    else
      $ANDROID_HOME/platform-tools/adb install \
          Benign/app/build/outputs/apk/app-$api_version-debug.apk
    fi

    if [ -f Malicious/app/build/outputs/apk/$api_version/debug/app-$api_version-debug.apk ];
    then
      $ANDROID_HOME/platform-tools/adb install \
          Malicious/app/build/outputs/apk/$api_version/debug/app-$api_version-debug.apk
    else
      $ANDROID_HOME/platform-tools/adb install \
          Malicious/app/build/outputs/apk/app-$api_version-debug.apk
    fi
    if [ -f Testing/benign_app/build/outputs/apk/androidTest/$api_version/debug/benign_app-$api_version-debug-androidTest.apk];
    then
      $ANDROID_HOME/platform-tools/adb install -t \
          Testing/benign_app/build/outputs/apk/androidTest/$api_version/debug/benign_app-$api_version-debug-androidTest.apk
    else
      $ANDROID_HOME/platform-tools/adb install -t \
          Testing/benign_app/build/outputs/apk/$api_version/debug/benign_app-$api_version-debug-androidTest.apk
    fi
    $ANDROID_HOME/platform-tools/adb install -t \
        Testing/benign_app/build/outputs/apk/androidTest/$api_version/debug/benign_app-$api_version-debug-androidTest.apk
    $ANDROID_HOME/platform-tools/adb shell am instrument -w \
        edu.ksu.cs.benign.test/android.support.test.runner.AndroidJUnitRunner \
        > vulnerable-$api_version-test.log
    $ANDROID_HOME/platform-tools/adb shell pm uninstall edu.ksu.cs.benign
    $ANDROID_HOME/platform-tools/adb shell pm uninstall edu.ksu.cs.malicious
    $ANDROID_HOME/platform-tools/adb shell pm uninstall edu.ksu.cs.benign.test
    if [ $strict -ne 0 ] ; then
        $ANDROID_HOME/platform-tools/adb reboot
        wait_for_boot_completion
    fi

    #echo "Testing Secure"
    #clean_data
    #$ANDROID_HOME/platform-tools/adb install \
    #    Secure/app/build/outputs/apk/$api_version/debug/app-$api_version-debug.apk
    #$ANDROID_HOME/platform-tools/adb install \
    #    Malicious/app/build/outputs/apk/$api_version/debug/app-$api_version-debug.apk
    #$ANDROID_HOME/platform-tools/adb install -t \
    #    Testing/secure_app/build/outputs/apk/androidTest/$api_version/debug/secure_app-$api_version-debug-androidTest.apk
    #$ANDROID_HOME/platform-tools/adb shell am instrument -w \
    #    edu.ksu.cs.benign.test/android.support.test.runner.AndroidJUnitRunner \
    #    > secure-$api_version-test.log
    #$ANDROID_HOME/platform-tools/adb shell pm uninstall edu.ksu.cs.benign
    #$ANDROID_HOME/platform-tools/adb shell pm uninstall edu.ksu.cs.malicious
    #$ANDROID_HOME/platform-tools/adb shell pm uninstall edu.ksu.cs.benign.test
    kill $emulator_pid
    wait $emulator_pid
done



#$ANDROID_HOME/platform-tools/adb shell pm list packages | grep edu
#$ANDROID_HOME/platform-tools/adb shell pm list instrumentation | grep edu
#$ANDROID_HOME/tools/bin/apkanalyzer apk summary app/build/outputs/apk/$api_version/debug/app-$api_version-debug.apk
