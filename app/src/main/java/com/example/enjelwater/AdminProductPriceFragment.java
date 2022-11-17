package com.example.enjelwater;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enjelwater.Adapter.MyDrinkAdapter;
import com.example.enjelwater.Adapter.MyPriceMaintenanceAdapter;
import com.example.enjelwater.Listener.IDrinkLoadListener;
import com.example.enjelwater.Model.DrinkModel;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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

    Button addproduct;

    Dialog dialog;

    IDrinkLoadListener drinkLoadListener;

    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_product_price, container, false);

        dialog = new Dialog(view.getContext());

        addproduct= view.findViewById(R.id.btn_add_product);

        reference = FirebaseDatabase.getInstance().getReference();

        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.add_new_product);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Button btnok = dialog.findViewById(R.id.btn_save_new_product);
                Button btnnotyet = dialog.findViewById(R.id.btn_notyet_edit);

                TextView producthead = dialog.findViewById(R.id.productstock);
                EditText productstock = dialog.findViewById(R.id.newProductStocks);

                EditText newproductname = dialog.findViewById(R.id.newProductName);

                RadioButton r1 = dialog.findViewById(R.id.add_round_gallon);
                RadioButton r2 = dialog.findViewById(R.id.add_slim_gallon);

                Spinner spin = dialog.findViewById(R.id.spinner);

                Spinner spin2 = dialog.findViewById(R.id.spinner2);

                r1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "Round Gallon", Toast.LENGTH_SHORT).show();
                        String text = spin.getSelectedItem().toString();
                        String text2 = spin2.getSelectedItem().toString();
                        if(r1.isChecked()&&text.equals("Refill Gallon")&&text2.equals("Mineral")){
                            newproductname.setText("Mineral Water - REFILL Round Gallon");
                        }else if(r2.isChecked()&&text.equals("Refill Gallon")&&text2.equals("Mineral")){
                            newproductname.setText("Mineral Water - REFILL Slim Gallon");
                        }else if(r1.isChecked()&&text.equals("Refill Gallon")&&text2.equals("Alkaline")){
                            newproductname.setText("Alkaline Water - REFILL Round Gallon");
                        }else if(r2.isChecked()&&text.equals("Refill Gallon")&&text2.equals("Alkaline")){
                            newproductname.setText("Alkaline Water - REFILL Slim Gallon");
                        }else if(r1.isChecked()&&text.equals("New Gallon")&&text2.equals("Mineral")){
                            newproductname.setText("Mineral Water - NEW Round Gallon");
                        }else if(r2.isChecked()&&text.equals("New Gallon")&&text2.equals("Mineral")){
                            newproductname.setText("Mineral Water - NEW Slim Gallon");
                        }else if(r1.isChecked()&&text.equals("New Gallon")&&text2.equals("Alkaline")){
                            newproductname.setText("Alkaline Water - NEW Round Gallon");
                        }else if(r2.isChecked()&&text.equals("New Gallon")&&text2.equals("Alkaline")){
                            newproductname.setText("Alkaline Water - NEW Slim Gallon");
                        }
                    }
                });
                r2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "Slim Gallon", Toast.LENGTH_SHORT).show();
                        String text = spin.getSelectedItem().toString();
                        String text2 = spin2.getSelectedItem().toString();
                        if(r1.isChecked()&&text.equals("Refill Gallon")&&text2.equals("Mineral")){
                            newproductname.setText("Mineral Water - REFILL Round Gallon");
                        }else if(r2.isChecked()&&text.equals("Refill Gallon")&&text2.equals("Mineral")){
                            newproductname.setText("Mineral Water - REFILL Slim Gallon");
                        }else if(r1.isChecked()&&text.equals("Refill Gallon")&&text2.equals("Alkaline")){
                            newproductname.setText("Alkaline Water - REFILL Round Gallon");
                        }else if(r2.isChecked()&&text.equals("Refill Gallon")&&text2.equals("Alkaline")){
                            newproductname.setText("Alkaline Water - REFILL Slim Gallon");
                        }else if(r1.isChecked()&&text.equals("New Gallon")&&text2.equals("Mineral")){
                            newproductname.setText("Mineral Water - NEW Round Gallon");
                        }else if(r2.isChecked()&&text.equals("New Gallon")&&text2.equals("Mineral")){
                            newproductname.setText("Mineral Water - NEW Slim Gallon");
                        }else if(r1.isChecked()&&text.equals("New Gallon")&&text2.equals("Alkaline")){
                            newproductname.setText("Alkaline Water - NEW Round Gallon");
                        }else if(r2.isChecked()&&text.equals("New Gallon")&&text2.equals("Alkaline")){
                            newproductname.setText("Alkaline Water - NEW Slim Gallon");
                        }
                    }
                });

                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.producttype, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin.setAdapter(adapter);
                spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String ttt = adapterView.getItemAtPosition(i).toString();
                        if(ttt.equals("Refill Gallon")){
                            Toast.makeText(getContext(), "You Select Refill Gallon", Toast.LENGTH_SHORT).show();
                            producthead.setVisibility(View.GONE);
                            productstock.setVisibility(View.GONE);
                            String text = spin.getSelectedItem().toString();
                            String text2 = spin2.getSelectedItem().toString();
                            if(r1.isChecked()&&text.equals("Refill Gallon")&&text2.equals("Mineral")){
                                newproductname.setText("Mineral Water - REFILL Round Gallon");
                            }else if(r2.isChecked()&&text.equals("Refill Gallon")&&text2.equals("Mineral")){
                                newproductname.setText("Mineral Water - REFILL Slim Gallon");
                            }else if(r1.isChecked()&&text.equals("Refill Gallon")&&text2.equals("Alkaline")){
                                newproductname.setText("Alkaline Water - REFILL Round Gallon");
                            }else if(r2.isChecked()&&text.equals("Refill Gallon")&&text2.equals("Alkaline")){
                                newproductname.setText("Alkaline Water - REFILL Slim Gallon");
                            }else if(r1.isChecked()&&text.equals("New Gallon")&&text2.equals("Mineral")){
                                newproductname.setText("Mineral Water - NEW Round Gallon");
                            }else if(r2.isChecked()&&text.equals("New Gallon")&&text2.equals("Mineral")){
                                newproductname.setText("Mineral Water - NEW Slim Gallon");
                            }else if(r1.isChecked()&&text.equals("New Gallon")&&text2.equals("Alkaline")){
                                newproductname.setText("Alkaline Water - NEW Round Gallon");
                            }else if(r2.isChecked()&&text.equals("New Gallon")&&text2.equals("Alkaline")){
                                newproductname.setText("Alkaline Water - NEW Slim Gallon");
                            }
                        }else if(ttt.equals("New Gallon")){
                            Toast.makeText(getContext(), "You Select New Gallon", Toast.LENGTH_SHORT).show();
                            producthead.setVisibility(View.VISIBLE);
                            productstock.setVisibility(View.VISIBLE);
                            String text = spin.getSelectedItem().toString();
                            String text2 = spin2.getSelectedItem().toString();
                            if(r1.isChecked()&&text.equals("Refill Gallon")&&text2.equals("Mineral")){
                                newproductname.setText("Mineral Water - REFILL Round Gallon");
                            }else if(r2.isChecked()&&text.equals("Refill Gallon")&&text2.equals("Mineral")){
                                newproductname.setText("Mineral Water - REFILL Slim Gallon");
                            }else if(r1.isChecked()&&text.equals("Refill Gallon")&&text2.equals("Alkaline")){
                                newproductname.setText("Alkaline Water - REFILL Round Gallon");
                            }else if(r2.isChecked()&&text.equals("Refill Gallon")&&text2.equals("Alkaline")){
                                newproductname.setText("Alkaline Water - REFILL Slim Gallon");
                            }else if(r1.isChecked()&&text.equals("New Gallon")&&text2.equals("Mineral")){
                                newproductname.setText("Mineral Water - NEW Round Gallon");
                            }else if(r2.isChecked()&&text.equals("New Gallon")&&text2.equals("Mineral")){
                                newproductname.setText("Mineral Water - NEW Slim Gallon");
                            }else if(r1.isChecked()&&text.equals("New Gallon")&&text2.equals("Alkaline")){
                                newproductname.setText("Alkaline Water - NEW Round Gallon");
                            }else if(r2.isChecked()&&text.equals("New Gallon")&&text2.equals("Alkaline")){
                                newproductname.setText("Alkaline Water - NEW Slim Gallon");
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),R.array.kindofwater, android.R.layout.simple_spinner_item);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin2.setAdapter(adapter2);
                spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String ttt = adapterView.getItemAtPosition(i).toString();
                        if(ttt.equals("Mineral")){
                            Toast.makeText(getContext(), "You Selected Mineral Water", Toast.LENGTH_SHORT).show();
                            String text = spin.getSelectedItem().toString();
                            String text2 = spin2.getSelectedItem().toString();
                            if(r1.isChecked()&&text.equals("Refill Gallon")&&text2.equals("Mineral")){
                                newproductname.setText("Mineral Water - REFILL Round Gallon");
                            }else if(r2.isChecked()&&text.equals("Refill Gallon")&&text2.equals("Mineral")){
                                newproductname.setText("Mineral Water - REFILL Slim Gallon");
                            }else if(r1.isChecked()&&text.equals("Refill Gallon")&&text2.equals("Alkaline")){
                                newproductname.setText("Alkaline Water - REFILL Round Gallon");
                            }else if(r2.isChecked()&&text.equals("Refill Gallon")&&text2.equals("Alkaline")){
                                newproductname.setText("Alkaline Water - REFILL Slim Gallon");
                            }else if(r1.isChecked()&&text.equals("New Gallon")&&text2.equals("Mineral")){
                                newproductname.setText("Mineral Water - NEW Round Gallon");
                            }else if(r2.isChecked()&&text.equals("New Gallon")&&text2.equals("Mineral")){
                                newproductname.setText("Mineral Water - NEW Slim Gallon");
                            }else if(r1.isChecked()&&text.equals("New Gallon")&&text2.equals("Alkaline")){
                                newproductname.setText("Alkaline Water - NEW Round Gallon");
                            }else if(r2.isChecked()&&text.equals("New Gallon")&&text2.equals("Alkaline")){
                                newproductname.setText("Alkaline Water - NEW Slim Gallon");
                            }
                        }else if(ttt.equals("Alkaline")){
                            Toast.makeText(getContext(), "You Selected Alkaline Water", Toast.LENGTH_SHORT).show();
                            String text = spin.getSelectedItem().toString();
                            String text2 = spin2.getSelectedItem().toString();
                            if(r1.isChecked()&&text.equals("Refill Gallon")&&text2.equals("Mineral")){
                                newproductname.setText("Mineral Water - REFILL Round Gallon");
                            }else if(r2.isChecked()&&text.equals("Refill Gallon")&&text2.equals("Mineral")){
                                newproductname.setText("Mineral Water - REFILL Slim Gallon");
                            }else if(r1.isChecked()&&text.equals("Refill Gallon")&&text2.equals("Alkaline")){
                                newproductname.setText("Alkaline Water - REFILL Round Gallon");
                            }else if(r2.isChecked()&&text.equals("Refill Gallon")&&text2.equals("Alkaline")){
                                newproductname.setText("Alkaline Water - REFILL Slim Gallon");
                            }else if(r1.isChecked()&&text.equals("New Gallon")&&text2.equals("Mineral")){
                                newproductname.setText("Mineral Water - NEW Round Gallon");
                            }else if(r2.isChecked()&&text.equals("New Gallon")&&text2.equals("Mineral")){
                                newproductname.setText("Mineral Water - NEW Slim Gallon");
                            }else if(r1.isChecked()&&text.equals("New Gallon")&&text2.equals("Alkaline")){
                                newproductname.setText("Alkaline Water - NEW Round Gallon");
                            }else if(r2.isChecked()&&text.equals("New Gallon")&&text2.equals("Alkaline")){
                                newproductname.setText("Alkaline Water - NEW Slim Gallon");
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String text = spin.getSelectedItem().toString();
                        String text2 = spin2.getSelectedItem().toString();
                        if(r1.isChecked()&&text.equals("Refill Gallon")&&text2.equals("Mineral")){
                            reference.child("Drink").child("01").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(!snapshot.exists()){
                                        Toast.makeText(getContext(), "Successfully Added!", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getContext(), "This Product is already Exist!", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }else if(r2.isChecked()&&text.equals("Refill Gallon")&&text2.equals("Mineral")){
                            reference.child("Drink").child("02").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(!snapshot.exists()){
                                        Toast.makeText(getContext(), "Successfully Added!", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getContext(), "This Product is already Exist!", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }else if(r1.isChecked()&&text.equals("Refill Gallon")&&text2.equals("Alkaline")){
                            reference.child("Drink").child("03").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(!snapshot.exists()){
                                        Toast.makeText(getContext(), "Successfully Added!", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getContext(), "This Product is already Exist!", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }else if(r2.isChecked()&&text.equals("Refill Gallon")&&text2.equals("Alkaline")){
                            reference.child("Drink").child("04").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(!snapshot.exists()){
                                        Toast.makeText(getContext(), "Successfully Added!", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getContext(), "This Product is already Exist!", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }else if(r1.isChecked()&&text.equals("New Gallon")&&text2.equals("Mineral")){
                            reference.child("NewDrink").child("05").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(!snapshot.exists()){
                                        Toast.makeText(getContext(), "Successfully Added!", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getContext(), "This Product is already Exist!", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }else if(r2.isChecked()&&text.equals("New Gallon")&&text2.equals("Mineral")){
                            reference.child("NewDrink").child("06").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(!snapshot.exists()){
                                        Toast.makeText(getContext(), "Successfully Added!", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getContext(), "This Product is already Exist!", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }else if(r1.isChecked()&&text.equals("New Gallon")&&text2.equals("Alkaline")){
                            reference.child("NewDrink").child("07").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(!snapshot.exists()){
                                        Toast.makeText(getContext(), "Successfully Added!", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getContext(), "This Product is already Exist!", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }else if(r2.isChecked()&&text.equals("New Gallon")&&text2.equals("Alkaline")){
                            reference.child("NewDrink").child("08").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(!snapshot.exists()){
                                        Toast.makeText(getContext(), "Successfully Added!", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getContext(), "This Product is already Exist!", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                        dialog.dismiss();
                    }
                });
                btnnotyet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });




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