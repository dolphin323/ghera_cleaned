package edu.ksu.cs.benign;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
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
        final Certificate trustedCA;
        InputStream fakeCAInput = null;
        try {
            fakeCAInput = context.getAssets().open("keyOne-publickey.crt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        final CertificateFactory cf = CertificateFactory.getInstance("X.509");
        trustedCA = cf.generateCertificate(fakeCAInput);
        for (X509Certificate certificate : chain) {
            certificate.checkValidity();
            if (!trustedCA.equals(certificate)) {
                throw new CertificateException();
            }
        }
    }


    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        Log.d("Benign", "Insied TM");
        final Certificate trustedCA;
        InputStream fakeCAInput = null;
        try {
            fakeCAInput = context.getAssets().open("keyOne-publickey.crt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        final CertificateFactory cf = CertificateFactory.getInstance("X.509");
        trustedCA = cf.generateCertificate(fakeCAInput);
        for (X509Certificate certificate : chain) {
            certificate.checkValidity();
            if (!trustedCA.equals(certificate)) {
                throw new CertificateException();
            }
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[]{};
    }
}
