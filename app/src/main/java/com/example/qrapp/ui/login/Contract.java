package com.example.qrapp.ui.login;

import com.example.qrapp.APICallback;
import com.example.qrapp.data.User;

import java.util.HashMap;

public class Contract {
    interface View{
        void createUserSession(String access_token, String permission);
        void startActivity(String permission);
        void showErr(String mess);
    }
    interface Presenter{
        void attachView(View view);
        void detachView();
        void login(User user);
    }
    interface Interactor{
        void login(User user, APICallback<HashMap<String,String>> callback);
    }
}
