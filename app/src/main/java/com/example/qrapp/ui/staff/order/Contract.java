package com.example.qrapp.ui.staff.order;

import com.example.qrapp.data.QRCode;

import java.util.List;

public class Contract {
    interface View{
        void showOrder();
    }

    interface Presenter{
        void attachView(View view);
        void detachView();
        void getOrder(List<QRCode> codes);
    }

    interface Interactor{
        void getOrder(List<QRCode> codes);
    }
}
