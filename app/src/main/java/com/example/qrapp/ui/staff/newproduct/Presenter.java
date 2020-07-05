package com.example.qrapp.ui.staff.newproduct;

import com.example.qrapp.API;
import com.example.qrapp.APICallback;
import com.example.qrapp.APIService;
import com.example.qrapp.data.Product;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Presenter implements Contract.Presenter {
    private Contract.View view;
    private Contract.Interactor interactor = new Interactor();
    @Override
    public void attachView(Contract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void createProduct(Product product) {
        if (view == null) return;
        view.showLoading();

        interactor.createProduct(product, isSuccessful -> {
            if (view == null) return;
            view.hideLoading();
            if (isSuccessful) {
                view.showDialogSuccess();
            } else {
                view.showErr("Có lỗi xảy ra. Kiểm tra lại các thông tin hoặc thử lại sau");
            }
        });
    }
}
