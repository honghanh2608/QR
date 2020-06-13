package com.example.qrapp.ui.staff.order;

import com.example.qrapp.data.QRCode;

import java.util.ArrayList;
import java.util.List;

public class Presenter implements Contract.Presenter {
    private Interactor interactor = new Interactor();
    private Contract.View view;
    private List<QRCode> codes = new ArrayList<>();

    @Override
    public void attachView(Contract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void getOrder(List<QRCode> codes) {

    }
}
