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
        UiObject benign_reset_pwd_email_et = mDevice.findObject(new UiSelector()
                .resourceId("edu.ksu.cs.benign:id/email")
                .className("android.widget.EditText"));
        assertThat(benign_reset_pwd_email_et, notNullValue());

        String email = "2222222222222222anniemaes@gmail.com";
        benign_reset_pwd_email_et.setText(email);
        UiObject get_token_btn = mDevice.findObject(new UiSelector()
                .resourceId("edu.ksu.cs.benign:id/get_token_btn"));
        assertThat(get_token_btn, notNullValue());
        get_token_btn.click();

        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        UiObject tokenEt = mDevice.findObject(new UiSelector()
                .resourceId("edu.ksu.cs.benign:id/benign_token")
                .className("android.widget.EditText"));
        assertThat(tokenEt, notNullValue());

        String token = tokenEt.getText();

        startApp(MALICIOUS_APP_PACKAGE);
        UiObject mal_token_et = mDevice.findObject(new UiSelector()
                .resourceId("edu.ksu.cs.malicious:id/malicious_token")
                .className("android.widget.EditText"));
        assertThat(mal_token_et, notNullValue());
        mal_token_et.setText(token);
        UiObject reset_pwd_button = mDevice.findObject(new UiSelector()
                .resourceId("edu.ksu.cs.malicious:id/reset_pwd_malicious"));
        assertThat(reset_pwd_button, notNullValue());
        reset_pwd_button.click();

        UiObject benign_new_pwd_email_et = mDevice.findObject(new UiSelector()
                .resourceId("edu.ksu.cs.benign:id/email_text"));
        assertThat(benign_new_pwd_email_et, notNullValue());
        assertThat(benign_new_pwd_email_et.getText(), equalTo("anniemaes@gmail.com"));
    }
}


