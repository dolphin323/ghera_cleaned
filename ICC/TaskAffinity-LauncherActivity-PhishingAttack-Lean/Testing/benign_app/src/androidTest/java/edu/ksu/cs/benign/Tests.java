package edu.ksu.cs.benign;

import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@LargeTest
@SdkSuppress(minSdkVersion = 22, maxSdkVersion = 27)
public class Tests {
    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String MALICIOUS_APP_PACKAGE = "edu.ksu.cs.malicious";
    private static final String BENIGN_APP_PACKAGE = "edu.ksu.cs.benign";
    private UiDevice mDevice;
    private UiObject allApps;

    private void setupDevice() {
        
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        if (Build.VERSION.SDK_INT > 21) {
            UiObject btn = mDevice.findObject(new UiSelector().text("GOT IT"));
            try {
                btn.click();
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (Build.VERSION.SDK_INT < 22) {
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

        if (Build.VERSION.SDK_INT < 22) {
            UiObject btn = mDevice.findObject(new UiSelector().text("OK"));
            try {
                btn.click();
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testPresenceOfVulnerability() throws UiObjectNotFoundException {
        setupDevice();
        UiObject mal = mDevice.findObject(new UiSelector().text("Malicious"));
        mal.click();
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        mDevice.pressHome();
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        allApps.click();
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        UiObject ben = mDevice.findObject(new UiSelector().text("Benign"));
        ben.click();
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        UiObject malText = mDevice.findObject(new UiSelector().resourceId(MALICIOUS_APP_PACKAGE + ":id/mal_text"));
        assertEquals("Welcome to Malicious World!!!", malText.getText());
    }
}


