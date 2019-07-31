package com.example.biweeklybudget;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class AddWeekly extends AppCompatActivity {

    public final byte SUNDAY = 1;
    public final byte MONDAY = 2;
    public final byte TUESDAY = 4;
    public final byte WEDNESDAY = 8;
    public final byte THURSDAY = 16;
    public final byte FRIDAY = 32;
    public final byte SATURDAY = 64;

    public static String label;
    public static String cost;
    public static String days;

    public static CheckBox sunday;
    public static CheckBox monday;
    public static CheckBox tuesday;
    public static CheckBox wednesday;
    public static CheckBox thursday;
    public static CheckBox friday;
    public static CheckBox saturday;

    public static EditText labelEdit;
    public static EditText costEdit;
    public static int pos;
    public static boolean fromList;
    public static byte daysBin = 0;
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
            pos = bundle.getInt("position");
            label = data.getWeekly(pos, 0);
            cost = data.getWeekly(pos, 1);
            daysBin = Byte.parseByte(data.getWeekly(pos, 2));

            labelEdit.setText(label);
            costEdit.setText(cost);
        }
        else{
            daysBin = 0;
        }
        sunday.setChecked((daysBin & SUNDAY) == SUNDAY);
        monday.setChecked((daysBin & MONDAY) == MONDAY);
        tuesday.setChecked((daysBin & TUESDAY) == TUESDAY);
        wednesday.setChecked((daysBin & WEDNESDAY) == WEDNESDAY);
        thursday.setChecked((daysBin & THURSDAY) == THURSDAY);
        friday.setChecked((daysBin & FRIDAY) == FRIDAY);
        saturday.setChecked((daysBin & SATURDAY) == SATURDAY);


    }
    public void save(View view){

        if (sunday.isChecked())
            daysBin |= SUNDAY;
        if (monday.isChecked())
            daysBin |= MONDAY;
        if (tuesday.isChecked())
            daysBin |= TUESDAY;
        if (wednesday.isChecked())
            daysBin |= WEDNESDAY;
        if (thursday.isChecked())
            daysBin |= THURSDAY;
        if (friday.isChecked())
            daysBin |= FRIDAY;
        if (saturday.isChecked())
            daysBin |= SATURDAY;

        label = labelEdit.getText().toString();
        cost = costEdit.getText().toString();
        if(label.isEmpty()){
            if(cost.isEmpty()){
                if(daysBin == 0){
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
            }else if (daysBin == 0){
                Toast.makeText(
                        getApplicationContext(),
                        R.string.label_days_empty,
                        Toast.LENGTH_LONG).show();
            }else Toast.makeText(
                    getApplicationContext(),
                    R.string.label_empty,
                    Toast.LENGTH_LONG).show();
        }else if(cost.isEmpty()){
            if (daysBin == 0){
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
        }else if (daysBin == 0){
            Toast.makeText(
                    getApplicationContext(),
                    R.string.days_empty,
                    Toast.LENGTH_LONG).show();
        }else {

            Intent mIntent = new Intent(this, WeeklyExpenses.class);
            Bundle backBundle = new Bundle();
            backBundle.putString("Label", label);
            backBundle.putString("Cost", cost);
            backBundle.putString("Days", String.valueOf(daysBin));
            if (fromList){
                backBundle.putInt("position", pos);
            }
            mIntent.putExtras(backBundle);
            setResult(RESULT_OK, mIntent);
            finish();
        }
    }
    public void delete(View view){
        Intent nIntent = new Intent(this, WeeklyExpenses.class);
        Bundle backBundle = new Bundle();
        backBundle.putInt("position", pos);
        nIntent.putExtras(backBundle);
        setResult(2, nIntent);
        finish();
    }
    public void cancel(View view){
        Intent pIntent = new Intent(this, WeeklyExpenses.class);
        Bundle backBundle = new Bundle();
        backBundle.putInt("position", pos);
        pIntent.putExtras(backBundle);
        setResult(RESULT_CANCELED, pIntent);
        finish();
    }

}
