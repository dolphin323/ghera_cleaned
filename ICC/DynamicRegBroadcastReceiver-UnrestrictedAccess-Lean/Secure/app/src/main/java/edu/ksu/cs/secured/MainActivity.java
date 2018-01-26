package edu.ksu.cs.secured;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private final String username = "";
    private UserLeftBroadcastRecv userLeftBroadcastRecv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userLeftBroadcastRecv = new UserLeftBroadcastRecv();
        registerReceiver(userLeftBroadcastRecv, new IntentFilter("edu.ksu.cs.action.EMAIL"), "edu.ksu.cs.secured.perm", null);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Button emailBtn = (Button) findViewById(R.id.send_email);
        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("edu.ksu.cs.action.EMAIL");
                intent.putExtra("email", "diagnostics@startup.com");
                intent.putExtra("text", "I am " + username);
                sendBroadcast(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userLeftBroadcastRecv != null) unregisterReceiver(userLeftBroadcastRecv);
    }
}
