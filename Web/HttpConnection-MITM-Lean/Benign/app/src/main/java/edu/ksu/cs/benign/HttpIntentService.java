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


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class HttpIntentService extends IntentService {

    public HttpIntentService() {
        super("HttpIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL("http://10.131.182.100:5000/intent/");
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                System.out.println(readStream(in,5000));
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            finally {
                if(urlConnection!=null) urlConnection.disconnect();
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
