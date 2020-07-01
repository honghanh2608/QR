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

import com.example.qrapp.Callback;
import com.example.qrapp.R;
import com.example.qrapp.data.Property;
import com.example.qrapp.databinding.FragmentNewProductBinding;
import com.example.qrapp.dialog.PropertiesDialog;
import com.google.gson.Gson;

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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.category_array, android.R.layout.simple_spinner_item);
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
                Toast.makeText(getContext(), binding.spnCategory.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.edtProperty.setOnClickListener(v -> addProperties());
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
    public void showDialogSucess() {

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
            binding.edtProperty.setText(new Gson().toJson(properties));
        });
        dialog.show();
    }
}
