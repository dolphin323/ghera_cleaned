package edu.ksu.cs.santos.benign.sendgreeting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import edu.ksu.cs.santos.benign.sendgreetings.R;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String packageNm = getPackageName();
        Toast.makeText(getApplicationContext(),packageNm,Toast.LENGTH_SHORT).show();
    }
}
