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
    public void testAbsenceOfVulnerability() throws UiObjectNotFoundException {
        setupDevice();

        startApp(BENIGN_APP_PACKAGE);
        UiObject add_student_btn = mDevice.findObject(new UiSelector()
                .resourceId(BENIGN_APP_PACKAGE + ":id/button"));
        assertThat(add_student_btn, notNullValue());
        add_student_btn.click();

        UiObject stu_name_text = mDevice.findObject(new UiSelector()
                .resourceId(BENIGN_APP_PACKAGE + ":id/stu_name"));
        assertThat(stu_name_text, notNullValue());
        stu_name_text.setText("Jane");

        UiObject stu_grade_text = mDevice.findObject(new UiSelector()
                .resourceId(BENIGN_APP_PACKAGE + ":id/stu_grade"));
        assertThat(stu_grade_text, notNullValue());
        stu_grade_text.setText("C");

        UiObject save_btn = mDevice.findObject(new UiSelector()
                .resourceId(BENIGN_APP_PACKAGE + ":id/save_btn"));
        assertThat(save_btn, notNullValue());
        save_btn.click();

        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        startApp(MALICIOUS_APP_PACKAGE);
        UiObject mal_stu_name_text = mDevice.findObject(new UiSelector()
                .resourceId(MALICIOUS_APP_PACKAGE + ":id/name"));
        assertThat(mal_stu_name_text, notNullValue());
        mal_stu_name_text.setText("Jane");

        UiObject mal_stu_grade_text = mDevice.findObject(new UiSelector()
                .resourceId(MALICIOUS_APP_PACKAGE + ":id/grade"));
        assertThat(mal_stu_grade_text, notNullValue());
        mal_stu_grade_text.setText("C");

        UiObject inject_btn = mDevice.findObject(new UiSelector()
                .resourceId(MALICIOUS_APP_PACKAGE + ":id/inject_btn"));
        assertThat(inject_btn, notNullValue());
        inject_btn.click();

        UiObject verify_btn = mDevice.findObject(new UiSelector()
                .resourceId(MALICIOUS_APP_PACKAGE + ":id/verify_btn"));
        assertThat(verify_btn, notNullValue());
        verify_btn.click();

        UiObject result = mDevice.findObject(new UiSelector()
                .resourceId(MALICIOUS_APP_PACKAGE + ":id/result"));
        assertThat(result, notNullValue());
        assertEquals("Guess result ...", result.getText());

    }
}
