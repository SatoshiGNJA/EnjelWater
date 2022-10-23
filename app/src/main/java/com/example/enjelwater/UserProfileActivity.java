package com.example.enjelwater;

import static android.app.Notification.DEFAULT_SOUND;
import static android.app.Notification.DEFAULT_VIBRATE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enjelwater.Adapter.MyCartAdapter;
import com.example.enjelwater.Adapter.MyPersonalHistoryAdapter;
import com.example.enjelwater.Adapter.MySummaryAdapter;
import com.example.enjelwater.Listener.ICartLoadListener;
import com.example.enjelwater.Listener.IPersonalOrderLoadListener;
import com.example.enjelwater.Model.CartModel;
import com.example.enjelwater.Model.PersonalOrderModel;
import com.example.enjelwater.Utils.SpaceItemDecoration;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserProfileActivity extends AppCompatActivity implements IPersonalOrderLoadListener,ICartLoadListener{

    @BindView(R.id.personal_badge)
    NotificationBadge badge;
    @BindView(R.id.personal_btnCart)
    FrameLayout btnCart;
    @BindView(R.id.Personal_layout)
    ScrollView pML;

    Calendar calendar = Calendar.getInstance();
    String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

    TextInputLayout fullname,email,phoneNo;
    TextView fullNameLabel,HomeAddressLabel;

    Button order,neworder,orderhistory,logout;

    DatabaseReference databaseReference,reffUser;
    FirebaseAuth auth;
    FirebaseUser user;

    PersonalOrderModel personalOrderModel;

    String uid;

    long usermaxid= 0;

    Dialog dialog;

    IPersonalOrderLoadListener personalOrderLoadListener;
    ICartLoadListener cartLoadListener;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        fullname = findViewById(R.id.profile_fullname_text);
        email = findViewById(R.id.profile_email);
        phoneNo = findViewById(R.id.profile_number);
        fullNameLabel = findViewById(R.id.profile_fullname);
        HomeAddressLabel = findViewById(R.id.profile_home_address);
        order =findViewById(R.id.profile_order);
        orderhistory = findViewById(R.id.profile_order_history);
        neworder = findViewById(R.id.profile_new_order);
        logout = findViewById(R.id.txt_logout);

        Toolbar toolbar = findViewById(R.id.profile_tool);
        setSupportActionBar(toolbar);

        dialog=new Dialog(this);
        personalOrderModel = new PersonalOrderModel();


        user = FirebaseAuth.getInstance().getCurrentUser();
        auth = FirebaseAuth.getInstance();
        uid = user.getUid();

        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String profFull = snapshot.child("Users").child(uid).child("name").getValue(String.class);
                String profEmail = snapshot.child("Users").child(uid).child("email").getValue(String.class);
                String profPhone = snapshot.child("Users").child(uid).child("phoneNo").getValue(String.class);
                String profHomeAddress = snapshot.child("Users").child(uid).child("homeaddress").getValue(String.class);

                fullNameLabel.setText(profFull);
                HomeAddressLabel.setText("Home Address:\n" + profHomeAddress);
                fullname.getEditText().setText(profFull);
                email.getEditText().setText(profEmail);
                phoneNo.getEditText().setText(profPhone);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),OrderActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        neworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),NewOrderActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        orderhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this,PersonalOrderActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_up, R.anim.no_animation);


            }
        });
        countCartItem();
        init();
        OutForDeliveryNotification();
        OnProcessNotification();
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.logout_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Button btnok = dialog.findViewById(R.id.btn_logout);
                Button btnnotyet = dialog.findViewById(R.id.btn_logoutnotyet);
                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(UserProfileActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();
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
    private void init() {
        ButterKnife.bind(this);

        cartLoadListener = this;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_profile_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile_editProfile:
                Intent intent = new Intent(UserProfileActivity.this,EditProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPersonalOrderLoadSuccess(List<PersonalOrderModel> personalOrderModelList) {

    }

    @Override
    public void onPersonalOrderLoadFailed(String message) {

    }

    @Override
    public void onCartLoadSuccess(List<CartModel> cartModelList) {

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("Cart").child(uid);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long pluscount = dataSnapshot.getChildrenCount();
                badge.setNumber((int) pluscount);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        };
        usersRef.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public void onCartLoadFailed(String message) {

        Snackbar.make(pML,message,Snackbar.LENGTH_LONG).show();

    }
    private void countCartItem() {
        List<CartModel> cartModels = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("Cart")
                .child(uid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for(DataSnapshot cartSnapshot:snapshot.getChildren()){
                            CartModel cartModel = cartSnapshot.getValue(CartModel.class);
                            cartModel.setKey(cartSnapshot.getKey());
                            cartModels.add(cartModel);
                        }
                        cartLoadListener.onCartLoadSuccess(cartModels);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        cartLoadListener.onCartLoadFailed(error.getMessage());

                    }
                });

    }
    private void OnProcessNotification(){
        FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(uid)
                .child("OrderHistory")
                .orderByChild("status")
                .equalTo("On Process")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists()) {
                            for(DataSnapshot personalsnapshot:snapshot.getChildren()) {
                                PersonalOrderModel personalOrderModel = personalsnapshot.getValue(PersonalOrderModel.class);
                                personalOrderModel.setKey(personalsnapshot.getKey());
                                personalOrderModel.setTotalPrice(personalOrderModel.getTotalPrice());
                                String value = String.valueOf(personalOrderModel.getStatus());
                                if(value.equals("On Process")){
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
                                        NotificationManager manager = getSystemService(NotificationManager.class);
                                        manager.createNotificationChannel(channel);
                                    }

                                    try {
                                        Intent resultIntent = new Intent(getApplicationContext(), PersonalOrderActivity.class);
                                        PendingIntent resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                                        NotificationCompat.Builder builder = new NotificationCompat.Builder(UserProfileActivity.this, "My Notification");
                                        builder.setContentTitle(fullNameLabel.getText().toString());
                                        builder.setContentText("Your Order is now On Process!");
                                        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round));
                                        builder.setSmallIcon(R.mipmap.ic_launcher_round);
                                        builder.setContentIntent(resultPendingIntent);
                                        builder.setAutoCancel(true);
                                        builder.setDefaults(DEFAULT_SOUND | DEFAULT_VIBRATE);
                                        builder.setStyle(new NotificationCompat.BigTextStyle());
                                        builder.setPriority(NotificationCompat.PRIORITY_HIGH);

                                        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                        builder.setSound(alarmSound);

                                        NotificationManagerCompat manager = NotificationManagerCompat.from(UserProfileActivity.this);
                                        manager.notify(0, builder.build());



                                    } catch (IllegalStateException exception) {

                                        System.out.println(exception);

                                    }
                                }
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        personalOrderLoadListener.onPersonalOrderLoadFailed(error.getMessage());

                    }
                });
    }
    private void OutForDeliveryNotification(){
        FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(uid)
                .child("OrderHistory")
                .orderByChild("status")
                .equalTo("On-going Delivery")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists()) {
                            for(DataSnapshot personalsnapshot:snapshot.getChildren()) {
                                PersonalOrderModel personalOrderModel = personalsnapshot.getValue(PersonalOrderModel.class);
                                personalOrderModel.setKey(personalsnapshot.getKey());
                                personalOrderModel.setTotalPrice(personalOrderModel.getTotalPrice());
                                String value = String.valueOf(personalOrderModel.getStatus());
                                if(value.equals("On-going Delivery")){
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
                                        NotificationManager manager = getSystemService(NotificationManager.class);
                                        manager.createNotificationChannel(channel);
                                    }

                                    try {
                                        Intent resultIntent = new Intent(getApplicationContext(), PersonalOrderActivity.class);
                                        PendingIntent resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                                        NotificationCompat.Builder builder = new NotificationCompat.Builder(UserProfileActivity.this, "My Notification");
                                        builder.setContentTitle(fullNameLabel.getText().toString());
                                        builder.setContentText("Your Order is now Out For Delivery!");
                                        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round));
                                        builder.setSmallIcon(R.mipmap.ic_launcher_round);
                                        builder.setContentIntent(resultPendingIntent);
                                        builder.setAutoCancel(true);
                                        builder.setDefaults(DEFAULT_SOUND | DEFAULT_VIBRATE);
                                        builder.setStyle(new NotificationCompat.BigTextStyle());
                                        builder.setPriority(NotificationCompat.PRIORITY_HIGH);

                                        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                        builder.setSound(alarmSound);

                                        NotificationManagerCompat manager = NotificationManagerCompat.from(UserProfileActivity.this);
                                        manager.notify(0, builder.build());



                                    } catch (IllegalStateException exception) {

                                        System.out.println(exception);

                                    }
                                }
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        personalOrderLoadListener.onPersonalOrderLoadFailed(error.getMessage());

                    }
                });
    }
    @Override
    public void onBackPressed() {
        dialog.setContentView(R.layout.logout_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnok = dialog.findViewById(R.id.btn_logout);
        Button btnnotyet = dialog.findViewById(R.id.btn_logoutnotyet);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(UserProfileActivity.this,LoginActivity.class);
                NotificationManager nMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                nMgr.cancelAll();
                startActivity(intent);
                finish();
                dialog.dismiss();
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

    @Override
    protected void onResume() {
        super.onResume();

        NotificationManager nMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.cancelAll();
    }
}