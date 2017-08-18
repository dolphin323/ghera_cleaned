package santos.cs.ksu.edu.benign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume(){
        super.onResume();
        String fileNm = "demo.txt";
        String fileContent = "demo file";
        Intent i = getIntent();
        if(i != null && i.getStringExtra("fileName") != null && !i.getStringExtra("fileName").equals("")
                && i.getStringExtra("fileContent") != null && !i.getStringExtra("fileContent").equals("")){
            fileNm = i.getStringExtra("fileName");
            fileContent = i.getStringExtra("fileContent");
        }
        try {
            Log.d("BenignMainActivity",fileNm);
            File dir = new File(getFilesDir(),"custom");
            if(!dir.exists()){
                dir.mkdir();
            }
            Log.d("BenignMainActivity",dir.getAbsolutePath());
            File file = new File(dir,fileNm);
            Log.d("BenignMainActivity",file.getAbsolutePath());
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.append(fileContent);
            fileWriter.flush();
            fileWriter.close();
            Log.d("BenignMainActivity","done!");
        }
        catch(FileNotFoundException e){
            Toast.makeText(getApplicationContext(),"file not found",Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED);
            e.printStackTrace();
        }
        catch(IOException e){
            Toast.makeText(getApplicationContext(),"IOError while writing",Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED);
            e.printStackTrace();
        }
        catch(Exception e){
            Toast.makeText(getApplicationContext(),"file not found",Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED);
            e.printStackTrace();
        }
        setResult(RESULT_OK);
    }
}
