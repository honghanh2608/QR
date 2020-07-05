package com.example.qrapp.ui.admin.staff;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.qrapp.OnItemClickListener;
import com.example.qrapp.R;
import com.example.qrapp.data.User;
import com.example.qrapp.databinding.FragmentAdminStaffBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminStaffFragment extends Fragment implements Contract.View {
    private FragmentAdminStaffBinding binding;
    private StaffAdapter adapter;
    private List<User> staffs = new ArrayList<>();
    private Presenter presenter = new Presenter();
    private View rootView;

    public AdminStaffFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminStaffBinding.inflate(inflater, container, false);
        presenter.attachView(this);
        if (rootView == null){
            rootView = binding.getRoot();
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new StaffAdapter(getContext(), staffs);
        binding.rvSwipe.setAdapter(adapter);
        binding.rvSwipe.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                staffs.remove(i);
                adapter.notifyDataSetChanged();
            }
        });
        SharedPreferences sharedPreferences = getContext().getApplicationContext().getSharedPreferences("QRApp", Context.MODE_PRIVATE);
        String access_token = sharedPreferences.getString("access_token",null);
        presenter.getListStaff(access_token);

        binding.imgAddStaff.setOnClickListener(v -> {
            startNewStaffFragment();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void showListStaff(List<User> staffs) {
        this.staffs.clear();
        this.staffs.addAll(staffs);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showErr(String mess) {
        Toast.makeText(getContext(), mess, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        binding.pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        binding.pbLoading.setVisibility(View.GONE);
    }

    private void startNewStaffFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        NewStaffFragment newStaffFragment = new NewStaffFragment();
        fragmentTransaction.replace(R.id.container, newStaffFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
