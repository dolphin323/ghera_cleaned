package edu.ksu.cs.benign;

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

import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.webClick;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@LargeTest
@SdkSuppress(minSdkVersion = 22, maxSdkVersion = 27)
public class Tests {
    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BENIGN_APP_PACKAGE = "edu.ksu.cs.benign";
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
    public void testPresenceOfVulnerability() throws UiObjectNotFoundException {
        setupDevice();

        startApp(BENIGN_APP_PACKAGE);

        UiObject urlEditText = mDevice.findObject(new UiSelector()
                .resourceId("edu.ksu.cs.benign:id/url"));
        assertThat(urlEditText, notNullValue());

        String urlString = "http:
                InstrumentationRegistry.getContext().getResources().getString(R.string.local_server_port) + "/intent/";
        urlEditText.setText(urlString);
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        UiObject button = mDevice.findObject(new UiSelector()
                .resourceId("edu.ksu.cs.benign:id/load"));
        assertThat(button, notNullValue());
        button.click();
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        onWebView()
                .withElement(findElement(Locator.ID, "click_intent"))
                .perform(webClick());
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        UiObject allowMaliciousButton = mDevice.findObject(new UiSelector().text("Malicious"));
        assertThat(allowMaliciousButton, notNullValue());
        allowMaliciousButton.click();
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        UiObject justOnceButton = mDevice.findObject(new UiSelector().textMatches("(?i)just once(?-i)"));
        assertThat(justOnceButton, notNullValue());
        justOnceButton.click();
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        UiObject text2 = mDevice.findObject(new UiSelector()
                .resourceId("edu.ksu.cs.malicious:id/infoText"));
        assertThat(text2, notNullValue());
        assertThat(text2.getText(), equalTo("Sensitive information"));
    }
}
