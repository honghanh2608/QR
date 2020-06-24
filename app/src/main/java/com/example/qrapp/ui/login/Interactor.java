package com.example.qrapp.ui.login;

import com.example.qrapp.API;
import com.example.qrapp.APICallback;
import com.example.qrapp.data.User;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Interactor implements Contract.Interactor {
    private API api = API.getInstance();
    @Override
    public void login(User user, APICallback<HashMap<String,String>> callback) {
        api.getApiService().authenticateAccount(user).enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                callback.callback(response.body());
            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                callback.callback(null);
            }
        });
    }
}
