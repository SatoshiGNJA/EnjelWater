package com.example.enjelwater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity {

    TextInputEditText editFull,editAddress;
    Button update,changepassword;
    ImageView back;

    FirebaseAuth auth;
    FirebaseUser user;
    String uid;

    DatabaseReference databaseReference;

    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editFull = findViewById(R.id.editname);
        editAddress = findViewById(R.id.editAddress);
        update = findViewById(R.id.btnupdateprof);
        changepassword = findViewById(R.id.btnchangepass);
        back = findViewById(R.id.btnbackEP);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        auth = FirebaseAuth.getInstance();
        uid = user.getUid();

        dialog=new Dialog(this);

        Drawable buttonDrawable = update.getBackground();
        buttonDrawable = DrawableCompat.wrap(buttonDrawable);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String fullname = snapshot.child("Users").child(uid).child("name").getValue(String.class);
                String address = snapshot.child("Users").child(uid).child("homeaddress").getValue(String.class);

                editFull.setText(fullname);
                editAddress.setText(address);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Drawable finalButtonDrawable = buttonDrawable;
        Drawable finalButtonDrawable1 = buttonDrawable;
        editFull.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String fullname = snapshot.child("Users").child(uid).child("name").getValue(String.class);
                        String address = snapshot.child("Users").child(uid).child("homeaddress").getValue(String.class);
                        String full = editFull.getText().toString();
                        String add = editAddress.getText().toString();

                        if(fullname.equals(full)&&address.equals(add)){
                            update.setEnabled(false);
                            DrawableCompat.setTint(finalButtonDrawable1, Color.parseColor("#009CFF"));
                            update.setBackground(finalButtonDrawable);
                        }else if(editFull.getText().toString().isEmpty()||editAddress.getText().toString().isEmpty()){

                            update.setEnabled(false);
                            DrawableCompat.setTint(finalButtonDrawable1, Color.parseColor("#009CFF"));
                            update.setBackground(finalButtonDrawable);

                        }else{
                            update.setEnabled(true);
                            DrawableCompat.setTint(finalButtonDrawable1, Color.GREEN);
                            update.setBackground(finalButtonDrawable);
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String fullname = snapshot.child("Users").child(uid).child("name").getValue(String.class);
                        String address = snapshot.child("Users").child(uid).child("homeaddress").getValue(String.class);
                        String full = editFull.getText().toString();
                        String add = editAddress.getText().toString();

                        if(fullname.equals(full)&&address.equals(add)){
                            update.setEnabled(false);
                            DrawableCompat.setTint(finalButtonDrawable1, Color.parseColor("#009CFF"));
                            update.setBackground(finalButtonDrawable);
                        }else if(editFull.getText().toString().isEmpty()||editAddress.getText().toString().isEmpty()){

                            update.setEnabled(false);
                            DrawableCompat.setTint(finalButtonDrawable1, Color.parseColor("#009CFF"));
                            update.setBackground(finalButtonDrawable);

                        }else{
                            update.setEnabled(true);
                            DrawableCompat.setTint(finalButtonDrawable1, Color.GREEN);
                            update.setBackground(finalButtonDrawable);
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        databaseReference.child("Users").child(uid).child("name").setValue(editFull.getText().toString());
                        databaseReference.child("Users").child(uid).child("homeaddress").setValue(editAddress.getText().toString());

                        String fullname = snapshot.child("Users").child(uid).child("name").getValue(String.class);
                        String address = snapshot.child("Users").child(uid).child("homeaddress").getValue(String.class);

                        editFull.setText(fullname);
                        editAddress.setText(address);
                        Toast.makeText(getApplicationContext(),"Successfully Updated",Toast.LENGTH_SHORT).show();
                        closeKeyBoard();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.changepassword);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Button okay = dialog.findViewById(R.id.btnchangeok);
                Button nope = dialog.findViewById(R.id.btnchangeno);
                TextInputEditText email = dialog.findViewById(R.id.emailchange);

                okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String test = email.getText().toString().trim();
                        if(test.isEmpty()){
                            email.setError("Field cannot be empty");
                            email.requestFocus();
                            return;
                        }else{
                            auth.sendPasswordResetEmail(test).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(EditProfileActivity.this,"Please check your email to reset Password!",Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                    }else {
                                        email.setError("Email does not Exist!");
                                        email.requestFocus();
                                    }
                                }
                            });
                        }
                    }
                });
                nope.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });

    }
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    private void closeKeyBoard(){
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}