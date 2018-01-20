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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
@SdkSuppress(minSdkVersion=19, maxSdkVersion=25)
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

    }

    @Test
    public void testPresenceOfVulnerability() throws UiObjectNotFoundException {
        setupDevice();

        //startApp(MALICIOUS_APP_PACKAGE);
        /*UiObject allApps = mDevice.findObject(new UiSelector()
                .resourceId("com.google.android.apps.nexuslauncher:id/all_apps_handle"));*/
        if(Build.VERSION.SDK_INT == 24
                || Build.VERSION.SDK_INT == 23){
            UiObject btn = mDevice.findObject(new UiSelector().text("GOT IT"));
            btn.click();
        }
        if(Build.VERSION.SDK_INT <= 22){
            UiObject btn = mDevice.findObject(new UiSelector().text("OK"));
            btn.click();
        }
        UiObject allApps = mDevice.findObject(new UiSelector().descriptionContains("Apps"));
        allApps.click();
        if(Build.VERSION.SDK_INT <= 22){
            UiObject btn = mDevice.findObject(new UiSelector().text("OK"));
            btn.click();
        }
        UiObject mal = mDevice.findObject(new UiSelector().text("Malicious"));
        mal.click();
        mDevice.pressHome();
        allApps.click();
        UiObject ben = mDevice.findObject(new UiSelector().text("Benign"));
        ben.click();
        UiObject malText = mDevice.findObject(new UiSelector().resourceId(MALICIOUS_APP_PACKAGE+":id/mal_text"));
        assertEquals("Welcome to Malicious World!!!",malText.getText());

    }
}


