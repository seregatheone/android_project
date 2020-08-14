package com.example.project_x;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public class Answer{
    public Boolean status;
    public String sql;

    private ArrayList<User> data;
    public void setData(ArrayList<User> users){
        this.data = users;
    }
    public ArrayList<User>getData(){
        return this.data;
    }

    public List<User> getUsers(){
        List<User> users = new ArrayList<User>();
        for (User u :data) {
            users.add(u);
        }
        return users;

    }
}

class User {
    public String title, desk,ssil;
    public String rating;
}

interface UserService{
    @GET("/select_info.php")
    Call<Answer> getAnswer();
}
interface UserService1{
    @GET("/select_math.php")
    Call<Answer> getAnswer();
}