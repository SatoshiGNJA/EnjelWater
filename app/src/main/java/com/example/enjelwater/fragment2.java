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

@RequiresApi(api = Build.VERSION_CODES.S)
public class fragment2 extends Fragment implements IDeliverLoadListener {

    View view;
    @BindView(R.id.fragmentlayout2)
    RelativeLayout fragML;
    @BindView(R.id.fragmentrecycler2)
    RecyclerView fragRecy;
    IDeliverLoadListener deliverLoadListener;
    Calendar calendar = Calendar.getInstance();
    String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

    private static final int PERMISSION_BLUETOOTH = 0;
    private static final int PERMISSION_BLUETOOTH_ADMIN = 3;
    private static final int PERMISSION_BLUETOOTH_CONNECT = 2;
    private static final int PERMISSION_BLUETOOTH_SCAN = 1;

    BluetoothDevice device = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment2, container, false);
        loadDeliverFromFirebase();
        init();

        //  Request for bluetooth permission
        if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) view.getContext(), new String[]{Manifest.permission.BLUETOOTH}, fragment2.PERMISSION_BLUETOOTH);
        } else if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) view.getContext(), new String[]{Manifest.permission.BLUETOOTH_ADMIN}, fragment2.PERMISSION_BLUETOOTH_ADMIN);
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) view.getContext(), new String[]{Manifest.permission.BLUETOOTH_CONNECT}, fragment2.PERMISSION_BLUETOOTH_CONNECT);
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) view.getContext(), new String[]{Manifest.permission.BLUETOOTH_SCAN}, fragment2.PERMISSION_BLUETOOTH_SCAN);
        } else {
            // make sure device is already paired
            BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
            Set<BluetoothDevice> mBtDevices = btAdapter.getBondedDevices();// Get first paired device

            for (BluetoothDevice bluetoothDevice : mBtDevices) {
                if (bluetoothDevice.getName().equals("MTP-2")) {
                    device = bluetoothDevice;
                }
            }
        }


        return view;
    }
    private void loadDeliverFromFirebase(){
        List<DeliverModel> deliverModels = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("Accepted")
                .child(currentDate)
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