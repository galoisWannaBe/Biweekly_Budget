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

public class upNext extends AppCompatActivity implements upNextAdapter.OnBillListener{
    private static final String TAG = "upNext";
    private RecyclerView nRecyclerView;
    private RecyclerView.LayoutManager nLayoutManager;
    public static int q;
    public final int ADD_REQUEST = 0;
    public final int EDIT_REQUEST = 1;
    public final int RESULT_DELETED = 2;
    LiveData<List<Bill>> nextBillsLive;
    List<Bill> nextBills;
    upNextAdapter nAdapter;

    ExpenseViewModel expenseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_next);
        setTitle("Bills Due This Pay");
        expenseViewModel = ViewModelProviders.of(this).get(ExpenseViewModel.class);
        nRecyclerView = findViewById(R.id.recyclerView);
        nAdapter = new upNextAdapter(this);
        nRecyclerView.setHasFixedSize(true);
        nLayoutManager = new LinearLayoutManager(this);
        nRecyclerView.setLayoutManager(nLayoutManager);
        nRecyclerView.setAdapter(nAdapter);
        expenseViewModel.getNextBills();
        nextBillsLive = expenseViewModel.getNextASink();
        expenseViewModel.getAllBills();

        observeNext();
        observeAll();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent Data) {
        super.onActivityResult(requestCode, resultCode, Data);
        String label = "\0";
        int due = 0;
        double cost = 0;
        int ID = 0;
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(
                    getApplicationContext(),
                    "Cancelled",
                    Toast.LENGTH_LONG).show();
        }else if (resultCode == RESULT_OK) {
            Bundle bundle = Data.getExtras();
            label = bundle.getString("Label");
            due = bundle.getInt("Due");
            cost = bundle.getDouble("Cost");
        } else {
            label = "\0";
            due = 0;
            cost = 0;
        } if (requestCode == ADD_REQUEST) {
            if (resultCode == RESULT_OK) {
                expenseViewModel.insertBill(new Bill(label, due, cost));
            }
        } else if (requestCode == EDIT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = Data.getExtras();
                ID = bundle.getInt("ID");
                expenseViewModel.updateBill(ID, label, due, cost);
            } else if (resultCode == RESULT_DELETED) {
                Bundle bundle = Data.getExtras();
                ID = bundle.getInt("ID");
                expenseViewModel.deleteBill(ID);
            }
        }
    }

    public void goToAdd(View view) {
        Intent intent = new Intent(this, AddToList.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("fromList", false);
        bundle.putString("origin_class", "upNext");
        intent.putExtras(bundle);
        startActivityForResult(intent, ADD_REQUEST);
    }

    public void gotoMain(View view){
        Intent mIntent = new Intent(this, MainActivity.class);
        startActivity(mIntent);
    }
    public void gotoUpAfter(View view){
        Intent nIntent = new Intent(this, upAfter.class);
        startActivity(nIntent);
    }
    public void gotoviewAll(View view){
        Intent vIntent = new Intent(this, viewAll.class);
        startActivity(vIntent);
    }
    public void goToWeekly(View view){
        Intent intent = new Intent(upNext.this, WeeklyExpenses.class);
        startActivity(intent);
    }

    @Override
    public void OnBillClick(int position) {
        Intent intent = new Intent(this, AddToList.class);
        Bundle bundle = new Bundle();
        bundle.putInt("index", nextBills.get(position).getId());
        Log.d(TAG, "Index was just set to " +nextBills);
        bundle.putString("origin_class", "upNext");
        bundle.putBoolean("fromList", true);
        intent.putExtras(bundle);
        startActivityForResult(intent, EDIT_REQUEST);
    }
    public void observeNext(){
        expenseViewModel.getNextBills().observe(this, new Observer<List<Bill>>() {
            @Override
            public void onChanged(List<Bill> bills) {
                nextBills = bills;
                nAdapter.setNextBills(bills);
                nAdapter.notifyDataSetChanged();
            }
        });
    }
    public void observeAll(){
        expenseViewModel.getAllBills().observe(this, bills -> AddToList.setBills(bills));
    }
}
