package edu.ksu.cs.logutil;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class BackgroundService extends IntentService {

    public BackgroundService() {
        super("BackgroundService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "BackgroundService");
            LogDebug.d("Service started");
            wl.acquire();
            LogDebug.d("Long running background service");
            startActivity(new Intent(getApplicationContext(), DisplayActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            wl.release();
        }
    }
}
