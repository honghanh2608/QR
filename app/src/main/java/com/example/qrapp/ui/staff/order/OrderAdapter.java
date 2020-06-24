package com.example.qrapp.ui.staff.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.qrapp.R;
import com.example.qrapp.data.OrderItem;
import com.example.qrapp.databinding.ItemOrderBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{
    private Context context;
    private List<OrderItem> orderItems;

    public OrderAdapter(Context context, List<OrderItem> orderItems){
        this.context = context;
        this.orderItems = orderItems;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(ItemOrderBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.binding.tvName.setText(orderItems.get(position).getName());
        holder.binding.tvQuantity.setText(orderItems.get(position).getQuantity() + "");
//        if (position%2 == 1){
//            holder.binding.tvName.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGreyish));
//            holder.binding.tvQuantity.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGreyish));
//        }
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder{
        ItemOrderBinding binding;

        public OrderViewHolder(@NonNull ItemOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
