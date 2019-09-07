package com.example.consumoandroid.api;

public class APIUtils {

    private APIUtils(){
    }

    public static String API_URL = "http://192.168.1.11/api/";

    public static LaravelService getLaravelService(){
        return  RetrofitClient.getClient(API_URL).create(LaravelService.class);
    }
}
