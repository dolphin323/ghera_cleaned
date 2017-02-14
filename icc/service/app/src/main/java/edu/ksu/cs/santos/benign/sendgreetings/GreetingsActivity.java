package edu.ksu.cs.santos.benign.sendgreetings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_list_item_1;


public class GreetingsActivity extends AppCompatActivity {

    private String selectedGreeting = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greetings);

        //Intent.ACTION_VIEW
        //vnd.android-dir/mms-sms
        /*TextView greeting = (TextView) findViewById(R.id.greet);
        final EditText number = (EditText) findViewById(R.id.textView3);
        final String greetMsg = (String) greeting.getText();
        Button sendGreet = (Button) findViewById(R.id.button);
        sendGreet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent("edu.ksu.cs.santos.benign.sendgreetings.display");
                String phone = number.getText().toString();
                i.putExtra("Phone", phone);
                i.putExtra("greet", greetMsg);
                startActivity(i);
            }
        });*/
    }

    @Override
    protected void onResume(){
        super.onResume();
        final ListView greetings  = (ListView) findViewById(R.id.greetings);
        List<String> gmsgs = new ArrayList<>();
        gmsgs.add("Merry Christmas!");
        gmsgs.add("Happy New Year!");
        gmsgs.add("Best of Luck!");
        ListAdapter greetingsAdap = new ArrayAdapter<>(this,simple_list_item_1,gmsgs);
        greetings.setAdapter(greetingsAdap);
        greetings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedGreeting = parent.getItemAtPosition(position).toString();
                Intent intent = new Intent(getApplicationContext(),SelectContact.class);
                intent.putExtra("greeting",selectedGreeting);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
    }
}
