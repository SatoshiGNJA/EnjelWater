package com.example.enjelwater;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enjelwater.Adapter.MyOrderAdapter;
import com.example.enjelwater.Listener.IDeliverLoadListener;
import com.example.enjelwater.Listener.IProductLoadListener;
import com.example.enjelwater.Model.DeliverModel;
import com.example.enjelwater.Model.ProductModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class fragment1 extends Fragment implements IProductLoadListener, IDeliverLoadListener {
    View view;
    @BindView(R.id.fragmentlayout1)
    RelativeLayout fragML;
    @BindView(R.id.fragmentrecycler)
    RecyclerView fragRecy;
    @BindView(R.id.sadwater)
    ImageView sadW;
    @BindView(R.id.txtNoOrder)
    TextView noOrder;
    IProductLoadListener productLoadListener;
    IDeliverLoadListener deliverLoadListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment1, container, false);

        loadOrderFromFirebase();
        init();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Orders").child(getTodaysDate());
        reference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {




                            sadW.setVisibility(View.GONE);
                            noOrder.setVisibility(View.GONE);




                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {




                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {


                    }
                });



        return view;

    }
    private void loadOrderFromFirebase(){
        List<ProductModel> productModels = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("Orders")
                .child(getTodaysDate())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            for(DataSnapshot productsnapshot:snapshot.getChildren())
                            {
                                ProductModel productModel = productsnapshot.getValue(ProductModel.class);
                                productModel.setKey(productsnapshot.getKey());
                                productModel.setTotalPrice(productModel.getTotalPrice());
                                productModels.add(productModel);
                            }
                            productLoadListener.onProductLoadSuccess(productModels);
                        }else{
                            sadW.setVisibility(View.VISIBLE);
                            noOrder.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        productLoadListener.onProductLoadFailed(error.getMessage());

                    }
                });
    }
    private void init(){
        ButterKnife.bind(this,view);

        productLoadListener = this;
        deliverLoadListener = this;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        fragRecy.setLayoutManager(layoutManager);
        fragRecy.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
    }


    @Override
    public void onProductLoadSuccess(List<ProductModel> productModelList) {
        MyOrderAdapter adapter = new MyOrderAdapter(getContext(),productModelList,deliverLoadListener);
        fragRecy.setAdapter(adapter);

    }

    @Override
    public void onProductLoadFailed(String message) {
        Snackbar.make(fragML,message,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onDeliverLoadSuccess(List<DeliverModel> deliverModelList) {

    }

    @Override
    public void onDeliverLoadFailed(String message) {

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