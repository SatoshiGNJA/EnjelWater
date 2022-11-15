package com.example.enjelwater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RiderActivity extends AppCompatActivity {


    ImageView goback;

    TextInputLayout ridername1,ridername2,ridername3;
    TextInputLayout riderusername1,riderusername2,riderusername3;
    TextInputLayout riderpassword1,riderpassword2,riderpassword3;

    Button editrider1,editrider2,editrider3;

    DatabaseReference RiderReference;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider);

        RiderReference = FirebaseDatabase.getInstance().getReference();

        goback = findViewById(R.id.returnback);
        ridername1 = findViewById(R.id.ridername1);
        ridername2 = findViewById(R.id.ridername2);
        ridername3 = findViewById(R.id.ridername3);
        riderusername1 = findViewById(R.id.UserRider1);
        riderusername2 = findViewById(R.id.UserRider2);
        riderusername3 = findViewById(R.id.UserRider3);
        riderpassword1 = findViewById(R.id.PassRider1);
        riderpassword2 = findViewById(R.id.PassRider2);
        riderpassword3 = findViewById(R.id.PassRider3);
        editrider1 = findViewById(R.id.btn_rider1);
        editrider2 = findViewById(R.id.btn_rider2);
        editrider3 = findViewById(R.id.btn_rider3);

        dialog=new Dialog(this);

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
            }
        });
        RiderReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String rider1name = snapshot.child("Admin").child("riders").child("Rider1").child("name").getValue(String.class);
                String rider1username = snapshot.child("Admin").child("riders").child("Rider1").child("username").getValue(String.class);
                String rider1pass = snapshot.child("Admin").child("riders").child("Rider1").child("password").getValue(String.class);

                ridername1.getEditText().setText(rider1name);
                riderusername1.getEditText().setText(rider1username);
                riderpassword1.getEditText().setText(rider1pass);

                String rider2name = snapshot.child("Admin").child("riders").child("Rider2").child("name").getValue(String.class);
                String rider2username = snapshot.child("Admin").child("riders").child("Rider2").child("username").getValue(String.class);
                String rider2pass = snapshot.child("Admin").child("riders").child("Rider2").child("password").getValue(String.class);

                ridername2.getEditText().setText(rider2name);
                riderusername2.getEditText().setText(rider2username);
                riderpassword2.getEditText().setText(rider2pass);

                String rider3name = snapshot.child("Admin").child("riders").child("Rider3").child("name").getValue(String.class);
                String rider3username = snapshot.child("Admin").child("riders").child("Rider3").child("username").getValue(String.class);
                String rider3pass = snapshot.child("Admin").child("riders").child("Rider3").child("password").getValue(String.class);

                ridername3.getEditText().setText(rider3name);
                riderusername3.getEditText().setText(rider3username);
                riderpassword3.getEditText().setText(rider3pass);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        editrider1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.update_rider1);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextInputLayout name1 = dialog.findViewById(R.id.update_name);
                TextInputLayout username1 = dialog.findViewById(R.id.update_username);
                TextInputLayout password1 = dialog.findViewById(R.id.update_password);

                Button btnok = dialog.findViewById(R.id.btn_change);
                Button btnnotyet = dialog.findViewById(R.id.btn_no);

                ProgressBar progressBar = dialog.findViewById(R.id.progress_update);

                RiderReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String rider1name = snapshot.child("Admin").child("riders").child("Rider1").child("name").getValue(String.class);
                        String rider1username = snapshot.child("Admin").child("riders").child("Rider1").child("username").getValue(String.class);
                        String rider1pass = snapshot.child("Admin").child("riders").child("Rider1").child("password").getValue(String.class);

                        name1.getEditText().setText(rider1name);
                        username1.getEditText().setText(rider1username);
                        password1.getEditText().setText(rider1pass);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                name1.getEditText().addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        RiderReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String rider1name = snapshot.child("Admin").child("riders").child("Rider1").child("name").getValue(String.class);
                                String rider1username = snapshot.child("Admin").child("riders").child("Rider1").child("username").getValue(String.class);
                                String rider1pass = snapshot.child("Admin").child("riders").child("Rider1").child("password").getValue(String.class);
                                btnok.setEnabled(!name1.getEditText().getText().toString().equals(rider1name) || !username1.getEditText().getText().toString().equals(rider1username) || !password1.getEditText().getText().toString().equals(rider1pass));
                                if (btnok.isEnabled()){
                                    btnok.setBackgroundColor(Color.parseColor("#00FF00"));
                                    btnok.setTextColor(Color.BLACK);
                                }else{
                                    btnok.setBackgroundColor(Color.parseColor("#009CFF"));
                                    btnok.setTextColor(Color.WHITE);
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
                username1.getEditText().addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        RiderReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String rider1name = snapshot.child("Admin").child("riders").child("Rider1").child("name").getValue(String.class);
                                String rider1username = snapshot.child("Admin").child("riders").child("Rider1").child("username").getValue(String.class);
                                String rider1pass = snapshot.child("Admin").child("riders").child("Rider1").child("password").getValue(String.class);
                                btnok.setEnabled(!name1.getEditText().getText().toString().equals(rider1name) || !username1.getEditText().getText().toString().equals(rider1username) || !password1.getEditText().getText().toString().equals(rider1pass));
                                if (btnok.isEnabled()){
                                    btnok.setBackgroundColor(Color.parseColor("#00FF00"));
                                    btnok.setTextColor(Color.BLACK);
                                }else{
                                    btnok.setBackgroundColor(Color.parseColor("#009CFF"));
                                    btnok.setTextColor(Color.WHITE);
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
                password1.getEditText().addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        RiderReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String rider1name = snapshot.child("Admin").child("riders").child("Rider1").child("name").getValue(String.class);
                                String rider1username = snapshot.child("Admin").child("riders").child("Rider1").child("username").getValue(String.class);
                                String rider1pass = snapshot.child("Admin").child("riders").child("Rider1").child("password").getValue(String.class);
                                btnok.setEnabled(!name1.getEditText().getText().toString().equals(rider1name) || !username1.getEditText().getText().toString().equals(rider1username) || !password1.getEditText().getText().toString().equals(rider1pass));
                                if (btnok.isEnabled()){
                                    btnok.setBackgroundColor(Color.parseColor("#00FF00"));
                                    btnok.setTextColor(Color.BLACK);
                                }else{
                                    btnok.setBackgroundColor(Color.parseColor("#009CFF"));
                                    btnok.setTextColor(Color.WHITE);
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
                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(name1.getEditText().getText().toString().isEmpty()&&username1.getEditText().getText().toString().isEmpty()&&password1.getEditText().getText().toString().isEmpty()){
                            name1.setError("Invalid Credentials");
                            username1.setError("Invalid Credentials");
                            password1.setError("Invalid Credentials");
                        }else{
                            new CountDownTimer(3000, 1000) {

                                public void onTick(long millisUntilFinished) {
                                    name1.setError(null);
                                    username1.setError(null);
                                    password1.setError(null);
                                    progressBar.setVisibility(View.VISIBLE);
                                    btnok.setVisibility(View.GONE);
                                    btnnotyet.setVisibility(View.GONE);
                                }

                                public void onFinish() {
                                    Toast.makeText(RiderActivity.this, "Successfully Updated!", Toast.LENGTH_SHORT).show();
                                    String updatedname1 = name1.getEditText().getText().toString();
                                    String updatedusername1 = username1.getEditText().getText().toString();
                                    String updatedpassword1 = password1.getEditText().getText().toString();

                                    RiderReference.child("Admin").child("riders").child("Rider1").child("name").setValue(updatedname1);
                                    RiderReference.child("Admin").child("riders").child("Rider1").child("username").setValue(updatedusername1);
                                    RiderReference.child("Admin").child("riders").child("Rider1").child("password").setValue(updatedpassword1);
                                    dialog.dismiss();
                                }

                            }.start();
                        }
                    }
                });
                btnnotyet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        editrider2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.update_rider1);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextInputLayout name1 = dialog.findViewById(R.id.update_name);
                TextInputLayout username1 = dialog.findViewById(R.id.update_username);
                TextInputLayout password1 = dialog.findViewById(R.id.update_password);

                Button btnok = dialog.findViewById(R.id.btn_change);
                Button btnnotyet = dialog.findViewById(R.id.btn_no);

                ProgressBar progressBar = dialog.findViewById(R.id.progress_update);

                RiderReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String rider1name = snapshot.child("Admin").child("riders").child("Rider2").child("name").getValue(String.class);
                        String rider1username = snapshot.child("Admin").child("riders").child("Rider2").child("username").getValue(String.class);
                        String rider1pass = snapshot.child("Admin").child("riders").child("Rider2").child("password").getValue(String.class);

                        name1.getEditText().setText(rider1name);
                        username1.getEditText().setText(rider1username);
                        password1.getEditText().setText(rider1pass);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                name1.getEditText().addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        RiderReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String rider1name = snapshot.child("Admin").child("riders").child("Rider2").child("name").getValue(String.class);
                                String rider1username = snapshot.child("Admin").child("riders").child("Rider2").child("username").getValue(String.class);
                                String rider1pass = snapshot.child("Admin").child("riders").child("Rider2").child("password").getValue(String.class);
                                btnok.setEnabled(!name1.getEditText().getText().toString().equals(rider1name) || !username1.getEditText().getText().toString().equals(rider1username) || !password1.getEditText().getText().toString().equals(rider1pass));
                                if (btnok.isEnabled()){
                                    btnok.setBackgroundColor(Color.parseColor("#00FF00"));
                                    btnok.setTextColor(Color.BLACK);
                                }else{
                                    btnok.setBackgroundColor(Color.parseColor("#009CFF"));
                                    btnok.setTextColor(Color.WHITE);
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
                username1.getEditText().addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        RiderReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String rider1name = snapshot.child("Admin").child("riders").child("Rider2").child("name").getValue(String.class);
                                String rider1username = snapshot.child("Admin").child("riders").child("Rider2").child("username").getValue(String.class);
                                String rider1pass = snapshot.child("Admin").child("riders").child("Rider2").child("password").getValue(String.class);
                                btnok.setEnabled(!name1.getEditText().getText().toString().equals(rider1name) || !username1.getEditText().getText().toString().equals(rider1username) || !password1.getEditText().getText().toString().equals(rider1pass));
                                if (btnok.isEnabled()){
                                    btnok.setBackgroundColor(Color.parseColor("#00FF00"));
                                    btnok.setTextColor(Color.BLACK);
                                }else{
                                    btnok.setBackgroundColor(Color.parseColor("#009CFF"));
                                    btnok.setTextColor(Color.WHITE);
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
                password1.getEditText().addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        RiderReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String rider1name = snapshot.child("Admin").child("riders").child("Rider2").child("name").getValue(String.class);
                                String rider1username = snapshot.child("Admin").child("riders").child("Rider2").child("username").getValue(String.class);
                                String rider1pass = snapshot.child("Admin").child("riders").child("Rider2").child("password").getValue(String.class);
                                btnok.setEnabled(!name1.getEditText().getText().toString().equals(rider1name) || !username1.getEditText().getText().toString().equals(rider1username) || !password1.getEditText().getText().toString().equals(rider1pass));
                                if (btnok.isEnabled()){
                                    btnok.setBackgroundColor(Color.parseColor("#00FF00"));
                                    btnok.setTextColor(Color.BLACK);
                                }else{
                                    btnok.setBackgroundColor(Color.parseColor("#009CFF"));
                                    btnok.setTextColor(Color.WHITE);
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
                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(name1.getEditText().getText().toString().isEmpty()&&username1.getEditText().getText().toString().isEmpty()&&password1.getEditText().getText().toString().isEmpty()){
                            name1.setError("Invalid Credentials");
                            username1.setError("Invalid Credentials");
                            password1.setError("Invalid Credentials");
                        }else{
                            new CountDownTimer(3000, 1000) {

                                public void onTick(long millisUntilFinished) {
                                    name1.setError(null);
                                    username1.setError(null);
                                    password1.setError(null);
                                    progressBar.setVisibility(View.VISIBLE);
                                    btnok.setVisibility(View.GONE);
                                    btnnotyet.setVisibility(View.GONE);
                                }

                                public void onFinish() {
                                    Toast.makeText(RiderActivity.this, "Successfully Updated!", Toast.LENGTH_SHORT).show();
                                    String updatedname1 = name1.getEditText().getText().toString();
                                    String updatedusername1 = username1.getEditText().getText().toString();
                                    String updatedpassword1 = password1.getEditText().getText().toString();

                                    RiderReference.child("Admin").child("riders").child("Rider2").child("name").setValue(updatedname1);
                                    RiderReference.child("Admin").child("riders").child("Rider2").child("username").setValue(updatedusername1);
                                    RiderReference.child("Admin").child("riders").child("Rider2").child("password").setValue(updatedpassword1);
                                    dialog.dismiss();
                                }

                            }.start();
                        }
                    }
                });
                btnnotyet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        editrider3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.update_rider1);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextInputLayout name1 = dialog.findViewById(R.id.update_name);
                TextInputLayout username1 = dialog.findViewById(R.id.update_username);
                TextInputLayout password1 = dialog.findViewById(R.id.update_password);

                Button btnok = dialog.findViewById(R.id.btn_change);
                Button btnnotyet = dialog.findViewById(R.id.btn_no);

                ProgressBar progressBar = dialog.findViewById(R.id.progress_update);

                RiderReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String rider1name = snapshot.child("Admin").child("riders").child("Rider3").child("name").getValue(String.class);
                        String rider1username = snapshot.child("Admin").child("riders").child("Rider3").child("username").getValue(String.class);
                        String rider1pass = snapshot.child("Admin").child("riders").child("Rider3").child("password").getValue(String.class);

                        name1.getEditText().setText(rider1name);
                        username1.getEditText().setText(rider1username);
                        password1.getEditText().setText(rider1pass);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                name1.getEditText().addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        RiderReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String rider1name = snapshot.child("Admin").child("riders").child("Rider3").child("name").getValue(String.class);
                                String rider1username = snapshot.child("Admin").child("riders").child("Rider3").child("username").getValue(String.class);
                                String rider1pass = snapshot.child("Admin").child("riders").child("Rider3").child("password").getValue(String.class);
                                btnok.setEnabled(!name1.getEditText().getText().toString().equals(rider1name) || !username1.getEditText().getText().toString().equals(rider1username) || !password1.getEditText().getText().toString().equals(rider1pass));
                                if (btnok.isEnabled()){
                                    btnok.setBackgroundColor(Color.parseColor("#00FF00"));
                                    btnok.setTextColor(Color.BLACK);
                                }else{
                                    btnok.setBackgroundColor(Color.parseColor("#009CFF"));
                                    btnok.setTextColor(Color.WHITE);
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
                username1.getEditText().addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        RiderReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String rider1name = snapshot.child("Admin").child("riders").child("Rider3").child("name").getValue(String.class);
                                String rider1username = snapshot.child("Admin").child("riders").child("Rider3").child("username").getValue(String.class);
                                String rider1pass = snapshot.child("Admin").child("riders").child("Rider3").child("password").getValue(String.class);
                                btnok.setEnabled(!name1.getEditText().getText().toString().equals(rider1name) || !username1.getEditText().getText().toString().equals(rider1username) || !password1.getEditText().getText().toString().equals(rider1pass));
                                if (btnok.isEnabled()){
                                    btnok.setBackgroundColor(Color.parseColor("#00FF00"));
                                    btnok.setTextColor(Color.BLACK);
                                }else{
                                    btnok.setBackgroundColor(Color.parseColor("#009CFF"));
                                    btnok.setTextColor(Color.WHITE);
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
                password1.getEditText().addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        RiderReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String rider1name = snapshot.child("Admin").child("riders").child("Rider3").child("name").getValue(String.class);
                                String rider1username = snapshot.child("Admin").child("riders").child("Rider3").child("username").getValue(String.class);
                                String rider1pass = snapshot.child("Admin").child("riders").child("Rider3").child("password").getValue(String.class);
                                btnok.setEnabled(!name1.getEditText().getText().toString().equals(rider1name) || !username1.getEditText().getText().toString().equals(rider1username) || !password1.getEditText().getText().toString().equals(rider1pass));
                                if (btnok.isEnabled()){
                                    btnok.setBackgroundColor(Color.parseColor("#00FF00"));
                                    btnok.setTextColor(Color.BLACK);
                                }else{
                                    btnok.setBackgroundColor(Color.parseColor("#009CFF"));
                                    btnok.setTextColor(Color.WHITE);
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
                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(name1.getEditText().getText().toString().isEmpty()&&username1.getEditText().getText().toString().isEmpty()&&password1.getEditText().getText().toString().isEmpty()){
                            name1.setError("Invalid Credentials");
                            username1.setError("Invalid Credentials");
                            password1.setError("Invalid Credentials");
                        }else{
                            new CountDownTimer(3000, 1000) {

                                public void onTick(long millisUntilFinished) {
                                    name1.setError(null);
                                    username1.setError(null);
                                    password1.setError(null);
                                    progressBar.setVisibility(View.VISIBLE);
                                    btnok.setVisibility(View.GONE);
                                    btnnotyet.setVisibility(View.GONE);
                                }

                                public void onFinish() {
                                    Toast.makeText(RiderActivity.this, "Successfully Updated!", Toast.LENGTH_SHORT).show();
                                    String updatedname1 = name1.getEditText().getText().toString();
                                    String updatedusername1 = username1.getEditText().getText().toString();
                                    String updatedpassword1 = password1.getEditText().getText().toString();

                                    RiderReference.child("Admin").child("riders").child("Rider3").child("name").setValue(updatedname1);
                                    RiderReference.child("Admin").child("riders").child("Rider3").child("username").setValue(updatedusername1);
                                    RiderReference.child("Admin").child("riders").child("Rider3").child("password").setValue(updatedpassword1);
                                    dialog.dismiss();
                                }

                            }.start();
                        }
                    }
                });
                btnnotyet.setOnClickListener(new View.OnClickListener() {
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
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
    }
}