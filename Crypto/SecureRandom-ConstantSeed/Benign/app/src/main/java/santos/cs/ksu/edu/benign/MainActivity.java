package santos.cs.ksu.edu.benign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume(){
        super.onResume();
        final EditText option = (EditText) findViewById(R.id.editText);
        final Button getRandom = (Button) findViewById(R.id.button);

        getRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MyIntentService.class);
                intent.putExtra("option",option.getText().toString().trim());
                startService(intent);
            }
        });
    }
}
