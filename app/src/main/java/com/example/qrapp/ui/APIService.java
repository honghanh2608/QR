package com.example.qrapp.ui;

import com.example.qrapp.data.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    @GET("/v1/api/products")
    Call<List<Product>> getProducts(@Query("ids") String ids);
}
