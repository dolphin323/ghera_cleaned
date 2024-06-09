package edu.ksu.cs.secure;

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
import android.support.test.espresso.web.webdriver.Locator;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.getText;
import static android.support.test.espresso.web.webdriver.DriverAtoms.webClick;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@LargeTest
@SdkSuppress(minSdkVersion = 22, maxSdkVersion = 27)
public class Tests {
    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String SECURE_APP_PACKAGE = "edu.ksu.cs.benign";
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

    @Test
    public void testAbsenceOfVulnerability() throws UiObjectNotFoundException {
        setupDevice();

        startApp(SECURE_APP_PACKAGE);

        UiObject locationAccessPermission = mDevice.findObject(new UiSelector().textMatches("(?i)allow(?-i)"));
        assertThat(locationAccessPermission, notNullValue());
        if (locationAccessPermission.exists()) {
            locationAccessPermission.click();
        }
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        UiObject urlEditText = mDevice.findObject(new UiSelector()
                .resourceId("edu.ksu.cs.benign:id/url"));
        assertThat(urlEditText, notNullValue());

        String urlString = "http:
                InstrumentationRegistry.getContext().getResources().getString(R.string.local_server_port) + "/location/";
        urlEditText.setText(urlString);
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        UiObject button = mDevice.findObject(new UiSelector()
                .resourceId("edu.ksu.cs.benign:id/load"));
        assertThat(button, notNullValue());
        button.click();
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        UiObject allowWebpage = mDevice.findObject(new UiSelector().text("NO"));
        assertThat(allowWebpage, notNullValue());
        if (allowWebpage.exists()) {
            allowWebpage.click();
        }
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        onWebView()
                .withElement(findElement(Locator.ID, "show-lat-button"))
                .perform(webClick());
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        onWebView()
                .withElement(findElement(Locator.ID, "lat"))
                .check(webMatches(getText(), containsString("Permission Denied")));
    }
}
