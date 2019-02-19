package edu.ksu.cs.benign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final Button benign = findViewById(R.id.benign);
        final Button mal = findViewById(R.id.malicious);

        benign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                benignWeb();
            }
        });

        mal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                malWeb();
            }
        });
    }

    private void benignWeb() {
        final Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
        intent.putExtra("url", getString(R.string.benign_url));
        startActivity(intent);
    }

    private void malWeb() {
        final Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
        intent.putExtra("url", getString(R.string.mal_url));
        startActivity(intent);
    }
}
