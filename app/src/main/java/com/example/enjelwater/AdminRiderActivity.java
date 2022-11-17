package com.example.enjelwater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enjelwater.Adapter.MyOnProcessAdapter;
import com.example.enjelwater.Adapter.MyRiderAdapter;
import com.example.enjelwater.Listener.IDeliverLoadListener;
import com.example.enjelwater.Listener.IRiderLoadListener;
import com.example.enjelwater.Model.DeliverModel;
import com.example.enjelwater.Model.HistoryModel;
import com.example.enjelwater.Model.ProductModel;
import com.example.enjelwater.Model.RiderModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminRiderActivity extends AppCompatActivity implements IRiderLoadListener {

    @BindView(R.id.rider_RL)
    RelativeLayout fragML;
    @BindView(R.id.rider_RV)
    RecyclerView fragRecy;
    IRiderLoadListener riderLoadListener;


    SharedPreferences sharedPreferences;

    DatabaseReference reference;

    public static final String fileName = "login";
    public static final String Rider1 = "ridername1";
    public static final String Rider2 = "ridername2";
    public static final String Rider3 = "ridername3";

    Button logout,datetoday;

    String ridernumber;

    TextView ridername,date,totalsales;

    String rider1,rider2,rider3;

    DatePickerDialog datePickerDialog;

    String NAMER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_rider);

        logout = findViewById(R.id.logout_rider);
        ridername = findViewById(R.id.ridername);
        datetoday = findViewById(R.id.datePicker);
        totalsales = findViewById(R.id.totalsales);

        datetoday.setText(getTodaysDate());

        reference = FirebaseDatabase.getInstance().getReference("Admin").child("riders");

        sharedPreferences = getSharedPreferences(fileName, Context.MODE_PRIVATE);

        datetoday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        DatabaseReference riderreff = FirebaseDatabase.getInstance().getReference().child("Admin").child("riders");
        riderreff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                rider1 = snapshot.child("Rider1").child("name").getValue(String.class);
                rider2 = snapshot.child("Rider2").child("name").getValue(String.class);
                rider3 = snapshot.child("Rider3").child("name").getValue(String.class);

                if (ridername.getText().toString().equals(rider1)){
                    reference.child("Rider1").child("DeliveryList").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else if (ridername.getText().toString().equals(rider2)){
                    reference.child("Rider2").child("DeliveryList").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else if (ridername.getText().toString().equals(rider3)){
                    reference.child("Rider3").child("DeliveryList").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                NotificationManager nMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                nMgr.cancelAll();
                startActivity(i);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        if(sharedPreferences.contains(Rider1)){

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ridernumber = snapshot.child("Rider1").getKey();
                    String ridername1 = snapshot.child("Rider1").child("name").getValue(String.class);
                    ridername.setText(ridername1);
                    loadRiderFromFirebase();
                    loadHistoryFromFirebase();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }else if(sharedPreferences.contains(Rider2)){

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ridernumber = snapshot.child("Rider2").getKey();
                    String ridername2 = snapshot.child("Rider2").child("name").getValue(String.class);
                    ridername.setText(ridername2);
                    loadRiderFromFirebase();
                    loadHistoryFromFirebase();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else if(sharedPreferences.contains(Rider3)){

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ridernumber = snapshot.child("Rider3").getKey();
                    String ridername3 = snapshot.child("Rider3").child("name").getValue(String.class);
                    ridername.setText(ridername3);
                    loadRiderFromFirebase();
                    loadHistoryFromFirebase();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        init();
        initDatePicker();

    }
    private void loadRiderFromFirebase(){
        List<RiderModel> riderModels = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("Admin").child("riders").child(ridernumber).child("DeliveryList")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            for(DataSnapshot ridersnapshot:snapshot.getChildren())
                            {
                                RiderModel riderModel = ridersnapshot.getValue(RiderModel.class);
                                riderModel.setKey(ridersnapshot.getKey());
                                riderModel.setTotalPrice(riderModel.getTotalPrice());
                                riderModels.add(riderModel);
                            }
                            riderLoadListener.onRiderLoadSuccess(riderModels);
                        }else{
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        riderLoadListener.onRiderLoadFailed(error.getMessage());

                    }
                });
    }
    private void init(){
        ButterKnife.bind(this);

        riderLoadListener = this;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        fragRecy.setLayoutManager(layoutManager);
        fragRecy.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
    }


    private String getTodaysDate() {

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month,year);

    }

    private void initDatePicker() {

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                String date = makeDateString(day,month,year);
                datetoday.setText(date);
                loadHistoryFromFirebase();
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this,style,dateSetListener,year,month,day);

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

    private void loadHistoryFromFirebase(){

        FirebaseDatabase.getInstance()
                .getReference("Admin")
                .child("riders").child(ridernumber).child("TotalSales")
                .child(datetoday.getText().toString()).child("totalsales")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){

                            Double sales = snapshot.getValue(Double.class);
                            totalsales.setText("₱"+String.format("%.2f",sales));

                        }else{

                            totalsales.setText("₱0.00");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }



    @Override
    public void onRiderLoadSuccess(List<RiderModel> riderModelList) {

        MyRiderAdapter adapter = new MyRiderAdapter(this,riderModelList);
        fragRecy.setAdapter(adapter);

    }

    @Override
    public void onRiderLoadFailed(String message) {

        Snackbar.make(fragML,message,Snackbar.LENGTH_LONG).show();
    }
}