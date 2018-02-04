package edu.ksu.cs.secure;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class MyIntentService extends IntentService {
    private static String TAG = "Benign";
//    private static int PORT = 8888;

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final String HOST = getResources().getString(R.string.local_server_ipv4);
        final int PORT = Integer.parseInt(getResources().getString(R.string.local_server_port));
        StringBuffer bf = new StringBuffer();
        if (intent != null) {
            Log.d(TAG, "In service");
            SocketAddress saddr = new InetSocketAddress(HOST, PORT);
            SocketFactory sf = SSLSocketFactory.getDefault();
            try (SSLSocket s = (SSLSocket) sf.createSocket(HOST, PORT)) {
                s.connect(saddr);
                if (s.isConnected()) {
                    Log.d(TAG, "Connected to " + HOST + "at port " + PORT);
                    InputStream is = s.getInputStream();
                    int data;
                    while ((data = is.read()) != -1) {
                        Log.d(TAG, "Result = " + (char) data);
                        bf.append((char) data);
                    }
                    result(bf.toString());
                }
            } catch (IOException e) {
                bf.append(e.toString());
                result(bf.toString());
            }
        }

    }

    private void result(String r) {
        Intent i = new Intent();
        i.putExtra("Result", r);
        i.setComponent(new ComponentName("edu.ksu.cs.benign", "edu.ksu.cs.secure.Result"));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}
