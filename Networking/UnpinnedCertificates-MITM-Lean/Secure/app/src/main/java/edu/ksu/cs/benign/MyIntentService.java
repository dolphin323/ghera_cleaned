package edu.ksu.cs.benign;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

public class MyIntentService extends IntentService {

    private static final String TAG = "TrustManagerExploit";

    public MyIntentService() {
        super("MyIntentService");
    }

    private String copyInputStreamToString(InputStream in) throws IOException {
        try {
            byte[] bytes = new byte[1024];
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
        Log.d("Secure", "Handling Intent");
        Certificate ca;
        try (InputStream caInput = getApplication().getAssets().open("keyTwo-publickey.crt")) {
            final CertificateFactory cf = CertificateFactory.getInstance("X.509");
            ca = cf.generateCertificate(caInput);

            final String keyStoreType = KeyStore.getDefaultType();
            final KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            
            final String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            final TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, tmf.getTrustManagers(), null);

            
            final String urlString = "https:
                    getResources().getString(R.string.local_server_port) + getResources().getString(R.string.url_extension);
            final URL url = new URL(urlString);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setSSLSocketFactory(context.getSocketFactory());
            urlConnection.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    Log.d("Secure-Hostname", hostname);
                    return hostname.equals("10.0.2.2");
                }
            });

            InputStream in = urlConnection.getInputStream();
            Log.d("Secure", "SUCCESS");
            result(copyInputStreamToString(in));

        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | KeyManagementException | IOException e) {
            Log.d("Secure", "FAILURE");
            result(e.toString());
        }
    }

    private void result(String res) {
        final Intent activityIntent = new Intent(this, ResponseActivity.class);
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activityIntent.putExtra("status_msg", res);
        startActivity(activityIntent);
    }
}