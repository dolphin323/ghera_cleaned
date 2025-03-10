package edu.ksu.cs.benign;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    static final int REQ_IMG_CAPTURE = 0;
    static final int REQ_ACTIVITY_FOR_RES = 100;
    private ImageView iv = null;
    private Button takePic = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void onResume() {
        super.onResume();
        iv = (ImageView) findViewById(R.id.imageView);
        takePic = (Button) findViewById(R.id.takePic);
        takePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQ_IMG_CAPTURE);
                }
            }
        });
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ImageEditor.class);
                Bundle bundle = new Bundle();
                BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
                Bitmap bmap = drawable.getBitmap();
                if (bmap == null)
                    Toast.makeText(getApplicationContext(), "image is null in Home", Toast.LENGTH_SHORT).show();
                bundle.putParcelable("image", bmap);
                intent.putExtras(bundle);
                intent.setAction("Home");
                startActivityForResult(intent, REQ_ACTIVITY_FOR_RES);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        Toast.makeText(getApplicationContext(), "on Activity result called.." + requestCode, Toast.LENGTH_SHORT).show();
        if (requestCode == REQ_IMG_CAPTURE && responseCode == RESULT_OK) {
            Bitmap image = data.getParcelableExtra("data");
            iv.setImageBitmap(image);
            iv.setClickable(true);
        }
    }
}
