package edu.ksu.cs.benign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText user = null;
    EditText pwd = null;
    Button signIn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onResume() {
        super.onResume();
        user = (EditText) findViewById(R.id.usrnm);
        pwd = (EditText) findViewById(R.id.pwd);
        signIn = (Button) findViewById(R.id.signIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (authenticate(user.getText().toString(), pwd.getText().toString())) {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                } else
                    Toast.makeText(getApplicationContext(), "Enter valid credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean authenticate(String username, String password) {
        return (username != null && password != null);
    }
}
