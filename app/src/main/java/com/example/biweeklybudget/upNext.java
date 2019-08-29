package com.example.biweeklybudget;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    LiveData<List<Bill>> nextBillsSplitLive;
    static List<Bill> nextBills;
    static List<Bill> nextBillsEndMo;
    static List<Bill> nextBillsBegMo;
    static upNextAdapter nAdapter;
    static boolean splitDue = false;
    TextView ttlView;
    double billTtl;

    BudgetData budgetData;
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
        splitDue = expenseViewModel.isSplitDue();
        budgetData = BudgetData.getInstance();
        billTtl = 0;
        observeAll();
        if(splitDue){
            expenseViewModel.getNextBillsEndMo();
            expenseViewModel.getAfterBillsBegMo();
            observeNextSplit();
        }else{
            expenseViewModel.getNextASink();
            observeNext();
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "on resume ran");

        expenseViewModel.getAllBills();
        observeAll();

        if (splitDue) {
            expenseViewModel.getNexBillsBegMo();
            expenseViewModel.getNextBillsEndMo();
            observeNextSplit();
            Log.d(TAG, "Observing next bills, but like, it's for a split month");
        }else {
            expenseViewModel.getNextASink();
            observeNext();
        }

        //ttlView = findViewById(R.id.total_box);
        //ttlView.setText(String.valueOf(budgetData.getTtlbills()));

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
        int pos = position;
        int id;
        if (splitDue){
            if (pos > nextBillsEndMo.size()){
                pos -= nextBillsEndMo.size();
                id = nextBillsBegMo.get(pos).getId();
            }else{
                id = nextBillsEndMo.get(pos).getId();
            }
        }else{
            id = nextBills.get(pos).getId();
        }
        bundle.putInt("index", id);
        Log.d(TAG, "Index was just set to " +nextBills);
        bundle.putString("origin_class", "upNext");
        bundle.putBoolean("fromList", true);
        intent.putExtras(bundle);
        startActivityForResult(intent, EDIT_REQUEST);
    }
    public void observeNext(){
        expenseViewModel.getNextASink().observe(this, new Observer<List<Bill>>() {
            @Override
            public void onChanged(List<Bill> bills) {
                nextBills = bills;
                //nAdapter.isSplitDue(splitDue);
                nAdapter.setNextBills(bills);
                nAdapter.notifyDataSetChanged();
                budgetData.setNextBills(nextBills);
                ttlView = findViewById(R.id.total_box);
                ttlView.setText(String.valueOf(budgetData.getTtlbills()));
            }
        });
    }
    public void observeNextSplit(){
        expenseViewModel.getNextBillsEndMo().observe(this, new Observer<List<Bill>>() {
            @Override
            public void onChanged(List<Bill> bills) {
                Log.d(TAG, "Change detected!");
                nextBillsEndMo = bills;
                //nAdapter.isSplitDue(splitDue);
                nAdapter.setNextSplitEnds(nextBillsEndMo);
                nAdapter.notifyDataSetChanged();
                budgetData.setNextSplitEndMo(nextBillsEndMo);
                ttlView = findViewById(R.id.total_box);
                ttlView.setText(String.valueOf(budgetData.getTtlbills()));
                //ttlView.setText("Change detected");
            }
        });
        expenseViewModel.getNexBillsBegMo().observe(this, new Observer<List<Bill>>() {
            @Override
            public void onChanged(List<Bill> bills) {
                nextBillsBegMo = bills;
                //nAdapter.isSplitDue(splitDue);
                nAdapter.setNextSplitBegins(nextBillsBegMo);
                nAdapter.notifyDataSetChanged();
                budgetData.setNextBillsBegMo(nextBillsBegMo);
                ttlView = findViewById(R.id.total_box);
                ttlView.setText(String.valueOf(budgetData.getTtlbills()));
                Log.d(TAG, "Other change detectect");
            }
        });
    }

    public void observeAll(){
        expenseViewModel.getAllBills().observe(this, bills -> AddToList.setBills(bills));
    }

    public static void setNextBills(List<Bill> NextBills) {
        splitDue = false;
        nextBills = NextBills;
        nAdapter.setNextBills(nextBills);
        nAdapter.notifyDataSetChanged();
    }

    public static void setNextBillsEndMo(List<Bill> NextBillsEndMo) {
        splitDue = true;
        nextBillsEndMo = NextBillsEndMo;
        nAdapter.setNextSplitEnds(nextBillsEndMo);
        nAdapter.notifyDataSetChanged();
    }

    public static void setNextBillsBegMo(List<Bill> NextBillsBegMo) {
        nextBillsBegMo = NextBillsBegMo;
        nAdapter.setNextSplitBegins(nextBillsBegMo);
        nAdapter.notifyDataSetChanged();
    }
}
