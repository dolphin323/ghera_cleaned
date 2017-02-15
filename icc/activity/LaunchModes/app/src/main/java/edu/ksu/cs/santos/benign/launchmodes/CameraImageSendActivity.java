package edu.ksu.cs.santos.benign.launchmodes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CameraImageSendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_image_send);
    }

    @Override
    public void onBackPressed(){
        Intent data = new Intent();
        data.putExtra("data","success");
        setResult(RESULT_OK,data);
        super.onBackPressed();

    }
}
