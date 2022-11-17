package com.example.enjelwater.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.enjelwater.EventBus.MyUpdateCartEvent;
import com.example.enjelwater.Listener.IDeliverLoadListener;
import com.example.enjelwater.Model.DeliverModel;
import com.example.enjelwater.Model.Notification;
import com.example.enjelwater.Model.ProductModel;
import com.example.enjelwater.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyOrderHolder> {

    Calendar calendar = Calendar.getInstance();
    DatabaseReference reff, reff2, reff3, reff4, NewDrink;
    long maxid = 0;
    DeliverModel deliverModel;
    ProductModel productModel;
    Dialog dialog;
    String key;
    private Context context;
    private List<ProductModel> productModelList;
    private IDeliverLoadListener iDeliverLoadListener;

    String stock5,stock6,stock7,stock8;


    public MyOrderAdapter(Context context, List<ProductModel> productModelList, IDeliverLoadListener iDeliverLoadListener) {
        this.context = context;
        this.productModelList = productModelList;
        this.iDeliverLoadListener = iDeliverLoadListener;


    }

    @NonNull
    @Override
    public MyOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyOrderHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderHolder holder, int position) {

        deliverModel = new DeliverModel();
        dialog = new Dialog(context);

        reff = FirebaseDatabase.getInstance().getReference().child("Cancel").child(getTodaysDate());
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    maxid = (snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
        reff3 = FirebaseDatabase.getInstance().getReference();
        reff4 = FirebaseDatabase.getInstance().getReference();

        if (productModelList.get(position).getName1() == null) {
            holder.txtN1.setVisibility(View.GONE);
        } else {
            holder.txtN1.setVisibility(View.VISIBLE);
            holder.txtN1.setText(new StringBuilder().append(productModelList.get(position).getName1()).append(" x").append(productModelList.get(position).getQty1()));
        }
        if (productModelList.get(position).getName2() == null) {
            holder.txtN2.setVisibility(View.GONE);
        } else {
            holder.txtN2.setVisibility(View.VISIBLE);
            holder.txtN2.setText(new StringBuilder().append(productModelList.get(position).getName2()).append(" x").append(productModelList.get(position).getQty2()));
        }
        if (productModelList.get(position).getName3() == null) {
            holder.txtN3.setVisibility(View.GONE);
        } else {
            holder.txtN3.setVisibility(View.VISIBLE);
            holder.txtN3.setText(new StringBuilder().append(productModelList.get(position).getName3()).append(" x").append(productModelList.get(position).getQty3()));
        }
        if (productModelList.get(position).getName4() == null) {
            holder.txtN4.setVisibility(View.GONE);
        } else {
            holder.txtN4.setVisibility(View.VISIBLE);
            holder.txtN4.setText(new StringBuilder().append(productModelList.get(position).getName4()).append(" x").append(productModelList.get(position).getQty4()));
        }
        if (productModelList.get(position).getName5() == null) {
            holder.txtN5.setVisibility(View.GONE);
        } else {
            holder.txtN5.setVisibility(View.VISIBLE);
            holder.txtN5.setText(new StringBuilder().append(productModelList.get(position).getName5()).append(" x").append(productModelList.get(position).getQty5()));
        }
        if (productModelList.get(position).getName6() == null) {
            holder.txtN6.setVisibility(View.GONE);
        } else {
            holder.txtN6.setVisibility(View.VISIBLE);
            holder.txtN6.setText(new StringBuilder().append(productModelList.get(position).getName6()).append(" x").append(productModelList.get(position).getQty6()));
        }
        if (productModelList.get(position).getName7() == null) {
            holder.txtN7.setVisibility(View.GONE);
        } else {
            holder.txtN7.setVisibility(View.VISIBLE);
            holder.txtN7.setText(new StringBuilder().append(productModelList.get(position).getName7()).append(" x").append(productModelList.get(position).getQty7()));
        }
        if (productModelList.get(position).getName8() == null) {
            holder.txtN8.setVisibility(View.GONE);
        } else {
            holder.txtN8.setVisibility(View.VISIBLE);
            holder.txtN8.setText(new StringBuilder().append(productModelList.get(position).getName8()).append(" x").append(productModelList.get(position).getQty8()));
        }
        holder.txtName.setText(new StringBuilder().append(productModelList.get(position).getCustname()));
        holder.txtAddress.setText(new StringBuilder().append(productModelList.get(position).getAddress()));
        holder.txtStat.setText(new StringBuilder().append(productModelList.get(position).getStatus()));
        holder.txtStat.setTextColor(Color.parseColor("#FF0000"));
        holder.txtTotalOrderP.setText(new StringBuilder().append(String.format("%.2f", productModelList.get(position).getTotalPrice())));
        holder.txtIDNUM.setText(new StringBuilder().append(productModelList.get(position).getKey()));
        holder.txtPID.setText(new StringBuilder().append(productModelList.get(position).getPersonalID()));
        holder.txtUID.setText(new StringBuilder().append(productModelList.get(position).getUid()));
        holder.txtKey.setText(new StringBuilder().append(productModelList.get(position).getKey()));
        holder.txtPhone.setText(new StringBuilder().append(productModelList.get(position).getPhonenum()));
        holder.date.setText(new StringBuilder().append(productModelList.get(position).getOrderdate()));
        holder.time.setText(new StringBuilder().append(productModelList.get(position).getOrdertime()));

        String name1 = String.valueOf(new StringBuilder().append(productModelList.get(position).getName1()));
        String qty1 = String.valueOf(new StringBuilder().append(productModelList.get(position).getQty1()));

        String name2 = String.valueOf(new StringBuilder().append(productModelList.get(position).getName2()));
        String qty2 = String.valueOf(new StringBuilder().append(productModelList.get(position).getQty2()));

        String name3 = String.valueOf(new StringBuilder().append(productModelList.get(position).getName3()));
        String qty3 = String.valueOf(new StringBuilder().append(productModelList.get(position).getQty3()));

        String name4 = String.valueOf(new StringBuilder().append(productModelList.get(position).getName4()));
        String qty4 = String.valueOf(new StringBuilder().append(productModelList.get(position).getQty4()));

        String name5 = String.valueOf(new StringBuilder().append(productModelList.get(position).getName5()));
        String qty5 = String.valueOf(new StringBuilder().append(productModelList.get(position).getQty5()));

        String name6 = String.valueOf(new StringBuilder().append(productModelList.get(position).getName6()));
        String qty6 = String.valueOf(new StringBuilder().append(productModelList.get(position).getQty6()));

        String name7 = String.valueOf(new StringBuilder().append(productModelList.get(position).getName7()));
        String qty7 = String.valueOf(new StringBuilder().append(productModelList.get(position).getQty7()));

        String name8 = String.valueOf(new StringBuilder().append(productModelList.get(position).getName8()));
        String qty8 = String.valueOf(new StringBuilder().append(productModelList.get(position).getQty8()));


        if (holder.txtStat.getText().toString().equals("Pending")) {
            holder.btnProc.setVisibility(View.GONE);
            holder.btnAccept.setVisibility(View.VISIBLE);
            holder.txtStat.setTextColor(Color.parseColor("#8B8000"));
        } else if (holder.txtStat.getText().toString().equals("On Process")) {
            holder.btnProc.setVisibility(View.VISIBLE);
            holder.btnDecline.setVisibility(View.GONE);
            holder.btnAccept.setVisibility(View.GONE);
            holder.txtStat.setTextColor(Color.parseColor("#FFA500"));
        }

        reff2 = FirebaseDatabase.getInstance().getReference().child("Delivered").child(getTodaysDate()).child(holder.txtIDNUM.getText().toString());

        holder.btnProc.setOnClickListener(view -> {

            dialog.setContentView(R.layout.out_delivery_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);

            ProgressBar progressBar = dialog.findViewById(R.id.progressbarDeliver);

            Button btnok = dialog.findViewById(R.id.btn_okay2);
            Button notyet = dialog.findViewById(R.id.btn_notyet);
            TextView N1, N2, N3, N4, N5, N6, N7, N8, Cust, Address, phone;
            RadioButton rb1,rb2,rb3;
            N1 = dialog.findViewById(R.id.txtName1);
            N2 = dialog.findViewById(R.id.txtName2);
            N3 = dialog.findViewById(R.id.txtName3);
            N4 = dialog.findViewById(R.id.txtName4);
            N5 = dialog.findViewById(R.id.txtName5);
            N6 = dialog.findViewById(R.id.txtName6);
            N7 = dialog.findViewById(R.id.txtName7);
            N8 = dialog.findViewById(R.id.txtName8);
            rb1 = dialog.findViewById(R.id.rb_rider1);
            rb2 = dialog.findViewById(R.id.rb_rider2);
            rb3 = dialog.findViewById(R.id.rb_rider3);
            Cust = dialog.findViewById(R.id.txtCustomerName);
            Address = dialog.findViewById(R.id.txtAddressDialog);
            phone = dialog.findViewById(R.id.txtPhonenumbb);

            Cust.setText(new StringBuilder().append(productModelList.get(position).getCustname()));
            Address.setText(new StringBuilder().append(productModelList.get(position).getAddress()));
            phone.setText(new StringBuilder().append(productModelList.get(position).getPhonenum()));

            reff3.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String rider1 = snapshot.child("Admin").child("riders").child("Rider1").child("name").getValue(String.class);
                    String rider2 = snapshot.child("Admin").child("riders").child("Rider2").child("name").getValue(String.class);
                    String rider3 = snapshot.child("Admin").child("riders").child("Rider3").child("name").getValue(String.class);

                    rb1.setText(rider1);
                    rb2.setText(rider2);
                    rb3.setText(rider3);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            rb1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rb1.isChecked()){
                        btnok.setEnabled(true);
                    }
                }
            });
            rb2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rb2.isChecked()){
                        btnok.setEnabled(true);
                    }
                }
            });
            rb3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rb3.isChecked()){
                        btnok.setEnabled(true);
                    }
                }
            });

            if (productModelList.get(holder.getAdapterPosition()).getName1() == null) {
                N1.setVisibility(View.GONE);
            } else {
                N1.setText(new StringBuilder().append(productModelList.get(holder.getAdapterPosition()).getName1()).append(" x").append(productModelList.get(position).getQty1()));
            }
            if (productModelList.get(holder.getAdapterPosition()).getName2() == null) {
                N2.setVisibility(View.GONE);
            } else {
                N2.setText(new StringBuilder().append(productModelList.get(holder.getAdapterPosition()).getName2()).append(" x").append(productModelList.get(position).getQty2()));
            }
            if (productModelList.get(holder.getAdapterPosition()).getName3() == null) {
                N3.setVisibility(View.GONE);
            } else {
                N3.setText(new StringBuilder().append(productModelList.get(holder.getAdapterPosition()).getName3()).append(" x").append(productModelList.get(position).getQty3()));
            }
            if (productModelList.get(holder.getAdapterPosition()).getName4() == null) {
                N4.setVisibility(View.GONE);
            } else {
                N4.setText(new StringBuilder().append(productModelList.get(holder.getAdapterPosition()).getName4()).append(" x").append(productModelList.get(position).getQty4()));
            }
            if (productModelList.get(holder.getAdapterPosition()).getName5() == null) {
                N5.setVisibility(View.GONE);
            } else {
                N5.setText(new StringBuilder().append(productModelList.get(holder.getAdapterPosition()).getName5()).append(" x").append(productModelList.get(position).getQty5()));
            }
            if (productModelList.get(holder.getAdapterPosition()).getName6() == null) {
                N6.setVisibility(View.GONE);
            } else {
                N6.setText(new StringBuilder().append(productModelList.get(holder.getAdapterPosition()).getName6()).append(" x").append(productModelList.get(position).getQty6()));
            }
            if (productModelList.get(holder.getAdapterPosition()).getName7() == null) {
                N7.setVisibility(View.GONE);
            } else {
                N7.setText(new StringBuilder().append(productModelList.get(holder.getAdapterPosition()).getName7()).append(" x").append(productModelList.get(position).getQty7()));
            }
            if (productModelList.get(holder.getAdapterPosition()).getName8() == null) {
                N8.setVisibility(View.GONE);
            } else {
                N8.setText(new StringBuilder().append(productModelList.get(holder.getAdapterPosition()).getName8()).append(" x").append(productModelList.get(position).getQty8()));
            }

                btnok.setOnClickListener(view1 -> new CountDownTimer(3000, 1000) {
                @Override
                public void onTick(long l) {
                    btnok.setVisibility(View.GONE);
                    notyet.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFinish() {

                    DatabaseReference reffUsers = FirebaseDatabase.getInstance().getReference()
                            .child("Users")
                            .child(holder.txtUID.getText().toString())
                            .child("OrderHistory")
                            .child(holder.txtPID.getText().toString());

                    try {
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Orders");
                        Query query = reference.orderByKey().equalTo(getTodaysDate());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String key2 = holder.txtIDNUM.getText().toString();
                                    if (snapshot.child(key2).child("name1").getValue() == null) {
                                        reff2.getRef().child("name1").removeValue();
                                        reff2.getRef().child("qty1").removeValue();
                                    } else {
                                        deliverModel.setName1(name1);
                                        deliverModel.setQty1(Integer.parseInt(qty1));
                                    }
                                    if (snapshot.child(key2).child("name2").getValue() == null) {
                                        reff2.getRef().child("name2").removeValue();
                                        reff2.getRef().child("qty2").removeValue();
                                    } else {
                                        deliverModel.setName2(name2);
                                        deliverModel.setQty2(Integer.parseInt(qty2));
                                    }
                                    if (snapshot.child(key2).child("name3").getValue() == null) {
                                        reff2.getRef().child("name3").removeValue();
                                        reff2.getRef().child("qty3").removeValue();
                                    } else {
                                        deliverModel.setName3(name3);
                                        deliverModel.setQty3(Integer.parseInt(qty3));
                                    }
                                    if (snapshot.child(key2).child("name4").getValue() == null) {
                                        reff2.getRef().child("name4").removeValue();
                                        reff2.getRef().child("qty4").removeValue();
                                    } else {
                                        deliverModel.setName4(name4);
                                        deliverModel.setQty4(Integer.parseInt(qty4));
                                    }
                                    if (snapshot.child(key2).child("name5").getValue() == null) {
                                        reff2.getRef().child("name5").removeValue();
                                        reff2.getRef().child("qty5").removeValue();
                                    } else {
                                        deliverModel.setName5(name5);
                                        deliverModel.setQty5(Integer.parseInt(qty5));
                                    }
                                    if (snapshot.child(key2).child("name6").getValue() == null) {
                                        reff2.getRef().child("name6").removeValue();
                                        reff2.getRef().child("qty6").removeValue();
                                    } else {
                                        deliverModel.setName6(name6);
                                        deliverModel.setQty6(Integer.parseInt(qty6));
                                    }
                                    if (snapshot.child(key2).child("name7").getValue() == null) {
                                        reff2.getRef().child("name7").removeValue();
                                        reff2.getRef().child("qty7").removeValue();
                                    } else {
                                        deliverModel.setName7(name7);
                                        deliverModel.setQty7(Integer.parseInt(qty7));
                                    }
                                    if (snapshot.child(key2).child("name8").getValue() == null) {
                                        reff2.getRef().child("name8").removeValue();
                                        reff2.getRef().child("qty8").removeValue();
                                    } else {
                                        deliverModel.setName8(name8);
                                        deliverModel.setQty8(Integer.parseInt(qty8));
                                    }
                                    deliverModel.setAddress(String.valueOf(snapshot.child(key2).child("address").getValue()));
                                    deliverModel.setTotalPrice(Float.parseFloat((snapshot.child(key2).child("totalPrice").getValue().toString())));
                                    deliverModel.setStatus("On-going Delivery");
                                    deliverModel.setCustomerName(holder.txtName.getText().toString());
                                    deliverModel.setPID(holder.txtPID.getText().toString());
                                    deliverModel.setOrderdate(getTodaysDate());
                                    deliverModel.setUID(holder.txtUID.getText().toString());
                                    deliverModel.setKey(holder.txtKey.getText().toString());
                                    deliverModel.setPhonenum(holder.txtPhone.getText().toString());
                                    if(rb1.isChecked()){
                                        deliverModel.setRidername(rb1.getText().toString());
                                        deliverModel.setTime_in(holder.time.getText().toString());
                                        reff3.child("Admin").child("riders").child("Rider1").child("DeliveryList").child(holder.txtKey.getText().toString()).setValue(deliverModel);
                                    }else if(rb2.isChecked()){
                                        deliverModel.setRidername(rb2.getText().toString());
                                        deliverModel.setTime_in(holder.time.getText().toString());
                                        reff3.child("Admin").child("riders").child("Rider2").child("DeliveryList").child(holder.txtKey.getText().toString()).setValue(deliverModel);
                                    }else if(rb3.isChecked()){
                                        deliverModel.setRidername(rb3.getText().toString());
                                        deliverModel.setTime_in(holder.time.getText().toString());
                                        reff3.child("Admin").child("riders").child("Rider3").child("DeliveryList").child(holder.txtKey.getText().toString()).setValue(deliverModel);
                                    }
                                    reffUsers.child("status").setValue("On-going Delivery");
                                    reff3.child("Delivered").child(holder.txtKey.getText().toString()).setValue(deliverModel);
                                     FirebaseDatabase.getInstance().getReference("Users")
                                                        .child(holder.txtUID.getText().toString())
                                                        .child("Notifications")
                                                        .push().setValue(new Notification(
                                                                "Order Status",
                                                                "Your order is now out for delivery"
                                                        ));
                                    snapshot.child(key2).getRef().removeValue().addOnSuccessListener(aVoid -> EventBus.getDefault().postSticky(new MyUpdateCartEvent()));

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } catch (Exception exception) {
                        System.out.println(exception);
                    }

                    productModelList.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                    notifyItemRangeChanged(holder.getAdapterPosition(), productModelList.size());
                    btnok.setVisibility(View.VISIBLE);
                    notyet.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    dialog.dismiss();

                }
            }.start());
            notyet.setOnClickListener(view12 -> dialog.dismiss());
            dialog.show();
        });


        holder.btnAccept.setOnClickListener(view -> {

            dialog.setContentView(R.layout.delivery_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);

            ProgressBar progressBar = dialog.findViewById(R.id.progressbarDeliver);

            Button btnok = dialog.findViewById(R.id.btn_okay2);
            Button notyet = dialog.findViewById(R.id.btn_notyet);
            TextView N1, N2, N3, N4, N5, N6, N7, N8, Name, Address, phone;
            N1 = dialog.findViewById(R.id.txtName1);
            N2 = dialog.findViewById(R.id.txtName2);
            N3 = dialog.findViewById(R.id.txtName3);
            N4 = dialog.findViewById(R.id.txtName4);
            N5 = dialog.findViewById(R.id.txtName5);
            N6 = dialog.findViewById(R.id.txtName6);
            N7 = dialog.findViewById(R.id.txtName7);
            N8 = dialog.findViewById(R.id.txtName8);
            // count = dialog.findViewById(R.id.txtcountdown);
            Name = dialog.findViewById(R.id.txtCustomerName);
            Address = dialog.findViewById(R.id.txtAddressDialog);
            phone = dialog.findViewById(R.id.txtPhoneDialog);
            Name.setText(new StringBuilder().append(productModelList.get(position).getCustname()));
            Address.setText(new StringBuilder().append(productModelList.get(position).getAddress()));
            phone.setText(new StringBuilder().append(productModelList.get(position).getPhonenum()));


            if (productModelList.get(holder.getAdapterPosition()).getName1() == null) {
                N1.setVisibility(View.GONE);
            } else {
                N1.setText(new StringBuilder().append(productModelList.get(holder.getAdapterPosition()).getName1()).append(" x").append(productModelList.get(position).getQty1()));
            }
            if (productModelList.get(holder.getAdapterPosition()).getName2() == null) {
                N2.setVisibility(View.GONE);
            } else {
                N2.setText(new StringBuilder().append(productModelList.get(holder.getAdapterPosition()).getName2()).append(" x").append(productModelList.get(position).getQty2()));
            }
            if (productModelList.get(holder.getAdapterPosition()).getName3() == null) {
                N3.setVisibility(View.GONE);
            } else {
                N3.setText(new StringBuilder().append(productModelList.get(holder.getAdapterPosition()).getName3()).append(" x").append(productModelList.get(position).getQty3()));
            }
            if (productModelList.get(holder.getAdapterPosition()).getName4() == null) {
                N4.setVisibility(View.GONE);
            } else {
                N4.setText(new StringBuilder().append(productModelList.get(holder.getAdapterPosition()).getName4()).append(" x").append(productModelList.get(position).getQty4()));
            }
            if (productModelList.get(holder.getAdapterPosition()).getName5() == null) {
                N5.setVisibility(View.GONE);
            } else {
                N5.setText(new StringBuilder().append(productModelList.get(holder.getAdapterPosition()).getName5()).append(" x").append(productModelList.get(position).getQty5()));
            }
            if (productModelList.get(holder.getAdapterPosition()).getName6() == null) {
                N6.setVisibility(View.GONE);
            } else {
                N6.setText(new StringBuilder().append(productModelList.get(holder.getAdapterPosition()).getName6()).append(" x").append(productModelList.get(position).getQty6()));
            }
            if (productModelList.get(holder.getAdapterPosition()).getName7() == null) {
                N7.setVisibility(View.GONE);
            } else {
                N7.setText(new StringBuilder().append(productModelList.get(holder.getAdapterPosition()).getName7()).append(" x").append(productModelList.get(position).getQty7()));
            }
            if (productModelList.get(holder.getAdapterPosition()).getName8() == null) {
                N8.setVisibility(View.GONE);
            } else {
                N8.setText(new StringBuilder().append(productModelList.get(holder.getAdapterPosition()).getName8()).append(" x").append(productModelList.get(position).getQty8()));
            }
            btnok.setOnClickListener(view14 -> new CountDownTimer(3000, 1000) {
                @Override
                public void onTick(long l) {
                    btnok.setVisibility(View.GONE);
                    notyet.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFinish() {
                    DatabaseReference reffUsers = FirebaseDatabase.getInstance().getReference()
                            .child("Users")
                            .child(holder.txtUID.getText().toString())
                            .child("OrderHistory")
                            .child(holder.txtPID.getText().toString());

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Orders");
                    Query query = reference.orderByKey().equalTo(getTodaysDate());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            reffUsers.child("status").setValue("On Process");
                            reff4.child("Orders").child(getTodaysDate()).child(holder.txtKey.getText().toString().trim()).child("status").setValue("On Process");
                            FirebaseDatabase.getInstance().getReference("Users")
                                                        .child(holder.txtUID.getText().toString())
                                                        .child("Notifications")
                                                        .push().setValue(new Notification(
                                                                "Order Status",
                                                                "Your order is now on process"
                                                        ));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    btnok.setVisibility(View.VISIBLE);
                    notyet.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    dialog.dismiss();

                }
            }.start());
            notyet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();

        });
        holder.btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.setContentView(R.layout.decline_order_confirmation);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCanceledOnTouchOutside(false);

                ProgressBar progressBar = dialog.findViewById(R.id.progress_decline);

                Button btnok = dialog.findViewById(R.id.btn_confirm_cancel);
                Button notyet = dialog.findViewById(R.id.btn_notyet_cancel);


                RadioGroup reason = dialog.findViewById(R.id.rg_reason);
                RadioButton notsupport = dialog.findViewById(R.id.rb_locationNotSupported);
                RadioButton others = dialog.findViewById(R.id.rb_Other);

                EditText specify = dialog.findViewById(R.id.specifyhere);

                reason.check(R.id.rb_locationNotSupported);
                if (notsupport.isChecked()) {
                    specify.setVisibility(View.GONE);
                }

                notsupport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        specify.setVisibility(View.GONE);
                        InputMethodManager imm = (InputMethodManager) view.getContext()
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                });
                others.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        specify.setVisibility(View.VISIBLE);
                    }
                });

                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RadioButton rb = dialog.findViewById(reason.getCheckedRadioButtonId());
                        String reason = rb.getText().toString();
                        new CountDownTimer(3000, 1000) {
                            public void onTick(long millisUntilFinished) {
                                btnok.setVisibility(View.GONE);
                                notyet.setVisibility(View.GONE);
                                progressBar.setVisibility(View.VISIBLE);
                            }

                            public void onFinish() {

                                DatabaseReference reffUsers = FirebaseDatabase.getInstance().getReference()
                                        .child("Users")
                                        .child(holder.txtUID.getText().toString())
                                        .child("OrderHistory")
                                        .child(holder.txtPID.getText().toString());

                                try {
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Orders");
                                    Query query = reference.orderByKey().equalTo(getTodaysDate());
                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                String key2 = holder.txtIDNUM.getText().toString();
                                                if (snapshot.child(key2).child("name1").getValue() == null) {
                                                    reff2.getRef().child("name1").removeValue();
                                                    reff2.getRef().child("qty1").removeValue();
                                                } else {
                                                    deliverModel.setName1(name1);
                                                    deliverModel.setQty1(Integer.parseInt(qty1));
                                                }
                                                if (snapshot.child(key2).child("name2").getValue() == null) {
                                                    reff2.getRef().child("name2").removeValue();
                                                    reff2.getRef().child("qty2").removeValue();
                                                } else {
                                                    deliverModel.setName2(name2);
                                                    deliverModel.setQty2(Integer.parseInt(qty2));
                                                }
                                                if (snapshot.child(key2).child("name3").getValue() == null) {
                                                    reff2.getRef().child("name3").removeValue();
                                                    reff2.getRef().child("qty3").removeValue();
                                                } else {
                                                    deliverModel.setName3(name3);
                                                    deliverModel.setQty3(Integer.parseInt(qty3));
                                                }
                                                if (snapshot.child(key2).child("name4").getValue() == null) {
                                                    reff2.getRef().child("name4").removeValue();
                                                    reff2.getRef().child("qty4").removeValue();
                                                } else {
                                                    deliverModel.setName4(name4);
                                                    deliverModel.setQty4(Integer.parseInt(qty4));
                                                }
                                                if(snapshot.child(key).child("name5").getValue()==null){
                                                    reff2.getRef().child("name5").removeValue();
                                                    reff2.getRef().child("qty5").removeValue();
                                                }else{
                                                    deliverModel.setName5(name5);
                                                    deliverModel.setQty5(Integer.parseInt(qty5));
                                                    int st5 = Integer.parseInt(stock5);
                                                    String UpdatedStock = String.valueOf(st5+Integer.parseInt(qty5));
                                                    NewDrink.child("05").child("stocks").setValue(UpdatedStock);
                                                }
                                                if(snapshot.child(key).child("name6").getValue()==null){
                                                    reff2.getRef().child("name6").removeValue();
                                                    reff2.getRef().child("qty6").removeValue();
                                                }else{
                                                    deliverModel.setName6(name6);
                                                    deliverModel.setQty6(Integer.parseInt(qty6));
                                                    int st6 = Integer.parseInt(stock6);
                                                    String UpdatedStock = String.valueOf(st6+Integer.parseInt(qty6));
                                                    NewDrink.child("06").child("stocks").setValue(UpdatedStock);
                                                }
                                                if(snapshot.child(key).child("name7").getValue()==null){
                                                    reff2.getRef().child("name7").removeValue();
                                                    reff2.getRef().child("qty7").removeValue();
                                                }else{
                                                    deliverModel.setName7(name7);
                                                    deliverModel.setQty7(Integer.parseInt(qty7));
                                                    int st7 = Integer.parseInt(stock7);
                                                    String UpdatedStock = String.valueOf(st7+Integer.parseInt(qty7));
                                                    NewDrink.child("07").child("stocks").setValue(UpdatedStock);
                                                }
                                                if(snapshot.child(key).child("name8").getValue()==null){
                                                    reff2.getRef().child("name8").removeValue();
                                                    reff2.getRef().child("qty8").removeValue();
                                                }else{
                                                    deliverModel.setName8(name8);
                                                    deliverModel.setQty8(Integer.parseInt(qty8));
                                                    int st8 = Integer.parseInt(stock8);
                                                    String UpdatedStock = String.valueOf(st8+Integer.parseInt(qty8));
                                                    NewDrink.child("08").child("stocks").setValue(UpdatedStock);
                                                }


                                                deliverModel.setAddress(String.valueOf(snapshot.child(key2).child("address").getValue()));
                                                deliverModel.setTotalPrice(Float.parseFloat((snapshot.child(key2).child("totalPrice").getValue().toString())));
                                                deliverModel.setStatus("Cancelled");
                                                deliverModel.setCustomerName(holder.txtName.getText().toString());
                                                deliverModel.setOrderdate(getTodaysDate());
                                                deliverModel.setUID(holder.txtUID.getText().toString());
                                                deliverModel.setPhonenum(holder.txtPhone.getText().toString());
                                                reffUsers.child("status").setValue("Cancelled");

                                                if (notsupport.isChecked()) {
                                                    reffUsers.child("cancel_reason").setValue(reason);
                                                } else {
                                                    reffUsers.child("cancel_reason").setValue(specify.getText().toString());
                                                }

                                                FirebaseDatabase.getInstance().getReference("Users")
                                                        .child(holder.txtUID.getText().toString())
                                                        .child("Notifications")
                                                        .push().setValue(new Notification(
                                                                "Your order has been cancelled!",
                                                                notsupport.isChecked() ? reason : specify.getText().toString()
                                                        ));

                                                reff.child(String.valueOf(maxid + 1)).setValue(deliverModel);
                                                snapshot.child(key2).getRef().removeValue().addOnSuccessListener(aVoid -> EventBus.getDefault().postSticky(new MyUpdateCartEvent()));

                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                } catch (Exception exception) {
                                    System.out.println(exception);
                                }

                                productModelList.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                notifyItemRangeChanged(holder.getAdapterPosition(), productModelList.size());
                                btnok.setVisibility(View.VISIBLE);
                                notyet.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                dialog.dismiss();
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
        });
    }


    @Override
    public int getItemCount() {

        return productModelList.size();
    }

    private String getTodaysDate() {

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);

    }

    private String makeDateString(int day, int month, int year) {

        return day + " " + getMonthFormat(month) + " " + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1)
            return "Jan";
        if (month == 2)
            return "Feb";
        if (month == 3)
            return "Mar";
        if (month == 4)
            return "Apr";
        if (month == 5)
            return "May";
        if (month == 6)
            return "Jun";
        if (month == 7)
            return "Jul";
        if (month == 8)
            return "Aug";
        if (month == 9)
            return "Sep";
        if (month == 10)
            return "Oct";
        if (month == 11)
            return "Nov";
        if (month == 12)
            return "Dec";

        return "Jan";

    }

    public class MyOrderHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtName1)
        TextView txtN1;
        @BindView(R.id.txtName2)
        TextView txtN2;
        @BindView(R.id.txtName3)
        TextView txtN3;
        @BindView(R.id.txtName4)
        TextView txtN4;
        @BindView(R.id.txtName5)
        TextView txtN5;
        @BindView(R.id.txtName6)
        TextView txtN6;
        @BindView(R.id.txtName7)
        TextView txtN7;
        @BindView(R.id.txtName8)
        TextView txtN8;
        @BindView(R.id.txtAddress)
        TextView txtAddress;
        @BindView(R.id.txtTotalOrderPrice)
        TextView txtTotalOrderP;
        @BindView(R.id.txtStatus)
        TextView txtStat;
        @BindView(R.id.idnum)
        TextView txtIDNUM;
        @BindView(R.id.personalID)
        TextView txtPID;
        @BindView(R.id.uid)
        TextView txtUID;
        @BindView(R.id.key)
        TextView txtKey;
        @BindView(R.id.txtcustname)
        TextView txtName;
        @BindView(R.id.txtphone)
        TextView txtPhone;
        @BindView(R.id.btnAccept)
        Button btnAccept;
        @BindView(R.id.btnProceed)
        Button btnProc;
        @BindView(R.id.btn_decline)
        Button btnDecline;
        @BindView(R.id.orderdate)
        TextView date;
        @BindView(R.id.ordertime)
        TextView time;


        Unbinder unbinder;

        public MyOrderHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}
