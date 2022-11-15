package com.example.enjelwater.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.enjelwater.EventBus.MyUpdateCartEvent;
import com.example.enjelwater.Listener.ICartLoadListener;
import com.example.enjelwater.Listener.IRecyclerViewClickListener;
import com.example.enjelwater.Model.CartModel;
import com.example.enjelwater.Model.DrinkModel;
import com.example.enjelwater.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyDrinkAdapter extends RecyclerView.Adapter<MyDrinkAdapter.MyDrinkViewHolder> {

    private Context context;
    private List<DrinkModel> drinkModelList;
    private ICartLoadListener iCartLoadListener;
    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();


    public MyDrinkAdapter(Context context, List<DrinkModel> drinkModelList, ICartLoadListener iCartLoadListener) {
        this.context = context;
        this.drinkModelList = drinkModelList;
        this.iCartLoadListener = iCartLoadListener;
    }

    @NonNull
    @Override
    public MyDrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyDrinkViewHolder(LayoutInflater.from(context)
        .inflate(R.layout.layout_drink_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyDrinkViewHolder holder, int position) {
        Glide.with(context)
                .load(drinkModelList.get(position).getImage())
                .into(holder.imageView);
        holder.txtPrice.setText(new StringBuilder("₱").append(drinkModelList.get(position).getPrice()).append(".00"));
        holder.txtName.setText(new StringBuilder().append(drinkModelList.get(position).getName()));
        holder.txtStock.setText(new StringBuilder("Stock: ").append(drinkModelList.get(position).getStocks()));

        if(drinkModelList.get(holder.getAdapterPosition()).getStocks()==null){
            holder.txtStock.setVisibility(View.GONE);
        }else{
            holder.txtStock.setVisibility(View.VISIBLE);
            if(drinkModelList.get(holder.getAdapterPosition()).getStocks().equals("0")){
                holder.txtStock.setText("No STOCKS");
            }
        }

        holder.setListener((view, adapterPosition) -> {
            if (drinkModelList.get(holder.getAdapterPosition()).getStocks() == null){
                addToCart(drinkModelList.get(position));
            }else if(drinkModelList.get(holder.getAdapterPosition()).getStocks().equals("0")){
                Toast.makeText(context, "OUT OF STOCK!", Toast.LENGTH_SHORT).show();
            }else{
                addToCart(drinkModelList.get(position));
            }

        });

    }

    private void addToCart(DrinkModel drinkModel) {
        DatabaseReference userCart = FirebaseDatabase.getInstance().getReference("Cart");
        DatabaseReference key = userCart.child(currentuser);

        key.child(drinkModel.getKey())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists()) {
                            CartModel cartModel = snapshot.getValue(CartModel.class);
                            cartModel.setQuantity(cartModel.getQuantity()+1);
                            Map<String,Object> updateData = new HashMap<>();
                            updateData.put("quantity",cartModel.getQuantity());
                            updateData.put("totalPrice",cartModel.getQuantity()*Float.parseFloat(cartModel.getPrice()));

                            key.child(drinkModel.getKey())
                                    .updateChildren(updateData)
                                    .addOnSuccessListener(aVoid -> {
                                        iCartLoadListener.onCartLoadFailed("Add To Cart Success");
                                    })
                            .addOnFailureListener(e -> iCartLoadListener.onCartLoadFailed(e.getMessage()));


                        }else{
                            CartModel cartModel = new CartModel();
                            cartModel.setName(drinkModel.getName());
                            cartModel.setImage(drinkModel.getImage());
                            cartModel.setKey(drinkModel.getKey());
                            cartModel.setPrice(drinkModel.getPrice());
                            cartModel.setQuantity(1);
                            cartModel.setTotalPrice(Float.parseFloat(drinkModel.getPrice()));

                                key.child(drinkModel.getKey())
                                    .setValue(cartModel)
                                    .addOnSuccessListener(aVoid -> {
                                        iCartLoadListener.onCartLoadFailed("Add To Cart Success");
                                    })
                                    .addOnFailureListener(e -> iCartLoadListener.onCartLoadFailed(e.getMessage()));

                        }
                        EventBus.getDefault().postSticky(new MyUpdateCartEvent());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        iCartLoadListener.onCartLoadFailed(error.getMessage());
                    }
                });

    }

    @Override
    public int getItemCount() {
        return drinkModelList.size();
    }

    public class MyDrinkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtPrice)
        TextView txtPrice;
        @BindView(R.id.txtStock)
        TextView txtStock;

        IRecyclerViewClickListener listener;

        public void setListener(IRecyclerViewClickListener listener) {
            this.listener = listener;
        }

        private Unbinder unbinder;
        public MyDrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onRecycleClick(v,getAdapterPosition());
        }
    }
}
