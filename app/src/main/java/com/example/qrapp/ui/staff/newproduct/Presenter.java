package com.example.qrapp.ui.staff.newproduct;

import com.example.qrapp.data.Product;

public class Presenter implements Contract.Presenter {
    private Contract.View view;
    private Interactor interactor = new Interactor();
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

    }
}
