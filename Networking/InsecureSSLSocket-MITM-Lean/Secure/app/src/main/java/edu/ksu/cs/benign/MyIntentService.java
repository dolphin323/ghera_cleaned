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
            x.setTrustManagers(trustAllCerts);
