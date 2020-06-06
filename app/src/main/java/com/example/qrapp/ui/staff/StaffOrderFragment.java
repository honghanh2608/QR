package com.example.qrapp.ui.staff;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.qrapp.R;
import com.example.qrapp.databinding.FragmentStaffOrderBinding;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class StaffOrderFragment extends Fragment implements ZXingScannerView.ResultHandler{
    private ZXingScannerView scannerView;
    private FragmentStaffOrderBinding binding;
//    private static final int REQUEST_CAMERA = 1;

    public StaffOrderFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
 //       scannerView = new ZXingScannerView(getContext());
  //      binding.linearScan.addView(scannerView);
        binding = FragmentStaffOrderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        int apiVersion = android.os.Build.VERSION.SDK_INT;
//        if (apiVersion >= android.os.Build.VERSION_CODES.M) {
//            if (!checkPermission()) {
//                requestPermission();
//            }
//        }
        Dexter.withContext(getContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        binding.scannerView.setResultHandler(new ZXingScannerView.ResultHandler() {
                            @Override
                            public void handleResult(Result result) {

                            }
                        });
                        binding.scannerView.startCamera();
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

    @Override
    public void onDestroy() {
        binding.scannerView.stopCamera();
        super.onDestroy();
    }

    @Override
    public void handleResult(Result result) {
       binding.tvContent.setText(result.getText());
    }

//    @Override
 //   public void handleResult(Result result) {
//        final String strResult = result.getText();
//        new AlertDialog.Builder(getContext())
//                .setMessage(strResult)
//                .setCancelable(false)
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                })
//                .setNegativeButton("Again", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                })
//                .create()
//                .show();
 //   }

//    private boolean checkPermission(){
//        return ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED;
//    }
//
//    private void requestPermission(){
//        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode){
//            case REQUEST_CAMERA :
//                if (grantResults.length > 0){
//                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                        Toast.makeText(getContext(), "Permission granted", Toast.LENGTH_SHORT).show();
//                    } else{
//                        Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//                            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)){
//                                showMessage("You need to allow access to both the permissions", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//                                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
//                                        }
//                                    }
//                                });
//                                return;
//                            }
//                        }
//                    }
//                }
//                break;
//        }
//    }
//
//    private void showMessage(String message, DialogInterface.OnClickListener clickListener){
//        new AlertDialog.Builder(getContext())
//                .setMessage(message)
//                .setCancelable(false)
//                .setPositiveButton("OK", clickListener)
//                .setNegativeButton("Cancel", null)
//                .create()
//                .show();
//    }

}
