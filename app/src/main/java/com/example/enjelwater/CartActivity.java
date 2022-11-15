package com.example.enjelwater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enjelwater.Adapter.MyCartAdapter;
import com.example.enjelwater.EventBus.MyUpdateCartEvent;
import com.example.enjelwater.Listener.ICartLoadListener;
import com.example.enjelwater.Model.CartModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends AppCompatActivity implements ICartLoadListener {



    @BindView(R.id.recycler_cart)
    RecyclerView recyclerCart;
    @BindView(R.id.mainLayout)
    RelativeLayout cartML;
    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.txtTotal)
    TextView txtTotal;
    @BindView(R.id.placeorder)
    Button placeorders;
    @BindView(R.id.cartempty)
    ImageView crtty;
    @BindView(R.id.emptycarts)
    TextView cty;

    String stock5,stock6,stock7,stock8;
    int CartQTY5,CartQTY6,CartQTY7,CartQTY8;
    DatabaseReference NewDrink,CartQTY;

    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

    ICartLoadListener cartLoadListener;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onStop() {
        if(EventBus.getDefault().hasSubscriberForEvent(MyUpdateCartEvent.class))
            EventBus.getDefault().removeStickyEvent(MyUpdateCartEvent.class);
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onUpdateCart(MyUpdateCartEvent event){
        loadCartFromFirebase();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);

        NewDrink = FirebaseDatabase.getInstance().getReference("NewDrink");
        NewDrink.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                stock5 = snapshot.child("05").child("stocks").getValue(String.class);
                stock6 = snapshot.child("06").child("stocks").getValue(String.class);
                stock7 = snapshot.child("07").child("stocks").getValue(String.class);
                stock8 = snapshot.child("08").child("stocks").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        CartQTY = FirebaseDatabase.getInstance().getReference("Cart").child(currentuser);
        CartQTY.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try{
                    CartQTY5 = snapshot.child("05").child("quantity").getValue(Integer.class);
                }catch (Exception e){
                    System.out.println(e);
                }
                try{
                    CartQTY6 = snapshot.child("06").child("quantity").getValue(Integer.class);
                }catch (Exception e){
                    System.out.println(e);
                }
                try{
                    CartQTY7 = snapshot.child("07").child("quantity").getValue(Integer.class);
                }catch (Exception e){
                    System.out.println(e);
                }
                try{
                    CartQTY8 = snapshot.child("08").child("quantity").getValue(Integer.class);
                }catch (Exception e){
                    System.out.println(e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });

        init();
        loadCartFromFirebase();
        TotalPrice();
        content();

    }
    public void content(){

        refresh(500);
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("Cart").child(currentuser);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long pluscount = dataSnapshot.getChildrenCount();
                placeorders.setText("Check Out (" + pluscount + ")");
                if(pluscount==0){
                    crtty.setVisibility(View.VISIBLE);
                    cty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        };
        usersRef.addListenerForSingleValueEvent(valueEventListener);
    }
    private  void refresh(int milliseconds){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                content();
            }
        };

        handler.postDelayed(runnable,milliseconds);

    }

    private void TotalPrice(){
        List<CartModel> cartModels = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("Cart")
                .child(currentuser)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        cartModels.clear();
                        Double total = 0.0;
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                CartModel cartModel = ds.getValue(CartModel.class);
                                Double cost = Double.valueOf(cartModel.getTotalPrice());
                                total = total + cost;
                                txtTotal.setText(new StringBuilder("Total Price: ₱").append(String.format("%.2f", total)));
                                placeorders.setEnabled(total != 0.0);
                            }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void loadCartFromFirebase() {
        List<CartModel> cartModels = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("Cart")
                .child(currentuser)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot cartSnapshot:snapshot.getChildren()){
                                CartModel cartModel = cartSnapshot.getValue(CartModel.class);
                                cartModel.setKey(cartSnapshot.getKey());
                                cartModels.add(cartModel);
                            }
                            crtty.setVisibility(View.GONE);
                            cty.setVisibility(View.GONE);
                            cartLoadListener.onCartLoadSuccess(cartModels);
                        }else{
                            crtty.setVisibility(View.VISIBLE);
                            cty.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        cartLoadListener.onCartLoadFailed(error.getMessage());

                    }
                });
    }

    private void init(){
        ButterKnife.bind(this);

        cartLoadListener = this;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerCart.setLayoutManager(layoutManager);
        recyclerCart.addItemDecoration(new DividerItemDecoration(this,layoutManager.getOrientation()));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        placeorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int st5 = Integer.parseInt(stock5);
                int st6 = Integer.parseInt(stock6);
                int st7 = Integer.parseInt(stock7);
                int st8 = Integer.parseInt(stock8);
                if(st5>=CartQTY5&&st6>=CartQTY6&&st7>=CartQTY7&&st8>=CartQTY8){
                    Intent intent = new Intent(getApplicationContext(),SummaryActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }else{
                    Toast.makeText(CartActivity.this, "Some of your Item has Reach it Limit Please Check your Orders!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onCartLoadSuccess(List<CartModel> cartModelList) {
        MyCartAdapter adapter = new MyCartAdapter(this,cartModelList);
        recyclerCart.setAdapter(adapter);
    }

    @Override
    public void onCartLoadFailed(String message) {

        Snackbar.make(cartML,message,Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}