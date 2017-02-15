package edu.ksu.cs.santos.benign.launchmodes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*

 SingleTopActivity has android:launchmode="singleTop". This means that if an instance of this activity
 exists at the top of the current stack then that instance would be invoked otherwise a
 new instance will be created. A malicious activity will not be able to invoke a previous instance
 in this case because to do that a malicious activity will have to push itself onto the stack that
 this activity resides in. By doing that the malicious activity becomes the new top of the stack and
 invoking this activity will create a brand new instance of it. However, any malicious component including
 an activity could still be able to exploit it if it starts SingleTopActivity via an intent and
 sets the flag of the intent as FLAG_ACTIVITY_NEW_TASK.

 Steps to re-create
 1. Run SingleTopActivity
 2. Enter the username and password
 3. click on Home
 4. Run ExploitSingleTopActivity in LauncModesExploit app.
 */

public class SingleTopActivity extends AppCompatActivity {

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
