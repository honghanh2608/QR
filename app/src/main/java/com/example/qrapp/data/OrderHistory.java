package com.example.qrapp.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderHistory {
    private long id;
    private List<OrderHistoryProduct> products;

    public long getId() {
        return id;
    }

    public List<OrderHistoryProduct> getProducts() {
        return products;
    }
}
