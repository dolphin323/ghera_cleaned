package santos.cs.ksu.edu.benign;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MySensitiveService
  extends Service
{
  public MySensitiveService() {}
  
  public IBinder onBind(Intent paramIntent)
  {
    throw new UnsupportedOperationException("Not yet implemented");
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    Log.d("PendingIntent...", "Do something important");
    return super.onStartCommand(paramIntent, paramInt1, paramInt2);
  }
}
