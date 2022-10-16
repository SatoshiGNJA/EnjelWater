package com.example.enjelwater;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;

public class AdminProfileFragment extends Fragment {
    View view;
    TextInputEditText adminusername;
    TextInputEditText adminpassword;
    Button update;
    DatabaseReference databaseReference;
    ProgressBar progressBar;

    Dialog dialog;

    public static final String fileName = "login";

    SharedPreferences sharedPreferences;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_profile,container,false);

        adminusername = view.findViewById(R.id.admin_username);
        adminpassword = view.findViewById(R.id.admin_password);
        update = view.findViewById(R.id.admin_btn);
        progressBar = view.findViewById(R.id.admin_Progressbar);
        Drawable buttonDrawable = update.getBackground();
        buttonDrawable = DrawableCompat.wrap(buttonDrawable);

        dialog=new Dialog(view.getContext());

        sharedPreferences = getActivity().getSharedPreferences(fileName, Context.MODE_PRIVATE);


        databaseReference= FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String adminuser = snapshot.child("Admin").child("admin").child("username").getValue(String.class);
                String adminpass = snapshot.child("Admin").child("admin").child("password").getValue(String.class);

                adminusername.setText(adminuser);
                adminpassword.setText(adminpass);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Drawable finalButtonDrawable = buttonDrawable;
        Drawable finalButtonDrawable1 = buttonDrawable;
        adminusername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String adminuser = snapshot.child("Admin").child("admin").child("username").getValue(String.class);
                        String adminpass = snapshot.child("Admin").child("admin").child("password").getValue(String.class);
                        String user = adminusername.getText().toString();
                        String pass = adminpassword.getText().toString();

                        if(adminuser.equals(user)&&adminpass.equals(pass)){
                            update.setEnabled(false);
                            DrawableCompat.setTint(finalButtonDrawable1, Color.RED);
                            update.setBackground(finalButtonDrawable);
                        }else if(adminusername.getText().toString().isEmpty()||adminpassword.getText().toString().isEmpty()){

                            update.setEnabled(false);
                            DrawableCompat.setTint(finalButtonDrawable1, Color.RED);
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
        adminpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String adminuser = snapshot.child("Admin").child("admin").child("username").getValue(String.class);
                        String adminpass = snapshot.child("Admin").child("admin").child("password").getValue(String.class);
                        String user = adminusername.getText().toString();
                        String pass = adminpassword.getText().toString();

                        if(adminuser.equals(user)&&adminpass.equals(pass)){
                            update.setEnabled(false);
                            update.setEnabled(false);
                            DrawableCompat.setTint(finalButtonDrawable1, Color.RED);
                            update.setBackground(finalButtonDrawable);
                        }else if(adminusername.getText().toString().isEmpty()||adminpassword.getText().toString().isEmpty()){

                            update.setEnabled(false);
                            DrawableCompat.setTint(finalButtonDrawable1, Color.RED);
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
                dialog.setContentView(R.layout.update_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Button btnok = dialog.findViewById(R.id.btn_continue);
                Button btnnotyet = dialog.findViewById(R.id.btn_no);

                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.commit();

                                databaseReference.child("Admin").child("admin").child("username").setValue(adminusername.getText().toString());
                                databaseReference.child("Admin").child("admin").child("password").setValue(adminpassword.getText().toString());

                                String adminuser = snapshot.child("Admin").child("admin").child("username").getValue(String.class);
                                String adminpass = snapshot.child("Admin").child("admin").child("password").getValue(String.class);

                                adminusername.setText(adminuser);
                                adminpassword.setText(adminpass);

                                Intent i = new Intent(getContext(),LoginActivity.class);
                                startActivity(i);
                                dialog.dismiss();



                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

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




        return view;
    }
}
