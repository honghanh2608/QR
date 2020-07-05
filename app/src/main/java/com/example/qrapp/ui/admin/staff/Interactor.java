package com.example.qrapp.ui.admin.staff;

import com.example.qrapp.API;
import com.example.qrapp.APICallback;
import com.example.qrapp.data.User;
import com.google.gson.JsonElement;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Interactor implements Contract.Interactor {
    private API api = API.getInstance();
    @Override
    public void getListStaff(String access_token, APICallback<List<User>> callback) {
        api.getApiService().getListStaff(access_token).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                callback.callback(response.body());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                callback.callback(null);
            }
        });
    }

    @Override
    public void createStaff(String access_token, User staff, APICallback<Boolean> callback) {
        api.getApiService().createStaff(access_token,staff).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()){
                    callback.callback(true);
                }else {
                    callback.callback(false);
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                callback.callback(false);
            }
        });
    }
}
