package com.example.qrapp.ui.staff.newproduct;

import android.content.Context;
import android.graphics.Outline;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.qrapp.Callback;
import com.example.qrapp.R;
import com.example.qrapp.data.Category;
import com.example.qrapp.data.Product;
import com.example.qrapp.data.Property;
import com.example.qrapp.databinding.FragmentNewProductBinding;
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
    private Product product = new Product();

    public NewProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewProductBinding.inflate(inflater, container, false);
        presenter.attachView(this);
        if (rootView == null){
            rootView = binding.getRoot();
        }
        //
        getCategories();
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spnCategory.setAdapter(adapter);
        binding.llActionBar.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRect(-100, -15, view.getWidth()+100, view.getHeight());
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), binding.spnCategory.getId()+"", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.tvAddProperty.setOnClickListener(v -> addProperties());

        binding.imgScan.setOnClickListener(v -> startScanBarcodeFragment());
    }

    private String getJSONFromAsset(){
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
        Type listCategoriesType = new TypeToken<List<Category>>(){}.getType();
        Gson gson = new Gson();
        categories = gson.fromJson(jsonFileString, listCategoriesType);
    }

    public void createNewProduct(){
        String name = binding.edtName.getText().toString();
        String manufacturer = binding.edtManufacturer.getText().toString();
        String mfg = binding.edtMFG.getText().toString();
        String exp = binding.edtEXP.getText().toString();
//        int count = binding.edtCount.getText();
    }

    @Override
    public void showErr(String mess) {

    }

    @Override
    public void showDialogSuccess() {

    }

    private void startScanBarcodeFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ScanBarcodeFragment scanBarcodeFragment = new ScanBarcodeFragment();
        fragmentTransaction.replace(R.id.container, scanBarcodeFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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
}
