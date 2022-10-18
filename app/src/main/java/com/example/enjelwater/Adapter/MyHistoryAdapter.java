package com.example.enjelwater.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.enjelwater.Model.HistoryModel;
import com.example.enjelwater.R;
import com.google.firebase.database.DatabaseReference;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyHistoryAdapter extends RecyclerView.Adapter<MyHistoryAdapter.MyHistoryHolder>{

    private Context context;
    private List<HistoryModel> historyModelList;

    public MyHistoryAdapter(Context context, List<HistoryModel>historyModelList){
        this.context = context;
        this.historyModelList = historyModelList;
    }

    @NonNull
    @Override
    public MyHistoryAdapter.MyHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHistoryAdapter.MyHistoryHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_history_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHistoryAdapter.MyHistoryHolder holder, int position) {

        if(historyModelList.get(position).getName1() == null){
            holder.txtN1.setVisibility(View.GONE);
        }else{
            holder.txtN1.setText(new StringBuilder().append(historyModelList.get(position).getName1()));
        }
        if(historyModelList.get(position).getName2() == null){
            holder.txtN2.setVisibility(View.GONE);
        }else{
            holder.txtN2.setText(new StringBuilder().append(historyModelList.get(position).getName2()));
        }
        if(historyModelList.get(position).getName3() == null){
            holder.txtN3.setVisibility(View.GONE);
        }else{
            holder.txtN3.setText(new StringBuilder().append(historyModelList.get(position).getName3()));
        }
        if(historyModelList.get(position).getName4() == null){
            holder.txtN4.setVisibility(View.GONE);
        }else{
            holder.txtN4.setText(new StringBuilder().append(historyModelList.get(position).getName4()));
        }
        if(historyModelList.get(position).getName5() == null){
            holder.txtN5.setVisibility(View.GONE);
        }else{
            holder.txtN5.setText(new StringBuilder().append(historyModelList.get(position).getName5()));
        }
        if(historyModelList.get(position).getName6() == null){
            holder.txtN6.setVisibility(View.GONE);
        }else{
            holder.txtN6.setText(new StringBuilder().append(historyModelList.get(position).getName6()));
        }
        if(historyModelList.get(position).getName7() == null){
            holder.txtN7.setVisibility(View.GONE);
        }else{
            holder.txtN7.setText(new StringBuilder().append(historyModelList.get(position).getName7()));
        }
        if(historyModelList.get(position).getName8() == null){
            holder.txtN8.setVisibility(View.GONE);
        }else{
            holder.txtN8.setText(new StringBuilder().append(historyModelList.get(position).getName8()));
        }
        holder.txtCustomerN.setText(new StringBuilder("Customer Name: ").append(historyModelList.get(position).getCustomerName()));
        holder.txtAddress.setText(new StringBuilder("Address: ").append(historyModelList.get(position).getAddress()));
        holder.txtStat.setText(new StringBuilder().append(historyModelList.get(position).getStatus()));
        holder.txtStat.setTextColor(Color.parseColor("#00FF00"));
        holder.txtTotalOrderP.setText(new StringBuilder().append(String.format("%.2f",historyModelList.get(position).getTotalPrice())));
        holder.txtIDNUM.setText(new StringBuilder().append(historyModelList.get(position).getKey()));
        holder.txtPhone.setText(new StringBuilder("Phone#: ").append(historyModelList.get(position).getPhonenum()));
        if(Objects.equals(historyModelList.get(position).getStatus(), "Finish")){
        }
        if(Objects.equals(historyModelList.get(position).getStatus(), "Cancel")){
            holder.txtStat.setTextColor(Color.parseColor("#FF0000"));
        }

    }

    @Override
    public int getItemCount() {
        return historyModelList.size();
    }
    public class MyHistoryHolder extends RecyclerView.ViewHolder {

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
        @BindView(R.id.historycustname)
        TextView txtCustomerN;
        @BindView(R.id.historyphone)
        TextView txtPhone;


        Unbinder unbinder;

        public MyHistoryHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
        }
    }
}
