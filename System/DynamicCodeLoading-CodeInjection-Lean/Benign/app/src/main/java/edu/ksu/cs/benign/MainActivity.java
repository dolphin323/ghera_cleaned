package edu.ksu.cs.benign;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FilenameFilter;

import dalvik.system.PathClassLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final File externalDir = getExternalFilesDir(null);
                final File file = new File(externalDir + File.separator +
                        "greetings.jar");

                try {
                    final ClassLoader cl = new PathClassLoader(file.toString(),
                            this.getClass().getClassLoader());
                    final Class clazz = Class.forName("secureiti.Helper", true, cl);
                    final String text = (String) clazz.getDeclaredField("GREETING").get(null);
                    final TextView textView = (TextView) findViewById(R.id.textView);
                    textView.setText(text);
                } catch (ClassNotFoundException | IllegalAccessException |
                        NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
