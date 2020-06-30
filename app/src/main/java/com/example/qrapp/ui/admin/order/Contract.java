package com.example.qrapp.ui.admin.order;

import com.example.qrapp.APICallback;
import com.example.qrapp.data.OrderHistory;
import com.example.qrapp.data.OrderHistoryProduct;

import java.util.Calendar;
import java.util.List;

public class Contract {
    interface View {
        void showLoading();
        void hideLoading();
        void fillData(List<OrderHistory> orders);
    }

    interface Presenter {
        void attachView(View view);
        void detachView();
        void fetchData(Calendar startDate, Calendar endDate);
    }

    interface Interator {
        void getAllOrder(Calendar startDate, Calendar endDate, APICallback<List<OrderHistory>> callback);
    }
}
