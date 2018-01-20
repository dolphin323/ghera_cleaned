package edu.ksu.cs.benign;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ImageEditor extends AppCompatActivity {

    ImageView iv = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_editor);
    }

    @Override
    protected void onResume(){
        super.onResume();
        Intent intent = getIntent();
        String img = intent.getStringExtra("image");
        TextView tv = (TextView) findViewById(R.id.editImg);
        tv.setText(img);
    }
}
