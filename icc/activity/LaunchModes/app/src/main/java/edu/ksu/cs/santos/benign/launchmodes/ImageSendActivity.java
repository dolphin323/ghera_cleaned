package edu.ksu.cs.santos.benign.launchmodes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ImageSendActivity extends AppCompatActivity {


    private Button takePic;
    private TextView disp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_send);
        takePic = (Button) findViewById(R.id.takePic);
        disp = (TextView) findViewById(R.id.dispImg);
        takePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(),CameraImageSendActivity.class),100);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.d("Benign","onActivityResult invoked");
        if(resultCode == RESULT_OK){
            String str = data.getStringExtra("data");
            disp.setText(str);
        }
    }

}
