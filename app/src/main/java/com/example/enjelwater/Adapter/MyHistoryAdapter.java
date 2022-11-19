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
            holder.txtN1.setVisibility(View.VISIBLE);
            holder.txtN1.setText(new StringBuilder().append(historyModelList.get(position).getName1()).append(" x").append(historyModelList.get(position).getQty1()));
        }
        if(historyModelList.get(position).getName2() == null){
            holder.txtN2.setVisibility(View.GONE);
        }else{
            holder.txtN2.setVisibility(View.VISIBLE);
            holder.txtN2.setText(new StringBuilder().append(historyModelList.get(position).getName2()).append(" x").append(historyModelList.get(position).getQty2()));
        }
        if(historyModelList.get(position).getName3() == null){
            holder.txtN3.setVisibility(View.GONE);
        }else{
            holder.txtN3.setVisibility(View.VISIBLE);
            holder.txtN3.setText(new StringBuilder().append(historyModelList.get(position).getName3()).append(" x").append(historyModelList.get(position).getQty3()));
        }
        if(historyModelList.get(position).getName4() == null){
            holder.txtN4.setVisibility(View.GONE);
        }else{
            holder.txtN4.setVisibility(View.VISIBLE);
            holder.txtN4.setText(new StringBuilder().append(historyModelList.get(position).getName4()).append(" x").append(historyModelList.get(position).getQty4()));
        }
        if(historyModelList.get(position).getName5() == null){
            holder.txtN5.setVisibility(View.GONE);
        }else{
            holder.txtN5.setVisibility(View.VISIBLE);
            holder.txtN5.setText(new StringBuilder().append(historyModelList.get(position).getName5()).append(" x").append(historyModelList.get(position).getQty5()));
        }
        if(historyModelList.get(position).getName6() == null){
            holder.txtN6.setVisibility(View.GONE);
        }else{
            holder.txtN6.setVisibility(View.VISIBLE);
            holder.txtN6.setText(new StringBuilder().append(historyModelList.get(position).getName6()).append(" x").append(historyModelList.get(position).getQty6()));
        }
        if(historyModelList.get(position).getName7() == null){
            holder.txtN7.setVisibility(View.GONE);
        }else{
            holder.txtN7.setVisibility(View.VISIBLE);
            holder.txtN7.setText(new StringBuilder().append(historyModelList.get(position).getName7()).append(" x").append(historyModelList.get(position).getQty7()));
        }
        if(historyModelList.get(position).getName8() == null){
            holder.txtN8.setVisibility(View.GONE);
        }else{
            holder.txtN8.setVisibility(View.VISIBLE);
            holder.txtN8.setText(new StringBuilder().append(historyModelList.get(position).getName8()).append(" x").append(historyModelList.get(position).getQty8()));
        }
        holder.txtCustomerN.setText(new StringBuilder().append(historyModelList.get(position).getCustomerName()));
        holder.txtAddress.setText(new StringBuilder().append(historyModelList.get(position).getAddress()));
        holder.txtStat.setText(new StringBuilder().append(historyModelList.get(position).getStatus()));
        holder.txtStat.setTextColor(Color.parseColor("#00FF00"));
        holder.txtTotalOrderP.setText(new StringBuilder().append(String.format("%.2f",historyModelList.get(position).getTotalPrice())));
        holder.txtIDNUM.setText(new StringBuilder().append(historyModelList.get(position).getKey()));
        holder.txtPhone.setText(new StringBuilder().append(historyModelList.get(position).getPhonenum()));
        holder.txtin.setText(new StringBuilder().append(historyModelList.get(position).getTime_in()));
        holder.txtout.setText(new StringBuilder().append(historyModelList.get(position).getTime_out()));
        if(Objects.equals(historyModelList.get(position).getStatus(), "Cancelled")){
            holder.txtout.setVisibility(View.GONE);
            holder.text.setVisibility(View.GONE);
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
        @BindView(R.id.timein)
        TextView txtin;
        @BindView(R.id.timeout)
        TextView txtout;
        @BindView(R.id.textView8)
        TextView text;

        Unbinder unbinder;

        public MyHistoryHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
        }
    }
}
