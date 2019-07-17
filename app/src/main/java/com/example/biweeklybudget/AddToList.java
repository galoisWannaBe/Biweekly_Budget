package com.example.biweeklybudget;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class AddToList extends AppCompatActivity {

    EditText tvBill;
    EditText tvDue;
    EditText tvCost;
    String varButton;
    String billStr;
    String dueStr;
    String costStr;
    static int position;
    static boolean fromList;
    String originClass;
    static int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_list);
        tvBill = findViewById(R.id.name);
        tvDue = findViewById(R.id.due);
        tvCost = findViewById(R.id.cost);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        originClass = bundle.getString("origin_class");
        System.out.println("Origin Class" +originClass);
        fromList = bundle.getBoolean("fromList");
        System.out.println("fromList " +fromList);
        if (fromList) {
            position = bundle.getInt("index");
            billStr = data.getData(position, 0);
            dueStr = data.getData(position, 1);
            costStr = data.getData(position, 2);
            System.out.println(billStr);
            System.out.println(dueStr);
            System.out.println(costStr);
            tvBill.setText(billStr);
            tvDue.setText(dueStr);
            tvCost.setText(costStr);
        }
        }


    public void addSave(View view){
        Intent backIntent;
        switch (originClass){
            case "viewAll":
                backIntent = new Intent(this, viewAll.class);
                break;
                default:
                    backIntent = new Intent();
        }
        Bundle backBundle = new Bundle();
        billStr = tvBill.getText().toString();
        dueStr = tvDue.getText().toString();
        costStr = tvCost.getText().toString();
        backBundle.putString("Label", billStr);
        backBundle.putString("Due", dueStr);
        backBundle.putString("Cost", costStr);
        if (fromList){
            backBundle.putInt("ID" , ID);
        }
        backIntent.putExtras(backBundle);
        setResult(RESULT_OK, backIntent);
        finish();
    }
    public void delete(View view){
        Intent backIntent;
        switch (originClass){
            case "viewAll":
                backIntent = new Intent(this, viewAll.class);
                break;
                default:
                    backIntent = new Intent();
                    break;
        }
        Bundle backBundle = new Bundle();
        backBundle.putInt("ID", position);
        backIntent.putExtras(backBundle);
        setResult(2, backIntent);
        finish();
    }
    public void cancel(View view){
        Intent backIntent;
        Bundle bundle = new Bundle();
        bundle.putInt("ID", 0);
        switch (originClass){
            case "viewAll":
                backIntent = new Intent(this, viewAll.class);
                break;
                default:
                    backIntent = new Intent();
                    break;
        }
        backIntent.putExtras(bundle);
        setResult(RESULT_CANCELED, backIntent);
        finish();
    }

}
