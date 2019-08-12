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
import android.widget.Toast;

import java.util.List;

public class WeeklyExpenses extends AppCompatActivity implements weeklyAdapter.OnWeeklyListener {
    private static final String TAG = "Weekly Expenses";

    private RecyclerView wRecyclerView;
    private  weeklyAdapter wAdapter;
    private RecyclerView.LayoutManager wLayoutManager;
    public final int ADD_REQUEST = 0;
    public final int EDIT_REQUEST = 1;
    public final int RESULT_DELETED = 2;
    private ExpenseViewModel expenseViewModel;
    private LiveData<List<Weekly>>allWeekly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_expenses);
        this.setTitle("Weekly Expenses");
        expenseViewModel = ViewModelProviders.of(this).get(ExpenseViewModel.class);
        wRecyclerView = this.findViewById(R.id.weekliesRecyclerView);
        wAdapter = new com.example.biweeklybudget.weeklyAdapter(this);
        wRecyclerView.setHasFixedSize(true);
        wLayoutManager = new LinearLayoutManager(this);
        wRecyclerView.setLayoutManager(wLayoutManager);
        wRecyclerView.setAdapter(wAdapter);
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
        } else if (resultCode == RESULT_OK || resultCode == RESULT_DELETED) {
            label = bundle.getString("Label");
            cost = bundle.getDouble("Cost");
            days = bundle.getByte("Days");
            Log.d(TAG, "got " +label +", " +cost +", and " +days +"from Intent data");
        }
        if (requestCode == ADD_REQUEST && resultCode == RESULT_OK){
            Weekly weekly = new Weekly(label, cost, days);
            expenseViewModel.insertWeekly(weekly);
            Log.d(TAG, "inserted Weekly");
        }else if (requestCode == EDIT_REQUEST && resultCode == RESULT_OK){
            int ID = bundle.getInt("ID");
            expenseViewModel.updateWeekly(ID, label, cost, days);
            Log.d(TAG, "Updated Weekly at ID: " +ID);
        }else if (requestCode == EDIT_REQUEST && resultCode == RESULT_DELETED){
            int ID = bundle.getInt("ID");
            expenseViewModel.deleteWeekly(ID);
            Log.d(TAG, "Deleted Weekly at ID: " +ID);
        }
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
        nintent.putExtras(bundle);
        startActivityForResult(nintent, ADD_REQUEST);
    }

    @Override
    public void OnWeeklyClick(int position) {
        Intent intent = new Intent(this, AddWeekly.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("fromList", true);
        bundle.putInt("position", position);
        intent.putExtras(bundle);
        startActivityForResult(intent, EDIT_REQUEST);
    }
    public void observeAllWeekly(){
        expenseViewModel.getAllWeekly().observe(this, weeklies -> {
            wAdapter.setAllWeekly(weeklies);
            AddWeekly.setAllWeekly(weeklies);
            wAdapter.notifyDataSetChanged();
            Log.d(TAG, "observed uptdate to Weekly list");
        });
    }
}
