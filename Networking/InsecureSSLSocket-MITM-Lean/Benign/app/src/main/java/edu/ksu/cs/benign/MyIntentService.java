package edu.ksu.cs.benign;

import android.app.IntentService;
import android.content.Intent;
import android.net.SSLCertificateSocketFactory;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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
            Log.d(TAG, "Service started");
            SSLCertificateSocketFactory x = (SSLCertificateSocketFactory) SSLCertificateSocketFactory.getDefault(0);
            String ipAddrv4 = getResources().getString(R.string.local_server_ipv4);
            String[] ipAddrv4Arr = ipAddrv4.split("\\.");
            byte[] addr = {Byte.parseByte(ipAddrv4Arr[0]), Byte.parseByte(ipAddrv4Arr[1]), Byte.parseByte(ipAddrv4Arr[2]), Byte.parseByte(ipAddrv4Arr[3])};
            InetAddress add = InetAddress.getByAddress(addr);
            x.setTrustManagers(trustAllCerts);
            SSLSocket y = (SSLSocket) x.createSocket(add, Integer.parseInt(getResources().getString(R.string.local_server_port)));
            if (y.isConnected()) {
                Intent activityIntent = new Intent(this, ResponseActivity.class);
                activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activityIntent.putExtra("status_msg", "SUCCESS");
                this.startActivity(activityIntent);
            } else {
                Intent activityIntent = new Intent(this, ResponseActivity.class);
                activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activityIntent.putExtra("status_msg", "FAILURE");
                this.startActivity(activityIntent);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            Log.d(TAG, "Unknown Host error.");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Exception Occured.");
        }
    }
}
