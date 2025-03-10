package edu.ksu.cs.benign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class EmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        TextView emailView = (TextView) findViewById(R.id.emailText);
        if (intent != null) {
            String email = intent.getStringExtra("email");
            String text = intent.getStringExtra("text");
            if (email != null && text != null
                    && !"".equals(email)
                    && !"".equals(text)) {
                String msg = "An email will be sent to " + email + "with the text : " + text;
                emailView.setText(msg);
            }
        }

    }
}
