package com.example.enjelwater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    ImageView image;

    TextInputLayout forgotpassword;

    FirebaseAuth mAuth;

    Button resetPass,goback;

    ProgressBar progressBar;

    boolean isConnected = false;
    Dialog dialog;
    ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgot_password);

        forgotpassword = findViewById(R.id.forgotpassemail);
        image = findViewById(R.id.forgotImage);
        progressBar = findViewById(R.id.forgotbar);
        resetPass = findViewById(R.id.reset_pass);
        goback = findViewById(R.id.gobackreset);
        mAuth = FirebaseAuth.getInstance();

        dialog=new Dialog(this);


        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnected){

                    String email = forgotpassword.getEditText().getText().toString().trim();
                    if(email.isEmpty()){
                        forgotpassword.setError("Field cannot be empty");
                        forgotpassword.requestFocus();
                        return;
                    }else{
                        resetPass.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ForgotPasswordActivity.this,"Please check your email to reset Password!",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(ForgotPasswordActivity.this,LoginActivity.class);
                                    Pair[] pairs = new Pair[4];
                                    pairs[0] = new Pair<View,String>(image,"logo_image");
                                    pairs[1] = new Pair<View,String>(forgotpassword,"email_tran");
                                    pairs[2] = new Pair<View,String>(resetPass,"button_tran");
                                    pairs[3] = new Pair<View,String>(goback,"forgot_tran");
                                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ForgotPasswordActivity.this,pairs);
                                    startActivity(intent,options.toBundle());
                                    finish();
                                }else {
                                    forgotpassword.setError("Email does not Exist!");
                                    forgotpassword.requestFocus();
                                }
                                resetPass.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                    }

                }else{
                    openNoConnectionDialog();
                }
            }
        });
        goback.setOnClickListener(v -> super.onBackPressed());
    }
    private void registerNetworkCallBack(){
        try{
            connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback(){
                @Override
                public void onAvailable(@NonNull Network network) {
                    isConnected = true;
                }

                @Override
                public void onLost(@NonNull Network network) {
                    isConnected = false;
                }
            });
        }catch (Exception e){
            isConnected = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerNetworkCallBack();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    private void openNoConnectionDialog() {

        dialog.setContentView(R.layout.no_connection_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnok = dialog.findViewById(R.id.btn_notyet);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();


    }
}