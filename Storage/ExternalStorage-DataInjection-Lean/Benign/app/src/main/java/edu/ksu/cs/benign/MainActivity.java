package edu.ksu.cs.benign;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void readFromFile(File file) {
        final TextView bible = (TextView) findViewById(R.id.bibleText);
        Scanner sc;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found");
        }
        StringBuffer bibleText = new StringBuffer();
        while (sc.hasNextLine())
            bibleText.append(sc.nextLine());
        bible.setText(bibleText);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(getExternalFilesDir(null), "bible.txt");
                if (file.exists()) {
                    readFromFile(file);
                } else {
                    String absPath = file.getAbsolutePath();
                    OutputStream os;
                    try {
                        os = new FileOutputStream(new File(absPath));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException("File not found");
                    }
                    String benignMessage = "This is written by Benign";
                    try {
                        os.write(benignMessage.getBytes());
                        os.close();
                    } catch (IOException e) {
                        throw new RuntimeException("Unable to write into file by Benign");
                    }
                    readFromFile(file);
                }
            }
        });
    }
}
