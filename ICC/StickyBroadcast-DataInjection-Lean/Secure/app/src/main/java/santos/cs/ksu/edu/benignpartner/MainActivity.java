package santos.cs.ksu.edu.benignpartner;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver myBr = new MyReceiver();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerReceiver(myBr,new IntentFilter("santos.cs.ksu.edu.benign.MyReceiver"));
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(myBr);
    }
}
