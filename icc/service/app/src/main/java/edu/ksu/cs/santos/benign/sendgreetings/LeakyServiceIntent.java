package edu.ksu.cs.santos.benign.sendgreetings;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.net.Uri;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class LeakyServiceIntent extends IntentService {

    public LeakyServiceIntent() {
        super("LeakyServiceIntent");
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            ArrayList<String> contacts = new ArrayList();
            /*
            download contacts and add to contacts arrayList....
             */
            if(contacts.isEmpty()){
                // create dummy date
                contacts.add("7857706217");
                contacts.add("7857706237");
                contacts.add("7857716234");
                contacts.add("4853706237");
                contacts.add("8857706237");
                contacts.add("7857206437");
                contacts.add("5827702233");
            }


            Intent i = new Intent("edu.ksu.cs.santos.benign.sendgreetings.contactsRecvr");
            i.putStringArrayListExtra("contacts",contacts);
            /*
                Broadcasting the list of contacts. This is insecure because a malicious receiver can intercept the broadcast
                and steal the data. Should use either one of the following
                    1. If the data being broadcasted is intended to be used locally then one should consider using a LocalBroadcast
                        eg. LocalBroadcast.getInstance(getApplicationContext()).sendBroadcast(i). This limits the broadcast to only local
                        receivers.

                    2. use an explicit intent by setting intent.setPackage(PackageName)
                    3. write the downloaded data to an internal file and broadcast a value indicating the status of the
                       write. If the write is successful, broadcast receiver can read from the file.
             */
            sendBroadcast(i);
        }
    }

}
