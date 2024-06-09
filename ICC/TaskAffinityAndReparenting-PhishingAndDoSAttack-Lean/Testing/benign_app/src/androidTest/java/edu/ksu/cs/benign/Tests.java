package edu.ksu.cs.benign;

import android.content.Context;
import android.content.Intent;
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
import static org.junit.Assert.assertThat;

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

        UiObject btn = mDevice.findObject(new UiSelector().text("GOT IT"));
        try {
            btn.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        UiObject btn1 = mDevice.findObject(new UiSelector().text("OK"));
        try {
            btn1.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        allApps = mDevice.findObject(new UiSelector().descriptionContains("Apps"));
        try {
            allApps.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        UiObject btn2 = mDevice.findObject(new UiSelector().text("OK"));
        try {
            btn2.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
    }

    @Test
    public void testPresenceOfVulnerability() throws UiObjectNotFoundException {
        setupDevice();

        UiObject mal = mDevice.findObject(new UiSelector().text("Malicious"));
        mal.click();
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        UiObject btn = mDevice.findObject(new UiSelector().resourceId(MALICIOUS_APP_PACKAGE + ":id/btn"));
        btn.click();
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        mDevice.pressHome();
        allApps.click();
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        UiObject ben = mDevice.findObject(new UiSelector().text("Benign"));
        ben.click();
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        UiObject username = mDevice.findObject(new UiSelector()
                .resourceId(MALICIOUS_APP_PACKAGE + ":id/username"));
        username.setText("user");
    }
}


