package edu.ksu.cs.benign;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.File;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class DeleteFilesIntentService extends IntentService {


    public DeleteFilesIntentService() {
        super("DeleteFilesIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        File dir = getFilesDir();
        File file = new File(dir, "myfile");
        try {
            if (file != null && file.exists()) {
                Log.d("DeleteFilesService", file.getName() + " exists");
                if (file.delete()) Log.d("DeleteFilesService", file.getName() + " deleted");
                else Log.d("DeleteFilesService", file.getName() + " could not be deleted");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
