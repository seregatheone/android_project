package com.example.project_x;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;


public class Answer {
    private ArrayList<User> data;

    public ArrayList<User> getData() {
        return this.data;
    }
}

class User {
    public String title, desk, ssil;
    public String rating;
}

interface UserService {
    @GET("/select_info.php")
    Call<Answer> getAnswer();
}

interface UserService1 {
    @GET("/select_math.php")
    Call<Answer> getAnswer();
}

interface UserService2 {
    @GET("/select_biol.php")
    Call<Answer> getAnswer();
}

interface UserService3 {
    @GET("/select_chim.php")
    Call<Answer> getAnswer();
}