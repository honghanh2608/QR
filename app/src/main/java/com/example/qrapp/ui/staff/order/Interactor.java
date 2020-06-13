package com.example.qrapp.ui.staff.order;

import com.example.qrapp.data.QRCode;
import com.example.qrapp.ui.API;

import java.util.List;

public class Interactor implements Contract.Interactor {
    private API api = API.getInstance();
    @Override
    public void getOrder(List<QRCode> codes) {

    }
}
