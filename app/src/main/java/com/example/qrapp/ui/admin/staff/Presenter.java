package com.example.qrapp.ui.admin.staff;

import com.example.qrapp.APICallback;
import com.example.qrapp.data.User;

import java.util.List;

public class Presenter implements Contract.Presenter {
    private Contract.View view;
    private Contract.NewStaffView newStaffView;
    private Contract.Interactor interactor = new Interactor();
    @Override
    public void attachView(Contract.View view) {
        this.view = view;
    }

    @Override
    public void attchNewStaffView(Contract.NewStaffView newStaffView) {
        this.newStaffView = newStaffView;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void getListStaff(String access_token) {
        view.showLoading();
        interactor.getListStaff(access_token, new APICallback<List<User>>() {
            @Override
            public void callback(List<User> data) {
                if (data == null){
                    view.showErr("Call api failed");
                }else {
                    view.hideLoading();
                    view.showListStaff(data);
                }
            }
        });
    }

    @Override
    public void createStaff(String access_token, User staff) {
        interactor.createStaff(access_token, staff, new APICallback<Boolean>() {
            @Override
            public void callback(Boolean data) {
                if (data){
                    newStaffView.showDialogSuccess();
                }else {
                    newStaffView.showErr("Đã có nhân viên này");
                }
            }
        });
    }
}
