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

public class DownloadService extends IntentService {

    private static String TAG = "Benign/DownloadService";

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        File file;
        if (intent != null) {
            if (intent.getStringExtra("filename") != null) {
                String FILENAME = intent.getStringExtra("filename");
                try (FileInputStream fis = openFileInput(FILENAME); InputStreamReader isr = new InputStreamReader(fis);
                     BufferedReader bufferedReader = new BufferedReader(isr)) {
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }
                    Log.d(TAG, sb.toString());

                    file = new File(getExternalFilesDir(null), FILENAME);
                    OutputStream os = new FileOutputStream(file);
                    os.write(sb.toString().getBytes());
                } catch (IOException e) {
                    Log.d(TAG, "error while saving ...");
                    e.printStackTrace();
                    throw new RuntimeException("IOException occurred.");
                }
                Log.d(TAG, "Written to external storage in = " + file.getAbsolutePath());
                String getPackage = intent.getStringExtra("confirmationPackage");
                String getAction = intent.getStringExtra("confirmationReceiver");
                Intent i = new Intent();
                i.setPackage(getPackage);
                i.setAction(getAction);
                sendBroadcast(i);
            }
        }
    }
}
