package com.example.enjelwater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enjelwater.Adapter.MySummaryAdapter;
import com.example.enjelwater.Listener.ICartLoadListener;
import com.example.enjelwater.Model.CartModel;
import com.example.enjelwater.Model.ProductModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SummaryActivity extends AppCompatActivity implements ICartLoadListener {

    @BindView(R.id.txtTotalPlace)
    TextView TotalPrice;
    @BindView(R.id.checkoutName)
    TextView FName;
    @BindView(R.id.txtphonenumber)
    TextView phone;

    ProgressBar progressbbar;

    ImageView btnBackSum;
    TextInputEditText DeliveryAdd;
    ICartLoadListener cartLoadListener;
    DatabaseReference databaseReference,reff,reff2,reffUser;
    Button btnCheckOut;
    FirebaseAuth auth;
    FirebaseUser user;
    long maxid=0;
    long usermaxid=0;
    String key,userKey;
    ProductModel productModel;
    CartModel cartModel;
    RadioGroup radioGroup;
    RadioButton radioone,radiotwo;
    Dialog dialog;

    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @BindView(R.id.mainLayout3)
    RelativeLayout summaryML;
    @BindView(R.id.recycler_summary)
    RecyclerView recyclerSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        progressbbar = findViewById(R.id.placeprogress);
        FName = findViewById(R.id.checkoutName);
        user = FirebaseAuth.getInstance().getCurrentUser();
        auth = FirebaseAuth.getInstance();
        btnBackSum = findViewById(R.id.btnBackSum);
        btnCheckOut = findViewById(R.id.checkout);
        DeliveryAdd = findViewById(R.id.DAddress);
        radioGroup = findViewById(R.id.radio_group);
        radioone = findViewById(R.id.radio1);
        radiotwo = findViewById(R.id.radio2);
        productModel=new ProductModel();
        cartModel= new CartModel();
        dialog=new Dialog(this);


        btnBackSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                radioGroup.check(R.id.radio1);
                if(radioone.isChecked()){
                    String profHomeAddress = snapshot.child("Users").child(currentuser).child("homeaddress").getValue(String.class);
                    DeliveryAdd.getText().clear();
                    DeliveryAdd.setText(profHomeAddress);
                    DeliveryAdd.setEnabled(false);
                    DeliveryAdd.setError(null);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        radioone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference= FirebaseDatabase.getInstance().getReference();

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        String profHomeAddress = snapshot.child("Users").child(currentuser).child("homeaddress").getValue(String.class);
                        DeliveryAdd.getText().clear();
                        DeliveryAdd.setText(profHomeAddress);
                        DeliveryAdd.setEnabled(false);
                        DeliveryAdd.setError(null);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        radiotwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeliveryAdd.getText().clear();
                DeliveryAdd.setEnabled(true);

            }
        });

        reff = FirebaseDatabase.getInstance().getReference().child("Data").child("OrderID");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    maxid=(snapshot.getChildrenCount());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reffUser = FirebaseDatabase.getInstance().getReference().child("Data").child("CustomerOrderID");
        reffUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    usermaxid=(snapshot.getChildrenCount());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reff2 = FirebaseDatabase.getInstance().getReference().child("Orders").child(getTodaysDate()).child(String.valueOf(maxid));
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (DeliveryAdd.getText().toString().isEmpty()){
                    DeliveryAdd.setError("Field cannot be empty");
                }else {
                    DeliveryAdd.setError(null);
                    dialog.setContentView(R.layout.place_order);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    Button btnok = dialog.findViewById(R.id.btnOK);
                    Button notyet = dialog.findViewById(R.id.btnNO);
                    TextView N1,N2,N3,N4,N5,N6,N7,N8,Address;
                    N1 = dialog.findViewById(R.id.Name1);
                    N2 = dialog.findViewById(R.id.Name2);
                    N3 = dialog.findViewById(R.id.Name3);
                    N4 = dialog.findViewById(R.id.Name4);
                    N5 = dialog.findViewById(R.id.Name5);
                    N6 = dialog.findViewById(R.id.Name6);
                    N7 = dialog.findViewById(R.id.Name7);
                    N8 = dialog.findViewById(R.id.Name8);
                    Address = dialog.findViewById(R.id.txtPlaceAddress);

                    Address.setText("Address: " +DeliveryAdd.getText().toString());

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Cart").child(currentuser);
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            String name1,name2,name3,name4,name5,name6,name7,name8;
                            int quantity1,quantity2,quantity3,quantity4,quantity5,quantity6,quantity7,quantity8;

                            if(snapshot.child("01").child("quantity").getValue()==null&&snapshot.child("01").child("name").getValue()==null){
                                N1.setVisibility(View.GONE);
                            }else{
                                name1 = snapshot.child("01").child("name").getValue(String.class);
                                quantity1 = Integer.parseInt(snapshot.child("01").child("quantity").getValue().toString());
                                N1.setText(name1+" x " + quantity1);
                            }
                            if(snapshot.child("02").child("quantity").getValue()==null&&snapshot.child("02").child("name").getValue()==null){
                                N2.setVisibility(View.GONE);
                            }else{
                                name2 = snapshot.child("02").child("name").getValue(String.class);
                                quantity2 = Integer.parseInt(snapshot.child("02").child("quantity").getValue().toString());
                                N2.setText(name2+" x " + quantity2);
                            }
                            if(snapshot.child("03").child("quantity").getValue()==null&&snapshot.child("03").child("name").getValue()==null){
                                N3.setVisibility(View.GONE);
                            }else{
                                name3 = snapshot.child("03").child("name").getValue(String.class);
                                quantity3 = Integer.parseInt(snapshot.child("03").child("quantity").getValue().toString());
                                N3.setText(name3+" x " + quantity3);
                            }
                            if(snapshot.child("04").child("quantity").getValue()==null&&snapshot.child("04").child("name").getValue()==null){
                                N4.setVisibility(View.GONE);
                            }else{
                                name4 = snapshot.child("04").child("name").getValue(String.class);
                                quantity4 = Integer.parseInt(snapshot.child("04").child("quantity").getValue().toString());
                                N4.setText(name4+" x " + quantity4);
                            }
                            if(snapshot.child("05").child("quantity").getValue()==null&&snapshot.child("05").child("name").getValue()==null){
                                N5.setVisibility(View.GONE);
                            }else{
                                name5 = snapshot.child("05").child("name").getValue(String.class);
                                quantity5 = Integer.parseInt(snapshot.child("05").child("quantity").getValue().toString());
                                N5.setText(name5+" x " + quantity5);
                            }
                            if(snapshot.child("06").child("quantity").getValue()==null&&snapshot.child("06").child("name").getValue()==null){
                                N6.setVisibility(View.GONE);
                            }else{
                                name6 = snapshot.child("06").child("name").getValue(String.class);
                                quantity6 = Integer.parseInt(snapshot.child("06").child("quantity").getValue().toString());
                                N6.setText(name6+" x " + quantity6);
                            }
                            if(snapshot.child("07").child("quantity").getValue()==null&&snapshot.child("07").child("name").getValue()==null){
                                N7.setVisibility(View.GONE);
                            }else{
                                name7 = snapshot.child("07").child("name").getValue(String.class);
                                quantity7 = Integer.parseInt(snapshot.child("07").child("quantity").getValue().toString());
                                N7.setText(name7+" x " + quantity7 );
                            }
                            if(snapshot.child("08").child("quantity").getValue()==null&&snapshot.child("08").child("name").getValue()==null){
                                N8.setVisibility(View.GONE);
                            }else{
                                name8 = snapshot.child("08").child("name").getValue(String.class);
                                quantity8 = Integer.parseInt(snapshot.child("08").child("quantity").getValue().toString());
                                N8.setText(name8+" x " + quantity8);
                            }



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });
                    btnok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            new CountDownTimer(3000, 1000) {
                                @Override
                                public void onTick(long l) {
                                    dialog.dismiss();
                                    btnCheckOut.setVisibility(View.GONE);
                                    progressbbar.setVisibility(View.VISIBLE);
                                    DeliveryAdd.setEnabled(false);
                                    radioone.setEnabled(false);
                                    radiotwo.setEnabled(false);
                                    btnBackSum.setEnabled(false);
                                }

                                @Override
                                public void onFinish() {
                                    DatabaseReference order = FirebaseDatabase.getInstance().getReference();

                                    Toast.makeText(SummaryActivity.this,"Successfully Order",Toast.LENGTH_LONG).show();
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Cart");
                                    Query query = reference.orderByKey().equalTo(currentuser);
                                    query.addListenerForSingleValueEvent(new ValueEventListener() {

                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                                            for(DataSnapshot snapshot : datasnapshot.getChildren()){

                                                String name1,name2,name3,name4,name5,name6,name7,name8;
                                                int quantity1,quantity2,quantity3,quantity4,quantity5,quantity6,quantity7,quantity8;

                                                if(snapshot.child("01").child("quantity").getValue()==null&&snapshot.child("01").child("name").getValue()==null){
                                                    reff2.getRef().child("name1").removeValue();
                                                    reff2.getRef().child("qty1").removeValue();
                                                }else{
                                                    name1 = snapshot.child("01").child("name").getValue(String.class);
                                                    quantity1 = Integer.parseInt(snapshot.child("01").child("quantity").getValue().toString());
                                                    productModel.setName1(name1);
                                                    productModel.setQty1(quantity1);
                                                }
                                                if(snapshot.child("02").child("quantity").getValue()==null&&snapshot.child("02").child("name").getValue()==null){
                                                    reff2.getRef().child("name2").removeValue();
                                                    reff2.getRef().child("qty2").removeValue();
                                                }else{
                                                    name2 = snapshot.child("02").child("name").getValue(String.class);
                                                    quantity2 = Integer.parseInt(snapshot.child("02").child("quantity").getValue().toString());
                                                    productModel.setName2(name2);
                                                    productModel.setQty2(quantity2);
                                                }
                                                if(snapshot.child("03").child("quantity").getValue()==null&&snapshot.child("03").child("name").getValue()==null){
                                                    reff2.getRef().child("name3").removeValue();
                                                    reff2.getRef().child("qty3").removeValue();
                                                }else{
                                                    name3 = snapshot.child("03").child("name").getValue(String.class);
                                                    quantity3 = Integer.parseInt(snapshot.child("03").child("quantity").getValue().toString());
                                                    productModel.setName3(name3);
                                                    productModel.setQty3(quantity3);
                                                }
                                                if(snapshot.child("04").child("quantity").getValue()==null&&snapshot.child("04").child("name").getValue()==null){
                                                    reff2.getRef().child("name4").removeValue();
                                                    reff2.getRef().child("qty4").removeValue();
                                                }else{
                                                    name4 = snapshot.child("04").child("name").getValue(String.class);
                                                    quantity4 = Integer.parseInt(snapshot.child("04").child("quantity").getValue().toString());
                                                    productModel.setName4(name4);
                                                    productModel.setQty4(quantity4);
                                                }
                                                if(snapshot.child("05").child("quantity").getValue()==null&&snapshot.child("05").child("name").getValue()==null){
                                                    reff2.getRef().child("name5").removeValue();
                                                    reff2.getRef().child("qty5").removeValue();
                                                }else{
                                                    name5 = snapshot.child("05").child("name").getValue(String.class);
                                                    quantity5 = Integer.parseInt(snapshot.child("05").child("quantity").getValue().toString());
                                                    productModel.setName5(name5);
                                                    productModel.setQty5(quantity5);
                                                }
                                                if(snapshot.child("06").child("quantity").getValue()==null&&snapshot.child("06").child("name").getValue()==null){
                                                    reff2.getRef().child("name6").removeValue();
                                                    reff2.getRef().child("qty6").removeValue();
                                                }else{
                                                    name6 = snapshot.child("06").child("name").getValue(String.class);
                                                    quantity6 = Integer.parseInt(snapshot.child("06").child("quantity").getValue().toString());
                                                    productModel.setName6(name6);
                                                    productModel.setQty6(quantity6);
                                                }
                                                if(snapshot.child("07").child("quantity").getValue()==null&&snapshot.child("07").child("name").getValue()==null){
                                                    reff2.getRef().child("name7").removeValue();
                                                    reff2.getRef().child("qty7").removeValue();
                                                }else{
                                                    name7 = snapshot.child("07").child("name").getValue(String.class);
                                                    quantity7 = Integer.parseInt(snapshot.child("07").child("quantity").getValue().toString());
                                                    productModel.setName7(name7);
                                                    productModel.setQty7(quantity7);
                                                }
                                                if(snapshot.child("08").child("quantity").getValue()==null&&snapshot.child("08").child("name").getValue()==null){
                                                    reff2.getRef().child("name8").removeValue();
                                                    reff2.getRef().child("qty8").removeValue();
                                                }else{
                                                    name8 = snapshot.child("08").child("name").getValue(String.class);
                                                    quantity8 = Integer.parseInt(snapshot.child("08").child("quantity").getValue().toString());
                                                    productModel.setName8(name8);
                                                    productModel.setQty8(quantity8);
                                                }


                                                float total = Float.parseFloat(TotalPrice.getText().toString());
                                                productModel.setAddress(DeliveryAdd.getText().toString().trim());
                                                productModel.setTotalPrice(total);
                                                productModel.setStatus("Pending");
                                                productModel.setPersonalID(String.valueOf(usermaxid + 1));
                                                productModel.setKey(String.valueOf(maxid +1));
                                                productModel.setUid(currentuser);
                                                productModel.setOrderdate(getTodaysDate());
                                                productModel.setPhonenum(phone.getText().toString().trim());
                                                productModel.setCustname(FName.getText().toString().trim());
                                                reff.child(String.valueOf(maxid + 1)).child("uid").setValue(currentuser);
                                                order.child("Orders").child(getTodaysDate()).child(String.valueOf(maxid + 1)).setValue(productModel);
                                                reffUser.child(String.valueOf(usermaxid + 1)).child("uid").setValue(currentuser);
                                                order.child("Users").child(currentuser).child("OrderHistory").child(String.valueOf(usermaxid + 1)).setValue(productModel);
                                                order.child("Users").child(currentuser).child("OrderHistory").child(String.valueOf(usermaxid + 1)).child("orderdate").setValue(getTodaysDate());
                                                snapshot.getRef().removeValue();

                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    Intent intent = new Intent(getApplicationContext(),ThankYouActivity.class);
                                    startActivity(intent);
                                    finish();
                                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                                }
                            }.start();

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
            }
        });
        btnCheckOut.setVisibility(View.VISIBLE);
        progressbbar.setVisibility(View.GONE);
        loadSummaryFromFirebase();
        init();
        TotalPrice();
        NameandPhone();
    }

    private void loadSummaryFromFirebase(){
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
                            cartLoadListener.onCartLoadSuccess(cartModels);
                        }else{
                            cartLoadListener.onCartLoadFailed("Failed to Reload the Cart");
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
        recyclerSummary.setLayoutManager(layoutManager);
        recyclerSummary.addItemDecoration(new DividerItemDecoration(this,layoutManager.getOrientation()));
    }

    @Override
    public void onCartLoadSuccess(List<CartModel> cartModelList) {
        MySummaryAdapter adapter = new MySummaryAdapter(this,cartModelList);
        recyclerSummary.setAdapter(adapter);
    }

    @Override
    public void onCartLoadFailed(String message) {
        Snackbar.make(summaryML,message,Snackbar.LENGTH_LONG).show();
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
                            TotalPrice.setText(new StringBuilder().append(total));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    public void NameandPhone(){
        databaseReference= FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String profFull = snapshot.child("Users").child(currentuser).child("name").getValue(String.class);
                String profPhone = snapshot.child("Users").child(currentuser).child("phoneNo").getValue(String.class);

                FName.setText(profFull);
                phone.setText(profPhone);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if(progressbbar.isShown()){
            Toast.makeText(SummaryActivity.this,"Please Wait You Order is still in Process!",Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(getApplicationContext(),CartActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

    }
    private String getTodaysDate() {

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month,year);

    }
    private String makeDateString(int day, int month, int year) {

        return day + " " + getMonthFormat(month) + " " + year;
    }
    private String getMonthFormat(int month) {
        if(month == 1)
            return "Jan";
        if(month == 2)
            return "Feb";
        if(month == 3)
            return "Mar";
        if(month == 4)
            return "Apr";
        if(month == 5)
            return "May";
        if(month == 6)
            return "Jun";
        if(month == 7)
            return "Jul";
        if(month == 8)
            return "Aug";
        if(month == 9)
            return "Sep";
        if(month == 10)
            return "Oct";
        if(month == 11)
            return "Nov";
        if(month == 12)
            return "Dec";

        return "Jan";

    }
}