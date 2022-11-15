package com.example.enjelwater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Button callSignUp,login_btn,forgotbutt;
    ImageView image;
    TextView logoText,sloganText;
    TextInputLayout email,password;
    ProgressBar progressBar;
    String uid;
    FirebaseAuth mAuth;
    boolean isConnected = false;
    Dialog dialog;
    ConnectivityManager connectivityManager;

    SharedPreferences sharedPreference;

    DatabaseReference reference;

    String adminuser,admine,adminpass;

    public static final String fileName = "login";
    public static final String Username = "username";
    public static final String Password = "password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        callSignUp = findViewById(R.id.signup_screen);
        image = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        login_btn = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progressBarLogin);
        forgotbutt = findViewById(R.id.forgotbutton);
        dialog=new Dialog(this);

        mAuth = FirebaseAuth.getInstance();

        reference = FirebaseDatabase.getInstance().getReference();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adminuser = snapshot.child("Admin").child("admin").child("username").getValue(String.class);
                admine = snapshot.child("Admin").child("admin").child("email").getValue(String.class);
                adminpass = snapshot.child("Admin").child("admin").child("password").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sharedPreference = getSharedPreferences(fileName,Context.MODE_PRIVATE);




                callSignUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                        Pair[] pairs = new Pair[7];

                        pairs[0] = new Pair<View, String>(image, "logo_image");
                        pairs[1] = new Pair<View, String>(logoText, "logo_text");
                        pairs[2] = new Pair<View, String>(sloganText, "logo_desc");
                        pairs[3] = new Pair<View, String>(email, "email_tran");
                        pairs[4] = new Pair<View, String>(password, "password_tran");
                        pairs[5] = new Pair<View, String>(login_btn, "button_tran");
                        pairs[6] = new Pair<View, String>(callSignUp, "logo_signup_tran");

                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
                        startActivity(intent, options.toBundle());


                    }
                });

        forgotbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                Pair[] pairs = new Pair[4];
                pairs[0] = new Pair<View,String>(image,"logo_image");
                pairs[1] = new Pair<View,String>(email,"email_tran");
                pairs[2] = new Pair<View,String>(login_btn,"button_tran");
                pairs[3] = new Pair<View,String>(forgotbutt,"forgot_tran");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);
                startActivity(intent,options.toBundle());

            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(isConnected){

                    if(!validateUserName() |!validatePassword()){
                        return;
                    }else{
                        isAdmin();
                    }

                }else {
                    openNoConnectionDialog();
                }
            }
        });
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

    private Boolean validateUserName(){
        String val = email.getEditText().getText().toString();
        if(val.isEmpty()){
            email.setError("Email is not Valid");
            return false;
        }else{
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword(){
        String val = password.getEditText().getText().toString();

        if(val.isEmpty()){
            password.setError("Field cannot be empty");
            return false;
        }else{
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    private void isUser() {



         String userEnteredUsername = email.getEditText().getText().toString().trim();
         String userEnteredPassword = password.getEditText().getText().toString().trim();

        login_btn.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(userEnteredUsername,userEnteredPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    email.setError(null);
                    email.setErrorEnabled(false);
                    password.setError(null);
                    password.setErrorEnabled(false);
                    checkEmailVerification();

                }else{
                    mAuth.fetchSignInMethodsForEmail(email.getEditText().getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                @Override
                                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                    String val = email.getEditText().getText().toString();
                                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                                    if(!val.matches(emailPattern)) {
                                        email.setError("Invalid Data Input");
                                        password.setError("Invalid Data Input");
                                    }else{
                                        boolean check = !task.getResult().getSignInMethods().isEmpty();

                                        if(!check){
                                            email.setError("Email not Found!");
                                        }else{
                                            password.setError("Incorrect Password");
                                        }
                                        login_btn.setVisibility(View.VISIBLE);
                                        progressBar.setVisibility(View.GONE);
                                    }
                                    login_btn.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                }
            }
        });
    }
    private void isAdmin(){

        String userEnteredUsername = email.getEditText().getText().toString().trim();
        String userEnteredPassword = password.getEditText().getText().toString().trim();


                try {
                    if((admine.equals(userEnteredUsername)||adminuser.equals(userEnteredUsername))&&adminpass.equals(userEnteredPassword)){
                        mAuth.signInWithEmailAndPassword(admine,userEnteredPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    SharedPreferences.Editor editor = sharedPreference.edit();
                                    editor.putString(Username,userEnteredUsername);
                                    editor.putString(Password,userEnteredPassword);
                                    editor.apply();
                                    finish();
                                    Intent i = new Intent(getApplicationContext(),AdminActivity.class);
                                    i.putExtra("NewPassword", userEnteredPassword);
                                    startActivity(i);
                                }else{
                                    Toast.makeText(LoginActivity.this, "failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        mAuth.signInWithEmailAndPassword(admine,userEnteredPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    SharedPreferences.Editor editor = sharedPreference.edit();
                                    editor.putString(Username,userEnteredUsername);
                                    editor.putString(Password,userEnteredPassword);
                                    editor.apply();
                                    finish();
                                    Intent i = new Intent(getApplicationContext(),AdminActivity.class);
                                    i.putExtra("NewPassword", userEnteredPassword);
                                    startActivity(i);
                                }else{
                                    isUser();
                                }
                            }
                        });
                    }
                }catch (Exception e){
                    System.out.println(e);
                }


        login_btn.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        }

        private void checkEmailVerification(){
            FirebaseUser firebaseUser = mAuth.getInstance().getCurrentUser();
            Boolean emailflag = firebaseUser.isEmailVerified();

            if(emailflag){
                finish();
                Intent i = new Intent(getApplicationContext(),UserProfileActivity.class);
                startActivity(i);
            }else{
                email.setError("Verify your email!");
                login_btn.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                mAuth.signOut();
            }
        }
}