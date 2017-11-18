package edu.ksu.cs.secured;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

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
        Bundle b = intent.getExtras();
        iv = (ImageView) findViewById(R.id.imageEdit);
        if(b.getParcelable("image") != null)
            iv.setImageBitmap((Bitmap) b.getParcelable("image"));
    }

}

