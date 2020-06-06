package com.example.qrapp.ui.staff;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.qrapp.R;

import java.util.ArrayList;
import java.util.List;

public class StaffActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
        List<Fragment> fragments = new ArrayList<>();
        StaffOrderFragment staffOrderFragment = new StaffOrderFragment();
        fragments.add(staffOrderFragment);

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, staffOrderFragment);
        fragmentTransaction.commit();
    }
}
