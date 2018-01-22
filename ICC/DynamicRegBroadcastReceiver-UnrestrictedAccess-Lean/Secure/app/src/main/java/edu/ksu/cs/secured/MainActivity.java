package edu.ksu.cs.secured;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private final String username = "";
    private UserLeftBroadcastRecv userLeftBroadcastRecv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userLeftBroadcastRecv = new UserLeftBroadcastRecv();
        registerReceiver(userLeftBroadcastRecv, new IntentFilter("edu.ksu.cs.santos.action.USER_LEFT"), "edu.ksu.cs.secured.perm", null);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent = new Intent("edu.ksu.cs.santos.action.USER_LEFT");
        intent.putExtra("email", "diagnostics@startup.com");
        intent.putExtra("text", username + "left app after x mins");
        sendBroadcast(intent);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userLeftBroadcastRecv != null) unregisterReceiver(userLeftBroadcastRecv);
    }
}
