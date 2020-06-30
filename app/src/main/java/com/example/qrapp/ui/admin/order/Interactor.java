package com.example.qrapp.ui.admin.order;

import com.example.qrapp.API;
import com.example.qrapp.APICallback;
import com.example.qrapp.APIService;
import com.example.qrapp.data.OrderHistory;
import com.example.qrapp.data.OrderHistoryProduct;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Interactor implements Contract.Interator {

    private String token;

    public Interactor(String token) {
        this.token = token;
    }

    @Override
    public void getAllOrder(Calendar startDate, Calendar endDate, APICallback<List<OrderHistory>> callback) {
        APIService service = API.getInstance().getApiService();
        service.getAllOrder(token).enqueue(new Callback<List<OrderHistory>>() {
            @Override
            public void onResponse(Call<List<OrderHistory>> call, Response<List<OrderHistory>> response) {
                callback.callback(response.body());
            }

            @Override
            public void onFailure(Call<List<OrderHistory>> call, Throwable t) {
                callback.callback(null);
            }
        });
    }
}
