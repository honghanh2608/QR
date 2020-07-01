package com.example.qrapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.qrapp.Callback;
import com.example.qrapp.R;
import com.example.qrapp.data.Property;
import com.example.qrapp.databinding.DialogPropertiesBinding;

import java.util.ArrayList;
import java.util.List;

public class PropertiesDialog extends Dialog {

    private DialogPropertiesBinding binding;
    private Callback<List<Property>> callback;
    private List<Property> properties;
    private PropertyAdapter adapter;

    public PropertiesDialog(@NonNull Context context, List<Property> properties) {
        super(context);
        binding = DialogPropertiesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (properties == null) this.properties = new ArrayList<>();
        else this.properties = new ArrayList<>(properties);
        binding.imgClose.setOnClickListener(v -> cancel());
        binding.btnSave.setOnClickListener(v -> save());
        binding.rvProperties.setLayoutManager(new LinearLayoutManager(context));
        adapter = new PropertyAdapter(context, this.properties);
        binding.rvProperties.setAdapter(adapter);
        binding.btnAdd.setOnClickListener(v -> addProperty());
        adapter.setOnDeleteItemClickListener(this::deleteItem);

        Window window = getWindow();
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams params = window.getAttributes();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        params.width = metrics.widthPixels;
        params.height = metrics.heightPixels;
        window.setAttributes(params);
    }

    public void setCallback(Callback<List<Property>> callback) {
        this.callback = callback;
    }

    private void save() {
        if (callback != null) {
            callback.apply(properties);
        }
        cancel();
    }

    private void addProperty() {
        String name = binding.edtName.getText().toString().trim();
        String value = binding.edtValue.getText().toString().trim();
        binding.edtName.setText("");
        binding.edtValue.setText("");
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(value)) {
            return;
        }
        properties.add(new Property(name, value));
        adapter.notifyDataSetChanged();
    }

    private void deleteItem(int i) {
        properties.remove(i);
        adapter.notifyDataSetChanged();
    }
}
