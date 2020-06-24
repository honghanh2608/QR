package com.example.qrapp.ui.staff;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

import android.graphics.Color;
import android.graphics.Outline;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qrapp.ObservableManager;
import com.example.qrapp.R;
import com.example.qrapp.databinding.ActivityStaffBinding;
import com.example.qrapp.ui.staff.account.StaffAccountFragment;
import com.example.qrapp.ui.staff.newproduct.NewProductFragment;
import com.example.qrapp.ui.staff.order.ScannerFragment;

import java.util.ArrayList;
import java.util.List;

public class StaffActivity extends AppCompatActivity {
    private ActivityStaffBinding binding;
    private List<Fragment> fragments = new ArrayList<>();
    private boolean isScannerFragment = true;
    private boolean isNewProductFragment = false;
    private boolean isStaffAccountFragment = false;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStaffBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.lnStaffFunction.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRect(-100, -15, view.getWidth()+100, view.getHeight());
            }
        });
        addFragment();
        onClick();
        disposable = ObservableManager.subject.doOnNext(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Throwable {//ham khi nhan dc event
                getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                replaceFragment(0);
                ((ScannerFragment) fragments.get(0)).resetFragment();
            }
        }).subscribe();
    }

    public void addFragment(){
        ScannerFragment scannerFragment = new ScannerFragment();
        NewProductFragment newProductFragment = new NewProductFragment();
        StaffAccountFragment staffAccountFragment = new StaffAccountFragment();
        fragments.add(scannerFragment);
        fragments.add(newProductFragment);
        fragments.add(staffAccountFragment);

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, scannerFragment);
        fragmentTransaction.addToBackStack("scanner");
        fragmentTransaction.commit();
    }

    public void onClick(){
        binding.lnScanner.setOnClickListener(v -> {
            if (!isScannerFragment){
                replaceFragment(0);
                isScannerFragment = true;
                isStaffAccountFragment = isNewProductFragment = false;
                setColor(binding.imgScan, binding.tvScan, binding.imgAddProduct, binding.tvAddProduct, binding.imgAccount, binding.tvAccount);
            }
        });
        binding.lnNewProduct.setOnClickListener(v -> {
            if (!isNewProductFragment){
                replaceFragment(1);
                isNewProductFragment = true;
                isScannerFragment = isStaffAccountFragment = false;
                setColor(binding.imgAddProduct, binding.tvAddProduct, binding.imgScan, binding.tvScan, binding.imgAccount, binding.tvAccount);
            }
        });
        binding.lnAccount.setOnClickListener(v -> {
            if (!isStaffAccountFragment){
                replaceFragment(2);
                isStaffAccountFragment = true;
                isScannerFragment = isNewProductFragment = false;
                setColor(binding.imgAccount, binding.tvAccount, binding.imgScan, binding.tvScan, binding.imgAddProduct, binding.tvAddProduct);
            }
        });
    }

    public void replaceFragment(int index){
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragments.get(index));
        if (index == 0 && !getSupportFragmentManager().getFragments().contains(fragments.get(0))) {
            fragmentTransaction.addToBackStack("scanner");
        }
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void setColor(ImageView img1, TextView tv1, ImageView img2, TextView tv2, ImageView img3, TextView tv3){
        img1.setColorFilter(getColor(R.color.colorRed));
        tv1.setTextColor(getColor(R.color.colorRed));
        img2.setColorFilter(getColor(R.color.colorBlack));
        tv2.setTextColor(getColor(R.color.colorBlack));
        img3.setColorFilter(getColor(R.color.colorBlack));
        tv3.setTextColor(getColor(R.color.colorBlack));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
