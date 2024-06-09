package edu.ksu.cs.benign;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;


public class MyIntentService extends IntentService {

    private static String TAG = "Benign";
    private static int PORT = 8888;

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final String HOST = getResources().getString(R.string.local_server_ipv4);
        final int PORT = Integer.parseInt(getResources().getString(R.string.local_server_port));
        if (intent != null) {
            StringBuffer bf = new StringBuffer();
            try (Socket s = new Socket()) {
                SocketAddress saddr = new InetSocketAddress(HOST, PORT);
                s.connect(saddr);
                if (s.isConnected()) {
                    Log.d(TAG, "Connected to " + HOST + "at port " + PORT);
                    InputStream is = s.getInputStream();
                    int data;
                    while ((data = is.read()) != -1) {
                        Log.d(TAG, "Result = " + (char) data);
                        bf.append((char) data);
                    }
                    Log.d(TAG, "Final Result = " + bf.toString());
                    result(bf.toString());
                } else {
                    Log.d(TAG, "Connection refused by " + HOST + "at port " + PORT);
                    bf.append("Connection refused");
                    result(bf.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("IOException occurred");
            }
        }

    }

    private void result(String r) {
        Intent i = new Intent();
        i.putExtra("Result", r);
        i.setComponent(new ComponentName("edu.ksu.cs.benign", "edu.ksu.cs.benign.Result"));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}
