package com.example.enjelwater;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.enjelwater.Adapter.MyDrinkAdapter;
import com.example.enjelwater.Adapter.MyPriceMaintenanceAdapter;
import com.example.enjelwater.Listener.IDrinkLoadListener;
import com.example.enjelwater.Model.DrinkModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminProductPriceFragment extends Fragment implements IDrinkLoadListener {
    View view;
    @BindView(R.id.fragment_layout_productprice)
    RelativeLayout fragML;
    @BindView(R.id.fragment_recycler_productprice)
    RecyclerView fragRecy;

    IDrinkLoadListener drinkLoadListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_product_price, container, false);



        loadDrinkandNewDrinkFromFirebase();
        init();
        return view;
    }

    private void loadDrinkandNewDrinkFromFirebase() {
        List<DrinkModel> drinkModels = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("Drink")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists())
                        {
                            for(DataSnapshot drinkSnapshot:snapshot.getChildren())
                            {
                                DrinkModel drinkModel = drinkSnapshot.getValue(DrinkModel.class);
                                drinkModel.setKey(drinkModel.getKey());
                                drinkModels.add(drinkModel);
                            }
                            drinkLoadListener.onDrinkLoadSuccess(drinkModels);
                        }
                        else
                            drinkLoadListener.onDrinkLoadFailed("Can't find Drink");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        drinkLoadListener.onDrinkLoadFailed(error.getMessage());
                    }
                });
        FirebaseDatabase.getInstance()
                .getReference("NewDrink")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists())
                        {
                            for(DataSnapshot drinkSnapshot:snapshot.getChildren())
                            {
                                DrinkModel drinkModel = drinkSnapshot.getValue(DrinkModel.class);
                                drinkModel.setKey(drinkModel.getKey());
                                drinkModels.add(drinkModel);
                            }
                            drinkLoadListener.onDrinkLoadSuccess(drinkModels);
                        }
                        else
                            drinkLoadListener.onDrinkLoadFailed("Can't find Drink");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        drinkLoadListener.onDrinkLoadFailed(error.getMessage());
                    }
                });
    }
    private void init(){
        ButterKnife.bind(this,view);

        drinkLoadListener = this;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        fragRecy.setLayoutManager(layoutManager);
        fragRecy.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
    }

    @Override
    public void onDrinkLoadSuccess(List<DrinkModel> drinkModelList) {
        MyPriceMaintenanceAdapter adapter = new MyPriceMaintenanceAdapter(getContext(),drinkModelList);
        fragRecy.setAdapter(adapter);

    }

    @Override
    public void onDrinkLoadFailed(String message) {
        Snackbar.make(fragML,message,Snackbar.LENGTH_LONG).show();

    }
}