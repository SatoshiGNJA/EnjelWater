package com.example.enjelwater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ThankYouActivity extends AppCompatActivity {

    TextView countView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);

        countView = findViewById(R.id.countdown);

        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l) {
                countView.setText("This will redirect you to the main screen " + "("+l / 1000+")");
            }

            @Override
            public void onFinish() {

                Intent i = new Intent(ThankYouActivity.this,UserProfileActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();

            }
        }.start();
    }
    @Override
    public void onBackPressed() {

    }
}