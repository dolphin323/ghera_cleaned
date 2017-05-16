package santos.cs.ksu.edu.benign;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.IOException;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class InstallApkIntentService extends IntentService {

    public InstallApkIntentService() {
        super("InstallApkIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try{
            File file = new File(getExternalFilesDir(null),"bible.apk");
            Log.d("Benign","installing from " + file.getAbsolutePath());
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // without this flag android returned a intent error!
            startActivity(i);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }


}
