package edu.ksu.cs.secure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume(){
        super.onResume();
        final EditText phoneText = (EditText) findViewById(R.id.phone);
        final EditText msgText = (EditText) findViewById(R.id.msg);
        final Button sendBtn = (Button) findViewById(R.id.send);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phn = phoneText.getText().toString().trim();
                String msg = msgText.getText().toString();
                Intent intent = new Intent("edu.ksu.cs.benign.myrecv");
                intent.putExtra("number",phn);
                intent.putExtra("text",msg);
                sendBroadcast(intent);
                Toast.makeText(getApplicationContext(),"sending message",Toast.LENGTH_LONG).show();
            }
        });
    }
}
