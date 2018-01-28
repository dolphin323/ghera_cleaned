package edu.ksu.cs.malicious;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class MalActivity extends AppCompatActivity {

    private static final String TAG = "ExternalStorageMal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mal);
        if (checkPermission("android.permission.READ_EXTERNAL_STORAGE", android.os.Process.myPid(), android.os.Process.myUid()) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "android.permission.READ_EXTERNAL_STORAGE already granted");
        } else {
            Log.d(TAG, "need android.permission.READ_EXTERNAL_STORAGE permission");
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 100);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        TextView result = (TextView) findViewById(R.id.result);
        File file = new File(getExternalFilesDir(null), "ssn_bkup.jpg");
        String absPath = file.getAbsolutePath();
        String path = absPath.replace("edu.ksu.cs.malicious", "edu.ksu.cs.benign");
        Log.d(TAG, file.getAbsolutePath());
        Log.d(TAG, path);
        Bitmap image = BitmapFactory.decodeFile(path);
        if (image != null) {
            result.setText("ImageView loaded with SSN");
        } else {
            result.setText("Unable to load image");
        }
        imageView.setImageBitmap(image);
    }
}
