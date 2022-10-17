package com.example.enjelwater;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.enjelwater.Adapter.MyHistoryAdapter;
import com.example.enjelwater.Adapter.MyOnProcessAdapter;
import com.example.enjelwater.Listener.IHistoryLoadListener;
import com.example.enjelwater.Model.CartModel;
import com.example.enjelwater.Model.HistoryModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminHistoryFragment extends Fragment implements IHistoryLoadListener, AdapterView.OnItemSelectedListener {
    View view;
    @BindView(R.id.fragmentlayoutAHistory)
    RelativeLayout fragML;
    @BindView(R.id.fragmentrecyclerAHistory)
    RecyclerView fragRecy;
    IHistoryLoadListener historyLoadListener;
    Button dateButton;
    TextView NoSales,totalord,totalsales;
    DatePickerDialog datePickerDialog;
    Spinner spin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history,container,false);

        dateButton = view.findViewById(R.id.datePicker);
        NoSales = view.findViewById(R.id.NoSales);
        spin = view.findViewById(R.id.spinner);
        totalord = view.findViewById(R.id.txtTotOrder);
        totalsales = view.findViewById(R.id.txtTotSales);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.finishandcancel, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);

        dateButton.setText(getTodaysDate());


        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();

            }
        });

        init();
        initDatePicker();
        loadHistoryFromFirebase();

        return view;

    }

    private String getTodaysDate() {

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month,year);

    }

    private void initDatePicker() {

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                String date = makeDateString(day,month,year);
                dateButton.setText(date);
                loadHistoryFromFirebase();
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this.getContext(),style,dateSetListener,year,month,day);

    }
    private String makeDateString(int day, int month, int year) {

        return day + " " + getMonthFormat(month) + " " + year;
    }

    private String getMonthFormat(int month) {
        if(month == 1)
            return "Jan";
        if(month == 2)
            return "Feb";
        if(month == 3)
            return "Mar";
        if(month == 4)
            return "Apr";
        if(month == 5)
            return "May";
        if(month == 6)
            return "Jun";
        if(month == 7)
            return "Jul";
        if(month == 8)
            return "Aug";
        if(month == 9)
            return "Sep";
        if(month == 10)
            return "Oct";
        if(month == 11)
            return "Nov";
        if(month == 12)
            return "Dec";

        return "Jan";

    }




    private void loadHistoryFromFirebase(){

        String text = spin.getSelectedItem().toString();

        List<HistoryModel> historyModels = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference(text)
                .child(dateButton.getText().toString())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        historyModels.clear();
                        long num = snapshot.getChildrenCount();
                        totalord.setText("Total Orders: " + num );
                        historyModels.clear();
                        Double total = 0.0;
                        if(snapshot.exists())
                        {
                            for(DataSnapshot historysnapshot:snapshot.getChildren())
                            {
                                HistoryModel historyModel = historysnapshot.getValue(HistoryModel.class);
                                historyModel.setKey(historysnapshot.getKey());
                                historyModel.setTotalPrice(historyModel.getTotalPrice());
                                Double cost = Double.valueOf(historyModel.getTotalPrice());
                                total = total + cost;
                                totalsales.setText(new StringBuilder("Total Price: ₱").append(total));
                                historyModels.add(historyModel);
                            }
                            historyLoadListener.onHistoryLoadSuccess(historyModels);
                            fragRecy.setVisibility(View.VISIBLE);
                            NoSales.setVisibility(View.GONE);
                        }else{
                            totalsales.setText(new StringBuilder("Total Sales: ₱").append(0));
                            fragRecy.setVisibility(View.GONE);
                            NoSales.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        historyLoadListener.onHistoryLoadFailed(error.getMessage());

                    }
                });
    }
    private void init(){
        ButterKnife.bind(this,view);

        historyLoadListener = this;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        fragRecy.setLayoutManager(layoutManager);
        fragRecy.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
    }

    @Override
    public void onHistoryLoadSuccess(List<HistoryModel> historyModelList) {

        MyHistoryAdapter adapter = new MyHistoryAdapter(getContext(),historyModelList);
        fragRecy.setAdapter(adapter);

    }

    @Override
    public void onHistoryLoadFailed(String message) {

        Snackbar.make(fragML,message,Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        loadHistoryFromFirebase();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
