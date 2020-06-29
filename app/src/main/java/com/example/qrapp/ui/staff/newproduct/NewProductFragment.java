package com.example.qrapp.ui.staff.newproduct;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.qrapp.Callback;
import com.example.qrapp.R;
import com.example.qrapp.data.Property;
import com.example.qrapp.dialog.PropertiesDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewProductFragment extends Fragment {

    public NewProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        PropertiesDialog dialog = new PropertiesDialog(getContext(), null);
        dialog.setCallback(properties -> {
            String json = new Gson().toJson(properties);
            Toast.makeText(getContext(), json, Toast.LENGTH_SHORT).show();
        });
        dialog.show();
        return inflater.inflate(R.layout.fragment_new_product, container, false);
    }
}
