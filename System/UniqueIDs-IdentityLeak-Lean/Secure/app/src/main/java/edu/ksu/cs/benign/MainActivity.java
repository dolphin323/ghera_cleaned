package edu.ksu.cs.benign;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void onResume() {
        super.onResume();
        saveUuid(getId());
        TextView tv = (TextView) findViewById(R.id.disp_id);
        tv.setText(showUuid());
    }

    private String getId() {
        return UUID.randomUUID().toString();
    }

    private String showUuid() {
        String uuid;
        File file = new File(getExternalFilesDir(null), "UNIQUE_ID.txt");
        try (Scanner sc = new Scanner(new File(file.getAbsolutePath()))) {
            StringBuilder uniqueIDBuf = new StringBuilder();
            while (sc.hasNextLine())
                uniqueIDBuf.append(sc.nextLine());
            uuid = uniqueIDBuf.toString();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found");
        }
        return uuid;
    }

    private void saveUuid(String uuid) {
        try {
            File file = new File(getExternalFilesDir(null), "UNIQUE_ID.txt");
            OutputStream os = new FileOutputStream(file);
            os.write(("DeviceId :" + uuid).getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while saving unique id");
        }
    }
}
