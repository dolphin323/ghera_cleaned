package edu.ksu.cs.benign;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static int PERM_REQ_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkPermission("android.permission.SEND_SMS"
                , android.os.Process.myPid()
                , android.os.Process.myUid()) == PackageManager.PERMISSION_GRANTED
                && checkPermission("android.permission.READ_PHONE_STATE"
                , android.os.Process.myPid()
                , android.os.Process.myUid()) == PackageManager.PERMISSION_GRANTED) {
            performAction();
        } else if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{"android.permission.SEND_SMS", "android.permission.READ_PHONE_STATE"}, PERM_REQ_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERM_REQ_CODE && permissions[0].equals("android.permission.SEND_SMS")
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && permissions[1].equals("android.permission.READ_PHONE_STATE")
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            performAction();
        } else {
            throw new RuntimeException(new SecurityException());
        }
    }

    private void performAction() {
        final EditText phoneText = (EditText) findViewById(R.id.phone);
        final EditText msgText = (EditText) findViewById(R.id.msg);
        final Button sendBtn = (Button) findViewById(R.id.send);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phn = phoneText.getText().toString().trim();
                String msg = msgText.getText().toString();
                Intent intent = new Intent("edu.ksu.cs.benign.myrecv");
                intent.putExtra("number", phn);
                intent.putExtra("text", msg);
                sendBroadcast(intent);
                Toast.makeText(getApplicationContext(), "sending message", Toast.LENGTH_LONG).show();
            }
        });
    }
}
