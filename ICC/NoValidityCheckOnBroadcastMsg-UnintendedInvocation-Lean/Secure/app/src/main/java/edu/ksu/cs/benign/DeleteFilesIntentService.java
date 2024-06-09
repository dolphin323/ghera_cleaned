package edu.ksu.cs.benign;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.File;


public class DeleteFilesIntentService extends IntentService {


    public DeleteFilesIntentService() {
        super("DeleteFilesIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        boolean delete = intent.getBooleanExtra("Delete", false);
        if (delete) {
            File dir = getFilesDir();
            File file = new File(dir, "myfile");
            if (file.exists()) {
                Log.d("DeleteFilesService", file.getName() + " exists");
                if (file.delete()) {
                    startActivity(new Intent(getApplicationContext(), DeleteStatusActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .putExtra("status", "success"));
                    Log.d("DeleteFilesService", file.getName() + " deleted");
                } else {
                    Log.d("DeleteFilesService", file.getName() + " could not be deleted");
                }
            }
        } else
            startActivity(new Intent(getApplicationContext(), DeleteStatusActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .putExtra("status", "fail"));
    }

}
