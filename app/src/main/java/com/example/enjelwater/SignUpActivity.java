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
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enjelwater.Model.UserHelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    Button callLogin,register_btn;
    ImageView image;
    TextView logoText,sloganText;
    TextInputLayout regName,regHomeAddress,regemail,regPhoneNo,regpassword,regRetypePass;
    ProgressBar progressBar;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private FirebaseAuth mAuth;
    boolean isConnected = false;
    Dialog dialog;
    ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        callLogin = findViewById(R.id.loginbtn_screen);
        image = findViewById(R.id.llogo_image);
        logoText = findViewById(R.id.llogo_name);
        sloganText = findViewById(R.id.sslogan_name);
        regemail = findViewById(R.id.email);
        regName = findViewById(R.id.name);
        regHomeAddress = findViewById(R.id.username);
        regPhoneNo = findViewById(R.id.phoneNo);
        regpassword = findViewById(R.id.password);
        regRetypePass = findViewById(R.id.retype_password);
        register_btn = findViewById(R.id.register_btn);
        progressBar = findViewById(R.id.progressBar2);
        mAuth = FirebaseAuth.getInstance();
        dialog=new Dialog(this);

        callLogin.setOnClickListener(v -> super.onBackPressed());

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isConnected){

                    if(!validateName() | !validatePassword() | !validatePhoneNo() | !validateEmail() | !validateHomeAddress()){
                        return;
                    }else{
                        IsRegister();
                    }

                }else{
                    openNoConnectionDialog();
                }

            }
        });

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

    private Boolean validateName(){
        String val = regName.getEditText().getText().toString();

        if(val.isEmpty()){
            regName.setError("Field cannot be empty");
            return false;
        }else{
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateHomeAddress(){
        String val = regHomeAddress.getEditText().getText().toString();

        if(val.isEmpty()){
            regHomeAddress.setError("Field cannot be empty");
            return false;
        }else{
            regHomeAddress.setError(null);
            regHomeAddress.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail(){
        String val = regemail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(val.isEmpty()){
            regemail.setError("Field cannot be empty");
            return false;
        }else if(!val.matches(emailPattern)){
            regemail.setError("Invalid Email Address");
            return false;
        } else{
            regemail.setError(null);
            regemail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhoneNo(){
        String val = regPhoneNo.getEditText().getText().toString();

        if(val.isEmpty()){
            regPhoneNo.setError("Field cannot be empty");
            return false;
        }else{
            regPhoneNo.setError(null);
            regPhoneNo.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword(){
        String val = regpassword.getEditText().getText().toString();
        String retype = regRetypePass.getEditText().getText().toString();
        String passwordval = "^" +
                "(?=.*[a-zA-Z])" +
                "(?=\\S+$)" +
                ".{4,}" +
                "$";
        if(val.isEmpty()){
            regpassword.setError("Field cannot be empty");
            return false;
        }
        else if(!val.matches(passwordval)){
            regpassword.setError("Password is too weak");
            return false;
        }else if(!val.matches(retype)){
            regpassword.setError(null);
            regpassword.setErrorEnabled(false);
            regRetypePass.setError("Password not Matched!");
            return false;
        }else{
            regpassword.setError(null);
            regpassword.setErrorEnabled(false);
            regRetypePass.setError(null);
            regRetypePass.setErrorEnabled(false);
            return true;
        }

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
    private void IsRegister(){

        String name = regName.getEditText().getText().toString();
        String homeaddress = regHomeAddress.getEditText().getText().toString();
        String email = regemail.getEditText().getText().toString();
        String phoneNo = regPhoneNo.getEditText().getText().toString();
        String password = regpassword.getEditText().getText().toString();
        //UserHelperClass helperClass = new UserHelperClass(name,username,email,phoneNo,password);
        //reference.child(username).setValue(helperClass);

        progressBar.setVisibility(View.VISIBLE);
        register_btn.setVisibility(View.GONE);

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            UserHelperClass user = new UserHelperClass(name,homeaddress,email,phoneNo);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        sendEmailVerification();
                                    }
                                    else{
                                        Toast.makeText(SignUpActivity.this,"User Failed to Register", Toast.LENGTH_LONG).show();
                                    }
                                    progressBar.setVisibility(View.GONE);
                                    register_btn.setVisibility(View.VISIBLE);
                                }
                            });

                        }
                        else{
                            regemail.setError("Email Already Exist!");
                            register_btn.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });

    }
    private void sendEmailVerification(){
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(SignUpActivity.this, "Successfully Registered, Verification mail sent!", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                        finish();
                        startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                    }else{
                        Toast.makeText(SignUpActivity.this, "Verification mail has not been sent!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}