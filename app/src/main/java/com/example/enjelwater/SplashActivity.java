package com.example.enjelwater;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 5000;

    //Variables
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView logo,slogan;
    VideoView videoView;
    MediaPlayer player;

    SharedPreferences sharedPreference;

    public static final String fileName = "login";
    public static final String Username = "username";
    public static final String Rider1 = "ridername1";
    public static final String Rider2 = "ridername2";
    public static final String Rider3 = "ridername3";
    public static final String Password = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        videoView = findViewById(R.id.watersplashvideo);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //Hooks
        image = findViewById(R.id.imageView);
        logo = findViewById(R.id.textView2);
        slogan = findViewById(R.id.textView3);

        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);



        String path = "android.resource://com.example.enjelwater/"+R.raw.waterrips;
        Uri u = Uri.parse(path);
        videoView.setVideoURI(u);
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(false);
            }
        });
        player = MediaPlayer.create(this,R.raw.waterdrop);

        new CountDownTimer(1500, 1000) {
            public void onFinish() {
                player.start();
            }

            public void onTick(long millisUntilFinished) {
                // millisUntilFinished    The amount of time until finished.
            }
        }.start();

        new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                sharedPreference = getSharedPreferences(fileName, Context.MODE_PRIVATE);
                if(sharedPreference.contains(Username)){
                    Intent i = new Intent(getApplicationContext(),AdminActivity.class);
                    startActivity(i);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }else if(sharedPreference.contains(Rider1)||sharedPreference.contains(Rider2)||sharedPreference.contains(Rider3)) {
                    Intent i = new Intent(getApplicationContext(),AdminRiderActivity.class);
                    startActivity(i);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }else if(firebaseUser != null) {
                    Intent i = new Intent(getApplicationContext(), UserProfileActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this);
                    startActivity(i, options.toBundle());
                    finish();
                }else {
                    Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                    Pair[] pairs = new Pair[2];
                    pairs[0] = new Pair<View,String>(image,"logo_image");
                    pairs[1] = new Pair<View,String>(logo,"logo_text");

                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this,pairs);
                    startActivity(intent, options.toBundle());
                    finish();
                }
            }

        }.start();

    }

    @Override
    protected void onResume() {
        videoView.resume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        videoView.suspend();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        videoView.stopPlayback();
        super.onDestroy();
    }
}