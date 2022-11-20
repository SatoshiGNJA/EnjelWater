package com.example.enjelwater.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.enjelwater.EventBus.MyUpdateCartEvent;
import com.example.enjelwater.MainActivity;
import com.example.enjelwater.Model.Notification;
import com.example.enjelwater.Model.PersonalOrderModel;
import com.example.enjelwater.Model.ProductModel;
import com.example.enjelwater.PersonalOrderActivity;
import com.example.enjelwater.R;
import com.example.enjelwater.SummaryActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyPersonalHistoryAdapter extends RecyclerView.Adapter<MyPersonalHistoryAdapter.MyPersonalViewHolder> {

    private Context context;
    private List<PersonalOrderModel> personalOrderModelList;
    Dialog dialog;
    long maxid=0;
    long finishmaxid = 0;
    DatabaseReference reff,reff2,reff3,reff4,reff5,NewDrink;
    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
    PersonalOrderModel personalOrderModel;

    String stock5,stock6,stock7,stock8;

    Calendar calendar = Calendar.getInstance();

    public MyPersonalHistoryAdapter(Context context, List<PersonalOrderModel> personalOrderModelList){
        this.context = context;
        this.personalOrderModelList = personalOrderModelList;
    }

    @NonNull
    @Override
    public MyPersonalHistoryAdapter.MyPersonalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyPersonalHistoryAdapter.MyPersonalViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_personal_order_item,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyPersonalHistoryAdapter.MyPersonalViewHolder holder, int position) {


        personalOrderModel=new PersonalOrderModel();
        dialog=new Dialog(context);
        String dateTime;
        Calendar calendar;
        SimpleDateFormat simpleDateFormat;

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("hh:mm aaa");
        dateTime = simpleDateFormat.format(calendar.getTime());


        if(personalOrderModelList.get(position).getName1() == null){
            holder.txtN1.setVisibility(View.GONE);
        }else{
            holder.txtN1.setVisibility(View.VISIBLE);
            holder.txtN1.setText(new StringBuilder().append(personalOrderModelList.get(position).getName1()).append(" x").append(personalOrderModelList.get(position).getQty1()));
        }
        if(personalOrderModelList.get(position).getName2() == null){
            holder.txtN2.setVisibility(View.GONE);
        }else{
            holder.txtN2.setVisibility(View.VISIBLE);
            holder.txtN2.setText(new StringBuilder().append(personalOrderModelList.get(position).getName2()).append(" x").append(personalOrderModelList.get(position).getQty2()));
        }
        if(personalOrderModelList.get(position).getName3() == null){
            holder.txtN3.setVisibility(View.GONE);
        }else{
            holder.txtN3.setVisibility(View.VISIBLE);
            holder.txtN3.setText(new StringBuilder().append(personalOrderModelList.get(position).getName3()).append(" x").append(personalOrderModelList.get(position).getQty3()));
        }
        if(personalOrderModelList.get(position).getName4() == null){
            holder.txtN4.setVisibility(View.GONE);
        }else{
            holder.txtN4.setVisibility(View.VISIBLE);
            holder.txtN4.setText(new StringBuilder().append(personalOrderModelList.get(position).getName4()).append(" x").append(personalOrderModelList.get(position).getQty4()));
        }
        if(personalOrderModelList.get(position).getName5() == null){
            holder.txtN5.setVisibility(View.GONE);
        }else{
            holder.txtN5.setVisibility(View.VISIBLE);
            holder.txtN5.setText(new StringBuilder().append(personalOrderModelList.get(position).getName5()).append(" x").append(personalOrderModelList.get(position).getQty5()));
        }
        if(personalOrderModelList.get(position).getName6() == null){
            holder.txtN6.setVisibility(View.GONE);
        }else{
            holder.txtN6.setVisibility(View.VISIBLE);
            holder.txtN6.setText(new StringBuilder().append(personalOrderModelList.get(position).getName6()).append(" x").append(personalOrderModelList.get(position).getQty6()));
        }
        if(personalOrderModelList.get(position).getName7() == null){
            holder.txtN7.setVisibility(View.GONE);
        }else{
            holder.txtN7.setVisibility(View.VISIBLE);
            holder.txtN7.setText(new StringBuilder().append(personalOrderModelList.get(position).getName7()).append(" x").append(personalOrderModelList.get(position).getQty7()));
        }
        if(personalOrderModelList.get(position).getName8() == null){
            holder.txtN8.setVisibility(View.GONE);
        }else{
            holder.txtN8.setVisibility(View.VISIBLE);
            holder.txtN8.setText(new StringBuilder().append(personalOrderModelList.get(position).getName8()).append(" x").append(personalOrderModelList.get(position).getQty8()));
        }
        holder.txtAddress.setText(new StringBuilder().append(personalOrderModelList.get(position).getAddress()));
        holder.txtStat.setText(new StringBuilder().append(personalOrderModelList.get(position).getStatus()));
        holder.txtStat.setTextColor(Color.parseColor("#FF0000"));
        holder.txtTotalOrderP.setText(new StringBuilder().append(String.format("%.2f",personalOrderModelList.get(position).getTotalPrice())));
        holder.txtOrderDate.setText(new StringBuilder().append(personalOrderModelList.get(position).getOrderdate()));
        holder.txtPID.setText(new StringBuilder().append(personalOrderModelList.get(position).getPersonalID()));
        holder.txtIDNUM.setText(new StringBuilder().append(personalOrderModelList.get(position).getKey()));
        holder.txtCustomerName.setText(new StringBuilder().append(personalOrderModelList.get(position).getCustname()));
        holder.txtPhone.setText(new StringBuilder().append(personalOrderModelList.get(position).getPhonenum()));
        holder.time.setText(new StringBuilder().append(personalOrderModelList.get(position).getTime_in()));

        String name1 = String.valueOf(new StringBuilder().append(personalOrderModelList.get(position).getName1()));
        String qty1 =  String.valueOf(new StringBuilder().append(personalOrderModelList.get(position).getQty1()));

        String name2 = String.valueOf(new StringBuilder().append(personalOrderModelList.get(position).getName2()));
        String qty2 =  String.valueOf(new StringBuilder().append(personalOrderModelList.get(position).getQty2()));

        String name3 = String.valueOf(new StringBuilder().append(personalOrderModelList.get(position).getName3()));
        String qty3 =  String.valueOf(new StringBuilder().append(personalOrderModelList.get(position).getQty3()));

        String name4 = String.valueOf(new StringBuilder().append(personalOrderModelList.get(position).getName4()));
        String qty4 =  String.valueOf(new StringBuilder().append(personalOrderModelList.get(position).getQty4()));

        String name5 = String.valueOf(new StringBuilder().append(personalOrderModelList.get(position).getName5()));
        String qty5 =  String.valueOf(new StringBuilder().append(personalOrderModelList.get(position).getQty5()));

        String name6 = String.valueOf(new StringBuilder().append(personalOrderModelList.get(position).getName6()));
        String qty6 =  String.valueOf(new StringBuilder().append(personalOrderModelList.get(position).getQty6()));

        String name7 = String.valueOf(new StringBuilder().append(personalOrderModelList.get(position).getName7()));
        String qty7 =  String.valueOf(new StringBuilder().append(personalOrderModelList.get(position).getQty7()));

        String name8 = String.valueOf(new StringBuilder().append(personalOrderModelList.get(position).getName8()));
        String qty8 =  String.valueOf(new StringBuilder().append(personalOrderModelList.get(position).getQty8()));



        if (holder.txtStat.getText().toString().equals("Pending")){
            holder.btnCORD.setVisibility(View.VISIBLE);
            holder.btnreceived.setVisibility(View.GONE);
            holder.txtStat.setTextColor(Color.parseColor("#FF0000"));
        }else if (holder.txtStat.getText().toString().equals("On Process")){
            holder.btnCORD.setVisibility(View.GONE);
            holder.btnreceived.setVisibility(View.GONE);
            holder.txtStat.setTextColor(Color.parseColor("#edb009"));
        }else if(holder.txtStat.getText().toString().equals("Finish")){
            holder.btnCORD.setVisibility(View.GONE);
            holder.btnreceived.setVisibility(View.GONE);
            holder.txtStat.setTextColor(Color.parseColor("#4CAF50"));
        }else if(holder.txtStat.getText().toString().equals("On-going Delivery")){
            holder.btnCORD.setVisibility(View.GONE);
            holder.btnreceived.setVisibility(View.VISIBLE);
            holder.txtStat.setTextColor(Color.parseColor("#FF018786"));
        }else if(holder.txtStat.getText().toString().equals("Cancelled")){
            holder.btnCORD.setVisibility(View.GONE);
            holder.btnreceived.setVisibility(View.GONE);
            holder.txtStat.setTextColor(Color.parseColor("#FF0000"));
        }

        reff2 = FirebaseDatabase.getInstance().getReference().child("Cancel").child(holder.txtOrderDate.getText().toString()).child(String.valueOf(maxid+1));
        reff2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reff4 = FirebaseDatabase.getInstance().getReference().child("Data").child("FinishID");
        reff4.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    finishmaxid=(snapshot.getChildrenCount());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reff5= FirebaseDatabase.getInstance().getReference().child("Finish").child(holder.txtOrderDate.getText().toString());
        reff5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reff3 = FirebaseDatabase.getInstance().getReference();

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
        holder.btnreceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.setContentView(R.layout.order_received_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCanceledOnTouchOutside(false);

                Button btnok = dialog.findViewById(R.id.btn_finish);
                Button notyet = dialog.findViewById(R.id.btn_nope);

                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatabaseReference rideref = FirebaseDatabase.getInstance().getReference()
                                .child("Admin")
                                .child("riders");

                        DatabaseReference reffUsers = FirebaseDatabase.getInstance().getReference()
                                .child("Users")
                                .child(currentuser)
                                .child("OrderHistory")
                                .child(holder.txtPID.getText().toString());

                        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Users").child(currentuser).child("OrderHistory");
                        Query query = reference2.orderByKey().equalTo(holder.txtPID.getText().toString());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    if(snapshot.child("name1").getValue()==null){
                                        reff2.getRef().child("name1").removeValue();
                                        reff2.getRef().child("qty1").removeValue();
                                    }else{
                                        personalOrderModel.setName1(name1);
                                        personalOrderModel.setQty1(Integer.parseInt(qty1));
                                    }
                                    if(snapshot.child("name2").getValue()==null){
                                        reff2.getRef().child("name2").removeValue();
                                        reff2.getRef().child("qty2").removeValue();
                                    }else{
                                        personalOrderModel.setName2(name2);
                                        personalOrderModel.setQty2(Integer.parseInt(qty2));
                                    }
                                    if(snapshot.child("name3").getValue()==null){
                                        reff2.getRef().child("name3").removeValue();
                                        reff2.getRef().child("qty3").removeValue();
                                    }else{
                                        personalOrderModel.setName3(name3);
                                        personalOrderModel.setQty3(Integer.parseInt(qty3));
                                    }

                                    if(snapshot.child("name4").getValue()==null){
                                        reff2.getRef().child("name4").removeValue();
                                        reff2.getRef().child("qty4").removeValue();
                                    }else{
                                        personalOrderModel.setName4(name4);
                                        personalOrderModel.setQty4(Integer.parseInt(qty4));
                                    }
                                    if(snapshot.child("name5").getValue()==null){
                                        reff2.getRef().child("name5").removeValue();
                                        reff2.getRef().child("qty5").removeValue();
                                    }else{
                                        personalOrderModel.setName5(name5);
                                        personalOrderModel.setQty5(Integer.parseInt(qty5));
                                    }
                                    if(snapshot.child("name6").getValue()==null){
                                        reff2.getRef().child("name6").removeValue();
                                        reff2.getRef().child("qty6").removeValue();
                                    }else{
                                        personalOrderModel.setName6(name6);
                                        personalOrderModel.setQty6(Integer.parseInt(qty6));
                                    }
                                    if(snapshot.child("name7").getValue()==null){
                                        reff2.getRef().child("name7").removeValue();
                                        reff2.getRef().child("qty7").removeValue();
                                    }else{
                                        personalOrderModel.setName7(name7);
                                        personalOrderModel.setQty7(Integer.parseInt(qty7));
                                    }
                                    if(snapshot.child("name8").getValue()==null){
                                        reff2.getRef().child("name8").removeValue();
                                        reff2.getRef().child("qty8").removeValue();
                                    }else{
                                        personalOrderModel.setName8(name8);
                                        personalOrderModel.setQty8(Integer.parseInt(qty8));
                                    }


                                    personalOrderModel.setTotalPrice(Float.parseFloat(holder.txtTotalOrderP.getText().toString().trim()));
                                    personalOrderModel.setAddress(holder.txtAddress.getText().toString().trim());
                                    personalOrderModel.setStatus("Finish");
                                    personalOrderModel.setOrderdate(getTodaysDate());
                                    personalOrderModel.setTime_in(holder.time.getText().toString().trim());
                                    personalOrderModel.setTime_out(dateTime);
                                    personalOrderModel.setCustomerName(holder.txtCustomerName.getText().toString().trim());
                                    personalOrderModel.setPhonenum(holder.txtPhone.getText().toString().trim());
                                    reffUsers.child("status").setValue("Finish");
                                    rideref.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            String Rider1 = snapshot.child("Rider1").child("DeliveryList").child(holder.txtIDNUM.getText().toString()).child("key").getValue(String.class);
                                            String Rider2 = snapshot.child("Rider2").child("DeliveryList").child(holder.txtIDNUM.getText().toString()).child("key").getValue(String.class);
                                            String Rider3 = snapshot.child("Rider3").child("DeliveryList").child(holder.txtIDNUM.getText().toString()).child("key").getValue(String.class);


                                            if(Rider1!=null){
                                                DatabaseReference TotalSales = FirebaseDatabase.getInstance().getReference("Admin")
                                                        .child("riders").child("Rider1")
                                                        .child("TotalSales")
                                                        .child(holder.txtOrderDate.getText().toString())
                                                        .child("totalsales");
                                                TotalSales.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        if(snapshot.exists()){
                                                            double total = Double.parseDouble(holder.txtTotalOrderP.getText().toString());
                                                            double oldtotal = snapshot.getValue(Double.class);
                                                            total = total + oldtotal;
                                                            rideref.child("Rider1").child("TotalSales").child(holder.txtOrderDate.getText().toString()).child("totalsales").setValue(total);
                                                        }else{
                                                            rideref.child("Rider1").child("TotalSales").child(holder.txtOrderDate.getText().toString()).child("totalsales").setValue(Double.parseDouble(holder.txtTotalOrderP.getText().toString()));
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                                rideref.child("Rider1").child("DeliveryList").child(holder.txtIDNUM.getText().toString()).removeValue();
                                            }else if(Rider2!=null){
                                                DatabaseReference TotalSales = FirebaseDatabase.getInstance().getReference("Admin")
                                                        .child("riders").child("Rider2")
                                                        .child("TotalSales")
                                                        .child(holder.txtOrderDate.getText().toString())
                                                        .child("totalsales");
                                                TotalSales.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        if(snapshot.exists()){
                                                            double total = Double.parseDouble(holder.txtTotalOrderP.getText().toString());
                                                            double oldtotal = snapshot.getValue(Double.class);
                                                            total = total + oldtotal;
                                                            rideref.child("Rider2").child("TotalSales").child(holder.txtOrderDate.getText().toString()).child("totalsales").setValue(total);
                                                        }else{
                                                            rideref.child("Rider2").child("TotalSales").child(holder.txtOrderDate.getText().toString()).child("totalsales").setValue(Double.parseDouble(holder.txtTotalOrderP.getText().toString()));
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                                rideref.child("Rider2").child("DeliveryList").child(holder.txtIDNUM.getText().toString()).removeValue();
                                            }else if(Rider3!=null){
                                                DatabaseReference TotalSales = FirebaseDatabase.getInstance().getReference("Admin")
                                                        .child("riders").child("Rider3")
                                                        .child("TotalSales")
                                                        .child(holder.txtOrderDate.getText().toString())
                                                        .child("totalsales");
                                                TotalSales.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        if(snapshot.exists()){
                                                            double total = Double.parseDouble(holder.txtTotalOrderP.getText().toString());
                                                            double oldtotal = snapshot.getValue(Double.class);
                                                            total = total + oldtotal;
                                                            rideref.child("Rider3").child("TotalSales").child(holder.txtOrderDate.getText().toString()).child("totalsales").setValue(total);
                                                        }else{
                                                            rideref.child("Rider3").child("TotalSales").child(holder.txtOrderDate.getText().toString()).child("totalsales").setValue(Double.parseDouble(holder.txtTotalOrderP.getText().toString()));
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                                rideref.child("Rider3").child("DeliveryList").child(holder.txtIDNUM.getText().toString()).removeValue();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    reff3.child("Finish").child(holder.txtOrderDate.getText().toString().trim()).child(holder.txtIDNUM.getText().toString()).setValue(personalOrderModel);
                                    reff3.child("Delivered").child(holder.txtIDNUM.getText().toString()).removeValue();
                                    FirebaseDatabase.getInstance().getReference("Users")
                                                        .child(currentuser)
                                                        .child("Notifications")
                                                        .push().setValue(new Notification(
                                                                "Order Status",
                                                                "Order Completed"
                                                        ));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        Intent intent = new Intent(context, PersonalOrderActivity.class);
                        ((PersonalOrderActivity)context).finish();
                        ((PersonalOrderActivity) context).overridePendingTransition(0,0);
                        context.startActivity(intent);
                        ((PersonalOrderActivity) context).overridePendingTransition(0,0);
                        personalOrderModelList.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        notifyItemRangeChanged(holder.getAdapterPosition(), personalOrderModelList.size());
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

        holder.btnCORD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reff = FirebaseDatabase.getInstance().getReference().child("Cancel").child(holder.txtOrderDate.getText().toString());
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
                dialog.setContentView(R.layout.cancel_order_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCanceledOnTouchOutside(false);

                ProgressBar progressBar = dialog.findViewById(R.id.progressbarDeliver);

                Button btnok = dialog.findViewById(R.id.btn_cancel);
                Button notyet = dialog.findViewById(R.id.btn_dont_cancel);
                TextView N1,N2,N3,N4,N5,N6,N7,N8,Address;
                N1 = dialog.findViewById(R.id.txtCName1);
                N2 = dialog.findViewById(R.id.txtCName2);
                N3 = dialog.findViewById(R.id.txtCName3);
                N4 = dialog.findViewById(R.id.txtCName4);
                N5 = dialog.findViewById(R.id.txtCName5);
                N6 = dialog.findViewById(R.id.txtCName6);
                N7 = dialog.findViewById(R.id.txtCName7);
                N8 = dialog.findViewById(R.id.txtCName8);
                Address = dialog.findViewById(R.id.txtCancelAddress);

                Address.setText(new StringBuilder().append(personalOrderModelList.get(holder.getAdapterPosition()).getAddress()));

                if(personalOrderModelList.get(holder.getAdapterPosition()).getName1() == null){
                    N1.setVisibility(View.GONE);
                }else{
                    N1.setText(new StringBuilder().append(personalOrderModelList.get(holder.getAdapterPosition()).getName1()));
                }
                if(personalOrderModelList.get(holder.getAdapterPosition()).getName2() == null){
                    N2.setVisibility(View.GONE);
                }else{
                    N2.setText(new StringBuilder().append(personalOrderModelList.get(holder.getAdapterPosition()).getName2()));
                }
                if(personalOrderModelList.get(holder.getAdapterPosition()).getName3() == null){
                    N3.setVisibility(View.GONE);
                }else{
                    N3.setText(new StringBuilder().append(personalOrderModelList.get(holder.getAdapterPosition()).getName3()));
                }
                if(personalOrderModelList.get(holder.getAdapterPosition()).getName4() == null){
                    N4.setVisibility(View.GONE);
                }else{
                    N4.setText(new StringBuilder().append(personalOrderModelList.get(holder.getAdapterPosition()).getName4()));
                }
                if(personalOrderModelList.get(holder.getAdapterPosition()).getName5() == null){
                    N5.setVisibility(View.GONE);
                }else{
                    N5.setText(new StringBuilder().append(personalOrderModelList.get(holder.getAdapterPosition()).getName5()));
                }
                if(personalOrderModelList.get(holder.getAdapterPosition()).getName6() == null){
                    N6.setVisibility(View.GONE);
                }else{
                    N6.setText(new StringBuilder().append(personalOrderModelList.get(holder.getAdapterPosition()).getName6()));
                }
                if(personalOrderModelList.get(holder.getAdapterPosition()).getName7() == null){
                    N7.setVisibility(View.GONE);
                }else{
                    N7.setText(new StringBuilder().append(personalOrderModelList.get(holder.getAdapterPosition()).getName7()));
                }
                if(personalOrderModelList.get(holder.getAdapterPosition()).getName8() == null){
                    N8.setVisibility(View.GONE);
                }else{
                    N8.setText(new StringBuilder().append(personalOrderModelList.get(holder.getAdapterPosition()).getName8()));
                }
                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new CountDownTimer(3000, 1000) {
                            @Override
                            public void onTick(long l) {
                                btnok.setVisibility(View.GONE);
                                notyet.setVisibility(View.GONE);
                                progressBar.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onFinish() {

                                DatabaseReference refference1 = FirebaseDatabase.getInstance().getReference().child("Orders").child(getTodaysDate());

                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser);
                                Query query = reference.orderByKey().equalTo("OrderHistory");
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            String key = holder.txtIDNUM.getText().toString();
                                            if(snapshot.child(key).child("name1").getValue()==null){
                                                reff2.getRef().child("name1").removeValue();
                                                reff2.getRef().child("qty1").removeValue();
                                            }else{
                                                personalOrderModel.setName1(name1);
                                                personalOrderModel.setQty1(Integer.parseInt(qty1));
                                            }
                                            if(snapshot.child(key).child("name2").getValue()==null){
                                                reff2.getRef().child("name2").removeValue();
                                                reff2.getRef().child("qty2").removeValue();
                                            }else{
                                                personalOrderModel.setName2(name2);
                                                personalOrderModel.setQty2(Integer.parseInt(qty2));
                                            }
                                            if(snapshot.child(key).child("name3").getValue()==null){
                                                reff2.getRef().child("name3").removeValue();
                                                reff2.getRef().child("qty3").removeValue();
                                            }else{
                                                personalOrderModel.setName3(name3);
                                                personalOrderModel.setQty3(Integer.parseInt(qty3));
                                            }

                                            if(snapshot.child(key).child("name4").getValue()==null){
                                                reff2.getRef().child("name4").removeValue();
                                                reff2.getRef().child("qty4").removeValue();
                                            }else{
                                                personalOrderModel.setName4(name4);
                                                personalOrderModel.setQty4(Integer.parseInt(qty4));
                                            }
                                            if(snapshot.child(key).child("name5").getValue()==null){
                                                reff2.getRef().child("name5").removeValue();
                                                reff2.getRef().child("qty5").removeValue();
                                            }else{
                                                personalOrderModel.setName5(name5);
                                                personalOrderModel.setQty5(Integer.parseInt(qty5));
                                                int st5 = Integer.parseInt(stock5);
                                                String UpdatedStock = String.valueOf(st5+Integer.parseInt(qty5));
                                                NewDrink.child("05").child("stocks").setValue(UpdatedStock);
                                            }
                                            if(snapshot.child(key).child("name6").getValue()==null){
                                                reff2.getRef().child("name6").removeValue();
                                                reff2.getRef().child("qty6").removeValue();
                                            }else{
                                                personalOrderModel.setName6(name6);
                                                personalOrderModel.setQty6(Integer.parseInt(qty6));
                                                int st6 = Integer.parseInt(stock6);
                                                String UpdatedStock = String.valueOf(st6+Integer.parseInt(qty6));
                                                NewDrink.child("06").child("stocks").setValue(UpdatedStock);
                                            }
                                            if(snapshot.child(key).child("name7").getValue()==null){
                                                reff2.getRef().child("name7").removeValue();
                                                reff2.getRef().child("qty7").removeValue();
                                            }else{
                                                personalOrderModel.setName7(name7);
                                                personalOrderModel.setQty7(Integer.parseInt(qty7));
                                                int st7 = Integer.parseInt(stock7);
                                                String UpdatedStock = String.valueOf(st7+Integer.parseInt(qty7));
                                                NewDrink.child("07").child("stocks").setValue(UpdatedStock);
                                            }
                                            if(snapshot.child(key).child("name8").getValue()==null){
                                                reff2.getRef().child("name8").removeValue();
                                                reff2.getRef().child("qty8").removeValue();
                                            }else{
                                                personalOrderModel.setName8(name8);
                                                personalOrderModel.setQty8(Integer.parseInt(qty8));
                                                int st8 = Integer.parseInt(stock8);
                                                String UpdatedStock = String.valueOf(st8+Integer.parseInt(qty8));
                                                NewDrink.child("08").child("stocks").setValue(UpdatedStock);
                                            }

                                            personalOrderModel.setAddress(String.valueOf(snapshot.child(key).child("address").getValue()));
                                            personalOrderModel.setTotalPrice(Float.parseFloat((snapshot.child(key).child("totalPrice").getValue().toString())));
                                            personalOrderModel.setStatus("Cancelled");
                                            personalOrderModel.setOrderdate(holder.txtOrderDate.getText().toString());
                                            personalOrderModel.setPhonenum(holder.txtPhone.getText().toString().trim());
                                            personalOrderModel.setCustomerName(holder.txtCustomerName.getText().toString().trim());
                                            reff.child(String.valueOf(maxid + 1)).setValue(personalOrderModel);
                                            String key2 = holder.txtIDNUM.getText().toString();
                                            String value = String.valueOf(snapshot.child(key2).child("key").getValue());
                                            snapshot.child(key2).getRef().removeValue().addOnSuccessListener(aVoid ->  EventBus.getDefault().postSticky(new MyUpdateCartEvent()));
                                            Intent intent = new Intent(context, PersonalOrderActivity.class);
                                            ((PersonalOrderActivity)context).finish();
                                            ((PersonalOrderActivity) context).overridePendingTransition(0,0);
                                            context.startActivity(intent);
                                            ((PersonalOrderActivity) context).overridePendingTransition(0,0);
                                            refference1.child(value).removeValue();

                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                personalOrderModelList.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                notifyItemRangeChanged(holder.getAdapterPosition(),personalOrderModelList.size());

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
        return personalOrderModelList.size();
    }
    public class MyPersonalViewHolder extends RecyclerView.ViewHolder {

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
        @BindView(R.id.orderdate)
        TextView txtOrderDate;
        @BindView(R.id.idnum)
        TextView txtIDNUM;
        @BindView(R.id.btncancelorder)
        Button btnCORD;
        @BindView(R.id.btnOrderReceived)
        Button btnreceived;
        @BindView(R.id.pid)
        TextView txtPID;
        @BindView(R.id.custname)
        TextView txtCustomerName;
        @BindView(R.id.phonenome)
        TextView txtPhone;
        @BindView(R.id.time)
        TextView time;


        Unbinder unbinder;

        public MyPersonalViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
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
