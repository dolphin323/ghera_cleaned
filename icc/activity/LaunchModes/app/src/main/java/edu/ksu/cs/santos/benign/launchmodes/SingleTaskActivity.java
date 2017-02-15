package edu.ksu.cs.santos.benign.launchmodes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
SingleTaskActivity is an activity whose android:launchmode="singleTask". This means that
the Android system will have only one instance of this activity. Every time SingleTaskActivity
is invoked the Android system instantiates it with its last saved state and puts it at the root
of a new stack.

SingleTaskActivity is susceptible to attack from Malicious component. The sample code below
takes in username/password from a user and sends sms if the username and password are registered.
It also does not require users to sign in if they had signed in once before. A malicious component
can invoke SingleTaskActivity and use previously set username and password to send an sms.

Steps to re-create
 1. Launch the launchmodes app and enter the username and password fields.
 2. Click on the home button
 3. Launch the launchmodesExploit app


 To completely avoid this vulnerability set android:launchmode="standard" which is also the default.
 However this will also mean that every time SingleTaskActivity is invoked a new instance of it will
 be created by the system.
 */

public class SingleTaskActivity extends AppCompatActivity {

    private String nm = null;
    private String pass = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_task);
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
            //do something important like send sms
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
            /*
            elaborate business logic
             */
            return true;
        }
        return false;
    }
}
