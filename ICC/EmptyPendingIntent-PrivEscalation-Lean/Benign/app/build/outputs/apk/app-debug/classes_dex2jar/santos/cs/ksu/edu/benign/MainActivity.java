package santos.cs.ksu.edu.benign;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity
  extends AppCompatActivity
{
  public MainActivity() {}
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130968603);
  }
  
  protected void onResume()
  {
    super.onResume();
    ((Button)findViewById(2131427422)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Bundle localBundle = new Bundle();
        localBundle.putParcelable("pendingIntent", PendingIntent.getService(MainActivity.this.getApplicationContext(), 0, new Intent(), 134217728));
        Intent localIntent = new Intent("santos.cs.ksu.edu.benignpireceiver");
        localIntent.putExtra("bundle", localBundle);
        MainActivity.this.sendBroadcast(localIntent);
      }
    });
  }
}
