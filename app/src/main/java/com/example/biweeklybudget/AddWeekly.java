package com.example.biweeklybudget;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class AddWeekly extends AppCompatActivity {

    private static final String TAG = "AddWeekly";

    public final byte SUNDAY = 1;
    public final byte MONDAY = 2;
    public final byte TUESDAY = 4;
    public final byte WEDNESDAY = 8;
    public final byte THURSDAY = 16;
    public final byte FRIDAY = 32;
    public final byte SATURDAY = 64;

    public String label;
    public String cost;
    public byte days;

    public CheckBox sunday;
    public CheckBox monday;
    public CheckBox tuesday;
    public CheckBox wednesday;
    public CheckBox thursday;
    public CheckBox friday;
    public CheckBox saturday;

    public EditText labelEdit;
    public EditText costEdit;
    public int pos;
    public boolean fromList;
    public static List<Weekly> allWeekly;
    public Weekly currentWeekly;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_weekly);

        labelEdit = findViewById(R.id.labelsIn);
        costEdit = findViewById(R.id.costsIn);

        sunday = findViewById(R.id.sunday);
        monday = findViewById(R.id.monday);
        tuesday = findViewById(R.id.tuesday);
        wednesday = findViewById(R.id.wednesday);
        thursday = findViewById(R.id.thursday);
        friday = findViewById(R.id.friday);
        saturday = findViewById(R.id.saturday);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        fromList = bundle.getBoolean("fromList");

        if (fromList){
            setTitle("Edit a Weekly Expense");
            pos = bundle.getInt("position");
            getWeeklyByID(pos);
            label = currentWeekly.getLabel();
            cost = String.valueOf(currentWeekly.getCost());
            days = currentWeekly.getDays();
            Log.d(TAG, "Days: " +days);
            labelEdit.setText(label);
            costEdit.setText(cost);
        }
        else{
            setTitle("Add a Weekly Expense");
            days = 0;
        }
        sunday.setChecked((days & SUNDAY) == SUNDAY);
        monday.setChecked((days & MONDAY) == MONDAY);
        tuesday.setChecked((days & TUESDAY) == TUESDAY);
        wednesday.setChecked((days & WEDNESDAY) == WEDNESDAY);
        thursday.setChecked((days & THURSDAY) == THURSDAY);
        friday.setChecked((days & FRIDAY) == FRIDAY);
        saturday.setChecked((days & SATURDAY) == SATURDAY);


    }
    public void save(View view){

        days = 0;

        if (sunday.isChecked())
            days |= SUNDAY;
        if (monday.isChecked())
            days |= MONDAY;
        if (tuesday.isChecked())
            days |= TUESDAY;
        if (wednesday.isChecked())
            days |= WEDNESDAY;
        if (thursday.isChecked())
            days |= THURSDAY;
        if (friday.isChecked())
            days |= FRIDAY;
        if (saturday.isChecked())
            days |= SATURDAY;

        label = labelEdit.getText().toString();
        cost = costEdit.getText().toString();
        if(label.isEmpty()){
            if(cost.isEmpty()){
                if(days == 0){
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.weekly_empty,
                            Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.label_cost_empty,
                            Toast.LENGTH_LONG).show();
                }
            }else if (days == 0){
                Toast.makeText(
                        getApplicationContext(),
                        R.string.label_days_empty,
                        Toast.LENGTH_LONG).show();
            }else Toast.makeText(
                    getApplicationContext(),
                    R.string.label_empty,
                    Toast.LENGTH_LONG).show();
        }else if(cost.isEmpty()){
            if (days == 0){
                Toast.makeText(
                        getApplicationContext(),
                        R.string.cost_days_empty,
                        Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(
                        getApplicationContext(),
                        R.string.cost_empty,
                        Toast.LENGTH_LONG).show();
            }
        }else if (days == 0){
            Toast.makeText(
                    getApplicationContext(),
                    R.string.days_empty,
                    Toast.LENGTH_LONG).show();
        }else {

            Intent mIntent = new Intent(this, WeeklyExpenses.class);
            Bundle backBundle = new Bundle();
            backBundle.putString("Label", label);
            backBundle.putDouble("Cost", Double.parseDouble(cost));
            backBundle.putByte("Days", days);
            Log.d(TAG, "put " +label +", " +cost +", and " +days +"into bundle");
            if (fromList){
                int ID = currentWeekly.getId();
                backBundle.putInt("ID", ID);
                Log.d(TAG, "ALSO: " +ID);
            }
            mIntent.putExtras(backBundle);
            setResult(RESULT_OK, mIntent);
            Log.d(TAG, "RESULT_OK");
            finish();
        }
    }
    public void delete(View view){
        Intent nIntent = new Intent(this, WeeklyExpenses.class);
        Bundle backBundle = new Bundle();
        backBundle.putInt("ID", currentWeekly.getId());
        nIntent.putExtras(backBundle);
        setResult(2, nIntent);
        finish();
    }
    public void cancel(View view){
        Intent pIntent = new Intent(this, WeeklyExpenses.class);
        setResult(RESULT_CANCELED, pIntent);
        finish();
    }
    public static void setAllWeekly(List<Weekly> mAllWeekly) {
        allWeekly = mAllWeekly;
        Log.d(TAG, "AllWeekly set");
    }
    public void getWeeklyByID(int ID){
        int id = ID;
        Log.d(TAG, "searching for " +id);
        for (int i = 0; i < allWeekly.size(); i++){
            Log.d(TAG, "Current ID: " +allWeekly.get(i).getId());
            if (allWeekly.get(i).getId() == ID){
                currentWeekly = allWeekly.get(i);
                i = allWeekly.size();
            }
        }
        Log.d(TAG, "weekly label at ID is: " +currentWeekly.getLabel());
        if (ID != currentWeekly.getId()){
            fromList = false;
        }
    }
}
