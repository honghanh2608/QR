package com.example.qrapp.ui.staff.newproduct;

import com.example.qrapp.APICallback;
import com.example.qrapp.data.Product;

public class Contract {
    interface View{
        void showErr(String mess);
        void showDialogSuccess();
    }
    interface Presenter{
        void attachView(View view);
        void detachView();
        void createProduct(Product product);
    }
    interface Interactor{
        void createProduct(Product product, APICallback<Integer> callback);
    }
}
