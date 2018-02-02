package edu.ksu.cs.benign;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class DownloadService extends IntentService {
    private static String TAG = "Secure/DownloadService";

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            if (intent.getStringExtra("filename") != null) {
                File file = null;
                String FILENAME = intent.getStringExtra("filename");
                try (FileInputStream fis = openFileInput(FILENAME); InputStreamReader isr = new InputStreamReader(fis);
                     BufferedReader bufferedReader = new BufferedReader(isr)) {
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }
                    Log.d(TAG, sb.toString());

                /*
                writing to external storage
                 */
                    file = new File(getExternalFilesDir(null), FILENAME);
                    OutputStream os = new FileOutputStream(file);
                    os.write(sb.toString().getBytes());
                } catch (IOException e) {
                    Log.d(TAG, "error while saving ...");
                    e.printStackTrace();
                    throw new RuntimeException("IOException occurred.");
                }
                Log.d(TAG, "Written to external storage in = " + file.getAbsolutePath());
            }
        }
    }
}
