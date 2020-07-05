package com.example.qrapp.ui.admin.staff;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.SwipeLayout;
import com.example.qrapp.OnItemClickListener;
import com.example.qrapp.R;
import com.example.qrapp.data.User;
import com.example.qrapp.databinding.ItemSwipeStaffBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.StaffViewHolder> {
    private Context context;
    private List<User> staffs;
    private OnItemClickListener onItemClickListener;

    public StaffAdapter(Context context, List<User> staffs) {
        this.context = context;
        this.staffs = staffs;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public StaffViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_swipe_staff, parent, false);
        return new StaffViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StaffViewHolder holder, int position) {
        holder.binding.tvNumber.setText(position+1+"");
        holder.binding.tvName.setText(staffs.get(position).getName());
        holder.binding.tvEmail.setText(staffs.get(position).getEmail());

        holder.binding.swipe.setShowMode(SwipeLayout.ShowMode.PullOut);
        //drag from left
 //       holder.binding.swipe.addDrag(SwipeLayout.DragEdge.Left, holder.binding.bottomWrapper);
        holder.binding.bottomWrapper.setOnClickListener(v -> {
            onItemClickListener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        //chay thu di
        //alo alo oke oke
        return staffs.size(); //day ne a quen.hq chua sua :v
    }

    public static class StaffViewHolder extends RecyclerView.ViewHolder{
        private ItemSwipeStaffBinding binding;

        public StaffViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemSwipeStaffBinding.bind(itemView);
        }
    }
}

