package com.example.biweeklybudget;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class WeeklyExpenses extends AppCompatActivity implements weeklyAdapter.OnWeeklyListener {
    private static final String TAG = "Weekly Expenses";

    private RecyclerView wRecyclerView;
    private static weeklyAdapter wAdapter;
    private RecyclerView.LayoutManager wLayoutManager;
    public final int ADD_REQUEST = 0;
    public final int EDIT_REQUEST = 1;
    public final int RESULT_DELETED = 2;
    private ExpenseViewModel expenseViewModel;
    BudgetData budgetData;
    private static List<Weekly>allWeekly;
    double remainTtl;
    double ppdTtl;
    TextView remainView;
    TextView ttlView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_expenses);
        this.setTitle("Weekly Expenses");
        expenseViewModel = ViewModelProviders.of(this).get(ExpenseViewModel.class);
        budgetData = BudgetData.getInstance();
        wRecyclerView = this.findViewById(R.id.weekliesRecyclerView);
        wAdapter = new com.example.biweeklybudget.weeklyAdapter(this);
        wRecyclerView.setHasFixedSize(true);
        wLayoutManager = new LinearLayoutManager(this);
        wRecyclerView.setLayoutManager(wLayoutManager);
        wRecyclerView.setAdapter(wAdapter);
        remainTtl = 0;
        ppdTtl = 0;
        expenseViewModel.getAllWeekly();
        observeAllWeekly();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();
        String label = " ";
        double cost = 0;
        byte days = 0;
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(
                    getApplicationContext(),
                    "Cancelled",
                    Toast.LENGTH_LONG).show();
            Log.d(TAG, "action cancelled");
        } else if(resultCode == RESULT_OK && requestCode == ADD_REQUEST){
            label = bundle.getString("Label");
            cost = bundle.getDouble("Cost");
            days = bundle.getByte("Days");
            expenseViewModel.insertWeekly(new Weekly(label, cost, days));
        }else if(resultCode == RESULT_OK && requestCode == EDIT_REQUEST){
            label = bundle.getString("Label");
            cost = bundle.getDouble("Cost");
            days = bundle.getByte("Days");
            int ID = bundle.getInt("ID");
            expenseViewModel.updateWeekly(ID, label, cost, days);
        }else if (resultCode == RESULT_DELETED && requestCode == EDIT_REQUEST){
            int ID = bundle.getInt("ID");
            expenseViewModel.deleteWeekly(ID);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        expenseViewModel.getAllWeekly();
        observeAllWeekly();
    }

    public void gotoMain(View view){
        Intent mIntent = new Intent(WeeklyExpenses.this, MainActivity.class);
        startActivity(mIntent);
    }
    public void gotoUpNext(View view){
        Intent nIntent = new Intent(WeeklyExpenses.this, upNext.class);
        startActivity(nIntent);
    }
    public void gotoViewAll(View view){
        Intent vIntent = new Intent(WeeklyExpenses.this, viewAll.class);
        startActivity(vIntent);
    }
    public void goToWeekly(View view){
        Intent intent = new Intent(WeeklyExpenses.this, WeeklyExpenses.class);
        startActivity(intent);
    }
    public void goToAddWeeklies(View view){
        Intent nintent = new Intent(this, AddWeekly.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("fromList", false);
        bundle.putString("origin_class" , "WeeklyExpenses");
        nintent.putExtras(bundle);
        startActivityForResult(nintent, ADD_REQUEST);
    }

    @Override
    public void OnWeeklyClick(int position) {
        Intent intent = new Intent(this, AddWeekly.class);
        Bundle bundle = new Bundle();
        Weekly weekly = allWeekly.get(position);
        bundle.putBoolean("fromList", true);
        bundle.putInt("position", weekly.getId());
        Log.d(TAG, "clicked at position: " +position);
        bundle.putString("origin_class" , "WeeklyExpenses");
        intent.putExtras(bundle);
        startActivityForResult(intent, EDIT_REQUEST);
    }
    public void observeAllWeekly(){
        expenseViewModel.getAllWeekly().observe(this, weeklies -> {
        allWeekly = weeklies;
        AddWeekly.setAllWeekly(allWeekly);
        wAdapter.setAllWeekly(allWeekly);
        wAdapter.notifyDataSetChanged();
        Log.d(TAG,"observed update to Weekly list");
        budgetData.setAllWeekly(allWeekly);
        ttlView = findViewById(R.id.ttl_biweek);
        remainView =findViewById(R.id.total_ppd);
        ttlView.setText(budgetData.getWeeklyWholePay());
        remainView.setText(budgetData.getWeeklyTotal());
    });
    }

    public static void setAllWeekly(List<Weekly> AllWeekly) {
        allWeekly = AllWeekly;
        Log.d(TAG, "set Weeklies for real");
        Log.d(TAG, "There are " +allWeekly.size() +" weekly expenses");
        //wAdapter.setAllWeekly(allWeekly);
    }
}
