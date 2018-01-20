package edu.ksu.cs.secure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CameraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
    }

    @Override
    protected void onResume(){
        super.onResume();
        Intent i = new Intent();
        i.putExtra("image","image");
        setResult(RESULT_OK,i);
    }
}
