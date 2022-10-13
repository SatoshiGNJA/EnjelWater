package com.example.enjelwater;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

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
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



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