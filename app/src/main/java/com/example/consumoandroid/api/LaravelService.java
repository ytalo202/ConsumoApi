package com.example.consumoandroid.api;

import com.example.consumoandroid.model.Resul;
import com.example.consumoandroid.model.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LaravelService {

    @GET("task/")
    Call<List<Task>> getTasks();

    @DELETE("task/{id}")
    Call<Resul> deleteTask(@Path("id") Integer id);

    @POST("task/")
    Call<Task> createTask(@Body Task user);

    @PUT("task/{id}")
    Call<Resul> updateUser(@Path("id") Integer id, @Body Task user);
}
