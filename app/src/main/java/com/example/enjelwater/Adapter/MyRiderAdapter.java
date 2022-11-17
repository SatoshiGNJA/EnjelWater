package com.example.enjelwater.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.enjelwater.EventBus.MyUpdateCartEvent;
import com.example.enjelwater.Model.DeliverModel;
import com.example.enjelwater.Model.Notification;
import com.example.enjelwater.Model.RiderModel;
import com.example.enjelwater.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyRiderAdapter extends RecyclerView.Adapter<MyRiderAdapter.MyRiderHolder>{

    private Context context;

    private List<RiderModel> riderModelList;

    DatabaseReference TotalSales, reff, reff2;

    RiderModel riderModel;

    Dialog dialog;

    String rider1,rider2,rider3;






    public MyRiderAdapter(Context context, List<RiderModel> riderModelList) {
        this.context = context;
        this.riderModelList = riderModelList;
    }

    @NonNull
    @Override
    public MyRiderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyRiderHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_rider_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyRiderHolder holder, int position) {

        String dateTime;
        Calendar calendar;
        SimpleDateFormat simpleDateFormat;

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("hh:mm aaa");
        dateTime = simpleDateFormat.format(calendar.getTime());

        riderModel = new RiderModel();

        dialog = new Dialog(context);

        String name = riderModelList.get(position).getRidername();

        reff = FirebaseDatabase.getInstance().getReference();

        DatabaseReference riderreff = FirebaseDatabase.getInstance().getReference().child("Admin").child("riders");
                riderreff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        rider1 = snapshot.child("Rider1").child("name").getValue(String.class);
                        rider2 = snapshot.child("Rider2").child("name").getValue(String.class);
                        rider3 = snapshot.child("Rider3").child("name").getValue(String.class);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        if(riderModelList.get(position).getName1() == null){
            holder.txtN1.setVisibility(View.GONE);
        }else{
            holder.txtN1.setVisibility(View.VISIBLE);
            holder.txtN1.setText(new StringBuilder().append(riderModelList.get(position).getName1()).append(" x").append(riderModelList.get(position).getQty1()));
        }
        if(riderModelList.get(position).getName2() == null){
            holder.txtN2.setVisibility(View.GONE);
        }else{
            holder.txtN2.setVisibility(View.VISIBLE);
            holder.txtN2.setText(new StringBuilder().append(riderModelList.get(position).getName2()).append(" x").append(riderModelList.get(position).getQty2()));
        }
        if(riderModelList.get(position).getName3() == null){
            holder.txtN3.setVisibility(View.GONE);
        }else{
            holder.txtN3.setVisibility(View.VISIBLE);
            holder.txtN3.setText(new StringBuilder().append(riderModelList.get(position).getName3()).append(" x").append(riderModelList.get(position).getQty3()));
        }
        if(riderModelList.get(position).getName4() == null){
            holder.txtN4.setVisibility(View.GONE);
        }else{
            holder.txtN4.setVisibility(View.VISIBLE);
            holder.txtN4.setText(new StringBuilder().append(riderModelList.get(position).getName4()).append(" x").append(riderModelList.get(position).getQty4()));
        }
        if(riderModelList.get(position).getName5() == null){
            holder.txtN5.setVisibility(View.GONE);
        }else{
            holder.txtN5.setVisibility(View.VISIBLE);
            holder.txtN5.setText(new StringBuilder().append(riderModelList.get(position).getName5()).append(" x").append(riderModelList.get(position).getQty5()));
        }
        if(riderModelList.get(position).getName6() == null){
            holder.txtN6.setVisibility(View.GONE);
        }else{
            holder.txtN6.setVisibility(View.VISIBLE);
            holder.txtN6.setText(new StringBuilder().append(riderModelList.get(position).getName6()).append(" x").append(riderModelList.get(position).getQty6()));
        }
        if(riderModelList.get(position).getName7() == null){
            holder.txtN7.setVisibility(View.GONE);
        }else{
            holder.txtN7.setVisibility(View.VISIBLE);
            holder.txtN7.setText(new StringBuilder().append(riderModelList.get(position).getName7()).append(" x").append(riderModelList.get(position).getQty7()));
        }
        if(riderModelList.get(position).getName8() == null){
            holder.txtN8.setVisibility(View.GONE);
        }else{
            holder.txtN8.setVisibility(View.VISIBLE);
            holder.txtN8.setText(new StringBuilder().append(riderModelList.get(position).getName8()).append(" x").append(riderModelList.get(position).getQty8()));
        }
        holder.txtAddress.setText(new StringBuilder().append(riderModelList.get(position).getAddress()));
        holder.txtTotalOrderP.setText(String.format("%.2f",(riderModelList.get(position).getTotalPrice())));
        holder.txtIDNUM.setText(riderModelList.get(position).getKey());
        holder.txtPID.setText(riderModelList.get(position).getPID());
        holder.txtUID.setText(riderModelList.get(position).getUID());
        holder.customer.setText(riderModelList.get(position).getCustomerName());
        holder.phone.setText(riderModelList.get(position).getPhonenum());
        holder.date.setText(riderModelList.get(position).getOrderdate());
        holder.time.setText(riderModelList.get(position).getTime_in());


        String name1 = String.valueOf(new StringBuilder().append(riderModelList.get(position).getName1()));
        String qty1 =  String.valueOf(new StringBuilder().append(riderModelList.get(position).getQty1()));

        String name2 = String.valueOf(new StringBuilder().append(riderModelList.get(position).getName2()));
        String qty2 =  String.valueOf(new StringBuilder().append(riderModelList.get(position).getQty2()));

        String name3 = String.valueOf(new StringBuilder().append(riderModelList.get(position).getName3()));
        String qty3 =  String.valueOf(new StringBuilder().append(riderModelList.get(position).getQty3()));

        String name4 = String.valueOf(new StringBuilder().append(riderModelList.get(position).getName4()));
        String qty4 =  String.valueOf(new StringBuilder().append(riderModelList.get(position).getQty4()));

        String name5 = String.valueOf(new StringBuilder().append(riderModelList.get(position).getName5()));
        String qty5 =  String.valueOf(new StringBuilder().append(riderModelList.get(position).getQty5()));

        String name6 = String.valueOf(new StringBuilder().append(riderModelList.get(position).getName6()));
        String qty6 =  String.valueOf(new StringBuilder().append(riderModelList.get(position).getQty6()));

        String name7 = String.valueOf(new StringBuilder().append(riderModelList.get(position).getName7()));
        String qty7 =  String.valueOf(new StringBuilder().append(riderModelList.get(position).getQty7()));

        String name8 = String.valueOf(new StringBuilder().append(riderModelList.get(position).getName8()));
        String qty8 =  String.valueOf(new StringBuilder().append(riderModelList.get(position).getQty8()));



        holder.btnReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.finish_rider_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCanceledOnTouchOutside(false);

                Button btnok = dialog.findViewById(R.id.btn_rider_finish);
                Button notyet = dialog.findViewById(R.id.btn_rider_nope);


                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseReference reffUsers = FirebaseDatabase.getInstance().getReference()
                                .child("Users")
                                .child(holder.txtUID.getText().toString())
                                .child("OrderHistory")
                                .child(holder.txtPID.getText().toString());
                        reff2 = FirebaseDatabase.getInstance().getReference()
                                .child("Finish")
                                .child(holder.date.getText().toString())
                                .child(holder.txtIDNUM.getText().toString());

                        if (name.equals(rider1)){
                            TotalSales = FirebaseDatabase.getInstance().getReference("Admin").child("riders").child("Rider1")
                                    .child("TotalSales").child(holder.date.getText().toString()).child("totalsales");
                            TotalSales.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        double total = Double.parseDouble(holder.txtTotalOrderP.getText().toString());
                                        double oldtotal = snapshot.getValue(Double.class);
                                        total = total + oldtotal;
                                        riderreff.child("Rider1").child("TotalSales").child(holder.date.getText().toString()).child("totalsales").setValue(total);
                                    }else{
                                        riderreff.child("Rider1").child("TotalSales").child(holder.date.getText().toString()).child("totalsales").setValue(Double.parseDouble(holder.txtTotalOrderP.getText().toString()));
                                    }
                                    riderreff.child("Rider1").child("DeliveryList").child(holder.txtIDNUM.getText().toString()).removeValue();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }else if(name.equals(rider2)){
                            TotalSales = FirebaseDatabase.getInstance().getReference("Admin").child("riders").child("Rider2")
                                    .child("TotalSales").child(holder.date.getText().toString()).child("totalsales");
                            TotalSales.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        double total = Double.parseDouble(holder.txtTotalOrderP.getText().toString());
                                        double oldtotal = snapshot.getValue(Double.class);
                                        total = total + oldtotal;
                                        riderreff.child("Rider2").child("TotalSales").child(holder.date.getText().toString()).child("totalsales").setValue(total);
                                    }else{
                                        riderreff.child("Rider2").child("TotalSales").child(holder.date.getText().toString()).child("totalsales").setValue(Double.parseDouble(holder.txtTotalOrderP.getText().toString()));
                                    }
                                    riderreff.child("Rider2").child("DeliveryList").child(holder.txtIDNUM.getText().toString()).removeValue();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }else if(name.equals(rider3)){
                            TotalSales = FirebaseDatabase.getInstance().getReference("Admin").child("riders").child("Rider3")
                                    .child("TotalSales").child(holder.date.getText().toString()).child("totalsales");
                            TotalSales.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        double total = Double.parseDouble(holder.txtTotalOrderP.getText().toString());
                                        double oldtotal = snapshot.getValue(Double.class);
                                        total = total + oldtotal;
                                        riderreff.child("Rider3").child("TotalSales").child(holder.date.getText().toString()).child("totalsales").setValue(total);
                                    }else{
                                        riderreff.child("Rider3").child("TotalSales").child(holder.date.getText().toString()).child("totalsales").setValue(Double.parseDouble(holder.txtTotalOrderP.getText().toString()));
                                    }
                                    riderreff.child("Rider3").child("DeliveryList").child(holder.txtIDNUM.getText().toString()).removeValue();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }

                        try{

                            DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
                            Query query = reference2.orderByKey().equalTo("Delivered");
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        String key = holder.txtIDNUM.getText().toString();
                                        if(snapshot.child(key).child("name1").getValue()==null){
                                            reff2.getRef().child("name1").removeValue();
                                            reff2.getRef().child("qty1").removeValue();
                                        }else{
                                            riderModel.setName1(name1);
                                            riderModel.setQty1(Integer.parseInt(qty1));
                                        }
                                        if(snapshot.child(key).child("name2").getValue()==null){
                                            reff2.getRef().child("name2").removeValue();
                                            reff2.getRef().child("qty2").removeValue();
                                        }else{
                                            riderModel.setName2(name2);
                                            riderModel.setQty2(Integer.parseInt(qty2));
                                        }
                                        if(snapshot.child(key).child("name3").getValue()==null){
                                            reff2.getRef().child("name3").removeValue();
                                            reff2.getRef().child("qty3").removeValue();
                                        }else{
                                            riderModel.setName3(name3);
                                            riderModel.setQty3(Integer.parseInt(qty3));
                                        }

                                        if(snapshot.child(key).child("name4").getValue()==null){
                                            reff2.getRef().child("name4").removeValue();
                                            reff2.getRef().child("qty4").removeValue();
                                        }else{
                                            riderModel.setName4(name4);
                                            riderModel.setQty4(Integer.parseInt(qty4));
                                        }
                                        if(snapshot.child(key).child("name5").getValue()==null){
                                            reff2.getRef().child("name5").removeValue();
                                            reff2.getRef().child("qty5").removeValue();
                                        }else{
                                            riderModel.setName5(name5);
                                            riderModel.setQty5(Integer.parseInt(qty5));

                                        }
                                        if(snapshot.child(key).child("name6").getValue()==null){
                                            reff2.getRef().child("name6").removeValue();
                                            reff2.getRef().child("qty6").removeValue();
                                        }else{
                                            riderModel.setName6(name6);
                                            riderModel.setQty6(Integer.parseInt(qty6));
                                        }
                                        if(snapshot.child(key).child("name7").getValue()==null){
                                            reff2.getRef().child("name7").removeValue();
                                            reff2.getRef().child("qty7").removeValue();
                                        }else{
                                            riderModel.setName7(name7);
                                            riderModel.setQty7(Integer.parseInt(qty7));
                                        }
                                        if(snapshot.child(key).child("name8").getValue()==null){
                                            reff2.getRef().child("name8").removeValue();
                                            reff2.getRef().child("qty8").removeValue();
                                        }else{
                                            riderModel.setName8(name8);
                                            riderModel.setQty8(Integer.parseInt(qty8));
                                        }
                                        riderModel.setAddress(String.valueOf(snapshot.child(key).child("address").getValue()));
                                        riderModel.setTotalPrice(Float.parseFloat(holder.txtTotalOrderP.getText().toString().trim()));
                                        riderModel.setCustomerName(holder.customer.getText().toString().trim());
                                        riderModel.setPhonenum(holder.phone.getText().toString().trim());
                                        riderModel.setStatus("Finish");
                                        riderModel.setTime_in(holder.time.getText().toString().trim());
                                        riderModel.setTime_out(dateTime);
                                        reffUsers.child("status").setValue("Finish");
                                        reff2.setValue(riderModel);
                                        reff.child("Delivered").child(holder.txtIDNUM.getText().toString()).removeValue();
                                        FirebaseDatabase.getInstance().getReference("Users")
                                                .child(holder.txtUID.getText().toString())
                                                .child("Notifications")
                                                .push().setValue(new Notification(
                                                        "Order Completed",
                                                        "Your order has been completed"
                                                ));
                                        snapshot.child(key).getRef().removeValue().addOnSuccessListener(aVoid -> EventBus.getDefault().postSticky(new MyUpdateCartEvent()));
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            riderModelList.remove(holder.getAdapterPosition());
                            notifyItemRemoved(holder.getAdapterPosition());
                            notifyItemRangeChanged(holder.getAdapterPosition(), riderModelList.size());
                            notifyDataSetChanged();
                            dialog.dismiss();

                        }catch (Exception exception){
                            System.out.println("catch");
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

    }

    @Override
    public int getItemCount() {
        return riderModelList.size();
    }

    public class MyRiderHolder extends RecyclerView.ViewHolder{

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
        @BindView(R.id.idnum)
        TextView txtIDNUM;
        @BindView(R.id.personalID)
        TextView txtPID;
        @BindView(R.id.uid)
        TextView txtUID;
        @BindView(R.id.btnOrderReceived)
        Button btnReceived;
        @BindView(R.id.txtcustnamedel)
        TextView customer;
        @BindView(R.id.txtphonedel)
        TextView phone;
        @BindView(R.id.orderdate)
        TextView date;
        @BindView(R.id.ordertime)
        TextView time;

        Unbinder unbinder;
        public MyRiderHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}
