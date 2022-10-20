package com.example.enjelwater.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.enjelwater.Model.CartModel;
import com.example.enjelwater.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MySummaryAdapter extends RecyclerView.Adapter<MySummaryAdapter.MySummaryHolder> {

    private Context context;
    private List<CartModel> cartModelList;

    public MySummaryAdapter(Context context, List<CartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
    }


    @NonNull
    @Override
    public MySummaryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MySummaryHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_summary_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MySummaryHolder holder, int position) {
        Glide.with(context)
                .load(cartModelList.get(position).getImage())
                .into(holder.imageView);
        holder.txtPrice.setText(new StringBuilder("Price: ₱").append(cartModelList.get(position).getPrice()));
        holder.txtName.setText(new StringBuilder().append(cartModelList.get(position).getName()));
        holder.txtQuantity.setText(new StringBuilder("x").append(cartModelList.get(position).getQuantity() ));
        holder.txtTotal.setText(new StringBuilder("₱").append(cartModelList.get(position).getTotalPrice() ));
    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class MySummaryHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageViewSummary)
        ImageView imageView;
        @BindView(R.id.txtNameSummary)
        TextView txtName;
        @BindView(R.id.txtPriceSummary)
        TextView txtPrice;
        @BindView(R.id.txtQuantitySummary)
        TextView txtQuantity;
        @BindView(R.id.txtTotalSummary)
        TextView txtTotal;

        Unbinder unbinder;

        public MySummaryHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
        }
    }
}
