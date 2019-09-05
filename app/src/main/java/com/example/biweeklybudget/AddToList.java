package com.example.biweeklybudget;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.List;

public class AddToList extends AppCompatActivity {
    private static final String TAG = "AddToList";

    EditText tvBill;
    EditText tvDue;
    EditText tvCost;
    String varButton;
    String label;
    int due;
    double cost;
    static int position;
    static boolean fromList;
    String originClass;
    int ID;
    static List<Bill> allBills;
    Bill bill;

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
            setTitle("Edit a Monthly Bill");
            ID = bundle.getInt("index");
            getBillByID(ID);
            tvBill.setText(bill.getLabel());
            tvDue.setText(String.valueOf(bill.getDue()));
            tvCost.setText(String.valueOf(bill.getCost()));
        }else{
            setTitle("Add a Monthly Bill");
        }
    }


    public void addSave(View view) {
        Intent backIntent;
        switch (originClass) {
            case "viewAll":
                backIntent = new Intent(this, viewAll.class);
                break;
            case "upNext":
                backIntent = new Intent(this, upNext.class);
                break;
            case "upAfter":
                backIntent = new Intent(this, upAfter.class);
                break;
            case "MainActivity":
                backIntent = new Intent(this, MainActivity.class);
            default:
                backIntent = new Intent();
        }

        String labelStr = tvBill.getText().toString();
        String dueStr = tvDue.getText().toString();
        String costStr = tvCost.getText().toString();
        Log.d(TAG, "Strings Label: " +labelStr +" Due: " +dueStr +" Cost: " +costStr);
        if (labelStr.isEmpty()) {
            if (dueStr.isEmpty()) {
                if (costStr.isEmpty()) {
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.all_empty,
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.label_date_empty,
                            Toast.LENGTH_LONG).show();
                }
            } else if (costStr.isEmpty()) {
                Toast.makeText(
                        getApplicationContext(),
                        R.string.label_cost_empty,
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(
                        getApplicationContext(),
                        R.string.label_empty,
                        Toast.LENGTH_LONG).show();
            }

        } else if (dueStr.isEmpty()) {
            if (costStr.isEmpty()) {
                Toast.makeText(
                        getApplicationContext(),
                        R.string.due_cost_empty,
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(
                        getApplicationContext(),
                        R.string.due_empty,
                        Toast.LENGTH_LONG).show();
            }
        } else if (costStr.isEmpty()) {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.cost_empty,
                    Toast.LENGTH_LONG).show();
        } else if (Integer.parseInt(dueStr) < 1 || Integer.parseInt(dueStr) > 28) {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.bad_day,
                    Toast.LENGTH_LONG).show();
        } else {
            Bundle backBundle = new Bundle();
            backBundle.putString("Label", labelStr);
            backBundle.putInt("Due", Integer.parseInt(dueStr));
            backBundle.putDouble("Cost", Double.parseDouble(costStr));
            if (fromList) {
                backBundle.putInt("ID", bill.getId());
            }
            backIntent.putExtras(backBundle);
            setResult(RESULT_OK, backIntent);
            finish();
        }
    }

    public void delete(View view) {
        Intent backIntent;
        switch (originClass) {
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

    public void cancel(View view) {
        Intent backIntent;
        Bundle bundle = new Bundle();
        bundle.putInt("ID", 0);
        switch (originClass) {
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

    static void setBills(List<Bill> mAllBills) {
        allBills = mAllBills;
        Log.d(TAG, "set AllBills");
    }
    void getBillByID(int id){
        Log.d(TAG, "began getBillByID");
        ID = id;
        for (int i = 0; i < allBills.size(); i++){
            if (allBills.get(i).getId() == ID){
                bill = allBills.get(i);
                i = allBills.size();
            }
        }if (ID != bill.getId()){
            fromList = false;
        }
        Log.d(TAG, "Finished getBillByID");
        Log.d(TAG, "Currently looking at the " +bill.getLabel() +" bill");
    }
}
