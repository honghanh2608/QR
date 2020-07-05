package com.example.qrapp.ui.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.ColorStateList;
import android.graphics.Outline;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qrapp.R;
import com.example.qrapp.databinding.ActivityAdminBinding;
import com.example.qrapp.ui.admin.order.AdminOrderFragment;
import com.example.qrapp.ui.admin.staff.AdminStaffFragment;
import com.example.qrapp.ui.staff.account.StaffAccountFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminActivity extends AppCompatActivity {
    private ActivityAdminBinding binding;
    private AdminStaffFragment adminStaffFragment;
    private AdminOrderFragment adminOrderFragment;
    private List<Fragment> fragments = new ArrayList<>();

    private List<NavItem> navItems;
    private int currentIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.linearlayout.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRect(-100, -15, view.getWidth() + 100, view.getHeight());
            }
        });

        fragments = Arrays.asList(
                new AdminOrderFragment(),
                new AdminStaffFragment(),
                new StaffAccountFragment()
        );

        navItems = Arrays.asList(
                new NavItem(binding.layoutOrder, binding.imgOrder, binding.tvOrder),
                new NavItem(binding.layoutStaff, binding.imgStaff, binding.tvStaff),
                new NavItem(binding.layoutAdmin, binding.imgAccount, binding.tvAccount)
        );

        for (int i = 0; i < navItems.size(); i++) {
            applyItem(i, navItems.get(i));
            int finalI = i;
            navItems.get(i).view.setOnClickListener(v -> selectItem(finalI));
        }

        navigate(fragments.get(0));
    }

    private void selectItem(int index) {
        if (currentIndex == index) return;
        currentIndex = index;
        for (int i = 0; i < navItems.size(); i++) {
            applyItem(i, navItems.get(i));
        }
        navigate(fragments.get(index));
    }

    private void applyItem(int i, NavItem navItem) {
        if (i == currentIndex) applyItemSelected(navItem);
        else applyItemNotSelected(navItem);
    }

    private void applyItemSelected(NavItem navItem) {
        int color = getColor(R.color.colorRed);
        navItem.icon.setImageTintList(ColorStateList.valueOf(color));
        navItem.label.setTextColor(color);
    }

    private void applyItemNotSelected(NavItem navItem) {
        int color = getColor(R.color.colorBlack);
        navItem.icon.setImageTintList(null);
        navItem.label.setTextColor(color);
    }

    private void navigate(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }

    private static class NavItem {
        View view;
        ImageView icon;
        TextView label;

        NavItem(View view, ImageView icon, TextView label) {
            this.view = view;
            this.icon = icon;
            this.label = label;
        }
    }
}
