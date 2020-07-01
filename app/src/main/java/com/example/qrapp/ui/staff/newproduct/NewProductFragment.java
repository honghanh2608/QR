package com.example.qrapp.ui.staff.newproduct;

import android.graphics.Outline;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.qrapp.R;
import com.example.qrapp.databinding.FragmentNewProductBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewProductFragment extends Fragment implements Contract.View {
    private FragmentNewProductBinding binding;
    private Presenter presenter = new Presenter();
    private View rootView;

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
}
