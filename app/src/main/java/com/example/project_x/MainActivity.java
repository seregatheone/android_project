package com.example.project_x;

import android.app.ListActivity;
import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;


public class MainActivity extends ListActivity {
    final String[] subjects = new String[] { "Математика", "Информатика"};
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
    private static long back_pressed;
    public void onBackPressed() {
        if (back_pressed + 3000 > System.currentTimeMillis()) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            super.onBackPressed();
        }
        else
            Toast.makeText(getBaseContext(), "Нажмите ещё раз, чтобы выйти!",
                    Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
            case KeyEvent.KEYCODE_BACK:
                onBackPressed();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}