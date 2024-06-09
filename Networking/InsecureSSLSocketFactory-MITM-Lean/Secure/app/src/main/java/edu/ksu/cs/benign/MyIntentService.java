package edu.ksu.cs.benign;

import android.app.IntentService;
import android.content.Intent;
import android.net.SSLCertificateSocketFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSocketFactory;

public class MyIntentService extends IntentService {

    private static final String TAG = "TrustManagerExploit";

    public MyIntentService() {
        super("MyIntentService");
    }

    private String copyInputStreamToString(InputStream in) throws IOException {
        try {
            byte[] bytes = new byte[1000];
            StringBuilder sb = new StringBuilder();
            int numRead = 0;
            while ((numRead = in.read(bytes)) >= 0) {
                sb.append(new String(bytes, 0, numRead));
            }
            return sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        HttpsURLConnection urlConnection = null;
        try {
            SSLSocketFactory secureFactory = SSLCertificateSocketFactory.getDefault(0xFFFFFFFF, null);
            String urlString = "https:
                    getResources().getString(R.string.local_server_port) + getResources().getString(R.string.url_extension);
            URL url = new URL(urlString);
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setSSLSocketFactory(secureFactory);
            InputStream in = urlConnection.getInputStream();

            Intent activityIntent = new Intent(this, ResponseActivity.class);
            activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activityIntent.putExtra("status_msg", "SUCCESS");
            activityIntent.putExtra("response_msg", copyInputStreamToString(in));
            this.startActivity(activityIntent);
            if (urlConnection != null) urlConnection.disconnect();
        } catch (SSLHandshakeException e) {
            Intent activityIntent = new Intent(this, ResponseActivity.class);
            activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activityIntent.putExtra("status_msg", "FAILURE");
            this.startActivity(activityIntent);
            if (urlConnection != null) urlConnection.disconnect();
        } catch (IOException e) {
            Log.d(TAG, "Exception Occured.");
            e.printStackTrace();
            if (urlConnection != null) urlConnection.disconnect();
            throw new RuntimeException(e);
        }
    }
}