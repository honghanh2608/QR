package com.example.qrapp.ui.staff.newproduct;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qrapp.R;
import com.example.qrapp.databinding.FragmentScanBarcodeBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScanBarcodeFragment extends Fragment {
    private FragmentScanBarcodeBinding binding;

    public ScanBarcodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentScanBarcodeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
