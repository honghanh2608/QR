package com.example.qrapp.ui.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Outline;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.example.qrapp.R;
import com.example.qrapp.databinding.ActivityAdminBinding;
import com.example.qrapp.ui.admin.order.OrderFragment;
import com.example.qrapp.ui.admin.staff.StaffFragment;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {
    private ActivityAdminBinding binding;
    private StaffFragment staffFragment;
    private OrderFragment orderFragment;
    private List<Fragment> fragments = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.linearlayout.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRect(-100, -15, view.getWidth()+100, view.getHeight());
            }
        });

    }
}
