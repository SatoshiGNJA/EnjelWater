package com.example.enjelwater;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class GeneratePDFActivity extends AppCompatActivity {

    Button pdfbtn;

    int pageHeight = 1129;
    int pageWidth = 792;

    Bitmap bmp, scaledbmp;

    private static final int PERMISSION_REQUEST_CODE = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_pdfactivity);

        pdfbtn = findViewById(R.id.pdfdownload);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.delivery);
        scaledbmp = Bitmap.createScaledBitmap(bmp,140,140,false);

        if (checkPermission()) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }else{
            requestPermission();
        }

        pdfbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generatePDF();
            }
        });
    }
    private void generatePDF(){
        PdfDocument pdfDocument = new PdfDocument();

        Paint paint = new Paint();
        Paint title = new Paint();

        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pageWidth,pageHeight,1).create();

        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);

        Canvas canvas = myPage.getCanvas();

        canvas.drawBitmap(scaledbmp,56,40,paint);

        title.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.NORMAL));

        title.setTextSize(15);

        title.setColor(ContextCompat.getColor(this,R.color.purple_200));

        canvas.drawText("Enjel Waters.",209,100,title);
        canvas.drawText("Clean Water at your Service", 209,80,title);

        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        title.setColor(ContextCompat.getColor(this,R.color.purple_200));
        title.setTextSize(15);

        title.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Testing for PDF Download",396,560,title);

        pdfDocument.finishPage(myPage);

        File file = new File(Environment.getExternalStorageDirectory(),"WATERS.pdf");
        try{
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(this, "PDF has been downloaded", Toast.LENGTH_SHORT).show();
            finish();
        }catch (IOException e){
            e.printStackTrace();
        }

        pdfDocument.close();
    }
    private boolean checkPermission(){

        int permission1 = ContextCompat.checkSelfPermission(this,WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(this,READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
    }
     @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         super.onRequestPermissionsResult(requestCode, permissions, grantResults);
         if (requestCode == PERMISSION_REQUEST_CODE) {
             if (grantResults.length > 0) {
                 boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                 boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                 if (writeStorage && readStorage) {
                     Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                 } else {
                     Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                     finish();
                 }
             }
         }
     }
}