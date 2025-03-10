package edu.ksu.cs.benign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    static final int REQ_IMG_CAPTURE = 0;
    private TextView iv = null;
    private Button takePic = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void onResume() {
        super.onResume();
        iv = (TextView) findViewById(R.id.imageView);
        takePic = (Button) findViewById(R.id.takePic);
        takePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                startActivityForResult(intent, REQ_IMG_CAPTURE);
            }
        });
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ImageEditor.class);
                intent.putExtra("image", iv.getText());
                intent.setAction("Home");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        Toast.makeText(getApplicationContext(), "on Activity result called.." + requestCode, Toast.LENGTH_SHORT).show();
        if (requestCode == REQ_IMG_CAPTURE && responseCode == RESULT_OK) {
            String image = data.getStringExtra("image");
            iv.setText(image);
            iv.setClickable(true);
        }
    }

}
