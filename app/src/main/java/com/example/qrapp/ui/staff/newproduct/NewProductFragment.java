package com.example.qrapp.ui.staff.newproduct;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Outline;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.qrapp.Callback;
import com.example.qrapp.R;
import com.example.qrapp.data.Category;
import com.example.qrapp.data.Product;
import com.example.qrapp.data.Property;
import com.example.qrapp.databinding.FragmentNewProductBinding;
import com.example.qrapp.dialog.LoadingDialog;
import com.example.qrapp.dialog.PropertiesDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewProductFragment extends Fragment implements Contract.View {
    private FragmentNewProductBinding binding;
    private Presenter presenter = new Presenter();
    private View rootView;
    private List<Property> properties = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();
    private String barcode;
    private int selectedCategoryId;
    private LoadingDialog loadingDialog;

    public NewProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewProductBinding.inflate(inflater, container, false);
        presenter.attachView(this);
        if (rootView == null) {
            rootView = binding.getRoot();
        }
        //
        getCategories();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadingDialog = new LoadingDialog(view.getContext());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1) {
            @Nullable
            @Override
            public String getItem(int position) {
                return categories.get(position).getName();
            }

            @Override
            public int getCount() {
                return categories.size();
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spnCategory.setAdapter(adapter);
        binding.llActionBar.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRect(-100, -15, view.getWidth() + 100, view.getHeight());
            }
        });
        binding.spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategoryId = categories.get(position).getId();
                Toast.makeText(getContext(), binding.spnCategory.getId() + "", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.tvAddProperty.setOnClickListener(v -> addProperties());

        binding.imgScan.setOnClickListener(v -> startScanBarcodeFragment());

        binding.btnSave.setOnClickListener(v -> createNewProduct());
    }

    private void resetFragment(){
        binding.edtName.setText("");
        binding.edtPrice.setText("");
        binding.edtManufacturer.setText("");
        binding.edtEXP.setText("");
        binding.edtMFG.setText("");
        binding.edtCount.setText("");
        binding.tvProperty.setText("");
        binding.tvBarcode.setText("");
    }

    private String getJSONFromAsset() {
        String json = null;
        try {
            InputStream inputStream = getActivity().getAssets().open("categories.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    private void getCategories() {
        String jsonFileString = getJSONFromAsset();
        Type listCategoriesType = new TypeToken<List<Category>>() {
        }.getType();
        Gson gson = new Gson();
        categories = gson.fromJson(jsonFileString, listCategoriesType);
    }

    public void createNewProduct() {
        if (TextUtils.isEmpty(barcode)) {
            new AlertDialog.Builder(requireContext())
                    .setMessage("Quét mã barcode trước khi thêm sản phẩm")
                    .setNegativeButton("OK", (dialog, which) -> {
                        dialog.cancel();
                    }).show();
            return;
        }

        String name = binding.edtName.getText().toString();
        String manufacturer = binding.edtManufacturer.getText().toString();
        String mfg = binding.edtMFG.getText().toString();
        String exp = binding.edtEXP.getText().toString();
        int count = Integer.parseInt(binding.edtCount.getText().toString().trim());
        long price = Long.parseLong(binding.edtPrice.getText().toString().trim());
        Product product = new Product();
        product.setName(name);
        product.setProperties(new Gson().toJson(properties));
        product.setManufacturer(manufacturer);
        product.setMfg(mfg);
        product.setExp(exp);
        product.setCount(count);
        product.setPrice(price);
        product.setBarcode(barcode);
        product.setCategoryId(selectedCategoryId);
        presenter.createProduct(product);

    }

    @Override
    public void showErr(String mess) {
        new AlertDialog.Builder(requireContext())
                .setMessage(mess)
                .setNegativeButton("OK", (dialog, which) -> {
                    dialog.cancel();
                }).show();
    }

    @Override
    public void showDialogSuccess() {
        new AlertDialog.Builder(requireContext())
                .setMessage("Thêm sản phẩm thành công")
                .setNegativeButton("OK", (dialog, which) -> {
                    resetFragment();
                    dialog.cancel();
                }).show();
    }

    private void startScanBarcodeFragment() {
        Intent intent = new Intent(getContext(), ScanActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void addProperties() {
        Context context = getContext();
        if (context == null) return;
        PropertiesDialog dialog = new PropertiesDialog(context, properties);
        dialog.setCallback(data -> {
            if (data == null) return;
            properties.clear();
            properties.addAll(data);
            binding.tvProperty.setText(new Gson().toJson(properties));
        });
        dialog.show();
    }

    @Override
    public void showLoading() {
        loadingDialog.show();
    }

    @Override
    public void hideLoading() {
        loadingDialog.cancel();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            barcode = data.getStringExtra("barcode");
            binding.tvBarcode.setText(barcode);
        }
    }
}
