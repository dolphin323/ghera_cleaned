package edu.ksu.cs.benign;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

public class MyTrustManager implements X509TrustManager {

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        // This implementation of checkServerTrusted is not secure as it results in trusting all certificates
        // Here we have demonstrated the validity check of certificates in terms of expiration date

        if (chain != null && chain.length != 0) {
            for (int i = 0; i < chain.length; i++) {
                chain[i].checkValidity();
            }
        } else {
            throw new RuntimeException("Empty Certificate Chain");
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        // Here we are returning an empty array of certificate authority certificates as we are not implementing
        // above checkServerTrusted method to check trusted servers.
        return new X509Certificate[]{};
    }
}
