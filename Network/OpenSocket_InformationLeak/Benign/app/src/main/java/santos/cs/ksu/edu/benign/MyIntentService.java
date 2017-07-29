package santos.cs.ksu.edu.benign;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class MyIntentService extends IntentService {

    private static String TAG = "MyInetntService";
    private static int PORT = 8891;
    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            try{
                ServerSocket ss = new ServerSocket(PORT);
                Log.d(TAG,"Listening on port " + PORT + "...");
                Socket s = ss.accept();
                Log.d(TAG,"Remote connected on port " + PORT + "...");
                FileInputStream fis = openFileInput("myInfo.txt");
                OutputStream fos = s.getOutputStream();
                int data;
                while((data = fis.read()) != -1){
                    fos.write(data);
                }
                fis.close();
                fos.close();
                s.close();
            }
            catch(IOException e){
                Log.e(TAG,"Exception while opening port",e);
                e.printStackTrace();
            }
        }
    }
}
