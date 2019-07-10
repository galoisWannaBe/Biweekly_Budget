package com.example.biweeklybudget;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;

public class AddWeekly extends AppCompatActivity {
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
    public static boolean fromList= false;
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

        if (fromList){
            label = data.getWeekly(pos, 0);
            cost = data.getWeekly(pos, 1);
            days = data.getWeekly(pos, 2);
            if (days.contains("U")){
                sunday.setChecked(true);
            }
            if (days.contains("M")){
                monday.setChecked(true);
            }
            if (days.contains("T")){
                tuesday.setChecked(true);
            }
            if (days.contains("W")){
                wednesday.setChecked(true);
            }
            if (days.contains("R")){
                thursday.setChecked(true);
            }
            if (days.contains("F")){
                friday.setChecked(true);
            }
            if (days.contains("S")) {
                saturday.setChecked(true);
            }


        }
        else {
            label = " ";
            cost = " ";
            sunday.setChecked(false);
            monday.setChecked(false);
            tuesday.setChecked(false);
            wednesday.setChecked(false);
            thursday.setChecked(false);
            friday.setChecked(false);
            saturday.setChecked(false);
        }
        labelEdit.setText(label);
        costEdit.setText(cost);

    }
    public void save(View view){
        StringBuilder sb = new StringBuilder("\0");
        if (sunday.isChecked()) {
            sb = sb.append("U");
        }if (monday.isChecked()){
            sb = sb.append("M");
        }if (tuesday.isChecked()){
            sb = sb.append("T");
        }if (wednesday.isChecked()){
            sb = sb.append("W");
        }if (thursday.isChecked()){
            sb = sb.append("R");
        }if (friday.isChecked()){
            sb = sb.append("F");
        }if (saturday.isChecked()){
            sb = sb.append("S");
        }
        label = labelEdit.getText().toString();
        cost = costEdit.getText().toString();
        days = sb.toString();
        if (fromList){
            data.addWeekly(label, cost, days, pos);
            fromList = false;
        }else {
            data.addWeekly(label, cost, days);
        }
        Intent mIntent = new Intent(this, WeeklyExpenses.class);
        startActivity(mIntent);
    }
    public void delete(View view){
        data.removeWeekly(pos);
        fromList = false;
        Intent nIntent = new Intent(this, WeeklyExpenses.class);
        startActivity(nIntent);
    }
    public void cancel(View view){
        fromList = false;
        Intent pIntent = new Intent(this, WeeklyExpenses.class);
        startActivity(pIntent);
    }
    public static void setPosition(int i, boolean isFromList){
        pos = i;
        fromList = isFromList;
    }

}
