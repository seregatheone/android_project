package com.example.project_x;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
    final String[] subjects = new String[]{"Математика", "Информатика", "Биология", "Химия"};
    public String object = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter mAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, subjects);
        setListAdapter(mAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(getApplicationContext(),
                "Вы выбрали " + l.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
        object = l.getItemAtPosition(position).toString();
        Intent intent = new Intent(MainActivity.this, Object.class);
        intent.putExtra("object", object);
        startActivity(intent);

    }

}