package santos.cs.ksu.edu.benign;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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


    public static void copyInputStreamToOut (InputStream in, OutputStream out) {
        try {
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.flush();
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try{
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManager trustManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            TrustManager[] trustmanagers = new TrustManager[]{trustManager};
            sslContext.init(null,trustmanagers,null);
            /*
            url is signed by the CA that does not recognize the host name
             */
            URL url = new URL(getResources().getString(R.string.url));
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setSSLSocketFactory(sslContext.getSocketFactory());
            urlConnection.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            InputStream in = urlConnection.getInputStream();
            copyInputStreamToOut(in, System.out);
        }
        catch(MalformedURLException e){
            Log.d(TAG,"Something wrong with the url...");
            e.printStackTrace();
        }
        catch(IOException e){
            Log.d(TAG,"IO Error...");
            e.printStackTrace();
        }
        catch(NoSuchAlgorithmException e){
            Log.d(TAG,"Incorrect SSL protocol ... ");
            e.printStackTrace();
        }
        catch(KeyManagementException e){
            Log.d(TAG,"Key Mgmt Exception ... ");
            e.printStackTrace();
        }


    }

}
