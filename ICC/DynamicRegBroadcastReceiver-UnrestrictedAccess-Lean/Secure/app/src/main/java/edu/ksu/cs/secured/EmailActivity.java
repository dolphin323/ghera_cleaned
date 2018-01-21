package edu.ksu.cs.secured;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
    }

    @Override
    protected  void onResume(){
        super.onResume();
        Intent intent = getIntent();
        TextView emailView = (TextView) findViewById(R.id.emailText);
        String email = intent.getStringExtra("email");
        String text = intent.getStringExtra("text");
        String msg = "An email will be sent to " + email + "with the text : " +  text;
        emailView.setText(msg);

    }
}
