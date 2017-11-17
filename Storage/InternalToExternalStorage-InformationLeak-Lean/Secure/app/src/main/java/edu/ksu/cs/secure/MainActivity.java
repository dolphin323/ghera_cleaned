package edu.ksu.cs.secure;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "Secure";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume(){
        super.onResume();
        final EditText info = (EditText) findViewById(R.id.editText);
        Button button = (Button) findViewById(R.id.button);
        Button download = (Button) findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sens_info = info.getText().toString();
                String FILENAME = "sensitive_file";
                try{
                    FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                    fos.write(sens_info.getBytes());
                    fos.close();
                }
                catch(IOException e){
                    Log.d(TAG, "error while saving ...");
                    e.printStackTrace();
                }
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Writing sensitive files to SD card is not safe",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
