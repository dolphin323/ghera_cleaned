package santos.cs.ksu.edu.benign;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Random;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String option = intent.getStringExtra("option");
            switch (option){
                case "1":
                    Random secureRandom1 = new Random();
                    secureRandom1.setSeed(100);
                    System.out.println();
                    System.out.println("Secure Random 1 --- ");
                    for(int i=0;i<20;i++){
                        //System.out.print("ok");
                        //byte[] r = new byte[256/8];
                        long r = secureRandom1.nextLong();
                        Log.d("MyIntentService","" + r);
                        //System.out.print(r);
                        //System.out.print(',');
                    }
                    break;
                case "2":
                    Random secureRandom2 = new Random();
                    secureRandom2.setSeed(100);
                    System.out.println();
                    System.out.println("Secure Random 2 --- ");
                    for(int i=0;i<20;i++){
                        //System.out.print("ok");
                        //byte[] r = new byte[256/8];
                        long r = secureRandom2.nextLong();
                        Log.d("MyIntentService","" + r);
                        //System.out.print(r);
                        //System.out.print(',');
                    }
                    break;
            }
        }
    }
}
