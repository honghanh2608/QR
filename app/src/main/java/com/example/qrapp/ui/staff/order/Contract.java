package com.example.qrapp.ui.staff.order;

import com.example.qrapp.APICallback;
import com.example.qrapp.data.OrderItem;
import com.example.qrapp.data.Product;

import java.util.HashMap;
import java.util.List;

public class Contract {
    interface View{
        void showOrder();
        void showErr(String mess);
        void showNewOrderItem(Product product);
    }
    interface OrderView{
        void showErr(String mess);
        void showDialogSucess();
    }

    interface Presenter{
        void attachView(View view);
        void attachOrderView(OrderView orderView);
        void detachView();
        void getProduct(String id);
        void createOrder(String access_token,List<OrderItem> orderItems);
    }

    interface Interactor{
        void getProduct(String id, APICallback<Product> callback);
        void createOrder(String access_token,List<OrderItem> orderItems, APICallback<HashMap<String,Long>> callback);
    }
}
