package com.example.project_x;


import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DataInfo extends AppCompatActivity {
    String text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_preview);
        String date = getIntent().getExtras().getString("date");
        ArrayList<String> actions = getIntent().getExtras().getStringArrayList("actions");
        TextView time = (TextView) findViewById(R.id.time);
        time.setText(date);
        TextView note = (TextView) findViewById(R.id.note);
        Integer i = 0;
        for (String action : actions) {
            i+=1;
            text+=Integer.toString(i)+". "+action+"\n";
        }
        note.setText(text);
    }

}