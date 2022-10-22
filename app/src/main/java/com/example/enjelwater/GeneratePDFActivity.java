package com.example.enjelwater;

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

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class GeneratePDFActivity extends AppCompatActivity {


    String[] permissions={"android.permission.WRITE_EXTERNAL_STORAGE"};

    Button generatePDFbtn;
    TextView date;

    Bitmap bmp, scaledbmp;

    int pageWitdh = 1200;

    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_pdfactivity);

        requestPermissions(permissions,80);

        generatePDFbtn = findViewById(R.id.pdfdownload);
        date = findViewById(R.id.dates);
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
        canvas.drawText("Total",1050,830,paint);

        canvas.drawLine(180,790,180,840,paint);
        canvas.drawLine(680,790,680,840,paint);
        canvas.drawLine(880,790,880,840,paint);
        canvas.drawLine(1030,790,1030,840,paint);

        pdfDocument.finishPage(myPage);

        File file = new File(Environment.getExternalStorageDirectory(), "Enjels_Water_Sales_"+date.getText().toString().trim()+".pdf");

        try {
            pdfDocument.writeTo(new FileOutputStream(file));

            Toast.makeText(GeneratePDFActivity.this, "PDF Invoice Downloaded", Toast.LENGTH_SHORT).show();
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
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
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
}