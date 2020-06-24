package com.example.qrapp.data;

import java.util.List;

public class OrderRequest {
    private List<OrderItem> data;

    public OrderRequest(List<OrderItem> data){
        this.data = data;
    }

    public List<OrderItem> getData() {
        return data;
    }

    public void setData(List<OrderItem> data) {
        this.data = data;
    }
}
