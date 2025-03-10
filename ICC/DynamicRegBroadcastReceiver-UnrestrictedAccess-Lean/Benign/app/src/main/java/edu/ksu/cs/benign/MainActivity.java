package edu.ksu.cs.benign;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private final String username = "user";
    private EmailBroadcastRecv emailBroadcastRecv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailBroadcastRecv = new EmailBroadcastRecv();
        registerReceiver(emailBroadcastRecv, new IntentFilter("edu.ksu.cs.action.EMAIL"));
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
    protected void onDestroy() {
        super.onDestroy();
        if (emailBroadcastRecv != null) unregisterReceiver(emailBroadcastRecv);
    }
}
