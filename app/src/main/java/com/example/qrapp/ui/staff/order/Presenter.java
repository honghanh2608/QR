package com.example.qrapp.ui.staff.order;

import com.example.qrapp.APICallback;
import com.example.qrapp.data.OrderItem;
import com.example.qrapp.data.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Presenter implements Contract.Presenter {
    private Interactor interactor = new Interactor();
    private Contract.View view;
    private Contract.OrderView orderView;
    private List<OrderItem> codes = new ArrayList<>();

    @Override
    public void attachView(Contract.View view) {
        this.view = view;
    }

    @Override
    public void attachOrderView(Contract.OrderView orderView) {
        this.orderView = orderView;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void getProduct(String id) {
        interactor.getProduct(id, new APICallback<Product>() {
            @Override
            public void callback(Product data) {
                if (data == null){
                    view.showErr("Call API fail");
                }else {
                    view.showNewOrderItem(data);
                }
            }
        });
    }

    @Override
    public void createOrder(String access_token, List<OrderItem> orderItems) {
        interactor.createOrder(access_token, orderItems, new APICallback<HashMap<String, Long>>() {
            @Override
            public void callback(HashMap<String,Long> data) {
                if (data == null){
                    orderView.showErr("Call api failed");
                }else {
                    orderView.showDialogSucess();
                }
            }
        });
    }

}
