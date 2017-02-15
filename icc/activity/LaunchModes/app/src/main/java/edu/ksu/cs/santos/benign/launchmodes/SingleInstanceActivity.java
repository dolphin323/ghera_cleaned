package edu.ksu.cs.santos.benign.launchmodes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
SingleTaskActivity will be vulnerable if android:launchmode="singleInstance". This means that
 the Android system will have exactly one instance of this activity and this activity will be the only
 activity in a task. New activites started by SingleTaskActivity will start those activities in a separate
 task.

Steps to re-create
 1. Launch SingleInstanceActivity in the launchmodes app and enter the username and password fields.
 2. Click on the home button
 3. Launch the ExploitSingleInstanceActivity in the launchmodesExploit app
 */

public class SingleInstanceActivity extends AppCompatActivity {

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
