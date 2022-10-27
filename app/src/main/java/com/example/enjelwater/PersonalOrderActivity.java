package com.example.enjelwater;

import static android.app.Notification.DEFAULT_SOUND;
import static android.app.Notification.DEFAULT_VIBRATE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.enjelwater.Adapter.MyCartAdapter;
import com.example.enjelwater.Adapter.MyPersonalHistoryAdapter;
import com.example.enjelwater.Listener.IPersonalOrderLoadListener;
import com.example.enjelwater.Model.CartModel;
import com.example.enjelwater.Model.HistoryModel;
import com.example.enjelwater.Model.PersonalOrderModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonalOrderActivity extends AppCompatActivity implements IPersonalOrderLoadListener {

    @BindView(R.id.personal_RV)
    RecyclerView personalRecyLayout;
    @BindView(R.id.personal_RL)
    RelativeLayout personalLayout;
    Button press;
    ImageView goback;
    IPersonalOrderLoadListener personalOrderLoadListener;


    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_order);

        press = findViewById(R.id.pressmebutton);
        goback = findViewById(R.id.goback_user);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
            }
        });
        press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
                Intent intent = new Intent(PersonalOrderActivity.this,OrderActivity.class);
                startActivity(intent);
            }
        });

        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser).child("OrderHistory");
        reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                recreate();
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

        init();
        loadPersonalOrderFromFirebase();



    }
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
    }
    private void loadPersonalOrderFromFirebase(){
        List<PersonalOrderModel> personalOrderModels = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(currentuser)
                .child("OrderHistory")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            for(DataSnapshot personalsnapshot:snapshot.getChildren())
                            {
                                PersonalOrderModel personalOrderModel = personalsnapshot.getValue(PersonalOrderModel.class);
                                personalOrderModel.setKey(personalsnapshot.getKey());
                                personalOrderModel.setTotalPrice(personalOrderModel.getTotalPrice());
                                personalOrderModels.add(personalOrderModel);
                            }
                            personalOrderLoadListener.onPersonalOrderLoadSuccess(personalOrderModels);
                            press.setVisibility(View.GONE);
                        }else{
                            press.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        personalOrderLoadListener.onPersonalOrderLoadFailed(error.getMessage());

                    }
                });
    }

    private void init(){
        ButterKnife.bind(this);

        personalOrderLoadListener = this;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        personalRecyLayout.setLayoutManager(layoutManager);
        personalRecyLayout.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
    }

    @Override
    public void onPersonalOrderLoadSuccess(List<PersonalOrderModel> personalOrderModelList) {

        MyPersonalHistoryAdapter adapter = new MyPersonalHistoryAdapter(this,personalOrderModelList);
        personalRecyLayout.setAdapter(adapter);

    }

    @Override
    public void onPersonalOrderLoadFailed(String message) {

        Snackbar.make(personalLayout,message,Snackbar.LENGTH_LONG).show();

    }

    @Override
    protected void onResume() {
     //   Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.cancelAll();
        super.onResume();
    }
}