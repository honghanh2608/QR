package com.example.qrapp.ui.staff.newproduct;

import com.example.qrapp.API;
import com.example.qrapp.APICallback;
import com.example.qrapp.APIService;
import com.example.qrapp.data.Product;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Interactor implements Contract.Interactor {
    @Override
    public void createProduct(Product product, APICallback<Boolean> callback) {
        APIService service = API.getInstance().getApiService();
        service.createProduct(product).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                callback.callback(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                callback.callback(false);
            }
        });
    }
}
