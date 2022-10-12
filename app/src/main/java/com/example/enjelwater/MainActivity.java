package com.example.enjelwater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView n1,n2,n3,n4,n5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        n1 = findViewById(R.id.n1);
        n2 = findViewById(R.id.n2);
        n3 = findViewById(R.id.n3);
        n4 = findViewById(R.id.n4);
        n5 = findViewById(R.id.n5);

        String na1 = getIntent().getStringExtra("Name1");
        String na2 = getIntent().getStringExtra("Name2");
        String na3 = getIntent().getStringExtra("Name3");
        String na4 = getIntent().getStringExtra("Name4");
        String na5 = getIntent().getStringExtra("Name5");
        String na6 = getIntent().getStringExtra("Name6");
        String na7 = getIntent().getStringExtra("Name7");
        String na8 = getIntent().getStringExtra("Name8");

        n1.setText(na1);
        n2.setText(na2);
        n3.setText(na3);
        n4.setText(na4);
        n5.setText(na5);




        }

}