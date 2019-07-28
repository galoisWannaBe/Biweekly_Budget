package com.example.biweeklybudget;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Settings extends AppCompatActivity {

    EditText dateEdit;
    Button saveSeed;
    int date;
    StringBuilder sb;
    int mon;
    int day;
    int jul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        dateEdit = findViewById(R.id.seed_pay);
        saveSeed = findViewById(R.id.save_pay);
    }

    public void saveSeed(View view){
        date = Integer.valueOf(dateEdit.getText().toString());
        System.out.println("Settings ln 30: " +date);
        day = date%100;
        date -= day;
        date = date/100;
        mon = date;
        budgetData.setSeedPay(mon, day);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
