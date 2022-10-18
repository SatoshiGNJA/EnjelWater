package com.example.enjelwater;

import static android.app.Notification.DEFAULT_SOUND;
import static android.app.Notification.DEFAULT_VIBRATE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.view.MenuItem;
import android.widget.Toast;


import com.example.enjelwater.Model.PersonalOrderModel;
import com.example.enjelwater.databinding.ActivityAdminBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityAdminBinding binding;
    Calendar calendar = Calendar.getInstance();
    String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
    DatabaseReference reff;
    private DrawerLayout drawerLayout;
    long maxid = 0;

    SharedPreferences sharedPreferences;

    PersonalOrderModel personalOrderModel;

    public static final String fileName = "login";
    public static final String Username = "username";



    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new fragment1());

        Toolbar toolbar = findViewById(R.id.admin_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.admin_drawer_layout);
        NavigationView navigationView = findViewById(R.id.admin_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        sharedPreferences = getSharedPreferences(fileName, Context.MODE_PRIVATE);

        personalOrderModel = new PersonalOrderModel();

        FinishNotification();

        reff = FirebaseDatabase.getInstance().getReference().child("Data").child("OrderID");
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    maxid=(snapshot.getChildrenCount());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Orders").child(currentDate);
        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
                            NotificationManager manager = getSystemService(NotificationManager.class);
                            manager.createNotificationChannel(channel);
                        }

                        try {
                            Intent resultIntent = new Intent(getApplicationContext(), AdminActivity.class);
                            PendingIntent resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                            NotificationCompat.Builder builder = new NotificationCompat.Builder(AdminActivity.this, "My Notification");
                            builder.setContentTitle("New Order is Being Placed!");
                            builder.setContentText("Go Check it OUT!");
                            builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round));
                            builder.setSmallIcon(R.mipmap.ic_launcher_round);
                            builder.setContentIntent(resultPendingIntent);
                            builder.setAutoCancel(true);
                            builder.setDefaults(DEFAULT_SOUND | DEFAULT_VIBRATE);
                            builder.setStyle(new NotificationCompat.BigTextStyle());
                            builder.setPriority(NotificationCompat.PRIORITY_HIGH);

                            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            builder.setSound(alarmSound);

                            NotificationManagerCompat manager = NotificationManagerCompat.from(AdminActivity.this);
                            manager.notify(1, builder.build());
                            replaceFragment(new fragment1());



                        } catch (IllegalStateException exception) {

                            System.out.println(exception);

                        }
                }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                try {
                    replaceFragment(new fragment1());
                }catch (IllegalStateException exception) {

                    System.out.println(exception);

                }


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {


            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){

                case R.id.orderz:
                    replaceFragment(new fragment1());
                    break;
                case R.id.deliver:
                    replaceFragment(new fragment2());
                    break;
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_admin_prof:
                replaceFragment(new AdminProfileFragment());
                break;
            case R.id.nav_logout:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.nav_admin_finish:
                replaceFragment(new AdminHistoryFragment());
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onResume() {

        NotificationManager nMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.cancelAll();
        try {
            int intentFragment = getIntent().getExtras().getInt("frgToLoad");

            switch (intentFragment){
                case 1:
                    replaceFragment(new fragment1());
                    break;
                case 2:
                    replaceFragment(new fragment2());
                    break;
            }
        }
        catch (Exception e){
            System.out.println("Catch");
        }
        super.onResume();
    }
    private void FinishNotification(){
        FirebaseDatabase.getInstance()
                .getReference("Users")
                .orderByChild("status")
                .equalTo("Finish")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists()) {
                            for(DataSnapshot personalsnapshot:snapshot.getChildren()) {
                                PersonalOrderModel personalOrderModel = personalsnapshot.getValue(PersonalOrderModel.class);
                                personalOrderModel.setKey(personalsnapshot.getKey());
                                personalOrderModel.setTotalPrice(personalOrderModel.getTotalPrice());
                                String value = String.valueOf(personalOrderModel.getStatus());
                                Toast.makeText(AdminActivity.this, value, Toast.LENGTH_SHORT).show();
                                if(value.equals("Finish")){
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
                                        NotificationManager manager = getSystemService(NotificationManager.class);
                                        manager.createNotificationChannel(channel);
                                    }

                                    try {
                                        Intent resultIntent = new Intent(getApplicationContext(), PersonalOrderActivity.class);
                                        PendingIntent resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                                        NotificationCompat.Builder builder = new NotificationCompat.Builder(AdminActivity.this, "My Notification");
                                        builder.setContentTitle("Customer has Received the Gallon");
                                        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round));
                                        builder.setSmallIcon(R.mipmap.ic_launcher_round);
                                        builder.setContentIntent(resultPendingIntent);
                                        builder.setAutoCancel(true);
                                        builder.setDefaults(DEFAULT_SOUND | DEFAULT_VIBRATE);
                                        builder.setStyle(new NotificationCompat.BigTextStyle());
                                        builder.setPriority(NotificationCompat.PRIORITY_HIGH);

                                        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                        builder.setSound(alarmSound);

                                        NotificationManagerCompat manager = NotificationManagerCompat.from(AdminActivity.this);
                                        manager.notify(1, builder.build());



                                    } catch (IllegalStateException exception) {

                                        System.out.println(exception);

                                    }
                                }
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

}