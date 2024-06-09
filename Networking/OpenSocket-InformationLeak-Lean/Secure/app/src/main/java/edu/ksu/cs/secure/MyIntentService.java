package edu.ksu.cs.secure;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class MyIntentService extends IntentService {

    private static String TAG = "MyInetntService";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final int PORT = Integer.parseInt(getResources().getString(R.string.local_server_port));
        if (intent != null) {
            try (ServerSocket ss = new ServerSocket(PORT);
                 Socket s = ss.accept()) {
                Log.d(TAG, "Listening on port " + PORT + "...");
                Log.d(TAG, "Remote connected on port " + PORT + "...");
                if (s.getInetAddress().getHostName().equals("trusted.remote.com")) {
                    FileInputStream fis = openFileInput("myInfo.txt");
                    OutputStream fos = s.getOutputStream();
                    int data;
                    while ((data = fis.read()) != -1) {
                        fos.write(data);
                    }
                    fis.close();
                    fos.close();
                } else {
                    OutputStream fos = s.getOutputStream();
                    fos.write("Rejected".getBytes());
                    fos.close();
                    Log.d(TAG, "Connection rejected. Remote not trusted");
                }
            } catch (IOException e) {
                Log.e(TAG, "Exception while opening port", e);
                e.printStackTrace();
                throw new RuntimeException("Exception while opening port");
            }
        }
    }
}
