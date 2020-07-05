package com.example.qrapp.ui.admin.staff;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Outline;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.Toast;

import com.example.qrapp.R;
import com.example.qrapp.data.User;
import com.example.qrapp.databinding.FragmentNewStaffBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewStaffFragment extends Fragment implements Contract.NewStaffView {
    private Presenter presenter = new Presenter();
    private FragmentNewStaffBinding binding;

    public NewStaffFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewStaffBinding.inflate(inflater, container, false);
        presenter.attchNewStaffView(this);
        binding.llActionBar.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRect(-100, -15, view.getWidth()+100, view.getHeight());
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnOK.setOnClickListener(v -> {
            String name = binding.edtName.getText().toString();
            String email = binding.edtEmail.getText().toString();
            String password = binding.edtPassword.getText().toString();
            checkInfo(name,email,password);
            if (!name.equals("")&&!email.equals("")&&!password.equals("")&&Patterns.EMAIL_ADDRESS.matcher(email).matches()&&password.length()>=6) {
                User user = new User(name, email, password);
                SharedPreferences sharedPreferences = getContext().getApplicationContext().getSharedPreferences("QRApp", Context.MODE_PRIVATE);
                String access_token = sharedPreferences.getString("access_token", null);
                presenter.createStaff(access_token, user);
            }
        });

        binding.btnSucceed.setOnClickListener(v -> {
            resetFragment();
            binding.llDialogSucess.setVisibility(View.GONE);
        });

        binding.imgBack.setOnClickListener(v -> {
            FragmentManager fm = getFragmentManager();
            fm.popBackStack();
        });
    }

    private void resetFragment(){
        binding.edtName.setText("");
        binding.edtEmail.setText("");
        binding.edtPassword.setText("");
        binding.tvErrName.setVisibility(View.GONE);
        binding.tvErrPassword.setVisibility(View.GONE);
        binding.tvErrEmail.setVisibility(View.GONE);
    }

    private void checkInfo(String name,String email,String password){
        if (name.equals("")){
            binding.tvErrName.setText("Bắt buộc");
            binding.tvErrName.setVisibility(View.VISIBLE);
        }
        if (email.equals("")){
            binding.tvErrEmail.setText("Bắt buộc");
            binding.tvErrEmail.setVisibility(View.VISIBLE);
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.tvErrEmail.setText("Invalid email address");
            binding.tvErrEmail.setVisibility(View.VISIBLE);
        }
        if (password.equals("")){
            binding.tvErrPassword.setText("Bắt buộc");
            binding.tvErrPassword.setVisibility(View.VISIBLE);
        }else if (password.length()<6){
            binding.tvErrPassword.setText("Password length must be greater than 6");
            binding.tvErrPassword.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showErr(String mess) {
        Toast.makeText(getContext(),mess,Toast.LENGTH_LONG).show();
    }

    @Override
    public void showDialogSuccess() {
        binding.llDialogSucess.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
