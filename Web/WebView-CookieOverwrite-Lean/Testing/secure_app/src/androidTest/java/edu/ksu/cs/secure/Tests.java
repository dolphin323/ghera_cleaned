package edu.ksu.cs.secure;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.web.webdriver.Locator;
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

import static android.support.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.getText;
import static android.support.test.espresso.web.webdriver.DriverAtoms.webClick;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@LargeTest
@SdkSuppress(minSdkVersion = 22, maxSdkVersion = 27)
public class Tests {
    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String SECURE_APP_PACKAGE = "edu.ksu.cs.benign";
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
    public void testAbsenceOfVulnerability() throws UiObjectNotFoundException {
        setupDevice();

        startApp(SECURE_APP_PACKAGE);

        UiObject button = mDevice.findObject(new UiSelector().resourceId(SECURE_APP_PACKAGE + ":id/benign"));
        assertNotNull(button);
        button.clickAndWaitForNewWindow();
        onWebView()
                .withElement(findElement(Locator.ID, "putButton"))
                .perform(webClick())
                .withElement(findElement(Locator.ID, "id"))
                .check(webMatches(getText(), equalTo("Cookies are disabled!"))); // setAcceptCookie(false) will not send and accept cookies
        mDevice.pressBack();
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        button = mDevice.findObject(new UiSelector().resourceId(SECURE_APP_PACKAGE + ":id/malicious"));
        assertNotNull(button);
        button.clickAndWaitForNewWindow();
        onWebView()
                .withElement(findElement(Locator.ID, "putButton"))
                .perform(webClick())
                .withElement(findElement(Locator.ID, "id"))
                .check(webMatches(getText(), equalTo("Cookies are disabled!")));
        mDevice.pressBack();

        button = mDevice.findObject(new UiSelector().resourceId(SECURE_APP_PACKAGE + ":id/benign"));
        assertNotNull(button);
        button.clickAndWaitForNewWindow();

        onWebView()
                .withElement(findElement(Locator.ID, "getButton"))
                .perform(webClick())
                .withElement(findElement(Locator.ID, "id"))
                .check(webMatches(getText(), equalTo("")));
    }
}
