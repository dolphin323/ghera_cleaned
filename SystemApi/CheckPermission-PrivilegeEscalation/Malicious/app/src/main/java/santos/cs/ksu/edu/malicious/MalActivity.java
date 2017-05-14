package santos.cs.ksu.edu.malicious;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mal);
    }

    @Override
    protected void onResume(){
        super.onResume();

        Intent i = new Intent();
        i.setPackage("santos.cs.ksu.edu.benign");
        i.setClassName("santos.cs.ksu.edu.benign","santos.cs.ksu.edu.benign.MyService");
        startService(i);

    }

}
