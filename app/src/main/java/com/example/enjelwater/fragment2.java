package com.example.enjelwater;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.enjelwater.Adapter.MyOnProcessAdapter;
import com.example.enjelwater.Adapter.MyOrderAdapter;
import com.example.enjelwater.Listener.IDeliverLoadListener;
import com.example.enjelwater.Listener.IProductLoadListener;
import com.example.enjelwater.Model.DeliverModel;
import com.example.enjelwater.Model.ProductModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class fragment2 extends Fragment implements IDeliverLoadListener {

    View view;
    @BindView(R.id.fragmentlayout2)
    RelativeLayout fragML;
    @BindView(R.id.fragmentrecycler2)
    RecyclerView fragRecy;
    IDeliverLoadListener deliverLoadListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment2, container, false);
        loadDeliverFromFirebase();
        init();



        return view;
    }
    private void loadDeliverFromFirebase(){
        List<DeliverModel> deliverModels = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("Delivered")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            for(DataSnapshot deliversnapshot:snapshot.getChildren())
                            {
                                DeliverModel deliverModel = deliversnapshot.getValue(DeliverModel.class);
                                deliverModel.setKey(deliversnapshot.getKey());
                                deliverModel.setTotalPrice(deliverModel.getTotalPrice());
                                deliverModels.add(deliverModel);
                            }
                            deliverLoadListener.onDeliverLoadSuccess(deliverModels);
                        }else{
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        deliverLoadListener.onDeliverLoadFailed(error.getMessage());

                    }
                });
    }
    private void init(){
        ButterKnife.bind(this,view);

        deliverLoadListener = this;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        fragRecy.setLayoutManager(layoutManager);
        fragRecy.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
    }


    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    public void onDeliverLoadSuccess(List<DeliverModel> deliverModelList) {

        MyOnProcessAdapter adapter = new MyOnProcessAdapter(getContext(),deliverModelList);
        fragRecy.setAdapter(adapter);

    }

    @Override
    public void onDeliverLoadFailed(String message) {

        Snackbar.make(fragML,message,Snackbar.LENGTH_LONG).show();

    }
}