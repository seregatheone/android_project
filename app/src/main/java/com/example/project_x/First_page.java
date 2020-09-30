package com.example.project_x;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class First_page extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page_layout);
        Button olimp = (Button) findViewById(R.id.olimps);
        View.OnClickListener olimpia = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(First_page.this, MainActivity.class);
                startActivity(intent1);

            }
        };
        olimp.setOnClickListener(olimpia);
        Button calend = (Button) findViewById(R.id.calend);
        View.OnClickListener calenda = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(First_page.this, Calend.class);
                startActivity(intent2);
            }
        };
        calend.setOnClickListener(calenda);
    }
}