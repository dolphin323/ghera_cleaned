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

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
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
        
        mDevice = UiDevice.getInstance(getInstrumentation());
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
        getInstrumentation().waitForIdleSync();
    }

    private void bringAppToForeground(final String appPackageName) {
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(appPackageName);
        context.startActivity(intent);
    }

    private void clickAllow(UiObject allowPermissions) {
        if (allowPermissions.exists()) {
            try {
                allowPermissions.click();
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void allowPermissionsIfNeeded() {
        if (Build.VERSION.SDK_INT == 23) {
            UiObject allowPermissions = mDevice.findObject(new UiSelector().text("Allow"));
            clickAllow(allowPermissions);
        } else if (Build.VERSION.SDK_INT > 23) {
            UiObject allowPermissions = mDevice.findObject(new UiSelector().text("ALLOW"));
            clickAllow(allowPermissions);
        }
    }

    @Test
    public void testPresenceOfVulnerability() throws UiObjectNotFoundException {
        setupDevice();

        startApp(BENIGN_APP_PACKAGE);
        getInstrumentation().waitForIdleSync();
        UiObject button = mDevice.findObject(new UiSelector()
                .resourceId(BENIGN_APP_PACKAGE + ":id/button"));
        button.click();
        getInstrumentation().waitForIdleSync();
        UiObject bibleText = mDevice.findObject(new UiSelector()
                .resourceId(BENIGN_APP_PACKAGE + ":id/bibleText"));
        assertEquals(bibleText.getText(), "This is written by Benign");

        startApp(MALICIOUS_APP_PACKAGE);
        allowPermissionsIfNeeded();
        getInstrumentation().waitForIdleSync();
        mDevice.pressHome();

        bringAppToForeground(BENIGN_APP_PACKAGE);
        button.click();
        getInstrumentation().waitForIdleSync();
        bibleText = mDevice.findObject(new UiSelector()
                .resourceId(BENIGN_APP_PACKAGE + ":id/bibleText"));
        assertThat(bibleText.getText(), equalTo("To answer before listening — that is folly and shame."));
    }
}
