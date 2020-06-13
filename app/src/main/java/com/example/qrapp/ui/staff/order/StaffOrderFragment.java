package com.example.qrapp.ui.staff.order;

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

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.qrapp.R;
import com.example.qrapp.data.QRCode;
import com.example.qrapp.databinding.FragmentStaffOrderBinding;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class StaffOrderFragment extends Fragment implements Contract.View {
    private ZXingScannerView scannerView;
    private FragmentStaffOrderBinding binding;
    private static final int REQUEST_CAMERA = 1;
    private Presenter presenter = new Presenter();
    private List<QRCode> listCodes = new ArrayList<>();

    public StaffOrderFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStaffOrderBinding.inflate(inflater, container, false);
        presenter.attachView(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        //check version >= Android 6
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            //check if camera permission is granted
//            if (view.getContext().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
//                return;
//            }
//        }
//        startCamera();
        checkCameraPermission();
        binding.btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getOrder(listCodes);
                showOrder();
                binding.scannerView.stopCamera();
            }
        });

    }

    private void checkCameraPermission(){
        Dexter.withContext(getContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        binding.scannerView.setResultHandler(new ZXingScannerView.ResultHandler() {
                            @Override
                            public void handleResult(Result result) {
                                Toast.makeText(getContext(), "" + result.getText(), Toast.LENGTH_SHORT).show();
                                addQuantity(result.getText());
                                final ZXingScannerView.ResultHandler handler = this;
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        binding.scannerView.resumeCameraPreview(handler);
                                    }
                                }, 1000);
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

    private void addQuantity(String newCode){
        for (QRCode code : listCodes){
            if (newCode.equals(code.getCode())){
                code.setQuantity(code.getQuantity() + 1);
                return;
            }
        }
        listCodes.add(new QRCode(newCode, 1));
    }

    @Override
    public void showOrder() {

    }

    @Override
    public void onDestroy() {
        binding.scannerView.stopCamera();
        presenter.detachView();
        super.onDestroy();
    }

//    private void startCamera() {
//        binding.scannerView.setResultHandler(new ZXingScannerView.ResultHandler() {
//            @Override
//            public void handleResult(Result result) {
//                Toast.makeText(getContext(), "" + result.getText(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        binding.scannerView.startCamera();
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_CAMERA && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            startCamera();
//        } else {
//            new AlertDialog.Builder(getContext())
//                    .setMessage(getString(R.string.app_name) + " needs camera permission to scan barcode")
//                    .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
//                        }
//                    })
//                    .setNegativeButton("Don't allow", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            System.exit(0);
//                        }
//                    }).show();
//        }
//    }
}
