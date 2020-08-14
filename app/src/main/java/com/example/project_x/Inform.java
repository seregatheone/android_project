package com.example.project_x;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.Boolean.FALSE;

public class Inform extends AppCompatActivity {
    private List<Olimp> olimpList = new ArrayList<Olimp>();
    private ListView listView;
    private CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomListAdapter(this, olimpList);
        listView.setAdapter(adapter);
        AsyncSelectTask asyncSelectTask = new AsyncSelectTask();
        asyncSelectTask.execute("http://192.168.0.4");
    }

    class AsyncSelectTask extends AsyncTask<String, Integer, Answer> {
        @Override
        protected void onPreExecute(){
            //перед началом загрузки

        }

        @Override
        protected void onProgressUpdate(Integer...  values){
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
            try {
                Call<Answer> call = service.getAnswer();
                Response<Answer> response = call.execute();
                answer = response.body();
                ArrayList<User> users = new ArrayList<>(response.body().getData());
            }catch (Exception e){
                e.printStackTrace();
            }
            return answer;
        }

        @Override
        protected void onPostExecute(Answer answer){
            super.onPostExecute(answer);
            for(User u: answer.getData()){
                Olimp olimp = new Olimp(u.title,u.desk,u.rating,u.ssil);
                olimpList.add(olimp);
            }
            adapter.notifyDataSetChanged();

        }
    }

}