package com.example.enjelwater;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import com.example.enjelwater.Model.CartModel;
import com.example.enjelwater.Model.HistoryModel;
import com.example.enjelwater.Model.PersonalOrderModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GeneratePDFActivity extends AppCompatActivity {


    String[] permissions={"android.permission.WRITE_EXTERNAL_STORAGE"};

    Button generatePDFbtn;
    TextView date;

    TextView qty1,total1;
    TextView qty2,total2;
    TextView qty3,total3;
    TextView qty4,total4;
    TextView qty5,total5;
    TextView qty6,total6;
    TextView qty7,total7;
    TextView qty8,total8;

    TextView overall;

    Bitmap bmp, scaledbmp;

    int pageWitdh = 1200;

    private static final int PERMISSION_REQUEST_CODE = 200;

    HistoryModel historyModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_pdfactivity);

        historyModel = new HistoryModel();

        requestPermissions(permissions,80);

        generatePDFbtn = findViewById(R.id.pdfdownload);
        date = findViewById(R.id.dates);
        qty1 = findViewById(R.id.qty1);
        total1 = findViewById(R.id.total1);
        qty2 = findViewById(R.id.qty2);
        total2 = findViewById(R.id.total2);
        qty3 = findViewById(R.id.qty3);
        total3 = findViewById(R.id.total3);
        qty4 = findViewById(R.id.qty4);
        total4 = findViewById(R.id.total4);
        qty5 = findViewById(R.id.qty5);
        total5 = findViewById(R.id.total5);
        qty6 = findViewById(R.id.qty6);
        total6 = findViewById(R.id.total6);
        qty7 = findViewById(R.id.qty7);
        total7 = findViewById(R.id.total7);
        qty8 = findViewById(R.id.qty8);
        total8 = findViewById(R.id.total8);
        overall = findViewById(R.id.overalltotal);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.header_pdf);
        scaledbmp = Bitmap.createScaledBitmap(bmp, 1200, 518, false);

        String dates = getIntent().getStringExtra("Date");

        date.setText(dates);

        if (checkPermission()) {
            System.out.println("Permission Granted!");
        } else {
            reperm();
        }

        generatePDFbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePDF();
            }
        });
        Qty1Total();
        Qty2Total();
        Qty3Total();
        Qty4Total();
        Qty5Total();
        Qty6Total();
        Qty7Total();
        Qty8Total();

        TotalSales();


    }

    private void generatePDF() {

        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        Paint title = new Paint();
        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);
        Canvas canvas = myPage.getCanvas();
        canvas.drawBitmap(scaledbmp, 0, 0, paint);

        title.setTextAlign(Paint.Align.CENTER);
        title.setTypeface(Typeface.create(Typeface.MONOSPACE,Typeface.BOLD));
        title.setTextSize(70);
        canvas.drawText("Total Sales",pageWitdh/2,600,title);

        title.setTextAlign(Paint.Align.CENTER);
        title.setTypeface(Typeface.create(Typeface.MONOSPACE,Typeface.NORMAL));
        title.setTextSize(50);
        canvas.drawText(date.getText().toString().trim(),pageWitdh/2,700,title);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        canvas.drawRect(20,780,pageWitdh-20,860,paint);

        paint.setTextAlign(Paint.Align.LEFT);
        paint.setStyle(Paint.Style.FILL);

        paint.setTextSize(40);
        canvas.drawText("No.",40,830,paint);
        canvas.drawText("Item Description",200,830,paint);
        canvas.drawText("Price",700,830,paint);
        canvas.drawText("Qty",900,830,paint);
        canvas.drawText("Total",1000,830,paint);

        canvas.drawLine(180,790,180,840,paint);
        canvas.drawLine(680,790,680,840,paint);
        canvas.drawLine(880,790,880,840,paint);
        canvas.drawLine(980,790,980,840,paint);

        canvas.drawText("1.",40,950,paint);
        canvas.drawText("Mineral Water - REFILL",200,950,paint);
        canvas.drawText("Round Gallon",200,1000,paint);
        canvas.drawText("₱25",700,950,paint);
        canvas.drawText(qty1.getText().toString(),900,950,paint);
        canvas.drawText(total1.getText().toString(),1000,950,paint);
        paint.setTextAlign(Paint.Align.LEFT);

        canvas.drawText("2.",40,1050,paint);
        canvas.drawText("Alkaline Water - REFILL",200,1050,paint);
        canvas.drawText("Round Gallon",200,1100,paint);
        canvas.drawText("₱35",700,1050,paint);
        canvas.drawText(qty2.getText().toString(),900,1050,paint);
        canvas.drawText(total2.getText().toString(),1000,1050,paint);
        paint.setTextAlign(Paint.Align.LEFT);

        canvas.drawText("3.",40,1150,paint);
        canvas.drawText("Mineral Water - REFILL",200,1150,paint);
        canvas.drawText("Slim Gallon",200,1200,paint);
        canvas.drawText("₱25",700,1150,paint);
        canvas.drawText(qty3.getText().toString(),900,1150,paint);
        canvas.drawText(total3.getText().toString(),1000,1150,paint);
        paint.setTextAlign(Paint.Align.LEFT);

        canvas.drawText("4.",40,1250,paint);
        canvas.drawText("Alkaline Water - REFILL",200,1250,paint);
        canvas.drawText("Slim Gallon",200,1300,paint);
        canvas.drawText("₱35",700,1250,paint);
        canvas.drawText(qty4.getText().toString(),900,1250,paint);
        canvas.drawText(total4.getText().toString(),1000,1250,paint);
        paint.setTextAlign(Paint.Align.LEFT);

        canvas.drawText("5.",40,1350,paint);
        canvas.drawText("Mineral Water - NEW",200,1350,paint);
        canvas.drawText("Round Gallon",200,1400,paint);
        canvas.drawText("₱225",700,1350,paint);
        canvas.drawText(qty5.getText().toString(),900,1350,paint);
        canvas.drawText(total5.getText().toString(),1000,1350,paint);
        paint.setTextAlign(Paint.Align.LEFT);

        canvas.drawText("6.",40,1450,paint);
        canvas.drawText("Alkaline Water - NEW",200,1450,paint);
        canvas.drawText("Round Gallon",200,1500,paint);
        canvas.drawText("₱235",700,1450,paint);
        canvas.drawText(qty6.getText().toString(),900,1450,paint);
        canvas.drawText(total6.getText().toString(),1000,1450,paint);
        paint.setTextAlign(Paint.Align.LEFT);

        canvas.drawText("7.",40,1550,paint);
        canvas.drawText("Mineral Water - NEW",200,1550,paint);
        canvas.drawText("Slim Gallon",200,1600,paint);
        canvas.drawText("₱225",700,1550,paint);
        canvas.drawText(qty7.getText().toString(),900,1550,paint);
        canvas.drawText(total7.getText().toString(),1000,1550,paint);
        paint.setTextAlign(Paint.Align.LEFT);

        canvas.drawText("8.",40,1650,paint);
        canvas.drawText("Alkaline Water - NEW",200,1650,paint);
        canvas.drawText("Slim Gallon",200,1700,paint);
        canvas.drawText("₱235",700,1650,paint);
        canvas.drawText(qty8.getText().toString(),900,1650,paint);
        canvas.drawText(total8.getText().toString(),1000,1650,paint);
        paint.setTextAlign(Paint.Align.LEFT);

        canvas.drawText("Total: ",850,1800,paint);
        canvas.drawText(overall.getText().toString(),1000,1800,paint);
        paint.setTextAlign(Paint.Align.LEFT);

        pdfDocument.finishPage(myPage);

        File file = new File(Environment.getExternalStorageDirectory(), "Enjels_Water_Sales_"+date.getText().toString().trim()+".pdf");

        try {
            pdfDocument.writeTo(new FileOutputStream(file));

            Toast.makeText(GeneratePDFActivity.this, "PDF Sales has Been Generated", Toast.LENGTH_SHORT).show();
            finish();
        } catch (IOException e) {

            e.printStackTrace();
        }

        pdfDocument.close();
    }

    private boolean checkPermission() {
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void reperm() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void TestData(){
        FirebaseDatabase.getInstance()
                .getReference("Finish")
                .child(date.getText().toString().trim())
                .orderByChild("name1")
                .equalTo("Mineral Water - REFILL Round Gallon")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String count = String.valueOf(snapshot.getChildrenCount());
                        Toast.makeText(GeneratePDFActivity.this, count, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    private void Qty1Total(){
        List<HistoryModel> historyModels = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("Finish")
                .child(date.getText().toString().trim())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        historyModels.clear();
                        Integer quantity = 0;
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            HistoryModel historyModel = ds.getValue(HistoryModel.class);
                            Integer cost = Integer.valueOf(historyModel.getQty1());
                            quantity += cost;
                            qty1.setText(String.valueOf(quantity));
                            total1.setText("₱"+25*quantity+".00");

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    private void Qty2Total(){
        List<HistoryModel> historyModels = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("Finish")
                .child(date.getText().toString().trim())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        historyModels.clear();
                        Integer quantity = 0;
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            HistoryModel historyModel = ds.getValue(HistoryModel.class);
                            Integer cost = Integer.valueOf(historyModel.getQty2());
                            quantity += cost;
                            qty2.setText(String.valueOf(quantity));
                            total2.setText("₱"+35*quantity+".00");

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    private void Qty3Total(){
        List<HistoryModel> historyModels = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("Finish")
                .child(date.getText().toString().trim())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        historyModels.clear();
                        Integer quantity1 = 0;
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            HistoryModel historyModel = ds.getValue(HistoryModel.class);
                            Integer cost = Integer.valueOf(historyModel.getQty3());
                            quantity1 += cost;
                            qty3.setText(String.valueOf(quantity1));
                            total3.setText("₱"+25*quantity1+".00");

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    private void Qty4Total(){
        List<HistoryModel> historyModels = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("Finish")
                .child(date.getText().toString().trim())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        historyModels.clear();
                        Integer quantity1 = 0;
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            HistoryModel historyModel = ds.getValue(HistoryModel.class);
                            Integer cost = Integer.valueOf(historyModel.getQty4());
                            quantity1 += cost;
                            qty4.setText(String.valueOf(quantity1));
                            total4.setText("₱"+35*quantity1+".00");

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    private void Qty5Total(){
        List<HistoryModel> historyModels = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("Finish")
                .child(date.getText().toString().trim())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        historyModels.clear();
                        Integer quantity1 = 0;
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            HistoryModel historyModel = ds.getValue(HistoryModel.class);
                            Integer cost = Integer.valueOf(historyModel.getQty5());
                            quantity1 += cost;
                            qty5.setText(String.valueOf(quantity1));
                            total5.setText("₱"+225*quantity1+".00");

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    private void Qty6Total(){
        List<HistoryModel> historyModels = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("Finish")
                .child(date.getText().toString().trim())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        historyModels.clear();
                        Integer quantity1 = 0;
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            HistoryModel historyModel = ds.getValue(HistoryModel.class);
                            Integer cost = Integer.valueOf(historyModel.getQty6());
                            quantity1 += cost;
                            qty6.setText(String.valueOf(quantity1));
                            total6.setText("₱"+235*quantity1+".00");

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    private void Qty7Total(){
        List<HistoryModel> historyModels = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("Finish")
                .child(date.getText().toString().trim())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        historyModels.clear();
                        Integer quantity1 = 0;
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            HistoryModel historyModel = ds.getValue(HistoryModel.class);
                            Integer cost = Integer.valueOf(historyModel.getQty7());
                            quantity1 += cost;
                            qty7.setText(String.valueOf(quantity1));
                            total7.setText("₱"+225*quantity1+".00");

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    private void Qty8Total(){
        List<HistoryModel> historyModels = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("Finish")
                .child(date.getText().toString().trim())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        historyModels.clear();
                        Integer quantity1 = 0;
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            HistoryModel historyModel = ds.getValue(HistoryModel.class);
                            Integer cost = Integer.valueOf(historyModel.getQty8());
                            quantity1 += cost;
                            qty8.setText(String.valueOf(quantity1));
                            total8.setText("₱"+235*quantity1+".00");

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    private void TotalSales(){
        List<HistoryModel> historyModels = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("Finish")
                .child(date.getText().toString().trim())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        historyModels.clear();
                        Double total = 0.0;
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            HistoryModel historyModel = ds.getValue(HistoryModel.class);
                            Double cost = Double.valueOf(historyModel.getTotalPrice());
                            total += cost;
                            overall.setText("₱"+String.format("%.2f", total));

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}