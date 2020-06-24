package com.example.qrapp.ui.staff.order;

import com.example.qrapp.APICallback;
import com.example.qrapp.data.OrderItem;
import com.example.qrapp.data.OrderRequest;
import com.example.qrapp.data.Product;
import com.example.qrapp.API;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Interactor implements Contract.Interactor {
    private API api = API.getInstance();

    @Override
    public void getProduct(String id, APICallback<Product> callback) {
        api.getApiService().getProduct(id).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Product product = response.body();
                callback.callback(product);
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                callback.callback(null);
            }
        });
    }

    @Override
    public void createOrder(String access_token, List<OrderItem> orderItems, APICallback<HashMap<String, Long>> callback) {
        OrderRequest orderRequest = new OrderRequest(orderItems);
        api.getApiService().createOrder(access_token,"application/json",orderRequest).enqueue(new Callback<HashMap<String, Long>>() {
            @Override
            public void onResponse(Call<HashMap<String, Long>> call, Response<HashMap<String, Long>> response) {
                callback.callback(response.body());
            }

            @Override
            public void onFailure(Call<HashMap<String, Long>> call, Throwable t) {
                callback.callback(null);
            }
        });
    }


}
