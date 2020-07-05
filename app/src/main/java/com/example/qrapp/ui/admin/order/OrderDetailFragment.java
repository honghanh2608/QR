package com.example.qrapp.ui.admin.order;

import android.graphics.Outline;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

import com.example.qrapp.R;
import com.example.qrapp.data.OrderHistoryProduct;
import com.example.qrapp.data.OrderItem;
import com.example.qrapp.databinding.FragmentOrderDetailBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDetailFragment extends Fragment {
    private FragmentOrderDetailBinding binding;
    private OrderDetailAdapter adapter;
    private List<OrderHistoryProduct> products = new ArrayList<>();

    public void setProducts(List<OrderHistoryProduct> products) {
        this.products = products;
    }

    public OrderDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderDetailBinding.inflate(inflater, container, false);
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
        adapter = new OrderDetailAdapter(getContext(), products);
        binding.rvOrderDetail.setAdapter(adapter);
        binding.rvOrderDetail.setLayoutManager(new LinearLayoutManager(getContext()));
        setTotal();
        binding.imgBack.setOnClickListener(v -> {
            FragmentManager fm = getFragmentManager();
            fm.popBackStack();
        });
    }

    private void setTotal(){
        DecimalFormat df = new DecimalFormat("#,###,###");
        String p = df.format(calculateTotal());
        binding.tvTotal.setText(getString(R.string.price, p));
    }

    private long calculateTotal() {
        long value = 0;
        for (OrderHistoryProduct product:products){
            value += product.getQuantity() * product.getPrice();
        }
        return value;
    }
}
