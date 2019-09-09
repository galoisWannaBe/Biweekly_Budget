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

import java.net.Inet4Address;
import java.util.List;

public class upAfter extends AppCompatActivity implements upAfterAdapter.OnBillListener{
    private static final String TAG  ="upAfter";

    LiveData<List<Bill>> afterCrossLive;
    List<Bill> afterCross;

    private RecyclerView mRecyclerView;
    static upAfterAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static int q;
    public final int ADD_REQUEST = 0;
    public final int EDIT_REQUEST = 1;
    public final int RESULT_DELETED = 2;
    ExpenseViewModel expenseViewModel;
    static List<Bill> afterBills;
    static List<Bill> afterBillsEndMo;
    static List<Bill> afterBillsBegMo;
    LiveData<List<Bill>> afterBillsEndLive;
    LiveData<List<Bill>> afterBillsBegLive;
    LiveData<List<Bill>> afterLive;
    boolean splitMo;
    double billTtl;
    TextView ttlView;

    BudgetData budgetData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_after);
        setTitle("Bills Due Next Pay");
        expenseViewModel = ViewModelProviders.of(this).get(ExpenseViewModel.class);
        mRecyclerView = findViewById(R.id.recyclerView);
        mAdapter = new upAfterAdapter(this);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        splitMo = expenseViewModel.isSplitMo();
        budgetData = BudgetData.getInstance();
        billTtl = 0;
        observeAll();
        if(splitMo){
            expenseViewModel.getAfterBegMo();
            expenseViewModel.getAfterEndMo();
            observeAfterSplit();
        }else{
            expenseViewModel.getAfterBills();
            observeAfter();
        }
    }



        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent Data){
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
            }else if(resultCode == RESULT_OK){
                Bundle bundle = Data.getExtras();
                label = bundle.getString("Label");
                due = bundle.getInt("Due");
                cost = bundle.getDouble("Cost");
            } else {
                label = "\0";
                due = 0;
                cost = 0;
            }
            if(requestCode == ADD_REQUEST){
                if(resultCode == RESULT_OK){
                    expenseViewModel.insertBill(new Bill(label, due, cost));
                }
            } else if(requestCode == EDIT_REQUEST){
                if(resultCode == RESULT_OK){
                    Bundle bundle = Data.getExtras();
                    ID = bundle.getInt("ID");
                    expenseViewModel.updateBill(ID, label, due, cost);
                }else if(resultCode == RESULT_DELETED) {
                    Bundle bundle = Data.getExtras();
                    ID = bundle.getInt("ID");
                    expenseViewModel.deleteBill(ID);
                }
            }
        }

    @Override
    protected void onResume() {
        super.onResume();

        expenseViewModel.getAllBills();
        observeAll();

        if (splitMo) {
            expenseViewModel.getAfterBillsEndMo();
            expenseViewModel.getAfterBillsBegMo();
            observeAfterSplit();
        } else {
            expenseViewModel.getAfterBills();
            observeAfter();
        }
    }

    public void goToAdd(View view) {
        Intent intent = new Intent(this, AddToList.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("fromList", false);
        bundle.putString("origin_class", "upAfter");
        intent.putExtras(bundle);
        startActivityForResult(intent, ADD_REQUEST);
    }


    public void gotoMain(View view){
        Intent mIntent = new Intent(upAfter.this, MainActivity.class);
        startActivity(mIntent);
    }
    public void gotoUpNext(View view){
        Intent nIntent = new Intent(upAfter.this, upNext.class);
        startActivity(nIntent);
    }
    public void gotoViewAll(View view){
        Intent vIntent = new Intent(upAfter.this, viewAll.class);
        startActivity(vIntent);
    }
    public void goToWeekly(View view){
        Intent intent = new Intent(upAfter.this, WeeklyExpenses.class);
        startActivity(intent);
    }
    public void goToHelp(View view){
        Intent intent = new Intent(this, viewsHelp.class);
        Bundle bundle = new Bundle();
        bundle.putString("origin_class" , "upAfter");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void OnBillclick(int position) {
        Intent intent = new Intent(this, AddToList.class);
        Bundle bundle = new Bundle();
        int pos = position;
        int id;
        if(splitMo) {
            if (pos > afterBillsEndMo.size()) {
                pos -= afterBillsEndMo.size();
                id = afterBillsBegMo.get(pos).getId();
            } else {
                id = afterBillsEndMo.get(pos).getId();
            }
        } else {
            id = afterBills.get(pos).getId();
        }
        bundle.putBoolean("fromList", true);
        Log.d(TAG, "ID: " +afterBills.get(position).getId());
        bundle.putInt("index", id);
        bundle.putString("origin_class", "upAfter");
        intent.putExtras(bundle);
        startActivityForResult(intent, EDIT_REQUEST);
    }

    public void observeAfter(){
            expenseViewModel.getAfterBills().observe(this, bills -> {
                afterBills = bills;
                mAdapter.setSplitMo(splitMo);
                mAdapter.setUpAfter(bills);
                mAdapter.notifyDataSetChanged();
                Log.d(TAG, "getAfterBills observed");
                budgetData.setAfterBills(afterBills);
                ttlView = findViewById(R.id.total_box_after);
                ttlView.setText(budgetData.getAfterTtl());
            });
    }


    public void observeAfterSplit(){
        expenseViewModel.getAfterBegMo().observe(this, bills -> {
            afterBillsBegMo = bills;
            mAdapter.setUpAfterBegMo(afterBillsBegMo);
            mAdapter.notifyDataSetChanged();
            Log.d(TAG, "splitMo; ALSO");
            budgetData.setAfterBillsBegin(afterBillsBegMo);
            ttlView = findViewById(R.id.total_box_after);
            ttlView.setText(String.valueOf(budgetData.getAfterTtl()));
        });
        expenseViewModel.getAfterEndMo().observe(this, bills -> {
            afterBillsEndMo = bills;
            mAdapter.setUpAfterEndMo(afterBillsEndMo);
            mAdapter.notifyDataSetChanged();
            budgetData.setAfterBillsEnd(afterBillsEndMo);
            ttlView = findViewById(R.id.total_box_after);
            ttlView.setText(String.valueOf(budgetData.getAfterTtl()));
        });
    }
    public void observeAll(){
        expenseViewModel.getAllBills().observe(this, bills -> {
            AddToList.setBills(bills);
        });
    }
    public static void setAfterBills(List<Bill> AfterBills) {
        afterBills = AfterBills;
        //mAdapter.setUpAfter(afterBills);
        //mAdapter.notifyDataSetChanged();

    }

    public static void setAfterBillsEndMo(List<Bill> AfterBillsEndMo) {
        afterBillsEndMo = AfterBillsEndMo;
        //mAdapter.setUpAfterEndMo(afterBillsEndMo);
        //mAdapter.notifyDataSetChanged();
    }

    public static void setAfterBillsBegMo(List<Bill> AfterBillsBegMo) {
        afterBillsBegMo = AfterBillsBegMo;
        //mAdapter.setUpAfterBegMo(afterBillsBegMo);
        //mAdapter.notifyDataSetChanged();
    }
}
