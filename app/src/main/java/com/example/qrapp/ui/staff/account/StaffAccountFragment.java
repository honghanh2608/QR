package com.example.qrapp.ui.staff.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qrapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StaffAccountFragment extends Fragment {

    public StaffAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_staff_account, container, false);
    }
}
