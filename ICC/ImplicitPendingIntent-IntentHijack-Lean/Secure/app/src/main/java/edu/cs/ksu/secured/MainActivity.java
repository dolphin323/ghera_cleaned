package edu.cs.ksu.secured;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume(){
        super.onResume();
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent i = new Intent();
                ComponentName componentName = new ComponentName(getApplicationContext().getPackageName(),"edu.cs.ksu.secured.MyService");
                i.setComponent(componentName);
                i.putExtra("password","password");
                PendingIntent pi = PendingIntent.getService(getApplicationContext(),0,i,PendingIntent.FLAG_UPDATE_CURRENT);
                bundle.putParcelable("pendingIntent",pi);
                Intent intent = new Intent();
                intent.setPackage("edu.cs.ksu.benignpartner");
                intent.setClassName("edu.cs.ksu.benignpartner","edu.cs.ksu.benignpartner.MyReceiver");
                intent.putExtra("bundle",bundle);
                sendBroadcast(intent);
            }
        });
    }
}
