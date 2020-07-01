package com.example.qrapp.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qrapp.OnItemClickListener;
import com.example.qrapp.R;
import com.example.qrapp.data.Property;
import com.example.qrapp.databinding.ItemPropertyBinding;

import java.util.List;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder> {

    private Context context;
    private List<Property> properties;

    private OnItemClickListener onDeleteItemClickListener;

    public PropertyAdapter(Context context, List<Property> properties) {
        this.context = context;
        this.properties = properties;
    }

    public void setOnDeleteItemClickListener(OnItemClickListener onDeleteItemClickListener) {
        this.onDeleteItemClickListener = onDeleteItemClickListener;
    }

    @NonNull
    @Override
    public PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_property, parent, false);
        return new PropertyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return properties.size();
    }

    class PropertyViewHolder extends RecyclerView.ViewHolder {
        private ItemPropertyBinding binding;

        PropertyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemPropertyBinding.bind(itemView);
        }

        void bind(int i) {
            binding.tvName.setText(properties.get(i).getName());
            binding.tvValue.setText(properties.get(i).getValue());
            binding.imgDelete.setOnClickListener(v -> {
                if (onDeleteItemClickListener != null) onDeleteItemClickListener.onItemClick(i);
            });
        }
    }
}
