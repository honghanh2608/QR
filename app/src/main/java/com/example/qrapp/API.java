package com.example.qrapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {
    private APIService apiService;
    private static API instance;

    public static API getInstance() {
        if (instance == null){
            instance = new API();
        }
        return instance;
    }

    private API(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://qrappserver.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(APIService.class);
    }

    public APIService getApiService() {
        return apiService;
    }
}
