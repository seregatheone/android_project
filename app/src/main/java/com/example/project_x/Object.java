package com.example.project_x;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Object extends AppCompatActivity {
    private List<Olimp> olimpList = new ArrayList<Olimp>();
    private ListView listView;
    private CustomListAdapter adapter;
    private List<String> links = new ArrayList<String>();
    private List<String> names = new ArrayList<String>();
    Bundle object = new Bundle();

    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        //database

        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomListAdapter(this, olimpList);
        listView.setAdapter(adapter);
        AsyncSelectTask asyncSelectTask = new AsyncSelectTask();
        asyncSelectTask.execute("http://h152771.s22.test-hf.su/");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Dialogs dialogs = new Dialogs();

                object.putString("key", links.get(position));

                object.putString("name", names.get(position));
                dialogs.setArguments(object);
                dialogs.show(getSupportFragmentManager(), "missiles");
            }
        });
    }


    class AsyncSelectTask extends AsyncTask<String, Integer, Answer> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // тут можно обновить статусбар
        }

        @Override
        protected Answer doInBackground(String... strings) {
            Answer answer = null;

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(strings[0])
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            UserService service = retrofit.create(UserService.class);
            UserService1 service1 = retrofit.create(UserService1.class);
            UserService2 service2 = retrofit.create(UserService2.class);
            UserService3 service3 = retrofit.create(UserService3.class);

                Call<Answer> call;
                if (getIntent().getExtras().getString("object").equals("Математика")) {
                    call = service1.getAnswer();
                } else if (getIntent().getExtras().getString("object").equals("Биология")) {
                    call = service2.getAnswer();
                } else if (getIntent().getExtras().getString("object").equals("Химия")) {
                    call = service3.getAnswer();
                } else {
                    call = service.getAnswer();
                }


            Response<Answer> response = null;
            try {
                response = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            answer = response.body();

            return answer;
        }

        @Override
        protected void onPostExecute(Answer answer) {
            super.onPostExecute(answer);
            for (User u : answer.getData()) {
                Olimp olimp = new Olimp(u.title, u.desk, u.rating, u.ssil);
                olimpList.add(olimp);
                links.add(u.ssil);
                names.add(u.title);
            }
            adapter.notifyDataSetChanged();

        }

    }


}