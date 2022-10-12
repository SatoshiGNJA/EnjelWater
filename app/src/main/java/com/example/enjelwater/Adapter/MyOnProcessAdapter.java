package com.example.enjelwater.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.enjelwater.EventBus.MyUpdateCartEvent;
import com.example.enjelwater.Listener.IDeliverLoadListener;
import com.example.enjelwater.Model.DeliverModel;
import com.example.enjelwater.Model.ProductModel;
import com.example.enjelwater.R;
import com.example.enjelwater.SummaryActivity;
import com.example.enjelwater.ThankYouActivity;
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

public class MyOnProcessAdapter extends RecyclerView.Adapter<MyOnProcessAdapter.MyOrderHolder> {

    private Context context;
    private List<DeliverModel> deliverModelList;
    Calendar calendar = Calendar.getInstance();
    DatabaseReference reff,reff2;
    String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
    long maxid=0;
    DeliverModel deliverModel;
    Dialog dialog;


    public MyOnProcessAdapter(Context context,List<DeliverModel> deliverModelList){
        this.context = context;
        this.deliverModelList = deliverModelList;


    }

    @NonNull
    @Override
    public MyOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyOrderHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_order_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderHolder holder, int position) {

        deliverModel=new DeliverModel();
        dialog=new Dialog(context);


        reff = FirebaseDatabase.getInstance().getReference().child("Finish").child(currentDate);
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
        reff2 = FirebaseDatabase.getInstance().getReference().child("Finish").child(currentDate).child(String.valueOf(maxid+1));
        reff2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if(deliverModelList.get(position).getName1() == null){
            holder.txtN1.setVisibility(View.GONE);
        }else{
            holder.txtN1.setText(new StringBuilder().append(deliverModelList.get(position).getName1()));
        }
        if(deliverModelList.get(position).getName2() == null){
            holder.txtN2.setVisibility(View.GONE);
        }else{
            holder.txtN2.setText(new StringBuilder().append(deliverModelList.get(position).getName2()));
        }
        if(deliverModelList.get(position).getName3() == null){
            holder.txtN3.setVisibility(View.GONE);
        }else{
            holder.txtN3.setText(new StringBuilder().append(deliverModelList.get(position).getName3()));
        }
        if(deliverModelList.get(position).getName4() == null){
            holder.txtN4.setVisibility(View.GONE);
        }else{
            holder.txtN4.setText(new StringBuilder().append(deliverModelList.get(position).getName4()));
        }
        if(deliverModelList.get(position).getName5() == null){
            holder.txtN5.setVisibility(View.GONE);
        }else{
            holder.txtN5.setText(new StringBuilder().append(deliverModelList.get(position).getName5()));
        }
        if(deliverModelList.get(position).getName6() == null){
            holder.txtN6.setVisibility(View.GONE);
        }else{
            holder.txtN6.setText(new StringBuilder().append(deliverModelList.get(position).getName6()));
        }
        if(deliverModelList.get(position).getName7() == null){
            holder.txtN7.setVisibility(View.GONE);
        }else{
            holder.txtN7.setText(new StringBuilder().append(deliverModelList.get(position).getName7()));
        }
        if(deliverModelList.get(position).getName8() == null){
            holder.txtN8.setVisibility(View.GONE);
        }else{
            holder.txtN8.setText(new StringBuilder().append(deliverModelList.get(position).getName8()));
        }
        holder.txtAddress.setText(new StringBuilder("Address: ").append(deliverModelList.get(position).getAddress()));
        holder.txtStat.setText(new StringBuilder().append(deliverModelList.get(position).getStatus()));
        holder.txtStat.setTextColor(Color.parseColor("#00FF00"));
        holder.txtTotalOrderP.setText(new StringBuilder().append(deliverModelList.get(position).getTotalPrice()));
        holder.txtIDNUM.setText(new StringBuilder().append(deliverModelList.get(position).getKey()));
        holder.txtPID.setText(new StringBuilder().append(deliverModelList.get(position).getPID()));
        holder.txtUID.setText(new StringBuilder().append(deliverModelList.get(position).getUID()));


        holder.btnOFD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.setContentView(R.layout.out_delivery_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCanceledOnTouchOutside(false);

                ProgressBar progressBar = dialog.findViewById(R.id.progressbarDeliver);

                Button btnok = dialog.findViewById(R.id.btn_okay2);
                Button notyet = dialog.findViewById(R.id.btn_notyet);
                TextView N1,N2,N3,N4,N5,N6,N7,N8,Address;
                N1 = dialog.findViewById(R.id.txtName1);
                N2 = dialog.findViewById(R.id.txtName2);
                N3 = dialog.findViewById(R.id.txtName3);
                N4 = dialog.findViewById(R.id.txtName4);
                N5 = dialog.findViewById(R.id.txtName5);
                N6 = dialog.findViewById(R.id.txtName6);
                N7 = dialog.findViewById(R.id.txtName7);
                N8 = dialog.findViewById(R.id.txtName8);
                Address = dialog.findViewById(R.id.txtAddressDialog);

                Address.setText(new StringBuilder("Address: ").append(deliverModelList.get(holder.getAdapterPosition()).getAddress()));

                if(deliverModelList.get(holder.getAdapterPosition()).getName1() == null){
                    N1.setVisibility(View.GONE);
                }else{
                    N1.setText(new StringBuilder().append(deliverModelList.get(holder.getAdapterPosition()).getName1()));
                }
                if(deliverModelList.get(holder.getAdapterPosition()).getName2() == null){
                    N2.setVisibility(View.GONE);
                }else{
                    N2.setText(new StringBuilder().append(deliverModelList.get(holder.getAdapterPosition()).getName2()));
                }
                if(deliverModelList.get(holder.getAdapterPosition()).getName3() == null){
                    N3.setVisibility(View.GONE);
                }else{
                    N3.setText(new StringBuilder().append(deliverModelList.get(holder.getAdapterPosition()).getName3()));
                }
                if(deliverModelList.get(holder.getAdapterPosition()).getName4() == null){
                    N4.setVisibility(View.GONE);
                }else{
                    N4.setText(new StringBuilder().append(deliverModelList.get(holder.getAdapterPosition()).getName4()));
                }
                if(deliverModelList.get(holder.getAdapterPosition()).getName5() == null){
                    N5.setVisibility(View.GONE);
                }else{
                    N5.setText(new StringBuilder().append(deliverModelList.get(holder.getAdapterPosition()).getName5()));
                }
                if(deliverModelList.get(holder.getAdapterPosition()).getName6() == null){
                    N6.setVisibility(View.GONE);
                }else{
                    N6.setText(new StringBuilder().append(deliverModelList.get(holder.getAdapterPosition()).getName6()));
                }
                if(deliverModelList.get(holder.getAdapterPosition()).getName7() == null){
                    N7.setVisibility(View.GONE);
                }else{
                    N7.setText(new StringBuilder().append(deliverModelList.get(holder.getAdapterPosition()).getName7()));
                }
                if(deliverModelList.get(holder.getAdapterPosition()).getName8() == null){
                    N8.setVisibility(View.GONE);
                }else{
                    N8.setText(new StringBuilder().append(deliverModelList.get(holder.getAdapterPosition()).getName8()));
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

                                DatabaseReference reffUsers = FirebaseDatabase.getInstance().getReference()
                                        .child("Users")
                                        .child(holder.txtUID.getText().toString())
                                        .child("OrderHistory")
                                        .child(holder.txtPID.getText().toString());


                                DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Accepted");

                                Query query = reference2.orderByKey().equalTo(currentDate);
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            String key = holder.txtIDNUM.getText().toString();
                                            if(snapshot.child(key).child("name1").getValue()==null){
                                                reff2.getRef().child("name1").removeValue();
                                            }else{
                                                deliverModel.setName1(holder.txtN1.getText().toString());
                                            }
                                            if(snapshot.child(key).child("name2").getValue()==null){
                                                reff2.getRef().child("name2").removeValue();
                                            }else{
                                                deliverModel.setName2(holder.txtN2.getText().toString());
                                            }
                                            if(snapshot.child(key).child("name3").getValue()==null){
                                                reff2.getRef().child("name3").removeValue();
                                            }else{
                                                deliverModel.setName3(holder.txtN3.getText().toString());
                                            }

                                            if(snapshot.child(key).child("name4").getValue()==null){
                                                reff2.getRef().child("name4").removeValue();
                                            }else{
                                                deliverModel.setName4(holder.txtN4.getText().toString());
                                            }
                                            if(snapshot.child(key).child("name5").getValue()==null){
                                                reff2.getRef().child("name5").removeValue();
                                            }else{
                                                deliverModel.setName4(holder.txtN5.getText().toString());
                                            }
                                            if(snapshot.child(key).child("name6").getValue()==null){
                                                reff2.getRef().child("name6").removeValue();
                                            }else{
                                                deliverModel.setName4(holder.txtN6.getText().toString());
                                            }
                                            if(snapshot.child(key).child("name7").getValue()==null){
                                                reff2.getRef().child("name7").removeValue();
                                            }else{
                                                deliverModel.setName4(holder.txtN7.getText().toString());
                                            }
                                            if(snapshot.child(key).child("name8").getValue()==null){
                                                reff2.getRef().child("name8").removeValue();
                                            }else{
                                                deliverModel.setName4(holder.txtN8.getText().toString());
                                            }


                                            deliverModel.setAddress(String.valueOf(snapshot.child(key).child("address").getValue()));
                                            deliverModel.setTotalPrice(Float.parseFloat((snapshot.child(key).child("totalPrice").getValue().toString())));
                                            deliverModel.setStatus("Finish");
                                            reffUsers.child("status").setValue("Finish");
                                            reff.child(String.valueOf(maxid + 1)).setValue(deliverModel);

                                            snapshot.child(key).getRef().removeValue().addOnSuccessListener(aVoid ->  EventBus.getDefault().postSticky(new MyUpdateCartEvent()));

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                                deliverModelList.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                notifyItemRangeChanged(holder.getAdapterPosition(),deliverModelList.size());
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

        Unbinder unbinder;

        public MyOrderHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
        }
    }
}
