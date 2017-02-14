package edu.ksu.cs.santos.benign.sendgreetings;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.R.layout.simple_list_item_1;

public class SelectContact extends AppCompatActivity {

    private List<String> contacts;
    private BroadcastReceiver contactsReceiver;
    private String greeting;
    private Messenger messenger = null;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);
            Toast.makeText(getApplicationContext(),"Component name = " + name,Toast.LENGTH_LONG).show();
            try{
                Class<?> cls = Class.forName(name.getClassName());
                Object obj = cls.newInstance();
                Method m = cls.getMethod("isPhoneUSA",new Class[] {String.class});
                Boolean r = (Boolean) m.invoke(obj,new Object[] {new String("7855629011")});
                Toast.makeText(getApplicationContext(),"isPhoneUsa = " + r,Toast.LENGTH_LONG).show();
            }
            catch(SecurityException se){
                se.printStackTrace();
            }
            catch(ClassNotFoundException ce){
                ce.printStackTrace();
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

    class PhoneLocationResponseHandler extends Handler{
        @Override
        public void handleMessage(Message message){
            int resp = message.what;
            switch(resp){
                case PhoneLocationService.CHECK_PHONE_LOC_RESP:
                    String sendTo = message.getData().getString("newPhn");
                    try{
                        String desc = messenger.getBinder().getInterfaceDescriptor();
                        messenger.getBinder().queryLocalInterface(desc);
                        Toast.makeText(getApplicationContext(),"binder interface desc " + desc,Toast.LENGTH_LONG).show();
                    }
                    catch(RemoteException re){
                        re.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(),"supposed to send " + greeting + "to " + sendTo,Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        /*
        insecure usage of explicit intent. Always use getPackageName() to get the name of the package.
        eg. final String packageName = getPackageName() or create an explicit intent with the application
        context eg. Intent i = new Intent(getApplicationContext, PhoneLocationService.class)
         */
        final String packageName = "edu.ksu.cs.santos.benign.sendgreeting";
        Intent i = new Intent();
        i.setPackage(packageName);
        i.setClassName(packageName,packageName+".PhoneLocationService");
        bindService(i,serviceConnection,Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume(){
        super.onResume();
        greeting = getIntent().getStringExtra("greeting");
        final ListView contactsView = (ListView) findViewById(R.id.list);
        Intent intent = new Intent(getApplicationContext(),LeakyServiceIntent.class);
        //download contacts from a service
        startService(intent);
        /*
        listen for the service's result
         */
        contactsReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                contacts = intent.getStringArrayListExtra("contacts");
                ListAdapter contactsAdap = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,contacts);
                contactsView.setAdapter(contactsAdap);

            }
        };
        /*
        register the contactsReceiver
         */
        registerReceiver(contactsReceiver,new IntentFilter("edu.ksu.cs.santos.benign.sendgreetings.contactsRecvr"));

        /*
        send sms to selected contact
         */
        contactsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String phone = (String)parent.getItemAtPosition(position);
                Message msg = Message.obtain(null,PhoneLocationService.CHECK_PHONE_LOC_REQ);
                Bundle bundle = new Bundle();
                bundle.putString("phone",phone);
                msg.setData(bundle);
                msg.replyTo = new Messenger(new PhoneLocationResponseHandler());
                try{
                    messenger.send(msg);
                }
                catch(RemoteException re){
                    Log.d("SelectContact","when checking if number is a US number");
                    re.printStackTrace();
                }

                /*Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phone));
                i.putExtra("sms_body", greeting);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);*/
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        unregisterReceiver(contactsReceiver);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unbindService(serviceConnection);
    }

}
