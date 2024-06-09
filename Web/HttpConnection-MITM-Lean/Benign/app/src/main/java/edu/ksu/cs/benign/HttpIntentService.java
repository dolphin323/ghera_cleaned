package edu.ksu.cs.benign;

import android.app.IntentService;
import android.content.Intent;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpIntentService extends IntentService {

    public HttpIntentService() {
        super("HttpIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            HttpURLConnection urlConnection = null;
            try {
                String urlString = "http:
                        getResources().getString(R.string.local_server_port) + getResources().getString(R.string.url_extention);
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                Intent activityIntent = new Intent(this, ResponseActivity.class);
                activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activityIntent.putExtra("status_msg", "SUCCESS");
                activityIntent.putExtra("response_msg", readStream(in, 5000));
                this.startActivity(activityIntent);
                if (urlConnection != null) urlConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                if (urlConnection != null) urlConnection.disconnect();
                throw new RuntimeException(e);
            }
        }
    }

    private String readStream(InputStream stream, int maxReadSize)
            throws IOException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] rawBuffer = new char[maxReadSize];
        int readSize;
        StringBuffer buffer = new StringBuffer();
        while (((readSize = reader.read(rawBuffer)) != -1) && maxReadSize > 0) {
            if (readSize > maxReadSize) {
                readSize = maxReadSize;
            }
            buffer.append(rawBuffer, 0, readSize);
            maxReadSize -= readSize;
        }
        return buffer.toString();
    }
}
