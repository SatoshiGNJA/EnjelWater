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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

import butterknife.BindView;

public class AdminProfileFragment extends Fragment {
    View view;
    TextInputEditText adminusername;
    TextInputEditText adminpassword;
    TextInputEditText adminemail;
    Button updateuser,updatepassword,checkriders;
    DatabaseReference databaseReference;

    Dialog dialog;

    public static final String fileName = "login";

    SharedPreferences sharedPreferences;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_profile,container,false);

        adminusername = view.findViewById(R.id.admin_username);
        adminpassword = view.findViewById(R.id.admin_password);
        adminemail = view.findViewById(R.id.admin_email);
        updateuser = view.findViewById(R.id.admin_btn_username);
        updatepassword = view.findViewById(R.id.admin_btn_Password);
        checkriders = view.findViewById(R.id.btn_admin_riders);

        dialog=new Dialog(view.getContext());

        sharedPreferences = getActivity().getSharedPreferences(fileName, Context.MODE_PRIVATE);


        databaseReference= FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String adminuser = snapshot.child("Admin").child("admin").child("username").getValue(String.class);
                String adminpass = snapshot.child("Admin").child("admin").child("password").getValue(String.class);
                String admine = snapshot.child("Admin").child("admin").child("email").getValue(String.class);

                adminusername.setText(adminuser);
                adminpassword.setText(adminpass);
                adminemail.setText(admine);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        checkriders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),RiderActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_up, R.anim.no_animation);
            }
        });
        updateuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.update_username_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextInputLayout newuser = dialog.findViewById(R.id.new_username);

                Button btnok = dialog.findViewById(R.id.btn_continue);
                Button btnnotyet = dialog.findViewById(R.id.btn_no);

                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(newuser.getEditText().getText().toString().isEmpty()){
                            newuser.setError("This Field should not be Empty!");
                        }else{
                            databaseReference.child("Admin").child("admin").child("username").setValue(newuser.getEditText().getText().toString());
                            Toast.makeText(getContext(), "Your Username has been Updated!", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.clear();
                            editor.commit();
                            FirebaseAuth.getInstance().signOut();
                            Intent i = new Intent(view.getContext(),LoginActivity.class);
                            startActivity(i);
                            getActivity().finish();
                            dialog.dismiss();
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

        updatepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.update_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextInputLayout newpass = dialog.findViewById(R.id.new_password);
                TextInputLayout renewpass = dialog.findViewById(R.id.re_type_new_password);

                Button btnok = dialog.findViewById(R.id.btn_continue);
                Button btnnotyet = dialog.findViewById(R.id.btn_no);


                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(newpass.getEditText().getText().toString().isEmpty()){
                            newpass.setError("This Field should not be Empty!");
                        }else if (!newpass.getEditText().getText().toString().matches(renewpass.getEditText().getText().toString())){
                            newpass.setError(null);
                            renewpass.setError("Password Not Matched");
                        }else{
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null) {
                                user.updatePassword(newpass.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                       if(task.isSuccessful()){
                                           databaseReference.child("Admin").child("admin").child("password").setValue(newpass.getEditText().getText().toString());
                                           Toast.makeText(getContext(), "Your Password has been Updated!", Toast.LENGTH_SHORT).show();
                                           SharedPreferences.Editor editor = sharedPreferences.edit();
                                           editor.clear();
                                           editor.commit();
                                           FirebaseAuth.getInstance().signOut();
                                           Intent i = new Intent(view.getContext(),LoginActivity.class);
                                           startActivity(i);
                                           getActivity().finish();
                                           dialog.dismiss();
                                       }else{
                                           newpass.setError("Password too Short!");
                                           renewpass.setError(null);
                                       }
                                    }
                                });
                            }
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




        return view;
    }
}
