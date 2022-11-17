package com.example.enjelwater.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.enjelwater.EventBus.MyUpdateCartEvent;
import com.example.enjelwater.MainActivity;
import com.example.enjelwater.Model.DeliverModel;
import com.example.enjelwater.Model.Notification;
import com.example.enjelwater.PersonalOrderActivity;
import com.example.enjelwater.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyOnProcessAdapter extends RecyclerView.Adapter<MyOnProcessAdapter.MyOrderHolder> {

    private Context context;
    private List<DeliverModel> deliverModelList;
    Calendar calendar = Calendar.getInstance();
    DatabaseReference reff, reff2,reff3;
    long maxid = 0;
    DeliverModel deliverModel;
    Dialog dialog;



    public MyOnProcessAdapter(Context context, List<DeliverModel> deliverModelList) {
        this.context = context;
        this.deliverModelList = deliverModelList;
    }

    @NonNull
    @Override
    public MyOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyOrderHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_onprocess_item, parent, false));


    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    public void onBindViewHolder(@NonNull MyOrderHolder holder, int position) {

        deliverModel=new DeliverModel();
        dialog = new Dialog(context);

        String dateTime;
        Calendar calendar;
        SimpleDateFormat simpleDateFormat;

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("hh:mm aaa");
        dateTime = simpleDateFormat.format(calendar.getTime());

        reff3 = FirebaseDatabase.getInstance().getReference();

        if(deliverModelList.get(position).getName1() == null){
            holder.txtN1.setVisibility(View.GONE);
        }else{
            holder.txtN1.setVisibility(View.VISIBLE);
            holder.txtN1.setText(new StringBuilder().append(deliverModelList.get(position).getName1()).append(" x").append(deliverModelList.get(position).getQty1()));
        }
        if(deliverModelList.get(position).getName2() == null){
            holder.txtN2.setVisibility(View.GONE);
        }else{
            holder.txtN2.setVisibility(View.VISIBLE);
            holder.txtN2.setText(new StringBuilder().append(deliverModelList.get(position).getName2()).append(" x").append(deliverModelList.get(position).getQty2()));
        }
        if(deliverModelList.get(position).getName3() == null){
            holder.txtN3.setVisibility(View.GONE);
        }else{
            holder.txtN3.setVisibility(View.VISIBLE);
            holder.txtN3.setText(new StringBuilder().append(deliverModelList.get(position).getName3()).append(" x").append(deliverModelList.get(position).getQty3()));
        }
        if(deliverModelList.get(position).getName4() == null){
            holder.txtN4.setVisibility(View.GONE);
        }else{
            holder.txtN4.setVisibility(View.VISIBLE);
            holder.txtN4.setText(new StringBuilder().append(deliverModelList.get(position).getName4()).append(" x").append(deliverModelList.get(position).getQty4()));
        }
        if(deliverModelList.get(position).getName5() == null){
            holder.txtN5.setVisibility(View.GONE);
        }else{
            holder.txtN5.setVisibility(View.VISIBLE);
            holder.txtN5.setText(new StringBuilder().append(deliverModelList.get(position).getName5()).append(" x").append(deliverModelList.get(position).getQty5()));
        }
        if(deliverModelList.get(position).getName6() == null){
            holder.txtN6.setVisibility(View.GONE);
        }else{
            holder.txtN6.setVisibility(View.VISIBLE);
            holder.txtN6.setText(new StringBuilder().append(deliverModelList.get(position).getName6()).append(" x").append(deliverModelList.get(position).getQty6()));
        }
        if(deliverModelList.get(position).getName7() == null){
            holder.txtN7.setVisibility(View.GONE);
        }else{
            holder.txtN7.setVisibility(View.VISIBLE);
            holder.txtN7.setText(new StringBuilder().append(deliverModelList.get(position).getName7()).append(" x").append(deliverModelList.get(position).getQty7()));
        }
        if(deliverModelList.get(position).getName8() == null){
            holder.txtN8.setVisibility(View.GONE);
        }else{
            holder.txtN8.setVisibility(View.VISIBLE);
            holder.txtN8.setText(new StringBuilder().append(deliverModelList.get(position).getName8()).append(" x").append(deliverModelList.get(position).getQty8()));
        }
        holder.txtAddress.setText(new StringBuilder().append(deliverModelList.get(position).getAddress()));
        holder.txtStat.setText(deliverModelList.get(position).getStatus());
        holder.txtStat.setTextColor(Color.parseColor("#FFA500"));
        holder.txtTotalOrderP.setText(String.format("%.2f",(deliverModelList.get(position).getTotalPrice())));
        holder.txtIDNUM.setText(deliverModelList.get(position).getKey());
        holder.txtPID.setText(deliverModelList.get(position).getPID());
        holder.txtUID.setText(deliverModelList.get(position).getUID());
        holder.customer.setText(deliverModelList.get(position).getCustomerName());
        holder.phone.setText(deliverModelList.get(position).getPhonenum());
        holder.date.setText(deliverModelList.get(position).getOrderdate());
        holder.rider.setText(deliverModelList.get(position).getRidername());
        holder.time.setText(deliverModelList.get(position).getTime_in());

        String name1 = String.valueOf(new StringBuilder().append(deliverModelList.get(position).getName1()));
        String qty1 =  String.valueOf(new StringBuilder().append(deliverModelList.get(position).getQty1()));

        String name2 = String.valueOf(new StringBuilder().append(deliverModelList.get(position).getName2()));
        String qty2 =  String.valueOf(new StringBuilder().append(deliverModelList.get(position).getQty2()));

        String name3 = String.valueOf(new StringBuilder().append(deliverModelList.get(position).getName3()));
        String qty3 =  String.valueOf(new StringBuilder().append(deliverModelList.get(position).getQty3()));

        String name4 = String.valueOf(new StringBuilder().append(deliverModelList.get(position).getName4()));
        String qty4 =  String.valueOf(new StringBuilder().append(deliverModelList.get(position).getQty4()));

        String name5 = String.valueOf(new StringBuilder().append(deliverModelList.get(position).getName5()));
        String qty5 =  String.valueOf(new StringBuilder().append(deliverModelList.get(position).getQty5()));

        String name6 = String.valueOf(new StringBuilder().append(deliverModelList.get(position).getName6()));
        String qty6 =  String.valueOf(new StringBuilder().append(deliverModelList.get(position).getQty6()));

        String name7 = String.valueOf(new StringBuilder().append(deliverModelList.get(position).getName7()));
        String qty7 =  String.valueOf(new StringBuilder().append(deliverModelList.get(position).getQty7()));

        String name8 = String.valueOf(new StringBuilder().append(deliverModelList.get(position).getName8()));
        String qty8 =  String.valueOf(new StringBuilder().append(deliverModelList.get(position).getQty8()));

        holder.btnprint.setOnClickListener(view -> {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("Name1", name1);
            intent.putExtra("Name2", name2);
            intent.putExtra("Name3", name3);
            intent.putExtra("Name4", name4);
            intent.putExtra("Name5", name5);
            intent.putExtra("Name6", name6);
            intent.putExtra("Name7", name7);
            intent.putExtra("Name8", name8);
            intent.putExtra("Qty1", Integer.parseInt(qty1));
            intent.putExtra("Qty2", Integer.parseInt(qty2));
            intent.putExtra("Qty3", Integer.parseInt(qty3));
            intent.putExtra("Qty4", Integer.parseInt(qty4));
            intent.putExtra("Qty5", Integer.parseInt(qty5));
            intent.putExtra("Qty6", Integer.parseInt(qty6));
            intent.putExtra("Qty7", Integer.parseInt(qty7));
            intent.putExtra("Qty8", Integer.parseInt(qty8));
            intent.putExtra("CustomerN", holder.customer.getText().toString());
            intent.putExtra("PhoneNum", holder.phone.getText().toString());
            intent.putExtra("Address", holder.txtAddress.getText().toString());
            intent.putExtra("Total", holder.txtTotalOrderP.getText().toString());
            intent.putExtra("Date", holder.date.getText().toString());
            intent.putExtra("Rider", holder.rider.getText().toString());
            context.startActivity(intent);

        });

        holder.btnOFD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.setContentView(R.layout.finish_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCanceledOnTouchOutside(false);

                Button btnok = dialog.findViewById(R.id.btn_finish);
                Button notyet = dialog.findViewById(R.id.btn_nope);

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
                                            deliverModel.setName1(name1);
                                            deliverModel.setQty1(Integer.parseInt(qty1));
                                        }
                                        if(snapshot.child(key).child("name2").getValue()==null){
                                            reff2.getRef().child("name2").removeValue();
                                            reff2.getRef().child("qty2").removeValue();
                                        }else{
                                            deliverModel.setName2(name2);
                                            deliverModel.setQty2(Integer.parseInt(qty2));
                                        }
                                        if(snapshot.child(key).child("name3").getValue()==null){
                                            reff2.getRef().child("name3").removeValue();
                                            reff2.getRef().child("qty3").removeValue();
                                        }else{
                                            deliverModel.setName3(name3);
                                            deliverModel.setQty3(Integer.parseInt(qty3));
                                        }

                                        if(snapshot.child(key).child("name4").getValue()==null){
                                            reff2.getRef().child("name4").removeValue();
                                            reff2.getRef().child("qty4").removeValue();
                                        }else{
                                            deliverModel.setName4(name4);
                                            deliverModel.setQty4(Integer.parseInt(qty4));
                                        }
                                        if(snapshot.child(key).child("name5").getValue()==null){
                                            reff2.getRef().child("name5").removeValue();
                                            reff2.getRef().child("qty5").removeValue();
                                        }else{
                                            deliverModel.setName5(name5);
                                            deliverModel.setQty5(Integer.parseInt(qty5));

                                        }
                                        if(snapshot.child(key).child("name6").getValue()==null){
                                            reff2.getRef().child("name6").removeValue();
                                            reff2.getRef().child("qty6").removeValue();
                                        }else{
                                            deliverModel.setName6(name6);
                                            deliverModel.setQty6(Integer.parseInt(qty6));
                                        }
                                        if(snapshot.child(key).child("name7").getValue()==null){
                                            reff2.getRef().child("name7").removeValue();
                                            reff2.getRef().child("qty7").removeValue();
                                        }else{
                                            deliverModel.setName7(name7);
                                            deliverModel.setQty7(Integer.parseInt(qty7));
                                        }
                                        if(snapshot.child(key).child("name8").getValue()==null){
                                            reff2.getRef().child("name8").removeValue();
                                            reff2.getRef().child("qty8").removeValue();
                                        }else{
                                            deliverModel.setName8(name8);
                                            deliverModel.setQty8(Integer.parseInt(qty8));
                                        }


                                        deliverModel.setAddress(String.valueOf(snapshot.child(key).child("address").getValue()));
                                        deliverModel.setTotalPrice(Float.parseFloat((snapshot.child(key).child("totalPrice").getValue().toString())));
                                        deliverModel.setCustomerName(holder.customer.getText().toString().trim());
                                        deliverModel.setPhonenum(holder.phone.getText().toString().trim());
                                        deliverModel.setStatus("Finish");
                                        deliverModel.setTime_in(holder.time.getText().toString().trim());
                                        deliverModel.setTime_out(dateTime);
                                        reffUsers.child("status").setValue("Finish");
                                        reff2.setValue(deliverModel);
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
                            deliverModelList.remove(holder.getAdapterPosition());
                            notifyItemRemoved(holder.getAdapterPosition());
                            notifyItemRangeChanged(holder.getAdapterPosition(), deliverModelList.size());
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
        return deliverModelList.size();
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
        @BindView(R.id.btnPrint)
        Button btnprint;
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
        @BindView(R.id.btnOutForDeliver)
        Button btnOFD;
        @BindView(R.id.hidewhenfinish)
        CardView hide;
        @BindView(R.id.txtcustnamedel)
        TextView customer;
        @BindView(R.id.txtphonedel)
        TextView phone;
        @BindView(R.id.orderdate)
        TextView date;
        @BindView(R.id.ridername)
        TextView rider;
        @BindView(R.id.ordertime)
        TextView time;

        Unbinder unbinder;

        public MyOrderHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}
