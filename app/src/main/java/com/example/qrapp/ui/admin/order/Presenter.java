package com.example.qrapp.ui.admin.order;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Calendar;

public class Presenter implements Contract.Presenter {

    private String token;
    @NonNull
    private Contract.Interator interator;
    @Nullable
    private Contract.View view;

    public Presenter(String token) {
        this.token = token;
        interator = new Interactor(token);
    }

    @Override
    public void attachView(Contract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void fetchData(Calendar startDate, Calendar endDate) {
        if (view == null) return;
        view.showLoading();
        interator.getAllOrder(startDate, endDate, orderHistory -> {
            if (view == null) return;
            view.hideLoading();
            view.fillData(orderHistory);
        });
    }
}
