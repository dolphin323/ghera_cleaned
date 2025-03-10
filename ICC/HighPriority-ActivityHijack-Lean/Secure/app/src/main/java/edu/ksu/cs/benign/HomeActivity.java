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
    private Button takePic = null;
    private TextView res_view = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void onResume() {
        super.onResume();
        takePic = (Button) findViewById(R.id.takePic);
        res_view = (TextView) findViewById(R.id.result);
        res_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ImageEditor.class);
                intent.putExtra("image", res_view.getText());
                startActivity(intent);
            }
        });
        takePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), CameraActivity.class), REQ_IMG_CAPTURE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        Toast.makeText(getApplicationContext(), "on Activity result called.." + requestCode, Toast.LENGTH_SHORT).show();
        if (requestCode == REQ_IMG_CAPTURE && responseCode == RESULT_OK) {
            String result = data.getStringExtra("image");
            res_view.setText(result);
        }
    }
}
