package com.example.qrapp;

import com.example.qrapp.data.OrderItem;
import com.example.qrapp.data.OrderRequest;
import com.example.qrapp.data.Product;
import com.example.qrapp.data.User;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    @GET("/v1/product/detail")
    Call<Product> getProduct(@Query("barcode") String barcode);

    @POST("/v1/order")
    Call<HashMap<String,Long>> createOrder(@Header("access_token") String access_token,
                           @Header("Content-Type") String content_type, @Body OrderRequest orderRequest);

    @POST("/v1/auth/staff")
    Call<HashMap<String,String>> authenticateAccount(@Body User user);
}
