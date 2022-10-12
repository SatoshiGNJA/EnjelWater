package com.example.enjelwater.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.enjelwater.EventBus.MyUpdateCartEvent;
import com.example.enjelwater.Model.CartModel;
import com.example.enjelwater.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.MyCartViewHolder> {

    private Context context;
    private List<CartModel> cartModelList;
    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public MyCartAdapter(Context context, List<CartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
    }

    @NonNull
    @Override
    public MyCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyCartViewHolder(LayoutInflater.from(context)
        .inflate(R.layout.layout_cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartViewHolder holder, int position) {
        Glide.with(context)
                .load(cartModelList.get(position).getImage())
                .into(holder.imageView);
        holder.txtPrice.setText(new StringBuilder("Price: ₱").append(cartModelList.get(position).getPrice()));
        holder.txtName.setText(new StringBuilder().append(cartModelList.get(position).getName()));
        holder.txtQuantity.setText(new StringBuilder().append(cartModelList.get(position).getQuantity() ));
        holder.txtTotal.setText(new StringBuilder("₱").append(cartModelList.get(position).getTotalPrice() ));

        holder.btnMinus.setOnClickListener(view -> {
            minusCartItem(holder,cartModelList.get(position));
        });
        holder.btnPlus.setOnClickListener(view -> {
            plusCartItem(holder,cartModelList.get(position));
        });
        holder.btnDelete.setOnClickListener(view -> {
            deleteall(holder,cartModelList.get(position));
            deleteFromFirebase(cartModelList.get(position));
            cartModelList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,cartModelList.size());
        });

    }

    private void deleteFromFirebase(CartModel cartModel) {
        FirebaseDatabase.getInstance()
                .getReference("Cart")
                .child(currentuser)
                .child(cartModel.getKey())
                .removeValue()
                .addOnSuccessListener(aVoid ->  EventBus.getDefault().postSticky(new MyUpdateCartEvent()));

    }

    private void plusCartItem(MyCartViewHolder holder, CartModel cartModel) {
        cartModel.setQuantity(cartModel.getQuantity()+1);
        cartModel.setTotalPrice(cartModel.getTotalPrice());
        cartModel.setPrice(cartModel.getPrice());
        cartModel.setTotalPrice(cartModel.getQuantity()*Float.parseFloat(cartModel.getPrice()));
        holder.txtQuantity.setText(new StringBuilder().append(cartModel.getQuantity()));
        holder.txtTotal.setText(new StringBuilder("₱").append(cartModel.getTotalPrice()));
        holder.txtPrice.setText(new StringBuilder("Price: ₱").append(cartModel.getPrice()));
        updateFirebase(cartModel);
    }

    private void minusCartItem(MyCartViewHolder holder, CartModel cartModel) {
        if(cartModel.getQuantity() > 1){
            cartModel.setQuantity(cartModel.getQuantity()-1);
            cartModel.setTotalPrice(cartModel.getTotalPrice());
            cartModel.setPrice(cartModel.getPrice());
            cartModel.setTotalPrice(cartModel.getQuantity()*Float.parseFloat(cartModel.getPrice()));

            holder.txtQuantity.setText(new StringBuilder().append(cartModel.getQuantity()));
            holder.txtTotal.setText(new StringBuilder("₱").append(cartModel.getTotalPrice()));
            holder.txtPrice.setText(new StringBuilder("Price: ₱").append(cartModel.getPrice()));
            updateFirebase(cartModel);

        }

    }

    private void deleteall(MyCartViewHolder holder, CartModel cartModel) {
            cartModel.setQuantity(0);
            cartModel.setTotalPrice(cartModel.getTotalPrice());
            cartModel.setPrice(cartModel.getPrice());
            cartModel.setTotalPrice(cartModel.getQuantity()*Float.parseFloat(cartModel.getPrice()));

            holder.txtQuantity.setText(new StringBuilder().append(cartModel.getQuantity()));
            holder.txtTotal.setText(new StringBuilder("₱").append(cartModel.getTotalPrice()));
            holder.txtPrice.setText(new StringBuilder("Price: ₱").append(cartModel.getPrice()));
            updateFirebase(cartModel);

        }


    private void updateFirebase(CartModel cartModel) {
        FirebaseDatabase.getInstance()
                .getReference("Cart")
                .child(currentuser)
                .child(cartModel.getKey())
                .setValue(cartModel)
                .addOnSuccessListener(aVoid ->  EventBus.getDefault().postSticky(new MyUpdateCartEvent()));



    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class MyCartViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.btnMinus)
        ImageView btnMinus;
        @BindView(R.id.btnPlus)
        ImageView btnPlus;
        @BindView(R.id.btnDelete)
        ImageView btnDelete;
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtPrice)
        TextView txtPrice;
        @BindView(R.id.txtQuantity)
        TextView txtQuantity;
        @BindView(R.id.txtTotal)
        TextView txtTotal;

        Unbinder unbinder;

        public MyCartViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
        }
    }
}
