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
        }
        if (Build.VERSION.SDK_INT > 23) {
            UiObject allowPermissions = mDevice.findObject(new UiSelector().text("ALLOW"));
            clickAllow(allowPermissions);
        }
    }

    @Test
    public void testPresenceOfVulnerability() throws UiObjectNotFoundException {
        setupDevice();

        startApp(BENIGN_APP_PACKAGE);
        UiObject editText = mDevice.findObject(new UiSelector()
                .resourceId(BENIGN_APP_PACKAGE + ":id/editText"));
        editText.setText("Password");
        UiObject save = mDevice.findObject(new UiSelector()
                .resourceId(BENIGN_APP_PACKAGE + ":id/button"));
        save.click();
        getInstrumentation().waitForIdleSync();
        mDevice.pressHome();

        startApp(MALICIOUS_APP_PACKAGE);
        allowPermissionsIfNeeded();
        getInstrumentation().waitForIdleSync();
        UiObject serviceButton = mDevice.findObject(new UiSelector()
                .resourceId(MALICIOUS_APP_PACKAGE + ":id/serviceButton"));
        getInstrumentation().waitForIdleSync();
        serviceButton.click();
        getInstrumentation().waitForIdleSync();
        UiObject result = mDevice.findObject(new UiSelector()
                .resourceId(MALICIOUS_APP_PACKAGE + ":id/result"));
        result.waitForExists(10000);
        assertThat(result.getText(), equalTo("Password"));
    }
}
