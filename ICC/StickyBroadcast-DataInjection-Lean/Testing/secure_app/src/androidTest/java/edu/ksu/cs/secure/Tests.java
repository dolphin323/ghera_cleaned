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

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@LargeTest
@SdkSuppress(minSdkVersion = 19, maxSdkVersion = 25)
public class Tests {
    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String MALICIOUS_APP_PACKAGE = "edu.ksu.cs.malicious";
    private static final String BENIGN_APP_PACKAGE = "edu.ksu.cs.benign";
    private static final String BENIGN_PARTNER_APP_PACKAGE = "edu.ksu.cs.benignpartner";
    private UiDevice mDevice;
    private UiObject allApps;

    private void setupDevice() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        if (Build.VERSION.SDK_INT == 24
                || Build.VERSION.SDK_INT == 23) {
            UiObject btn = mDevice.findObject(new UiSelector().text("GOT IT"));
            try {
                btn.click();
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (Build.VERSION.SDK_INT <= 22) {
            UiObject btn = mDevice.findObject(new UiSelector().text("OK"));
            try {
                btn.click();
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }
        }
        allApps = mDevice.findObject(new UiSelector().descriptionContains("Apps"));
        try {
            allApps.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT <= 22) {
            UiObject btn = mDevice.findObject(new UiSelector().text("OK"));
            try {
                btn.click();
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }
        }
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

    }

    /*
    The way the apps are launched to test for the secure benchmark
    are different from the way the apps are launched for benign benchmark.
    The apps launched in this test are launched from the icon but the
     apps launched in the test for benign are launched via package name
     */
    @Test
    public void testAbsenceOfVulnerability() throws UiObjectNotFoundException {
        setupDevice();

        mDevice.pressHome();
        allApps.click();
        UiObject bpartner = mDevice.findObject(new UiSelector().text("BenignPartner"));
        bpartner.click();

        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        startApp(BENIGN_APP_PACKAGE);

        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        mDevice.pressHome();
        allApps.click();
        UiObject mal = mDevice.findObject(new UiSelector().text("Malicious"));
        mal.click();

        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        mDevice.pressHome();
        allApps.click();
        bpartner.click();

        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        UiObject text1 = mDevice.findObject(new UiSelector()
                .resourceId(BENIGN_PARTNER_APP_PACKAGE + ":id/msg"));
        assertThat(text1, notNullValue());
        assertEquals("Send SMS to +177770001111", text1.getText());


    }
}
