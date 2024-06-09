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
import static org.junit.Assert.assertEquals;
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

        UiObject ben_pass = mDevice.findObject(new UiSelector()
                .resourceId(BENIGN_APP_PACKAGE + ":id/passphrase"));
        assertThat(ben_pass, notNullValue());
        ben_pass.setText("password");

        UiObject ben_add_stu_btn = mDevice.findObject(new UiSelector()
                .resourceId(BENIGN_APP_PACKAGE + ":id/add_stu_btn"));
        assertThat(ben_add_stu_btn, notNullValue());
        ben_add_stu_btn.click();

        UiObject ben_stu_name = mDevice.findObject(new UiSelector()
                .resourceId(BENIGN_APP_PACKAGE + ":id/stu_name"));
        assertThat(ben_stu_name, notNullValue());
        ben_stu_name.setText("Jhontu");

        UiObject ben_stu_grade = mDevice.findObject(new UiSelector()
                .resourceId(BENIGN_APP_PACKAGE + ":id/stu_grade"));
        assertThat(ben_stu_grade, notNullValue());
        ben_stu_grade.setText("A");

        UiObject ben_save_btn = mDevice.findObject(new UiSelector()
                .resourceId(BENIGN_APP_PACKAGE + ":id/save_btn"));
        assertThat(ben_save_btn, notNullValue());
        ben_save_btn.click();

        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        startApp(MALICIOUS_APP_PACKAGE);
        UiObject mal_get_stu_btn = mDevice.findObject(new UiSelector()
                .resourceId(MALICIOUS_APP_PACKAGE + ":id/get_stuInfo_btn"));
        assertThat(mal_get_stu_btn, notNullValue());
        mal_get_stu_btn.click();

        UiObject mal_orig_name = mDevice.findObject(new UiSelector()
                .resourceId(MALICIOUS_APP_PACKAGE + ":id/orig_name"));
        assertThat(mal_orig_name, notNullValue());
        assertEquals("Jhontu", mal_orig_name.getText());

        UiObject mal_orig_grade = mDevice.findObject(new UiSelector()
                .resourceId(MALICIOUS_APP_PACKAGE + ":id/orig_grade"));
        assertThat(mal_orig_grade, notNullValue());
        assertEquals("A", mal_orig_grade.getText());
    }
}
