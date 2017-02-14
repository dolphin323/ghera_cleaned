package edu.ksu.cs.santos.benign.sendgreeting;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

public class PhoneLocationService extends Service {

    public static final int CHECK_PHONE_LOC_REQ = 1;
    public static final int CHECK_PHONE_LOC_RESP = 0;
    public PhoneLocationService() {
    }


    class PhoneLocationHandler extends Handler{
        public String phone = null;
        @Override
        public void handleMessage(Message m){
            int msgReq = m.what;
            phone = m.getData().getString("phone");
            switch (msgReq){
                case CHECK_PHONE_LOC_REQ:
                    Toast.makeText(getApplicationContext(),"Received client msg",Toast.LENGTH_SHORT).show();
                    if(isPhoneUSA(phone)){
                        Bundle data = new Bundle();
                        data.putString("newPhn","you are connected to a malicious service!");
                        Message message = Message.obtain(null,CHECK_PHONE_LOC_RESP);
                        message.setData(data);
                        try{

                            m.replyTo.send(message);
                        }
                        catch(RemoteException re){
                            Log.d("PhoneLocationHandler","Remote Exception while sending message");
                            re.printStackTrace();
                        }
                        catch(NullPointerException ne){
                            Log.d("PhoneLocationService","message is null");
                            ne.printStackTrace();
                        }
                    }
                default:
                    super.handleMessage(m);
            }
        }


    }

    public boolean isPhoneUSA(String phone){
        if(phone != null){
                /*
                elaborate business logic
                 */
            return true;
        }

        else return false;
    }

    private Messenger messenger = new Messenger(new PhoneLocationHandler());

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
