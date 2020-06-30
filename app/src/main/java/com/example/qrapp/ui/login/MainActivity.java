package com.example.qrapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.qrapp.R;
import com.example.qrapp.data.User;
import com.example.qrapp.databinding.ActivityMainBinding;
import com.example.qrapp.ui.admin.AdminActivity;
import com.example.qrapp.ui.staff.StaffActivity;
import com.example.qrapp.ui.staff.order.ConfirmOrderFragment;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements Contract.View {
    private ActivityMainBinding binding;
    private Presenter presenter = new Presenter();
    private SharedPreferences sharedPreferences;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferences = getApplicationContext().getSharedPreferences("QRApp", Context.MODE_PRIVATE);
        String permission = sharedPreferences.getString("permission", null);
        if (Calendar.getInstance().getTimeInMillis() <= sharedPreferences.getLong("timeDuration", 0)) {//ktra access token còn han k
            startActivity(permission);
        }
        presenter.attachView(this);

        binding.edtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    binding.layoutEmail.animate().translationZ(getResources().getDimension(R.dimen.elevation)).setDuration(300).start();
                } else {
                    binding.layoutEmail.animate().translationZ(0).setDuration(300).start();
                }
            }
        });

        binding.edtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    binding.layoutPassword.animate().translationZ(getResources().getDimension(R.dimen.elevation)).setDuration(300).start();
                } else {
                    binding.layoutPassword.animate().translationZ(0).setDuration(300).start();
                }
            }
        });

        binding.tvLogin.setOnClickListener(v -> {
            onClickLogin();
        });
    }

    public void onClickLogin() {
        String email = binding.edtEmail.getText().toString();
        String password = binding.edtPassword.getText().toString();
        if (email.equals("") || password.equals("")) {
            binding.tvErr.setText("* Enter your email and password");
            binding.tvErr.setVisibility(View.VISIBLE);
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tvErr.setText("* Invalid email address");
            binding.tvErr.setVisibility(View.VISIBLE);
        } else if (password.length() < 6) {
            binding.tvErr.setText("* Password length must be greater than 6");
            binding.tvErr.setVisibility(View.VISIBLE);
        } else {
            user = new User(email, password);
            binding.llProgressBar.setVisibility(View.VISIBLE);
            presenter.login(user);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void createUserSession(String access_token, String permission) {
        sharedPreferences.edit().putString("access_token", access_token).apply();//luu access token
        long timeDuration = Calendar.getInstance().getTimeInMillis() + 30 * 24 * 60 * 60 * 1000L;
        sharedPreferences.edit().putLong("timeDuration", timeDuration).apply();//luu tg cua session la 1 thang
        sharedPreferences.edit().putString("permission", permission).apply();
    }

    @Override
    public void startActivity(String permission) {
        if ("1".equals(permission)) {
            startAsAdmin();
        } else if ("2".equals(permission)){
            startAsStaff();
        }
    }

    @Override
    public void showErr(String mess) {
        binding.llProgressBar.setVisibility(View.GONE);
        binding.tvErr.setText(mess);
        binding.tvErr.setVisibility(View.VISIBLE);
    }

    private void startAsStaff() {
        binding.llProgressBar.setVisibility(View.GONE);
        Intent intent = new Intent(this, StaffActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void startAsAdmin() {
        binding.llProgressBar.setVisibility(View.GONE);
        Intent intent = new Intent(this, AdminActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
