package edu.ksu.cs.benign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.ksu.cs.benign.Util.Constant;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "Benign/MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final EditText pwd = (EditText) findViewById(R.id.passphrase);
        Button addStudent = (Button) findViewById(R.id.add_stu_btn);
        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pwd.getText().toString().equals(Constant.PASS))
                    startActivity(new Intent(getApplicationContext(), AddStudent.class));
                else
                    Toast.makeText(getApplicationContext(), "Wrong Passphrase!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
