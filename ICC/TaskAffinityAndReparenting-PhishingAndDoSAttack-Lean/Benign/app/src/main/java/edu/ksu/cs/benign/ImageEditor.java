package edu.ksu.cs.benign;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageEditor extends AppCompatActivity {

    ImageView iv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_editor);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        Toast.makeText(getApplicationContext(), intent.getAction(), Toast.LENGTH_SHORT).show();
        Bundle b = intent.getExtras();
        iv = (ImageView) findViewById(R.id.editImg);
        if (b.getParcelable("image") != null)
            iv.setImageBitmap((Bitmap) b.getParcelable("image"));
        else Toast.makeText(getApplicationContext(), "Image is null", Toast.LENGTH_SHORT).show();
    }
}
