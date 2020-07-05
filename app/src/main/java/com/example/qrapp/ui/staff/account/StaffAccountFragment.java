package com.example.qrapp.ui.staff.account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.qrapp.API;
import com.example.qrapp.APIService;
import com.example.qrapp.R;
import com.example.qrapp.data.UpdatePasswordRequest;
import com.example.qrapp.databinding.FragmentStaffAccountBinding;
import com.example.qrapp.ui.login.MainActivity;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class StaffAccountFragment extends Fragment {


    private FragmentStaffAccountBinding binding;
    SharedPreferences preferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStaffAccountBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = view.getContext();
        preferences = context.getSharedPreferences("QRApp", Context.MODE_PRIVATE);
        String username = preferences.getString("username", "No Name");
        binding.tvName.setText(username);
        String url = "https://ui-avatars.com/api/?background=0D8ABC&color=fff&name=" + username;
        Glide.with(context).load(url).into(binding.imgAvatar);

        String token = preferences.getString("access_token", "");
        String id = preferences.getString("id", "");
        if (TextUtils.isEmpty(token) || TextUtils.isEmpty(id)) {
            hideForm();
        }
        binding.cbEnabled.setOnCheckedChangeListener((buttonView, isChecked) -> enableForm(isChecked));
        binding.btnChangePassword.setOnClickListener(v -> onChangePassword());
        binding.tvLogout.setOnClickListener(v -> logout());
    }

    private void enableForm(boolean enabled) {
        binding.edtNewPassword.setEnabled(enabled);
        binding.edtOldPassword.setEnabled(enabled);
        binding.btnChangePassword.setEnabled(enabled);
        binding.edtNewPassword.setText("");
        binding.edtOldPassword.setText("");
    }

    private void onChangePassword() {
        String oldPassword = binding.edtOldPassword.getText().toString();
        String newPassword = binding.edtNewPassword.getText().toString();
        APIService service = API.getInstance().getApiService();
        String token = preferences.getString("access_token", "");
        String id = preferences.getString("id", "");
        UpdatePasswordRequest request = new UpdatePasswordRequest(id, oldPassword, newPassword);
        service.changePassword(token, request).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                handleResponse(response);
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {

            }
        });
    }

    private void handleResponse(Response<Map<String, String>> response) {
        if (response.body() == null) {
            Toast.makeText(requireContext(), "Something wen't wrong", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!response.isSuccessful()) {
            Toast.makeText(requireContext(), response.body().get("message"), Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(requireContext(), "Password has been updated successfully", Toast.LENGTH_SHORT).show();
    }

    private void hideForm() {
        binding.cbEnabled.setVisibility(View.GONE);
        binding.btnChangePassword.setVisibility(View.GONE);
        binding.edtNewPassword.setVisibility(View.GONE);
        binding.edtOldPassword.setVisibility(View.GONE);
    }

    private void logout() {
        preferences.edit().clear().apply();
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
