package edu.ksu.cs.benign;

import android.app.IntentService;
import android.content.Intent;
import android.net.SSLCertificateSocketFactory;
import android.util.Log;

import java.io.IOException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class MyIntentService extends IntentService {
    private static final String TAG = "TrustManagerExploit";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {

                    }
                }

        };
        try {
            SSLCertificateSocketFactory x = (SSLCertificateSocketFactory) SSLCertificateSocketFactory.getDefault(0);
            /*
            url is signed by the CA that does not recognize the host name
             */
            x.setTrustManagers(trustAllCerts);
            /* Use of a string as the first parameter will throw a hostname verification error*/
            SSLSocket y = (SSLSocket) x.createSocket(getResources().getString(R.string.local_server_ipv4), Integer.parseInt(getResources().getString(R.string.local_server_port)));
            Intent activityIntent = new Intent(this, ResponseActivity.class);
            activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activityIntent.putExtra("status_msg", "SUCCESS");
            this.startActivity(activityIntent);
        } catch (IOException e) {
            Log.d(TAG, "Exception Occured.");
            e.printStackTrace();
            Intent activityIntent = new Intent(this, ResponseActivity.class);
            activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activityIntent.putExtra("status_msg", "FAILURE");
            this.startActivity(activityIntent);
        }
    }
}
