package edu.ksu.cs.benign;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

        Button copy = (Button) findViewById(R.id.submit);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView res = (TextView) findViewById(R.id.result);
                res.setText(R.string.ben);
            }
        });

        Button copyPass = (Button) findViewById(R.id.copyPass);
        copyPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager)
                        getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Password", copyData());
                clipboard.setPrimaryClip(clip);
            }
        });
    }

    private String copyData() {
        EditText et = (EditText) findViewById(R.id.password);
        return et.getText().toString();
    }
}
