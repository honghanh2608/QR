package com.example.qrapp.ui.login;

import com.example.qrapp.APICallback;
import com.example.qrapp.data.User;

import java.util.HashMap;

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
    public void login(User user) {
        interactor.login(user, new APICallback<HashMap<String, String>>() {
            @Override
            public void callback(HashMap<String, String> data) {
                if (data == null){
                    view.showErr("Email or password is not correct");
                }else {
                    user.setAccess_token(data.get("access_token"));
                    view.createUserSession(data.get("access_token"), data.get("permission"), data.get("username"), data.get("id"),  user.getEmail());
                    view.startActivity(data.get("permission"));
                }
            }
        });
    }
}
