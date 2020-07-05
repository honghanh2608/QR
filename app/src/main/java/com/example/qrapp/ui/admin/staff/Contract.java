package com.example.qrapp.ui.admin.staff;

import com.example.qrapp.APICallback;
import com.example.qrapp.data.User;
import com.google.gson.JsonElement;

import java.util.List;

public class Contract {
    interface View{
        void showListStaff(List<User> staffs);
        void showErr(String mess);
        void showLoading();
        void hideLoading();
    }
    interface NewStaffView{
        void showErr(String mess);
        void showDialogSuccess();
    }
    interface Presenter{
        void attachView(View view);
        void attchNewStaffView(NewStaffView newStaffView);
        void detachView();
        void getListStaff(String access_token);
        void createStaff(String access_token,User staff);
    }
    interface Interactor{
        void getListStaff(String access_token, APICallback<List<User>> callback);
        void createStaff(String access_token, User staff, APICallback<Boolean> callback);
    }
}
