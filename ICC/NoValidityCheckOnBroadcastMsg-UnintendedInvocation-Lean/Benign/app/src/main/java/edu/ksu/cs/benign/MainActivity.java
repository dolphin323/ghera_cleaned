package edu.ksu.cs.benign;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;

/*
This app contains an unprotected exported Receiver i.e. LowMemoryReceiver
that is designed to listen to broadcasts from the system
when the device memory is low. On receiving such a signal
from the device, it invokes a service called DeleteFilesIntentService
to delete files in its internal memory.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String filename = "myfile";
        String string = "Hello world!";

        try (FileOutputStream outputStream = openFileOutput(filename, Context.MODE_PRIVATE)) {
            outputStream.write(string.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
