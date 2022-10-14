package com.example.enjelwater;

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

import java.util.Set;

@RequiresApi(api = Build.VERSION_CODES.S)
public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_BLUETOOTH = 0;
    private static final int PERMISSION_BLUETOOTH_ADMIN = 3;
    private static final int PERMISSION_BLUETOOTH_CONNECT = 2;
    private static final int PERMISSION_BLUETOOTH_SCAN = 1;

    BluetoothDevice device = null;

    Button print;

    TextView n1,n2,n3,n4,n5,n6,n7,n8;
    TextView custName,address,total;
    ImageView goback;

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
        total = findViewById(R.id.total);
        goback =  findViewById(R.id.returnback);
        print = findViewById(R.id.btn_print);

        String na1 = getIntent().getStringExtra("Name1");
        String na2 = getIntent().getStringExtra("Name2");
        String na3 = getIntent().getStringExtra("Name3");
        String na4 = getIntent().getStringExtra("Name4");
        String na5 = getIntent().getStringExtra("Name5");
        String na6 = getIntent().getStringExtra("Name6");
        String na7 = getIntent().getStringExtra("Name7");
        String na8 = getIntent().getStringExtra("Name8");
        String customer = getIntent().getStringExtra("CustomerN");
        String addr3ss = getIntent().getStringExtra("Address");
        String t0tal = getIntent().getStringExtra("Total");

        n1.setText(na1);
        n2.setText(na2);
        n3.setText(na3);
        n4.setText(na4);
        n5.setText(na5);
        n6.setText(na6);
        n7.setText(na7);
        n8.setText(na8);
        custName.setText(customer);
        address.setText(addr3ss);
        total.setText("Total: " + t0tal);

        if(n1.getText().toString().equals("Name1: ")){
            n1.setVisibility(View.GONE);
        }else{
            n1.setText(na1);
        }
        if(n2.getText().toString().equals("Name2: ")){
            n2.setVisibility(View.GONE);
        }else{
            n2.setText(na2);
        }
        if(n3.getText().toString().equals("Name3: ")){
            n3.setVisibility(View.GONE);
        }else{
            n3.setText(na3);
        }
        if(n4.getText().toString().equals("Name4: ")){
            n4.setVisibility(View.GONE);
        }else{
            n4.setText(na4);
        }
        if(n5.getText().toString().equals("Name5: ")){
            n5.setVisibility(View.GONE);
        }else{
            n5.setText(na5);
        }
        if(n6.getText().toString().equals("Name6: ")){
            n6.setVisibility(View.GONE);
        }else{
            n6.setText(na6);
        }
        if(n7.getText().toString().equals("Name7: ")){
            n7.setVisibility(View.GONE);
        }else{
            n7.setText(na7);
        }
        if(n8.getText().toString().equals("Name8: ")){
            n8.setVisibility(View.GONE);
        }else{
            n8.setText(na8);
        }

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AdminActivity.class);
                i.putExtra("frgToLoad", 2);
                startActivity(i);
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

        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String divider = String.format("%" + 32 + "s", "").replace(' ', '-');
                if (device != null) {
                    final BluetoothPrinter mPrinter = new BluetoothPrinter(device);
                    mPrinter.connectPrinter(new BluetoothPrinter.PrinterConnectListener() {
                        @Override
                        public void onConnected() {

                            mPrinter.setAlign(BluetoothPrinter.ALIGN_LEFT);
                            // table format: 32 character width per line
                            String format = "%-15s%5s%6s%6s";

                            mPrinter.printText("Customer Name:\n"+customer+"\n");
                            mPrinter.printText(divider);
                            if(n1.getText().toString().equals("Name1: ")){
                                n1.setVisibility(View.GONE);
                            }else{
                                mPrinter.printText("- "+na1+"\n");
                            }
                            if(n2.getText().toString().equals("Name2: ")){
                                n2.setVisibility(View.GONE);
                            }else{
                                mPrinter.printText("- "+na2+"\n");
                            }
                            if(n3.getText().toString().equals("Name3: ")){
                                n3.setVisibility(View.GONE);
                            }else{
                                mPrinter.printText("-"+na3+"\n");
                            }
                            if(n4.getText().toString().equals("Name4: ")){
                                n4.setVisibility(View.GONE);
                            }else{
                                mPrinter.printText("- "+na4+"\n");
                            }
                            if(n5.getText().toString().equals("Name5: ")){
                                n5.setVisibility(View.GONE);
                            }else{
                                mPrinter.printText("- "+na5+"\n");
                            }
                            if(n6.getText().toString().equals("Name6: ")){
                                n6.setVisibility(View.GONE);
                            }else{
                                mPrinter.printText("- "+na6+"\n");
                            }
                            if(n7.getText().toString().equals("Name7: ")){
                                n7.setVisibility(View.GONE);
                            }else{
                                mPrinter.printText("- "+na7+"\n");
                            }
                            if(n8.getText().toString().equals("Name8: ")){
                                n8.setVisibility(View.GONE);
                            }else{
                                mPrinter.printText("- "+na8+"\n");
                            }
                            mPrinter.printText(divider);
                            mPrinter.printText("Total: " + t0tal);
                            mPrinter.addNewLine();
                            mPrinter.printText(divider);
                            mPrinter.printText(addr3ss);
                            mPrinter.feedPaper();
                            mPrinter.finish();
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
        Intent i = new Intent(this, AdminActivity.class);
        i.putExtra("frgToLoad", 2);
        startActivity(i);
        super.onBackPressed();
    }
}