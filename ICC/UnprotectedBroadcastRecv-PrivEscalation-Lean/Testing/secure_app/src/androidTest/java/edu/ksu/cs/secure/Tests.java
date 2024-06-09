package edu.ksu.cs.secure;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@LargeTest
@SdkSuppress(minSdkVersion = 22, maxSdkVersion = 27)
public class Tests {
    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String MALICIOUS_APP_PACKAGE = "edu.ksu.cs.malicious";
    private static final String BENIGN_APP_PACKAGE = "edu.ksu.cs.benign";
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private UiDevice mDevice;

    private void setupDevice() {
        
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    private void startApp(final String appPackageName) {
        
        mDevice.pressHome();

        
        final String launcherPackage = mDevice.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                LAUNCH_TIMEOUT);

        
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(appPackageName);
        
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        
        mDevice.wait(Until.hasObject(By.pkg(appPackageName).depth(0)),
                LAUNCH_TIMEOUT);
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
    }

    private void askForPerm(){
        if (Build.VERSION.SDK_INT >= 23) {
            UiObject perm = mDevice.findObject(new UiSelector().textMatches("(?i)allow(?-i)"));
            try {
                perm.click();
            } catch (UiObjectNotFoundException e) {
                Log.d("BenignTest", "Perm related UI not found");
                e.printStackTrace();
            }
            InstrumentationRegistry.getInstrumentation().waitForIdleSync();
            UiObject perm1 = mDevice.findObject(new UiSelector().textMatches("(?i)allow(?-i)"));
            try {
                perm1.click();
            } catch (UiObjectNotFoundException e) {
                Log.d("BenignTest", "Perm related UI not found");
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    public void testAbsenceOfVulnerability() throws UiObjectNotFoundException {
        setupDevice();

        startApp(BENIGN_APP_PACKAGE);
        askForPerm();
        startApp(MALICIOUS_APP_PACKAGE);

        if (Build.VERSION.SDK_INT > 20) {
            mDevice.pressHome();
            UiObject msgApps = mDevice.findObject(new UiSelector().textStartsWith("Mess"));
            msgApps.click();
            UiObject text = mDevice.findObject(new UiSelector().textContains("Secure:"));
            thrown.expect(UiObjectNotFoundException.class);
            text.getText();
        } else {
            startApp("com.android.mms");
            UiObject text = mDevice.findObject(new UiSelector().textContains("Secure:"));
            thrown.expect(UiObjectNotFoundException.class);
            text.getText();
        }

    }
}
