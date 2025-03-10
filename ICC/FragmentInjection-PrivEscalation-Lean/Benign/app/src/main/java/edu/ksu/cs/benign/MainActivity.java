package edu.ksu.cs.benign;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final String fname = getIntent().getStringExtra("fname");
        Button loadFrag = (Button) findViewById(R.id.load_frag);
        loadFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fname != null && !fname.equals("")){
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.my_placeholder,Util.getFragmentInstance(fname));
                    ft.commit();
                }
                else{
                    Toast.makeText(getApplicationContext(),"fragment name missing",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
