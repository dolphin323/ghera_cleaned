package edu.ksu.cs.benign;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import dalvik.system.PathClassLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final File externalDir = getExternalFilesDir(null);
                final File file = new File(externalDir + File.separator +
                        "greetings.jar");

                final TextView textView = (TextView) findViewById(R.id.textView);
                final String ret = verify(file);
                if (ret != null) {
                    textView.setText(ret);
                    return;
                }
                try {
                    final ClassLoader cl = new PathClassLoader(file.toString(),
                            this.getClass().getClassLoader());
                    final Class clazz = Class.forName("secureiti.Helper", true,
                            cl);
                    final String text = (String) clazz.getDeclaredField("GREETING")
                            .get(null);
                    textView.setText(text);
                } catch (ClassNotFoundException | IllegalAccessException |
                        NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private String verify(File file) {
        final JarFile jf;
        try {
            jf = new JarFile(file);
            final Enumeration<JarEntry> entries = jf.entries();
            while (entries.hasMoreElements()) {
                final JarEntry je = entries.nextElement();
                if (je.getName().equals("classes.dex")) {
                    final InputStreamReader ir = 
                        new InputStreamReader(jf.getInputStream(je));
                    while (ir.read() > 0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "io error";
        }

        final PublicKey pk;
        try {
            final CertificateFactory cf = CertificateFactory.getInstance("X.509");
            final Certificate pc = cf.generateCertificate(
                    getApplicationContext().getAssets().open("keyOne-publickey.cert"));
            pk =  pc.getPublicKey();
        } catch (CertificateException | IOException e) {
            e.printStackTrace();
            return "certificate error";
        }

        final Enumeration<JarEntry> entries = jf.entries();
        while (entries.hasMoreElements()) {
            final JarEntry je = entries.nextElement();
            if (je.getName().equals("classes.dex")) {
                Certificate[] certs = je.getCertificates();
                if (certs == null || certs.length == 0) {
                    return "Hello";
                }
                try {
                    je.getCertificates()[0].verify(pk);
                } catch (CertificateException | NoSuchAlgorithmException | InvalidKeyException |
                        NoSuchProviderException | SignatureException e) {
                    e.printStackTrace();
                    return "Hello!";
                }
            }
        }

        return null;
    }
}
