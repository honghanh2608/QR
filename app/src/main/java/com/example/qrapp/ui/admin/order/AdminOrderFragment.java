package com.example.qrapp.ui.admin.order;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qrapp.R;
import com.example.qrapp.data.OrderHistory;
import com.example.qrapp.data.OrderHistoryProduct;
import com.example.qrapp.databinding.FragmentAdminOrderBinding;
import com.example.qrapp.utils.Number;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AdminOrderFragment extends Fragment implements Contract.View {

    private FragmentAdminOrderBinding binding;
    private Calendar startDate = Calendar.getInstance();
    private Calendar endDate = Calendar.getInstance();
    private OrderHistoryAdapter adapter;
    private List<OrderHistory> orders = new ArrayList<>();
    private Contract.Presenter presenter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdminOrderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences preferences = view.getContext().getApplicationContext().getSharedPreferences("QRApp", Context.MODE_PRIVATE);
        presenter = new Presenter(preferences.getString("access_token", ""));
        presenter.attachView(this);
        adapter = new OrderHistoryAdapter(view.getContext(), orders);
        binding.rvOrders.setAdapter(adapter);
        binding.rvOrders.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.tvStartDate.setOnClickListener(v -> pickStartDate());
        binding.tvEndDate.setOnClickListener(v -> pickEndDate());
        initText(binding.tvStartDate, startDate);
        initText(binding.tvEndDate, endDate);
        presenter.fetchData(startDate, endDate);
    }

    @Override
    public void onDestroyView() {
        presenter.detachView();
        super.onDestroyView();
    }

    private void initText(TextView tv, Calendar calendar) {
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        String dateStr = date + "/" + month + "/" + year;
        tv.setText(dateStr);
    }

    private void pickStartDate() {
        showDatePickerDialog(startDate, (view, year, month, dayOfMonth) -> {
            String dateStr = dayOfMonth + "/" + month + "/" + year;
            Toast.makeText(getContext(), dateStr, Toast.LENGTH_LONG).show();
            startDate.set(Calendar.DATE, dayOfMonth);
            startDate.set(Calendar.MONTH, month);
            startDate.set(Calendar.YEAR, year);
            binding.tvStartDate.setText(dateStr);
        });
    }

    private void pickEndDate() {
        showDatePickerDialog(endDate, (view, year, month, dayOfMonth) -> {
            String dateStr = dayOfMonth + "/" + month + "/" + year;
            endDate.set(Calendar.DATE, dayOfMonth);
            endDate.set(Calendar.MONTH, month);
            endDate.set(Calendar.YEAR, year);
            binding.tvEndDate.setText(dateStr);
        });
    }

    private void showDatePickerDialog(Calendar calendar, DatePickerDialog.OnDateSetListener onDateSetListener) {
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), onDateSetListener, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    public void showLoading() {
        binding.pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        binding.pbLoading.setVisibility(View.GONE);
    }

    @Override
    public void fillData(List<OrderHistory> orders) {
        this.orders.clear();
        if (orders != null) {
            this.orders.addAll(orders);
        }
        adapter.notifyDataSetChanged();
        binding.tvTotal.setText(getString(R.string.price, Number.currency(calculateTotal(this.orders))));
    }

    private long calculateTotal(List<OrderHistory> orders) {
        long value = 0;
        for (OrderHistory order: orders) {
            for (OrderHistoryProduct product: order.getProducts()) {
                value += product.getQuantity() * product.getPrice();
            }
        }
        return value;
    }
}
