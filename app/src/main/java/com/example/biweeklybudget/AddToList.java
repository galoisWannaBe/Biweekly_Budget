package com.example.biweeklybudget;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddToList extends AppCompatActivity {

    EditText tvBill;
    EditText tvDue;
    EditText tvCost;
    String varButton;
    String billStr;
    String dueStr;
    String costStr;
    static int position;
    static int list;
    static boolean fromList;
    static public Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_list);
        switch (list) {
            case 0:
                mIntent = new Intent(this, upNext.class);
                break;
            case 1:
                mIntent = new Intent(this, upAfter.class);
                break;
            case 2:
                mIntent = new Intent(this, viewAll.class);
                break;
        }

        tvBill = findViewById(R.id.name);
        tvDue = findViewById(R.id.due);
        tvCost = findViewById(R.id.cost);

        if(fromList == true) {

                billStr = data.getData(position, 0);
                dueStr = data.getData(position, 1);
                costStr = data.getData(position, 2);


        }


        else {
            tvBill.setText("");
            tvDue.setText("");
            tvCost.setText("");
        }

        tvBill.setText(billStr);
        tvDue.setText(dueStr);
        tvCost.setText(costStr);
    }
    public void addSave(View view){
        billStr = tvBill.getText().toString();
        dueStr = tvDue.getText().toString();
        costStr = tvCost.getText().toString();

        System.out.println(billStr +" " +dueStr +" " +costStr);


        if (fromList == true){
                data.addItem(billStr, dueStr, costStr, position);

        }
        else {
            data.addItem(billStr, dueStr, costStr);
        }
        budgetData.upNextGen();
        budgetData.upAfterGen();
        MainActivity.update();
        //Intent mIntent = new Intent(this, MainActivity.class);
        startActivity(mIntent);
    }
    public void delete(View view){
        if (fromList == true){
            data.removeItem(position);
        }
        budgetData.upNextGen();
        budgetData.upAfterGen();
        MainActivity.update();
        fromList = false;
        //Intent mIntent = new Intent(this, MainActivity.class);
        startActivity(mIntent);
    }
    public void cancel(View view){
        MainActivity.update();
        //Intent mIntent = new Intent(this, MainActivity.class);
        startActivity(mIntent);
    }
    public static void setPosition(boolean frommList, int positron, int listing){
        fromList = frommList;
        position = positron;
        list = listing;

    }
    public static void resetFromList(){
        fromList = false;
    }
    public static void setList(int listing){
        list = listing;
    }

}
