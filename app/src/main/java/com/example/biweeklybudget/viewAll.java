package com.example.biweeklybudget;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class viewAll extends AppCompatActivity implements viewAllAdapter.OnBillListener{

    private static final String TAG = "viewAll";

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    public static int q;
    public final int ADD_REQUEST = 0;
    public final int EDIT_REQUEST = 1;
    public final int RESULT_DELETED = 2;
    static viewAllAdapter mAdapter;
    static List<Bill> allBills;
    double billTtl;
    TextView ttlView;

    BudgetData budgetData;
    ExpenseViewModel expenseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        this.setTitle("All Bills");
        expenseViewModel = ViewModelProviders.of(this).get(ExpenseViewModel.class);
        budgetData = BudgetData.getInstance();
        mRecyclerView = findViewById(R.id.recyclerView);
        mAdapter = new com.example.biweeklybudget.viewAllAdapter(this);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        billTtl = 0;
        expenseViewModel.getAllBills();
        observeAllBills();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent Data) {
        super.onActivityResult(requestCode, resultCode, Data);
        String label = "\0";
        int due = 0;
        double cost = 0;
        int ID = 0;
        if(resultCode == RESULT_CANCELED){
            Toast.makeText(
                    getApplicationContext(),
                    "Cancelled",
                    Toast.LENGTH_LONG).show();
        }else if (resultCode == RESULT_OK){
            Bundle bundle = Data.getExtras();
            label = bundle.getString("Label");
            due = bundle.getInt("Due");
            cost = bundle.getDouble("Cost");
            Log.d(TAG, "Got " +label +", " +due +", and " +cost +" from bundle");
        }
        if (requestCode == ADD_REQUEST){
            if (resultCode == RESULT_OK){
                expenseViewModel.insertBill(new Bill(label, due, cost));
                Log.d(TAG, "Bill inserted... maybe");
            }
        } else if(requestCode == EDIT_REQUEST){
            if (resultCode == RESULT_OK){
                Bundle bundle = Data.getExtras();
                ID = bundle.getInt("ID");
                Log.d(TAG, "ALSO: " +ID);
                expenseViewModel.updateBill(ID, label, due, cost);
                Log.d(TAG, "Updated bills at " +ID);
                }else if (resultCode == RESULT_DELETED){
                Bundle bundle = Data.getExtras();
                ID = bundle.getInt("ID");
                Log.d(TAG, "ALSO: " +ID);
                expenseViewModel.deleteBill(ID);
                Log.d(TAG, "deleted bill at " +ID);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        expenseViewModel.getAllBills();
        observeAllBills();
    }

    public void goToAdd(View view) {
        Intent intent = new Intent(this, AddToList.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("fromList", false);
        bundle.putString("origin_class", "viewAll");
        intent.putExtras(bundle);
        startActivityForResult(intent, ADD_REQUEST);
    }
    public void gotoMain(View view){
        Intent mIntent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(mIntent);
    }
    public void gotoUpNext(View view){
        Intent nIntent = new Intent(getBaseContext(), upNext.class);
        startActivity(nIntent);
    }
    public void gotoviewAll(View view){
        Intent vIntent = new Intent(getBaseContext(), viewAll.class);
        startActivity(vIntent);
    }
    public void goToWeekly(View view){
        Intent wIntent = new Intent(viewAll.this, WeeklyExpenses.class);
        startActivity(wIntent);
    }
    public void goToHelp(View view){
        Intent intent = new Intent(this, viewsHelp.class);
        Bundle bundle = new Bundle();
        bundle.putString("origin_class" , "viewAll");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void OnBillClick(int position) {
        Intent intent = new Intent(this, AddToList.class);
        Bundle bundle = new Bundle();

        bundle.putBoolean("fromList", true);
        AddToList.setBills(allBills);
        bundle.putInt("index", allBills.get(position).getId());
        bundle.putString("origin_class", "viewAll");
        intent.putExtras(bundle);
        startActivityForResult(intent, EDIT_REQUEST);
    }
    public void observeAllBills(){
        expenseViewModel.getAllBills().observe(this, new Observer<List<Bill>>() {
            @Override
            public void onChanged(List<Bill> bills) {
                allBills = bills;
                mAdapter.setAllBills(allBills);
                mAdapter.notifyDataSetChanged();
                Log.d(TAG, "Update to bills observed");
                budgetData.setAllBills(allBills);
                ttlView = findViewById(R.id.total_box_all);
                ttlView.setText(budgetData.getAllBillsTtl());
            }
        });
    }

    public static void setAllBills(List<Bill> AllBills) {
        allBills = AllBills;
        Log.d(TAG, "Actually set the bills");
        Log.d(TAG, "There are " +allBills.size() +" bills");
        //mAdapter.notifyDataSetChanged();
    }
}
