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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@LargeTest
@SdkSuppress(minSdkVersion = 22, maxSdkVersion = 27)
public class Tests {
    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BENIGN_APP_PACKAGE = "edu.ksu.cs.benign";
    private static final String MALICIOUS_APP_PACKAGE = "edu.ksu.cs.malicious";
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
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(appPackageName).depth(0)),
                LAUNCH_TIMEOUT);
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
    }

    @Test
    public void testAbsenceOfVulnerability() throws UiObjectNotFoundException {
        setupDevice();

        startApp(BENIGN_APP_PACKAGE);


        UiObject usrnm = mDevice.findObject(new UiSelector()
                .resourceId(BENIGN_APP_PACKAGE + ":id/usrnm"));
        UiObject pwd = mDevice.findObject(new UiSelector()
                .resourceId(BENIGN_APP_PACKAGE + ":id/pwd"));
        UiObject signin_btn = mDevice.findObject(new UiSelector()
                .resourceId(BENIGN_APP_PACKAGE + ":id/signIn"));
        usrnm.setText("user");
        pwd.setText("pass");
        signin_btn.click();
        UiObject takePic_btn = mDevice.findObject(new UiSelector()
                .resourceId(BENIGN_APP_PACKAGE + ":id/takePic"));
        takePic_btn.click();
        mDevice.pressBack();
        UiObject img_view = mDevice.findObject(new UiSelector()
                .resourceId(BENIGN_APP_PACKAGE + ":id/imageView"));
        img_view.click();

        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        startApp(MALICIOUS_APP_PACKAGE);

        startApp(BENIGN_APP_PACKAGE);
        img_view.click();

        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        if (Build.VERSION.SDK_INT > 21) {
            UiObject img = mDevice.findObject(new UiSelector()
                    .resourceId(BENIGN_APP_PACKAGE + ":id/editImg"));
            assertEquals("image", img.getText());
        } else {
            UiObject uiObject = mDevice.findObject(new UiSelector()
                    .text("Secure"));
            uiObject.getText();
        }
    }
}
