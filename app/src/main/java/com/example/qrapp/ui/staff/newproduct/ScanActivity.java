package com.example.qrapp.ui.staff.newproduct;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.qrapp.ObservableManager;
import com.example.qrapp.R;
import com.example.qrapp.databinding.ActivityScanBinding;
import com.google.zxing.Result;
import com.nullexcom.qrscanner.Callback;

import org.jetbrains.annotations.NotNull;

public class ScanActivity extends AppCompatActivity implements Callback {

    private ActivityScanBinding binding;

    private String barcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.scannerView.startCamera(this);
        binding.scannerView.setCallback(0, this);
        binding.imgBack.setOnClickListener(v -> super.onBackPressed());
    }


    @Override
    public void apply(@NotNull Result result) {
        if (barcode == null) {
            barcode = result.getText();
            Intent intent = new Intent();
            intent.putExtra("barcode", barcode);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.scannerView.stopCamera();
        ObservableManager.fromScanner.onNext(true);
    }
}
