package com.example.enjelwater;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActionBar;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Set;

@RequiresApi(api = Build.VERSION_CODES.S)
public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_BLUETOOTH = 0;
    private static final int PERMISSION_BLUETOOTH_ADMIN = 3;
    private static final int PERMISSION_BLUETOOTH_CONNECT = 2;
    private static final int PERMISSION_BLUETOOTH_SCAN = 1;

    BluetoothDevice device = null;

    Button print;

    TextView n1, n2, n3, n4, n5, n6, n7, n8;
    TextView custName, address, phone, total, orderdate, rider;
    ImageView goback;

    DatabaseReference pricereference;

    int price1,price2,price3,price4,price5,price6,price7,price8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        n1 = findViewById(R.id.n1);
        n2 = findViewById(R.id.n2);
        n3 = findViewById(R.id.n3);
        n4 = findViewById(R.id.n4);
        n5 = findViewById(R.id.n5);
        n6 = findViewById(R.id.n6);
        n7 = findViewById(R.id.n7);
        n8 = findViewById(R.id.n8);
        custName = findViewById(R.id.custname);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        total = findViewById(R.id.total);
        goback = findViewById(R.id.returnback);
        print = findViewById(R.id.btn_print);
        orderdate = findViewById(R.id.orderdate);
        rider = findViewById(R.id.rider);

        pricereference = FirebaseDatabase.getInstance().getReference();

        String na1 = getIntent().getStringExtra("Name1");
        String na2 = getIntent().getStringExtra("Name2");
        String na3 = getIntent().getStringExtra("Name3");
        String na4 = getIntent().getStringExtra("Name4");
        String na5 = getIntent().getStringExtra("Name5");
        String na6 = getIntent().getStringExtra("Name6");
        String na7 = getIntent().getStringExtra("Name7");
        String na8 = getIntent().getStringExtra("Name8");
        int qty1 = getIntent().getIntExtra("Qty2",1);
        int qty2 = getIntent().getIntExtra("Qty2",1);
        int qty3 = getIntent().getIntExtra("Qty3",1);
        int qty4 = getIntent().getIntExtra("Qty4",1);
        int qty5 = getIntent().getIntExtra("Qty5",1);
        int qty6 = getIntent().getIntExtra("Qty6",1);
        int qty7 = getIntent().getIntExtra("Qty7",1);
        int qty8 = getIntent().getIntExtra("Qty8",1);

        String customer = getIntent().getStringExtra("CustomerN");
        String ph0ne = getIntent().getStringExtra("PhoneNum");
        String addr3ss = getIntent().getStringExtra("Address");
        String t0tal = getIntent().getStringExtra("Total");
        String date = getIntent().getStringExtra("Date");
        String ridername = getIntent().getStringExtra("Rider");

        n1.setText(na1);
        n2.setText(na2);
        n3.setText(na3);
        n4.setText(na4);
        n5.setText(na5);
        n6.setText(na6);
        n7.setText(na7);
        n8.setText(na8);
        custName.setText(customer);
        phone.setText(ph0ne);
        address.setText(addr3ss);
        total.setText(t0tal);
        orderdate.setText(date);
        rider.setText(ridername);


        if (n1.getText().toString().equals("null")) {
            n1.setVisibility(View.GONE);
        } else {
            n1.setText(na1);
        }
        if (n2.getText().toString().equals("null")) {
            n2.setVisibility(View.GONE);
        } else {
            n2.setText(na2);
        }
        if (n3.getText().toString().equals("null")) {
            n3.setVisibility(View.GONE);
        } else {
            n3.setText(na3);
        }
        if (n4.getText().toString().equals("null")) {
            n4.setVisibility(View.GONE);
        } else {
            n4.setText(na4);
        }
        if (n5.getText().toString().equals("null")) {
            n5.setVisibility(View.GONE);
        } else {
            n5.setText(na5);
        }
        if (n6.getText().toString().equals("null")) {
            n6.setVisibility(View.GONE);
        } else {
            n6.setText(na6);
        }
        if (n7.getText().toString().equals("null")) {
            n7.setVisibility(View.GONE);
        } else {
            n7.setText(na7);
        }
        if (n8.getText().toString().equals("null")) {
            n8.setVisibility(View.GONE);
        } else {
            n8.setText(na8);
        }

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //  Request for bluetooth permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH}, MainActivity.PERMISSION_BLUETOOTH);
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_ADMIN}, MainActivity.PERMISSION_BLUETOOTH_ADMIN);
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, MainActivity.PERMISSION_BLUETOOTH_CONNECT);
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_SCAN}, MainActivity.PERMISSION_BLUETOOTH_SCAN);
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

        pricereference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

              price1 = Integer.parseInt(snapshot.child("Drink").child("01").child("price").getValue(String.class));
              price2 = Integer.parseInt(snapshot.child("Drink").child("02").child("price").getValue(String.class));
              price3 = Integer.parseInt(snapshot.child("Drink").child("03").child("price").getValue(String.class));
              price4 = Integer.parseInt(snapshot.child("Drink").child("04").child("price").getValue(String.class));
              price5 = Integer.parseInt(snapshot.child("NewDrink").child("05").child("price").getValue(String.class));
              price6 = Integer.parseInt(snapshot.child("NewDrink").child("06").child("price").getValue(String.class));
              price7 = Integer.parseInt(snapshot.child("NewDrink").child("07").child("price").getValue(String.class));
              price8 = Integer.parseInt(snapshot.child("NewDrink").child("08").child("price").getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String divider = String.format("%" + 32 + "s", "").replace(' ', '*');
                if (device != null) {
                    final BluetoothPrinter mPrinter = new BluetoothPrinter(device);
                    mPrinter.connectPrinter(new BluetoothPrinter.PrinterConnectListener() {
                        @Override
                        public void onConnected() {

                            // table format: 32 character width per line
                            String format = "%-15s%5s%6s%6s";
                            mPrinter.setAlign(BluetoothPrinter.ALIGN_CENTER);
                            mPrinter.printText("ENJEL WATERS\n");
                            mPrinter.printText("Block 52 Lot 1, Bristol Street\n");
                            mPrinter.printText("North Fairview, Quezon City\n");
                            mPrinter.printText("09272574029/09194540889\n");
                            mPrinter.printText(date);
                            mPrinter.printText("\n");
                            mPrinter.printText("\n");
                            mPrinter.setAlign(BluetoothPrinter.ALIGN_LEFT);
                            mPrinter.printText("CUSTOMER NAME: \n" + customer + "\n");
                            mPrinter.printText("CELLPHONE NUMBER: " + ph0ne + "\n");
                            mPrinter.printText("ADDRESS: \n" + addr3ss + "\n");
                            mPrinter.printText(divider);
                            if (!n1.isShown()) {
                                n1.setVisibility(View.GONE);
                            } else {
                                mPrinter.printText("* " + na1 + " "+qty1+" x PHP"+price1+" = " + (qty1*price1) + "\n");
                            }
                            if (!n1.isShown()) {
                                n2.setVisibility(View.GONE);
                            } else {
                                mPrinter.printText("* " + na2 + " "+qty2+" x PHP"+price2+" = " + (qty2*price2) + "\n");
                            }
                            if (!n3.isShown()) {
                                n3.setVisibility(View.GONE);
                            } else {
                                mPrinter.printText("* " + na3 + " "+qty3+" x PHP"+price3+" = " + (qty3*price3) + "\n");
                            }
                            if (!n4.isShown()) {
                                n4.setVisibility(View.GONE);
                            } else {
                                mPrinter.printText("* " + na4 + " "+qty4+" x PHP"+price4+" = " + (qty4*price4) + "\n");
                            }
                            if (!n5.isShown()) {
                                n5.setVisibility(View.GONE);
                            } else {
                                mPrinter.printText("* " + na5 + " "+qty5+" x PHP"+price5+" = " + (qty5*price5) + "\n");
                            }
                            if (!n6.isShown()) {
                                n6.setVisibility(View.GONE);
                            } else {
                                mPrinter.printText("* " + na6 + " "+qty6+" x PHP"+price6+" = " + (qty6*price6) + "\n");
                            }
                            if (!n7.isShown()) {
                                n7.setVisibility(View.GONE);
                            } else {
                                mPrinter.printText("* " + na7 + " "+qty7+" x PHP"+price7+" = " + (qty7*price7) + "\n");
                            }
                            if (!n8.isShown()) {
                                n8.setVisibility(View.GONE);
                            } else {
                                mPrinter.printText("* " + na8 + " "+qty8+" x PHP"+price8+" = " + (qty8*price8) + "\n");
                            }
                            mPrinter.printText(divider);
                            mPrinter.setAlign(BluetoothPrinter.ALIGN_LEFT);
                            mPrinter.printText("RIDER:"+ridername+"\n");
                            mPrinter.printText("Total payment: PHP" + t0tal);
                            mPrinter.addNewLine();
                            mPrinter.feedPaper();
                            mPrinter.finish();
                            finish();
                        }

                        @Override
                        public void onFailed() {
                        }
                    });
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}