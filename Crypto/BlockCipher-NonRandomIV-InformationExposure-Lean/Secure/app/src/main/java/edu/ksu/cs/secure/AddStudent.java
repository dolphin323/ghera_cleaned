package edu.ksu.cs.secure;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddStudent extends AppCompatActivity {

    private static String TAG = "Benign/AddStudent";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
    }

    @Override
    protected void onResume(){
        super.onResume();
        final EditText name = (EditText) findViewById(R.id.stu_name);
        final EditText grade = (EditText) findViewById(R.id.stu_grade);
        Button save = (Button) findViewById(R.id.save_btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,name.getText().toString());
                Log.d(TAG,grade.getText().toString());
                Uri.Builder uriBuilder = new Uri.Builder();
                uriBuilder.authority("edu.ksu.cs.benign.grades");
                uriBuilder.scheme("content");
                ContentValues contentValues = new ContentValues();
                contentValues.put(name.getText().toString(),grade.getText().toString());
                Uri uri = getContentResolver().insert(uriBuilder.build(),contentValues);
                if(uri != null){
                    Toast.makeText(getApplicationContext(),"Student grades saved",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplication(),MainActivity.class));
                }
                else Toast.makeText(getApplicationContext(),"Failed to save. Try again",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
