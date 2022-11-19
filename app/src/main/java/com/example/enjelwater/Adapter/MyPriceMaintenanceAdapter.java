package com.example.enjelwater.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.pdf.parser.Line;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyPriceMaintenanceAdapter extends RecyclerView.Adapter<MyPriceMaintenanceAdapter.MyDrinkViewHolder> {

    private Context context;
    private List<DrinkModel> drinkModelList;
    Dialog dialog;

    DatabaseReference databaseReference;



    public MyPriceMaintenanceAdapter(Context context, List<DrinkModel> drinkModelList) {
        this.context = context;
        this.drinkModelList = drinkModelList;
    }

    @NonNull
    @Override
    public MyDrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyDrinkViewHolder(LayoutInflater.from(context)
        .inflate(R.layout.layout_price_product_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyDrinkViewHolder holder, int position) {
        dialog=new Dialog(context);


        Glide.with(context)
                .load(drinkModelList.get(position).getImage())
                .into(holder.imageView);
        holder.txtPrice.setText(new StringBuilder("₱").append(drinkModelList.get(position).getPrice()).append(".00"));
        holder.txtName.setText(new StringBuilder().append(drinkModelList.get(position).getName()));
        holder.txtKey.setText(new StringBuilder().append(drinkModelList.get(position).getKey()));
        holder.txtStocks.setText(new StringBuilder("Stocks: ").append(drinkModelList.get(position).getStocks()));

        if(holder.txtStocks.getText().toString().equals("Stocks: null")){
            holder.txtStocks.setVisibility(View.GONE);
        }else{
            holder.txtStocks.setVisibility(View.VISIBLE);
        }
        if(holder.txtStocks.getText().toString().equals("Stocks: 0")){
            holder.backgr.setBackgroundColor(Color.RED);
        }else if(holder.txtStocks.getText().toString().equals("Stocks: 1")||holder.txtStocks.getText().toString().equals("Stocks: 2")||holder.txtStocks.getText().toString().equals("Stocks: 3")||holder.txtStocks.getText().toString().equals("Stocks: 4")||holder.txtStocks.getText().toString().equals("Stocks: 5")){
            holder.backgr.setBackgroundColor(Color.parseColor("#FFA500"));
        }else if(holder.txtStocks.getText().toString().equals("Stocks: 6")||holder.txtStocks.getText().toString().equals("Stocks: 7")||holder.txtStocks.getText().toString().equals("Stocks: 8")||holder.txtStocks.getText().toString().equals("Stocks: 9")||holder.txtStocks.getText().toString().equals("Stocks: 10")){
            holder.backgr.setBackgroundColor(Color.YELLOW);
        }
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.edit_price_product_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCanceledOnTouchOutside(false);

                LinearLayout layout = dialog.findViewById(R.id.stockslayout);
                Button btnok = dialog.findViewById(R.id.btn_edit_confirm);
                Button notyet = dialog.findViewById(R.id.btn_notyet_edit);
                EditText productname = dialog.findViewById(R.id.txtEditProductName);
                EditText price = dialog.findViewById(R.id.txtEditPrice);
                EditText stocks = dialog.findViewById(R.id.txtEditStocks);

                productname.setText(holder.txtName.getText().toString());
                price.setText(new StringBuilder().append(drinkModelList.get(holder.getAdapterPosition()).getPrice()));
                stocks.setText(new StringBuilder().append(drinkModelList.get(holder.getAdapterPosition()).getStocks()));


                if(stocks.getText().toString().equals("null")){
                    layout.setVisibility(View.GONE);
                }else{
                    layout.setVisibility(View.VISIBLE);
                }


                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(productname.getText().toString().trim().isEmpty()&&price.getText().toString().trim().isEmpty()&&stocks.getText().toString().trim().isEmpty()){
                            productname.setError("Must not be Empty");
                            stocks.setError("Must not be Empty");
                            price.setError("Must not be Empty");
                        }else if(productname.getText().toString().trim().isEmpty()&&price.getText().toString().trim().isEmpty()){
                            productname.setError("Must not be Empty");
                            price.setError("Must not be Empty");
                        }else if(price.getText().toString().trim().isEmpty()&&stocks.getText().toString().trim().isEmpty()){
                            stocks.setError("Must not be Empty");
                            price.setError("Must not be Empty");
                        }else if(productname.getText().toString().trim().isEmpty()&&stocks.getText().toString().trim().isEmpty()){
                            stocks.setError("Must not be Empty");
                            productname.setError("Must not be Empty");
                        }else if(productname.getText().toString().trim().isEmpty()){
                            productname.setError("Must not be Empty");
                        }else if(price.getText().toString().trim().isEmpty()){
                            price.setError("Must not be Empty");
                        }else if(stocks.getText().toString().trim().isEmpty()){
                            stocks.setError("Must not be Empty");
                        }else{
                            productname.setError(null);
                            price.setError(null);
                            stocks.setError(null);
                            DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Drink");
                            reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String key = snapshot.child(holder.txtKey.getText().toString()).child("key").getValue(String.class);

                                    if(key == null){
                                        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("NewDrink");
                                        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                reference2.child(holder.txtKey.getText().toString()).child("name").setValue(productname.getText().toString().trim());
                                                reference2.child(holder.txtKey.getText().toString()).child("price").setValue(price.getText().toString().trim());
                                                reference2.child(holder.txtKey.getText().toString()).child("stocks").setValue(stocks.getText().toString().trim());

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }else{
                                        reference2.child(holder.txtKey.getText().toString()).child("name").setValue(productname.getText().toString().trim());
                                        reference2.child(holder.txtKey.getText().toString()).child("price").setValue(price.getText().toString().trim());

                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            dialog.dismiss();
                        }


                    }
                });
                notyet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.remove_product_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCanceledOnTouchOutside(false);

                Button btnok = dialog.findViewById(R.id.btn_remove);
                Button notyet = dialog.findViewById(R.id.btn_dont_remove);

                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Drink");
                        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String key = snapshot.child(holder.txtKey.getText().toString()).child("key").getValue(String.class);

                                if(key == null){
                                    DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("NewDrink");
                                    reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                            reference2.child(holder.txtKey.getText().toString()).removeValue();
                                            notifyDataSetChanged();

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }else{
                                    reference2.child(holder.txtKey.getText().toString()).removeValue();
                                    notifyDataSetChanged();

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        dialog.dismiss();
                    }
                });
                notyet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });

    }


    @Override
    public int getItemCount() {
        return drinkModelList.size();
    }

    public class MyDrinkViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtPrice)
        TextView txtPrice;
        @BindView(R.id.txtStocks)
        TextView txtStocks;
        @BindView(R.id.key)
        TextView txtKey;
        @BindView(R.id.edit_price_product)
        Button btnEdit;
        @BindView(R.id.delete_product)
        Button btnRemove;
        @BindView(R.id.background)
        LinearLayout backgr;


        Unbinder unbinder;
        public MyDrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
        }

    }
}
