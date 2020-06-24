package com.example.qrapp;

import com.example.qrapp.data.Product;

public interface APICallback<T> {
    void callback(T data);
}
