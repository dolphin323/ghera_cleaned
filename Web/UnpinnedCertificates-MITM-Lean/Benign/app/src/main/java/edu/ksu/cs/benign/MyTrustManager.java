package edu.ksu.cs.benign;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

public class MyTrustManager implements X509TrustManager {
    final private Context context;

    MyTrustManager(Context context) {
        this.context = context;
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        Log.d("Benign", "Insied TM");
        for (X509Certificate certificate : chain) {
            final Certificate trustedCA;
            InputStream fakeCAInput = null;
            final CertificateFactory cf = CertificateFactory.getInstance("X.509");
            try {
                fakeCAInput = context.getAssets().open("keyOne-publickey.crt");
                trustedCA = cf.generateCertificate(fakeCAInput);
                certificate.checkValidity();
                final PublicKey publicKey = certificate.getPublicKey();
                if (!trustedCA.getPublicKey().equals(publicKey)) {
                    throw new CertificateException();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        Log.d("Benign", "Insied TM");
        for (X509Certificate certificate : chain) {
            final Certificate trustedCA;
            InputStream fakeCAInput = null;
            final CertificateFactory cf = CertificateFactory.getInstance("X.509");
            try {
                fakeCAInput = context.getAssets().open("keyOne-publickey.crt");
                trustedCA = cf.generateCertificate(fakeCAInput);
                certificate.checkValidity();
                final PublicKey publicKey = certificate.getPublicKey();
                if (!trustedCA.getPublicKey().equals(publicKey)) {
                    throw new CertificateException();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}
