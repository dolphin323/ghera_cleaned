package edu.ksu.cs.santos.benign.launchmodes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity1 extends AppCompatActivity {

    private String nm = null;
    private String pass = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);
    }

    @Override
    protected void onResume(){
        super.onResume();
        EditText usrnm = (EditText) findViewById(R.id.usrnm);
        final EditText pwd = (EditText) findViewById(R.id.pwd);
        Button signIn = (Button) findViewById(R.id.signin);
        nm = usrnm.getText().toString();
        pass = pwd.getText().toString();
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRegistered(nm,pass)){
                    //send sms
                    Toast.makeText(getApplicationContext(),"sms sent!",Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getApplicationContext(),"Enter the correct credentials",Toast.LENGTH_SHORT).show();
            }
        });
        if(hasLoggedInBefore(nm,pass)){
            //send sms
            Toast.makeText(getApplicationContext(),"sms sent!",Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(getApplicationContext(),"Please sign in send message",Toast.LENGTH_SHORT).show();
    }

    private boolean isRegistered(String username, String pwd){
        if(username != null && pwd != null) return true;
        return false;
    }

    private boolean hasLoggedInBefore(String usr, String pwd){
        if(usr != null && pwd != null && usr.equals("joy") && pwd.equals("joy")){
            return true;
        }
        return false;
    }
}
