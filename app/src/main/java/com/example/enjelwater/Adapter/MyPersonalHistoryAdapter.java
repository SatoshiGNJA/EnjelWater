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
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyPersonalHistoryAdapter extends RecyclerView.Adapter<MyPersonalHistoryAdapter.MyPersonalViewHolder> {

    private Context context;
    private List<PersonalOrderModel> personalOrderModelList;
    Dialog dialog;
    long maxid=0;
    long finishmaxid = 0;
    DatabaseReference reff,reff2,reff3,reff4,reff5;
    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
    PersonalOrderModel personalOrderModel;

    Calendar calendar = Calendar.getInstance();
    String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

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

        reff = FirebaseDatabase.getInstance().getReference().child("Cancel").child(currentDate);
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
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
        reff2 = FirebaseDatabase.getInstance().getReference().child("Cancel").child(currentDate).child(String.valueOf(maxid+1));
        reff2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reff5= FirebaseDatabase.getInstance().getReference().child("Finish").child(currentDate).child(String.valueOf(maxid + 1));
        reff5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reff3 = FirebaseDatabase.getInstance().getReference();

        if(personalOrderModelList.get(position).getName1() == null){
            holder.txtN1.setVisibility(View.GONE);
        }else{
            holder.txtN1.setText(new StringBuilder().append(personalOrderModelList.get(position).getName1()));
        }
        if(personalOrderModelList.get(position).getName2() == null){
            holder.txtN2.setVisibility(View.GONE);
        }else{
            holder.txtN2.setText(new StringBuilder().append(personalOrderModelList.get(position).getName2()));
        }
        if(personalOrderModelList.get(position).getName3() == null){
            holder.txtN3.setVisibility(View.GONE);
        }else{
            holder.txtN3.setText(new StringBuilder().append(personalOrderModelList.get(position).getName3()));
        }
        if(personalOrderModelList.get(position).getName4() == null){
            holder.txtN4.setVisibility(View.GONE);
        }else{
            holder.txtN4.setText(new StringBuilder().append(personalOrderModelList.get(position).getName4()));
        }
        if(personalOrderModelList.get(position).getName5() == null){
            holder.txtN5.setVisibility(View.GONE);
        }else{
            holder.txtN5.setText(new StringBuilder().append(personalOrderModelList.get(position).getName5()));
        }
        if(personalOrderModelList.get(position).getName6() == null){
            holder.txtN6.setVisibility(View.GONE);
        }else{
            holder.txtN6.setText(new StringBuilder().append(personalOrderModelList.get(position).getName6()));
        }
        if(personalOrderModelList.get(position).getName7() == null){
            holder.txtN7.setVisibility(View.GONE);
        }else{
            holder.txtN7.setText(new StringBuilder().append(personalOrderModelList.get(position).getName7()));
        }
        if(personalOrderModelList.get(position).getName8() == null){
            holder.txtN8.setVisibility(View.GONE);
        }else{
            holder.txtN8.setText(new StringBuilder().append(personalOrderModelList.get(position).getName8()));
        }
        holder.txtAddress.setText(new StringBuilder("Address: ").append(personalOrderModelList.get(position).getAddress()));
        holder.txtStat.setText(new StringBuilder().append(personalOrderModelList.get(position).getStatus()));
        holder.txtStat.setTextColor(Color.parseColor("#FF0000"));
        holder.txtTotalOrderP.setText(new StringBuilder().append(personalOrderModelList.get(position).getTotalPrice()));
        holder.txtOrderDate.setText(new StringBuilder().append(personalOrderModelList.get(position).getOrderdate()));
        holder.txtPID.setText(new StringBuilder().append(personalOrderModelList.get(position).getPersonalID()));
        holder.txtIDNUM.setText(new StringBuilder().append(personalOrderModelList.get(position).getKey()));
        holder.txtCustomerName.setText(new StringBuilder().append(personalOrderModelList.get(position).getCustname()));
        holder.txtPhone.setText(new StringBuilder().append(personalOrderModelList.get(position).getPhonenum()));


        if (holder.txtStat.getText().toString().equals("On Process")){
            holder.btnCORD.setVisibility(View.GONE);
            holder.btnreceived.setVisibility(View.GONE);
            holder.txtStat.setTextColor(Color.parseColor("#8B8000"));
        }else if(holder.txtStat.getText().toString().equals("Finish")){
            holder.btnCORD.setVisibility(View.GONE);
            holder.btnreceived.setVisibility(View.GONE);
            holder.txtStat.setTextColor(Color.parseColor("#00FF00"));
        }else if(holder.txtStat.getText().toString().equals("On-going Delivery")){
            holder.btnCORD.setVisibility(View.GONE);
            holder.txtStat.setTextColor(Color.parseColor("#00FF00"));
        }
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
                                        reff5.getRef().child("name1").removeValue();
                                    }else{
                                        personalOrderModel.setName1(holder.txtN1.getText().toString());
                                    }
                                    if(snapshot.child("name2").getValue()==null){
                                        reff5.getRef().child("name2").removeValue();
                                    }else{
                                        personalOrderModel.setName2(holder.txtN2.getText().toString());
                                    }
                                    if(snapshot.child("name3").getValue()==null){
                                        reff5.getRef().child("name3").removeValue();
                                    }else{
                                        personalOrderModel.setName3(holder.txtN3.getText().toString());
                                    }

                                    if(snapshot.child("name4").getValue()==null){
                                        reff5.getRef().child("name4").removeValue();
                                    }else{
                                        personalOrderModel.setName4(holder.txtN4.getText().toString());
                                    }
                                    if(snapshot.child("name5").getValue()==null){
                                        reff5.getRef().child("name5").removeValue();
                                    }else{
                                        personalOrderModel.setName5(holder.txtN5.getText().toString());
                                    }
                                    if(snapshot.child("name6").getValue()==null){
                                        reff5.getRef().child("name6").removeValue();
                                    }else{
                                        personalOrderModel.setName6(holder.txtN6.getText().toString());
                                    }
                                    if(snapshot.child("name7").getValue()==null){
                                        reff5.getRef().child("name7").removeValue();
                                    }else{
                                        personalOrderModel.setName7(holder.txtN7.getText().toString());
                                    }
                                    if(snapshot.child("name8").getValue()==null){
                                        reff5.getRef().child("name8").removeValue();
                                    }else{
                                        personalOrderModel.setName8(holder.txtN8.getText().toString());
                                    }


                                    personalOrderModel.setTotalPrice(Float.parseFloat(holder.txtTotalOrderP.getText().toString().trim()));
                                    personalOrderModel.setAddress(holder.txtAddress.getText().toString().trim());
                                    personalOrderModel.setStatus("Finish");
                                    personalOrderModel.setCustomerName(holder.txtCustomerName.getText().toString().trim());
                                    personalOrderModel.setPhonenum(holder.txtPhone.getText().toString().trim());
                                    reffUsers.child("status").setValue("Finish");
                                    reff3.child("Finish").child(holder.txtOrderDate.getText().toString().trim()).child(holder.txtIDNUM.getText().toString()).setValue(personalOrderModel);
                                    reff3.child("Delivered").child(holder.txtOrderDate.getText().toString().trim()).child(holder.txtIDNUM.getText().toString()).removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
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
                dialog.setContentView(R.layout.cancel_order_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCanceledOnTouchOutside(false);

                ProgressBar progressBar = dialog.findViewById(R.id.progressbarDeliver);

                Button btnok = dialog.findViewById(R.id.btn_cancel);
                Button notyet = dialog.findViewById(R.id.btn_dont_cancel);
                TextView N1,N2,N3,N4,N5,N6,N7,N8,Address,count;
                N1 = dialog.findViewById(R.id.txtCName1);
                N2 = dialog.findViewById(R.id.txtCName2);
                N3 = dialog.findViewById(R.id.txtCName3);
                N4 = dialog.findViewById(R.id.txtCName4);
                N5 = dialog.findViewById(R.id.txtCName5);
                N6 = dialog.findViewById(R.id.txtCName6);
                N7 = dialog.findViewById(R.id.txtCName7);
                N8 = dialog.findViewById(R.id.txtCName8);
                count = dialog.findViewById(R.id.txtcancelcount);
                Address = dialog.findViewById(R.id.txtCancelAddress);

                Address.setText(new StringBuilder("Address: ").append(personalOrderModelList.get(holder.getAdapterPosition()).getAddress()));

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
                new CountDownTimer(3000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        count.setText("Cancel Button\nwill be Visible in(" + millisUntilFinished / 1000+")");
                    }

                    public void onFinish() {
                        if(progressBar.isShown()) {

                            btnok.setVisibility(View.GONE);

                        }else{
                            btnok.setVisibility(View.VISIBLE);
                            count.setVisibility(View.GONE);
                        }
                    }

                }.start();
                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new CountDownTimer(3000, 1000) {
                            @Override
                            public void onTick(long l) {
                                btnok.setVisibility(View.GONE);
                                notyet.setVisibility(View.GONE);
                                count.setVisibility(View.GONE);
                                progressBar.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onFinish() {

                                DatabaseReference refference1 = FirebaseDatabase.getInstance().getReference().child("Orders").child(currentDate);

                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser);
                                Query query = reference.orderByKey().equalTo("OrderHistory");
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            String key = holder.txtIDNUM.getText().toString();
                                            if(snapshot.child(key).child("name1").getValue()==null){
                                                reff2.getRef().child("name1").removeValue();
                                            }else{
                                                personalOrderModel.setName1(holder.txtN1.getText().toString());
                                            }
                                            if(snapshot.child(key).child("name2").getValue()==null){
                                                reff2.getRef().child("name2").removeValue();
                                            }else{
                                                personalOrderModel.setName2(holder.txtN2.getText().toString());
                                            }
                                            if(snapshot.child(key).child("name3").getValue()==null){
                                                reff2.getRef().child("name3").removeValue();
                                            }else{
                                                personalOrderModel.setName3(holder.txtN3.getText().toString());
                                            }

                                            if(snapshot.child(key).child("name4").getValue()==null){
                                                reff2.getRef().child("name4").removeValue();
                                            }else{
                                                personalOrderModel.setName4(holder.txtN4.getText().toString());
                                            }
                                            if(snapshot.child(key).child("name5").getValue()==null){
                                                reff2.getRef().child("name5").removeValue();
                                            }else{
                                                personalOrderModel.setName5(holder.txtN5.getText().toString());
                                            }

                                            if(snapshot.child(key).child("name6").getValue()==null){
                                                reff2.getRef().child("name6").removeValue();
                                            }else{
                                                personalOrderModel.setName6(holder.txtN6.getText().toString());
                                            }

                                            if(snapshot.child(key).child("name7").getValue()==null){
                                                reff2.getRef().child("name7").removeValue();
                                            }else{
                                                personalOrderModel.setName7(holder.txtN7.getText().toString());
                                            }
                                            if(snapshot.child(key).child("name8").getValue()==null){
                                                reff2.getRef().child("name8").removeValue();
                                            }else{
                                                personalOrderModel.setName8(holder.txtN8.getText().toString());
                                            }



                                            personalOrderModel.setAddress(String.valueOf(snapshot.child(key).child("address").getValue()));
                                            personalOrderModel.setTotalPrice(Float.parseFloat((snapshot.child(key).child("totalPrice").getValue().toString())));
                                            personalOrderModel.setStatus("Cancel");
                                            personalOrderModel.setPhonenum(holder.txtPhone.getText().toString().trim());
                                            personalOrderModel.setCustomerName(holder.txtCustomerName.getText().toString().trim());
                                            reff.child(String.valueOf(maxid + 1)).setValue(personalOrderModel);
                                            String key2 = holder.txtIDNUM.getText().toString();
                                            String value = String.valueOf(snapshot.child(key2).child("key").getValue());
                                            snapshot.child(key2).getRef().removeValue().addOnSuccessListener(aVoid ->  EventBus.getDefault().postSticky(new MyUpdateCartEvent()));
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


        Unbinder unbinder;

        public MyPersonalViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
        }
    }
    private void deleteFromFirebase(PersonalOrderModel personalOrderModel) {
        FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(currentuser)
                .child("OrderHistory")
                .child(personalOrderModel.getKey())
                .removeValue()
                .addOnSuccessListener(aVoid ->  EventBus.getDefault().postSticky(new MyUpdateCartEvent()));

    }
}
