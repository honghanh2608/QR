package com.example.qrapp.ui.staff.order;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.qrapp.R;
import com.example.qrapp.data.OrderItem;
import com.example.qrapp.data.Product;
import com.example.qrapp.databinding.FragmentScannerBinding;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScannerFragment extends Fragment implements Contract.View, ZXingScannerView.ResultHandler {
    private ZXingScannerView scannerView;
    private FragmentScannerBinding binding;
    private static final int REQUEST_CAMERA = 1;
    private Presenter presenter = new Presenter();
    private List<OrderItem> orderItems = new ArrayList<>();
    private OrderAdapter orderAdapter;
    private View rootView;
    private long total = 0;

    private boolean isCreated = false;

    public ScannerFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentScannerBinding.inflate(inflater, container, false);
        presenter.attachView(this);
        if (rootView == null){
            rootView = binding.getRoot();
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isCreated) {
            return;
        }
        isCreated = true;
        orderAdapter = new OrderAdapter(getContext(), orderItems);
        binding.rvOrder.setAdapter(orderAdapter);
        binding.rvOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        //
        checkCameraPermission();
        binding.btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrder();
            }
        });
    }

    private void checkCameraPermission() {
        Dexter.withContext(getContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        binding.scannerView.startCamera(ScannerFragment.this);
                        binding.scannerView.setCallback(2000, result -> handleResult(result));
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(getContext(), "You must accept this permission", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }
                })
                .check();
    }

    private void checkBarcode(String barcode) {
        for (int i = 0; i < orderItems.size(); i++) {
            if (barcode.equals(orderItems.get(i).getBarcode())) {//sp da quet
                int quantity = orderItems.get(i).getQuantity() + 1;
                orderItems.get(i).setQuantity(quantity);
                orderAdapter.notifyItemChanged(i);
                total = total + orderItems.get(i).getPrice();
                setPrice(total);
                return;
            }
        }
        presenter.getProduct(barcode);
    }

    @Override
    public void showOrder() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ConfirmOrderFragment confirmOrderFragment = new ConfirmOrderFragment();
        confirmOrderFragment.setOrderItems(orderItems);
        confirmOrderFragment.setPrice(total);
        fragmentTransaction.replace(R.id.container, confirmOrderFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void showErr(String mess) {
        Toast.makeText(getContext(), mess, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showNewOrderItem(Product product) {
        orderItems.add(new OrderItem(product.getBarcode(), 1, product.getName(), product.getPrice()));
        orderAdapter.notifyDataSetChanged();
        total = total + product.getPrice();
        setPrice(total);
    }

    private void setPrice(long price){
        DecimalFormat df = new DecimalFormat("#,###,###");
        String p = df.format(price);
        binding.tvPrice.setText(getString(R.string.price, p));
    }

    @Override
    public void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    public void resetFragment(){
        orderItems.clear();
        orderAdapter.notifyDataSetChanged();
        total = 0;
        setPrice(0);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void handleResult(Result result) {
        Toast.makeText(getContext(), "" + result.getText(), Toast.LENGTH_SHORT).show();
        Log.d("QRCodeAnalyzer", "handleResult: " + result.getText());
        checkBarcode(result.getText());
    }
}
