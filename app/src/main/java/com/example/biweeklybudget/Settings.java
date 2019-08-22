package com.example.biweeklybudget;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    public static final String TAG = "Settings";

    EditText dateEdit;
    Button saveSeed;
    int date = 0;
    StringBuilder sb;
    private int mon;
    private int day;
    private int currentYear;
    int jul;
    String dateStr;
    int[] months = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    clockStuff mClockStuff;
    CalendarView calendarView;
    DatePicker datePicker;
    int newMonth;
    int newDay;
    int newYear;
    boolean isUnchanged = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.setTitle("Settings");
        //dateEdit = findViewById(R.id.seed_pay);
        datePicker = findViewById(R.id.datePicker);
        saveSeed = findViewById(R.id.save_pay);
        mClockStuff = clockStuff.getInstance();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        jul = bundle.getInt("seed");
        mon = (mClockStuff.getMonth() - 1);
        day = mClockStuff.getStartPPD();
        currentYear = mClockStuff.getYear();
        isUnchanged = true;
        datePicker.init(currentYear, mon, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mon = monthOfYear + 1;
                day = dayOfMonth;
                newYear = year;
                isUnchanged = false;
                Log.d(TAG, mon +"/" +day +"/" +year);
                Log.d(TAG, mon +"/" +day +"/" +newYear);
            }
        });

    }

    public void saveSeed(View view){

        jul = 0;
        if(isUnchanged){
            mon++;
        }
        int i = 0;
        for(i = 0; i < mon; i++){
            jul += months[i];
            Log.d(TAG, "Counting julian date" +jul);
        }
        jul += day;
        jul --;
        jul -= months[i];
        jul = jul % 14;

        Log.d(TAG, "Seed pay: " +jul);
        Intent intent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("seed", jul);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();

    }

}
