package edu.ksu.cs.benign;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FileEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_edit);
    }

    @Override
    protected void onResume(){
        super.onResume();
        TextView tv = (TextView) findViewById(R.id.file);
        EditText et = (EditText) findViewById(R.id.fileData);
        Button save = (Button) findViewById(R.id.save_chng);
        final Intent i = getIntent();
        tv.setText(i.getStringExtra("filename"));
        final String data_in_file = queryContentProvider(i.getStringExtra("filename"));
        et.setText(data_in_file);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnectedToInternet()){
                    //TODO:save changes to Database
                }
                else{
                    //save changes to device.
                    EditText et = (EditText) findViewById(R.id.fileData);
                    Uri.Builder ub = new Uri.Builder();
                    ub.authority("edu.ksu.cs.benign.filecontentprovider");
                    ub.scheme("content");
                    Bundle b = new Bundle();
                    b.putCharSequence("notes",et.getText());
                    getContentResolver().call(ub.build(),"backup",i.getStringExtra("filename"),b);
                }
            }
        });

    }

    private String queryContentProvider(String filename){
        /*
        query Database.
         */
        return "data in " + filename;
    }

    private Boolean isConnectedToInternet(){
        return false;
    }
}
