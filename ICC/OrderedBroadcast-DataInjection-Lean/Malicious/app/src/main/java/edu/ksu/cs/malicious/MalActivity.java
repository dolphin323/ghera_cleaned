package edu.ksu.cs.malicious;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/*
This app contains a malicious broadcast that listens to outgoing
calls, intercepts them, changes the number and forwards it to
the next receiver.
 */
public class MalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mal);
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{"android.permission.PROCESS_OUTGOING_CALLS"}, 101);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
