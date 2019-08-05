package com.example.biweeklybudget;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

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
    int ID;

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
        fromList = bundle.getBoolean("fromList");

        if (fromList) {
            position = bundle.getInt("index");
            if(originClass.contains("viewAll")) {
                ID = position;
            }else if(originClass.contains("upNext")) {
                ID = data.findDue(position);
            }
            else if (originClass.contains("upAfter")){
                ID = data.findAfter(position);
            }
            billStr = data.getData(ID, 0);
            dueStr = data.getData(ID, 1);
            costStr = data.getData(ID, 2);
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
            case "upNext":
                backIntent = new Intent(this, upNext.class);
                break;
            case "upAfter":
                backIntent = new Intent(this, upAfter.class);
                break;
                default:
                    backIntent = new Intent();
        }

        billStr = tvBill.getText().toString();
        dueStr = tvDue.getText().toString();
        costStr = tvCost.getText().toString();
        if (billStr.isEmpty()){
            if (dueStr.isEmpty()){
                if (costStr.isEmpty()){
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.all_empty,
                            Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.label_date_empty,
                            Toast.LENGTH_LONG).show();
                }
            } else if (costStr.isEmpty()){
                Toast.makeText(
                        getApplicationContext(),
                        R.string.label_cost_empty,
                        Toast.LENGTH_LONG).show();
            } else{
                Toast.makeText(
                        getApplicationContext(),
                        R.string.label_empty,
                        Toast.LENGTH_LONG).show();
            }

        }else if (dueStr.isEmpty()){
            if (costStr.isEmpty()){
                Toast.makeText(
                        getApplicationContext(),
                        R.string.due_cost_empty,
                        Toast.LENGTH_LONG).show();
            } else{
                Toast.makeText(
                        getApplicationContext(),
                        R.string.due_empty,
                        Toast.LENGTH_LONG).show();
            }
        }
        else if (costStr.isEmpty()){
            Toast.makeText(
                    getApplicationContext(),
                    R.string.cost_empty,
                    Toast.LENGTH_LONG).show();
        }else if(Integer.parseInt(dueStr) < 1 || Integer.parseInt(dueStr) > 28){
            Toast.makeText(
                    getApplicationContext(),
                    R.string.bad_day,
                    Toast.LENGTH_LONG).show();
        }
        else {
            Bundle backBundle = new Bundle();
            backBundle.putString("Label", billStr);
            backBundle.putString("Due", dueStr);
            backBundle.putString("Cost", costStr);
            if (fromList) {
                backBundle.putInt("ID", ID);
            }
            backIntent.putExtras(backBundle);
            setResult(RESULT_OK, backIntent);
            finish();
        }
    }
    public void delete(View view){
        Intent backIntent;
        switch (originClass){
            case "viewAll":
                backIntent = new Intent(this, viewAll.class);
                break;
            case "upNext":
                backIntent = new Intent(this, upNext.class);
                break;
            case "upAfter":
                backIntent = new Intent(this, upAfter.class);
                default:
                    backIntent = new Intent();
                    break;
        }
        Bundle backBundle = new Bundle();
        backBundle.putInt("ID", ID);
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
            case "upNext":
                backIntent = new Intent(this, upNext.class);
                break;
            case "upAfter":
                backIntent = new Intent(this, upAfter.class);
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
