package edu.ksu.cs.secure;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import javax.net.SocketFactory;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLServerSocketFactory;
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
    private static String HOST = "10.131.179.85";
    private static int PORT = 9004;
    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            try{
                SocketAddress saddr = new InetSocketAddress(HOST,PORT);
                SocketFactory sf = SSLSocketFactory.getDefault();
                SSLSocket s = (SSLSocket) sf.createSocket(HOST, PORT);
                s.connect(saddr);
                if(s.isConnected()){
                    Log.d(TAG,"Connected to " + HOST + "at port " + PORT);
                    InputStream is = s.getInputStream();
                    int data;
                    StringBuffer bf = new StringBuffer();
                    while((data = is.read()) != -1){
                        Log.d(TAG,"Result = " + (char)data);
                        bf.append((char)data);
                    }
                    Log.d(TAG,"Final Result = " + bf.toString());
                }
                else Log.d(TAG,"Connected refused by " + HOST + "at port " + PORT);
            }
            catch(IOException e){
                Log.e(TAG,"error while connecting to socket endpoint...",e);
                e.printStackTrace();
            }
        }
    }
}
