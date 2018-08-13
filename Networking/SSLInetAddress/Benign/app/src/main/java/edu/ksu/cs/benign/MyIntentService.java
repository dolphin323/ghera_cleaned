package edu.ksu.cs.benign;

import android.app.IntentService;
import android.content.Intent;
import android.net.SSLCertificateSocketFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
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
        InputStream in = null;

        TrustManager[] trustAllCerts = new TrustManager[] {
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

            SSLCertificateSocketFactory x = (SSLCertificateSocketFactory) SSLCertificateSocketFactory.getDefault(0) ;
            /*
            url is signed by the CA that does not recognize the host name
            
             */
            byte [] addr = {10, 0, 2, 2};
            InetAddress add = InetAddress.getByAddress(addr);
            x.setTrustManagers(trustAllCerts);
            SSLSocket y = (SSLSocket) x.createSocket(add, Integer.parseInt(getResources().getString(R.string.local_server_port)));

            in = y.getInputStream();

            Intent activityIntent = new Intent(this, ResponseActivity.class);
            activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activityIntent.putExtra("status_msg", "SUCCESS");
            activityIntent.putExtra("response_msg", copyInputStreamToString(in));
            this.startActivity(activityIntent);

            if (in != null) in.close();
        //} //catch (SSLHandshakeException e) {
            //Intent activityIntent = new Intent(this, ResponseActivity.class);
            //activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //activityIntent.putExtra("status_msg", "FAILURE");
            //this.startActivity(activityIntent);
           // if (in != null) ;
        } catch (IOException e) {
            Log.d(TAG, "Exception Occured.");
            e.printStackTrace();
            if (in != null ) ;
            throw new RuntimeException(e);
        }
    }
}