package com.example.qrapp.ui.admin.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qrapp.R;
import com.example.qrapp.data.OrderHistory;
import com.example.qrapp.data.OrderHistoryProduct;
import com.example.qrapp.databinding.ItemAdminOrderBinding;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderViewHolder> {

    private Context context;
    private List<OrderHistory> orders;

    public OrderHistoryAdapter(Context context, List<OrderHistory> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_admin_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {

        private ItemAdminOrderBinding binding;
        OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemAdminOrderBinding.bind(itemView);
        }

        void bind(int i) {
            OrderHistory order = orders.get(i);
            bindDate(order.getId());
            bindValue(order.getProducts());
        }

        private void bindDate(long timestamp) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timestamp);
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm   dd MMM", Locale.getDefault());
            binding.tvTime.setText(sdf.format(calendar.getTime()));
        }

        private void bindValue(List<OrderHistoryProduct> products) {
            long value = 0;
            for (OrderHistoryProduct item: products) {
                value += item.getQuantity() * item.getPrice();
            }
            DecimalFormat df = new DecimalFormat("###,###");
            binding.tvValue.setText(df.format(value));
        }
    }
}
