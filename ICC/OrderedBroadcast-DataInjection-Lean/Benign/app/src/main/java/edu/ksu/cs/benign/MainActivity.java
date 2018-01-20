package edu.ksu.cs.benign;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileOutputStream;
/*
This app contains an unprotected exported Receiver that
intercepts an outgoing app and reformats it to a US
number by adding +1 to it.
 */
public class MainActivity extends AppCompatActivity {

    //final BroadcastReceiver br = new LowMemoryReceiver();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT >= 23){
            requestPermissions(new String[]{"android.permission.CALL_PHONE","android.permission.PROCESS_OUTGOING_CALLS"},101);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0377778888"));
                try{
                    startActivity(callIntent);
                }
                catch(SecurityException se){
                    Toast.makeText(getApplicationContext(),"Please grant permission to place call",Toast.LENGTH_SHORT).show();
                    se.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onPause(){
        super.onPause();
    }
}
