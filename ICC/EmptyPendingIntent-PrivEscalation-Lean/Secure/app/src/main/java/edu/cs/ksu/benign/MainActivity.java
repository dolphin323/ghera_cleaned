package edu.cs.ksu.benign;

import android.app.PendingIntent;
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
    protected void onResume() {
        super.onResume();
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                PendingIntent pi = PendingIntent.getService(getApplicationContext(), 0, new Intent("santos.cs.ksu.myServ"), PendingIntent.FLAG_UPDATE_CURRENT);
                bundle.putParcelable("pendingIntent", pi);
                Intent intent = new Intent("santos.cs.ksu.edu.benignpireceiver");
                intent.putExtra("bundle", bundle);
                sendBroadcast(intent);
            }
        });
    }
}
