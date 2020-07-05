package com.example.qrapp.ui.admin.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.qrapp.R;
import com.example.qrapp.data.OrderHistoryProduct;
import com.example.qrapp.databinding.ItemOrderDetailBinding;

import java.text.DecimalFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder> {
    private Context context;
    private List<OrderHistoryProduct> products;

    public OrderDetailAdapter(Context context, List<OrderHistoryProduct> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderDetailViewHolder(ItemOrderDetailBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailViewHolder holder, int position) {
        holder.binding.tvName.setText(products.get(position).getName());
        DecimalFormat df = new DecimalFormat("#,###,###");
        String p = df.format(products.get(position).getPrice());
        holder.binding.tvQuantity.setText(context.getString(R.string.price_detail, p, products.get(position).getQuantity()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class OrderDetailViewHolder extends RecyclerView.ViewHolder{
        ItemOrderDetailBinding binding;
        public OrderDetailViewHolder(@NonNull ItemOrderDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
