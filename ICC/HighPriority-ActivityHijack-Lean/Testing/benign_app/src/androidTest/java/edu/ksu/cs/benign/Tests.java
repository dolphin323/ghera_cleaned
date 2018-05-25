package edu.ksu.cs.benign;

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

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@LargeTest
@SdkSuppress(minSdkVersion = 22, maxSdkVersion = 27)
public class Tests {
    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String MALICIOUS_APP_PACKAGE = "edu.ksu.cs.malicious";
    private static final String BENIGN_APP_PACKAGE = "edu.ksu.cs.benign";
    private UiDevice mDevice;

    private void setupDevice() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    private void startApp(final String appPackageName) {
        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = mDevice.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                LAUNCH_TIMEOUT);

        // Launch the app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(appPackageName);
        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(appPackageName).depth(0)),
                LAUNCH_TIMEOUT);
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
    }

    @Test
    public void testPresenceOfVulnerability() throws UiObjectNotFoundException {
        setupDevice();

        startApp(BENIGN_APP_PACKAGE);
        UiObject takePic_btn = mDevice.findObject(new UiSelector().resourceId(BENIGN_APP_PACKAGE + ":id/takePic"));
        assertThat(takePic_btn, notNullValue());
        UiObject result_tv = mDevice.findObject(new UiSelector().resourceId(BENIGN_APP_PACKAGE + ":id/result"));
        assertThat(result_tv, notNullValue());
        takePic_btn.click();
        UiObject done_btn = mDevice.findObject(new UiSelector().resourceId(BENIGN_APP_PACKAGE + ":id/done_btn"));
        assertThat(done_btn, notNullValue());
        done_btn.click();
        assertEquals("image", result_tv.getText());
        result_tv.click();
        UiObject activity = mDevice.findObject(new UiSelector().text("Malicious"));
        assertThat(activity, notNullValue());
        activity.click();
        if(Build.VERSION.SDK_INT < 26){
          UiObject justOnce = mDevice.findObject(new UiSelector().textMatches("(?i)just once(?-i)"));
          assertThat(justOnce, notNullValue());
          justOnce.click();
        }

        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        if (Build.VERSION.SDK_INT > 21) {
            UiObject dispText = mDevice.findObject(new UiSelector().resourceId(MALICIOUS_APP_PACKAGE + ":id/disp"));
            assertThat(dispText, notNullValue());
            assertEquals("image", dispText.getText());
        } else {
            UiObject dispText = mDevice.findObject(new UiSelector().text("Malicious"));
            assertEquals("Malicious", dispText.getText().trim());
        }
    }
}
