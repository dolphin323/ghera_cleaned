package edu.ksu.cs.secured;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
    }

    @Override
    protected void onResume(){
        super.onResume();
        //registerReceiver(br,new IntentFilter("benign.intent.action.test"));
        //sendBroadcast(new Intent(getApplicationContext(),LowMemoryReceiver.class));
        //getApplicationContext().registerReceiver(new PowerConnectedDynaReceiver(),new IntentFilter("com.some.intent.filter"));
        Button button = (Button) findViewById(R.id.button);
        String filename = "myfile";
        String string = "Hello world!";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0377778888"));

                callIntent.putExtra(Intent.EXTRA_PHONE_NUMBER,"0377778888");
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
        //unregisterReceiver(br);
    }
}
