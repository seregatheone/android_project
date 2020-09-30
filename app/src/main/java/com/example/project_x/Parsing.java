package com.example.project_x;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Parsing extends AppCompatActivity {
    private ArrayList<String> olimpiads = new ArrayList<>();
    public String request;
    String content = "";
    TextView textView;
    Button button;
    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.parsing);
        textView = findViewById(R.id.timetable);
        button = findViewById(R.id.button);
        button.setEnabled(false);
        getting getting = new getting();
        getting.execute();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                olimpiads.add(name);
                button.setEnabled(false);
                Intent intent = new Intent(Parsing.this, Calend.class);
                intent.putStringArrayListExtra("names", olimpiads);
                intent.putExtra("request", request);
                intent.putExtra("name", name);
                startActivity(intent);

            }
        });
    }

    class getting extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //В этом методе код перед началом выполнения фонового процесса
            Toast.makeText(Parsing.this, "Загрузка данных :3", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... params) {
            ArrayList<String> datas = new ArrayList();

            /*Этот метод выполняется в фоне
            Тут мы обращаемся к сайту и вытаскиваем его html код
            */
            String url = getIntent().getExtras().getString("object");// Адрес сайта с расписанием
            Document document = null;
            try {
                document = Jsoup.connect(url).get();// Коннектимся и получаем страницу
            } catch (IOException e) {
                Toast.makeText(Parsing.this, "Возможно проблемы с интернетом", Toast.LENGTH_SHORT).show();
            }

            assert document != null;
            Elements all = document.getElementsByAttributeValue("class", "notgreyclass").select("td");

            for (Element element : all.select("a")) {
                datas.add((element.text()));
            }
            for (String element : datas) {
                content += (element + "\n");
            }
            if (content.equals("")) {
                content = "Расписание олимпиады в этом году пока не известно";
            }
            return content;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            /*Этот метод выполняется при завершении фонового кода
            Сюда возвращаются данные из потока
             */
            name = getIntent().getExtras().getString("name");
            String text = "";
            try {
                BufferedReader read = new BufferedReader(new InputStreamReader(openFileInput("olimpiads.txt")));
                String str = "";
                while ((str = read.readLine()) != null) {
                    text += str + "\n";
                }
                read.close();
            } catch (FileNotFoundException ex) {
                //Если файла с сохранённым расписанием нет, то записываем в answer пустоту
                text = "";
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            olimpiads.addAll(Arrays.asList(text.split("\n")));

            request = "";//Начинаем формировать ответ
            request += result;
            //Выводим результат
            textView.setText(request);
            if (request.equals("Расписание олимпиады в этом году пока не известно")) {
                Toast.makeText(Parsing.this, "Расписания пока нет", Toast.LENGTH_SHORT).show();
                button.setEnabled(false);
            } else if (olimpiads.contains(name)) {
                Toast.makeText(Parsing.this, "Олимпиада уже в вашем списке", Toast.LENGTH_SHORT).show();
                button.setEnabled(false);
            } else {
                button.setEnabled(true);
            }

        }
    }


}