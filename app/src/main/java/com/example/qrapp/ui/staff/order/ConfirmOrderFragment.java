package com.example.qrapp.ui.staff.order;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Outline;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.Toast;

import com.example.qrapp.ObservableManager;
import com.example.qrapp.R;
import com.example.qrapp.data.OrderItem;
import com.example.qrapp.data.Product;
import com.example.qrapp.databinding.FragmentConfirmOrderBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmOrderFragment extends Fragment implements Contract.OrderView {
    private FragmentConfirmOrderBinding binding;
    private List<OrderItem> orderItems;
    private long price;
    private Presenter presenter = new Presenter();

    public ConfirmOrderFragment() {
        // Required empty public constructor
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentConfirmOrderBinding.inflate(inflater, container, false);
        presenter.attachOrderView(this);
        binding.llActionBar.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRect(-100, -15, view.getWidth()+100, view.getHeight());
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OrderAdapter orderAdapter = new OrderAdapter(getContext(), orderItems);
        binding.rvOrder.setAdapter(orderAdapter);
        binding.rvOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        //
        DecimalFormat df = new DecimalFormat("#,###,###");
        String p = df.format(price);
        binding.tvPrice.setText(getString(R.string.price, p));
        //
        binding.imgBack.setOnClickListener(v -> {
            FragmentManager fm = getFragmentManager();
            fm.popBackStack();
        });

        binding.btnOK.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getContext().getApplicationContext().getSharedPreferences("QRApp", Context.MODE_PRIVATE);
            String access_token = sharedPreferences.getString("access_token",null);
            presenter.createOrder(access_token, orderItems);
        });

        binding.btnSucceed.setOnClickListener(v -> {
            ObservableManager.subject.onNext(true);//Phat ra su kien
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void showErr(String mess) {
        Toast.makeText(getContext(), mess, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showDialogSucess() {
        binding.llDialogSucess.setVisibility(View.VISIBLE);
    }
}
