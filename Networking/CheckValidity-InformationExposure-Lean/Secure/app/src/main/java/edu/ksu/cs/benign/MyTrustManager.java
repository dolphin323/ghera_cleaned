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
        
        
        return new X509Certificate[]{};
    }
}
