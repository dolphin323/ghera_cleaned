package edu.ksu.cs.benign;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static int REQ_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{"android.permission.PROCESS_OUTGOING_CALLS", "android.permission.CALL_PHONE"}, REQ_CODE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT < 23) {
            performAction();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQ_CODE && permissions[1].equals("android.permission.CALL_PHONE")
                && permissions[0].equals("android.permission.PROCESS_OUTGOING_CALLS")
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            performAction();
        } else throw new RuntimeException(new SecurityException());
    }

    private void performAction(){
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0377778888"));

                callIntent.putExtra(Intent.EXTRA_PHONE_NUMBER, "0377778888");
                startActivity(callIntent);
            }
        });
    }
}
