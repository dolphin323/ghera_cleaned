package edu.ksu.cs.benign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView textView = (TextView) findViewById(R.id.concat);
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            StringBuilder stringBuilder = new StringBuilder();
            String a = intent.getStringExtra("s1");
            String b = intent.getStringExtra("s2");
            int i;
            try {
                for (i = 0; i < Math.min(a.length(), b.length()); ++i) {
                    stringBuilder.append(a.charAt(i));
                    stringBuilder.append(b.charAt(i));
                }
                if (a.length() < i)
                    while (i < b.length())
                        stringBuilder.append(b.charAt(i++));
                else
                    while (i < a.length())
                        stringBuilder.append(a.charAt(i++));
                textView.setText("Concatenated string: " + stringBuilder.toString());
            } catch (NullPointerException e) {
                textView.setText("NullPointerException");
            }
        } else {
            textView.setText("Nothing to concatenate");
        }
    }
}
