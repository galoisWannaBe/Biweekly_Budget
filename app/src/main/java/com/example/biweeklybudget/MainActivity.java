package com.example.biweeklybudget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/* TODO: 8/31/19 Navigation map to eliminate eternal back press cycles
TODO: Fix UI elements that don't line up properly on larger screens
TODO: Make column widths uniform
TODO: Fix text spacing in subtotal boxes
TODO: Learn Fragments and implement them where appropriate (ie. just about everywhere)
TODO: support for landscape mode
TODO: FAB for adding weekly and monthly expenses from MainActivity
TODO: Make adding or deleting multiple expenses easier
TODO: Allow some sort of syncing option
TODO: Get rid of extraneous list adapters
 */


public class MainActivity extends AppCompatActivity {
        String balanceStr;
        String projBalanceStr;
        int daysRemain;
        int dayOfWeek;
        int seedPay = 0;
        LiveData<List<Bill>> nextBills;
        List<Bill> dueBills;
        List<Bill> dueSplitEnd;
        List<Bill> dueSplitBeginning;
        LiveData<List<Bill>> dueLive;
        LiveData<Boolean> liveSplitDue;
        boolean splitDue;
        boolean splitMo;
        EditText editText;
        boolean firstRun;

        private static final String TAG = "MainActivity";
        public static final int REQUEST_CHANGE_PPD = 0;
        public static final int REQUEST_ADD_BILL = 2;
        public static final int REQUEST_ADD_WEEKLY = 3;
        public static final int REQUEST_FIRST_RUN = 4;
        public static final int REQUEST_HELP = 5;

    ExpenseViewModel expenseViewModel;
    BudgetData budgetData;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            expenseViewModel = ViewModelProviders.of(this).get(ExpenseViewModel.class);
            SharedPreferences prefs = getApplication().getSharedPreferences("prefs", context.MODE_PRIVATE);
            seedPay = prefs.getInt("seedPay", 0);
            firstRun = prefs.getBoolean("firstRun", true);
            if (firstRun){
                Intent intent = new Intent(this, Settings.class);
                Bundle bundle = new Bundle();
                bundle.putInt("seed", seedPay);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_FIRST_RUN);
            }
            //seedPay = 0;
            Log.d(TAG, "Seedpay" +seedPay);
            expenseViewModel.setSeedPay(seedPay);
            daysRemain = expenseViewModel.getDaysRemain();
            dayOfWeek = expenseViewModel.getDayOfWeek();
            nextBills = expenseViewModel.getNextBills();
            Log.d(TAG, "Day of the week: " +dayOfWeek);
            budgetData = BudgetData.getInstance();
            budgetData.setWeek(dayOfWeek);
            budgetData.setDaysRemain(daysRemain);
            expenseViewModel.getAllBills();
            expenseViewModel.getAllWeekly();
            observeAllWeekly();
            observeAllBills();

        splitDue = expenseViewModel.isSplitDue();
        if(splitDue){
                expenseViewModel.getNextBillsEndMo();
                expenseViewModel.getNexBillsBegMo();
                observeNextSplitBegins();
                observeNextSplitEnds();
            }else{
                expenseViewModel.getNextASink();
                //not actually and async method
                Log.d(TAG, "getNextAsink ran");
            }
            if(splitDue){
                observeNextSplitEnds();
                observeNextSplitBegins();
            }else{
                expenseViewModel.getNextBills();
                observeNextBills();
            }
            splitMo = expenseViewModel.isSplitMo();
            if(splitMo){
                expenseViewModel.getAfterBillsEndMo();
                expenseViewModel.getAfterBillsBegMo();
                observeAfterSplit();
            } else {
                expenseViewModel.getAfterBills();
                observeAfter();
            }

        }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent Data) {
        super.onActivityResult(requestCode, resultCode, Data);
        expenseViewModel = ViewModelProviders.of(this).get(ExpenseViewModel.class);
        if (requestCode == REQUEST_CHANGE_PPD && resultCode == RESULT_OK) {
            Bundle bundle = Data.getExtras();
            seedPay = bundle.getInt("seed");
            SharedPreferences prefs = getApplication().getSharedPreferences("prefs", context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("seedPay", seedPay);
            editor.commit();
            expenseViewModel.setSeedPay(seedPay);
            daysRemain = expenseViewModel.getDaysRemain();
            Log.d(TAG, "There are " + daysRemain + " days remaining");
            //expenseViewModel.getAllBills();
            splitDue = expenseViewModel.isSplitDue();
            budgetData.setDaysRemain(daysRemain);
        }
        else if(requestCode == REQUEST_ADD_BILL && resultCode == RESULT_OK){
            Bundle bundle = Data.getExtras();
            String label = bundle.getString("Label");
            int due = bundle.getInt("Due");
            double cost = bundle.getDouble("Cost");
            expenseViewModel.insertBill(new Bill(label, due, cost));
        }
        else if (requestCode == REQUEST_ADD_WEEKLY && resultCode == RESULT_OK){
            Bundle bundle = Data.getExtras();
            String label = bundle.getString("Label");
            double cost = bundle.getDouble("Cost");
            byte days = bundle.getByte("Days");
            Log.d(TAG, label +" , " +cost +" , " +days +" added");
            expenseViewModel.insertWeekly(new Weekly(label, cost, days));
            Log.d(TAG, "added weekly through MainActivity");
        }else if (requestCode == REQUEST_FIRST_RUN && resultCode == RESULT_OK){
            firstRun = false;
            Bundle bundle = Data.getExtras();
            seedPay = bundle.getInt("seed");
            SharedPreferences prefs = getApplication().getSharedPreferences("prefs" , context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstRun" , false);
            editor.putInt("seed" , seedPay);
            editor.commit();
            expenseViewModel.setSeedPay(seedPay);
            daysRemain = expenseViewModel.getDaysRemain();
            splitDue = expenseViewModel.isSplitDue();
            budgetData.setDaysRemain(daysRemain);
        } else if (requestCode == REQUEST_HELP){
            //do nothing
        }
        if(splitDue){
            expenseViewModel.getNextBillsEndMo();
            expenseViewModel.getNexBillsBegMo();
            observeNextSplitBegins();
            observeNextSplitEnds();
        }else{
            expenseViewModel.getNextASink();
            observeNextBills();
            Log.d(TAG, "getNextAsink ran");
        }
        expenseViewModel.getAllBills();
        observeAllBills();
        expenseViewModel.getAllWeekly();
        observeAllWeekly();
        EditText editText = findViewById(R.id.balance);
        editText.getText().clear();
        TextView textView = findViewById(R.id.projBalance_box);
        textView.setText("");
    }


    @Override
    protected void onResume() {
        super.onResume();
        expenseViewModel = ViewModelProviders.of(this).get(ExpenseViewModel.class);
        Log.d(TAG, "Greetings from onResume");
        if(splitDue){
            expenseViewModel.getNextBillsEndMo();
            expenseViewModel.getNexBillsBegMo();
            observeNextSplitBegins();
            observeNextSplitEnds();
        }else{
            expenseViewModel.getNextASink();
            Log.d(TAG, "getNextAsink ran");
        }

        EditText editText = findViewById(R.id.balance);
        editText.getText().clear();
        TextView textView = findViewById(R.id.projBalance_box);
        textView.setText("");
    }

    public void gotoMain(View view) {
            Intent mIntent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(mIntent);
        }

    public void gotoUpNext(View view) {
            Intent nIntent = new Intent(MainActivity.this, upNext.class);
            startActivity(nIntent);
        }

    public void gotoViewAll(View view){
            Intent vIntent = new Intent(MainActivity.this, viewAll.class);
            startActivity(vIntent);
        }
    public void gotoWeeklyExpenses(View view){
            Intent pintent = new Intent(this, WeeklyExpenses.class);
            startActivity(pintent);
        }

    public void calculate(View view) {
        editText = findViewById(R.id.balance);
        balanceStr = editText.getText().toString();
            if(splitDue){
                expenseViewModel.getNextBillsEndMo();
                expenseViewModel.getNexBillsBegMo();
                observeNextSplitEnds();
                observeNextSplitBegins();
            }else {
                expenseViewModel.getNextBills();
                observeNextBills();
                Log.d(TAG, "getNextAsink ran");
            }
            if(balanceStr.isEmpty()){
                Toast.makeText(
                        getApplicationContext(),
                        R.string.null_entry,
                        Toast.LENGTH_LONG).show();
            }else {
                projBalanceStr = budgetData.calculate(Double.parseDouble(balanceStr));
                TextView textView = findViewById(R.id.projBalance_box);
                textView.setText(projBalanceStr);
            }
        }
    public void goToSettings(View view){
            Intent intent = new Intent(this, Settings.class);
            Bundle bundle = new Bundle();
            bundle.putInt("seed", seedPay);
            intent.putExtras(bundle);
            startActivityForResult(intent, 0);
        }

    public void goToAddToList(View view){
        Intent intent = new Intent(this, AddToList.class);
        Bundle bundle = new Bundle();
        bundle.putString("origin_class" , "MainActivity");
        bundle.putBoolean("fromList" , false);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_ADD_BILL);
    }
    public void goToAddWeekly(View view){
        Intent intent = new Intent(this, AddWeekly.class);
        Bundle bundle = new Bundle();
        bundle.putString("origin_class" , "MainActivity");
        bundle.putBoolean("fromList" , false);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_ADD_WEEKLY);
    }
    public void goToHelp(View view){
        Intent intent = new Intent(this, helpMainActivity.class);
        startActivity(intent);
    }

    public void observeNextBills(){
        expenseViewModel.getNextBills().observe(this, new Observer<List<Bill>>() {

            @Override
                public void onChanged(List<Bill> bills) {
                    //dueBills = bills;
                    budgetData.setNextBills(bills);
                    Log.d(TAG, "There are " + bills.size() + " bills due");
                    Log.d(TAG, "Change to next bills observed");
                }
            });
        }
    public void observeAllWeekly(){
            expenseViewModel.getAllWeekly().observe(this, weeklies -> {
                budgetData.setAllWeekly(weeklies);
                WeeklyExpenses.setAllWeekly(weeklies);
                Log.d(TAG, "Change to Weeklies observed");
            });
        }
    public void observeAllBills(){
            expenseViewModel.getAllBills().observe(this, bills -> {
                AddToList.setBills(bills);
            viewAll.setAllBills(bills);
            Log.d(TAG, "Change to all bills observed");
            });
        }
    public void observeNextSplitEnds(){
            expenseViewModel.getNextBillsEndMo().observe(this, bills -> {
                dueSplitEnd = bills;
                budgetData.setNextSplitEndMo(bills);
            });
        }
    public void observeNextSplitBegins(){
            expenseViewModel.getNexBillsBegMo().observe(this, bills -> {
                dueSplitBeginning = bills;
                budgetData.setNextBillsBegMo(bills);
                Log.d(TAG, "Change to nextSplitBegins observed");
            });
    }
    public void observeAfter(){
        expenseViewModel.getAfterBills().observe(this, bills-> {
            upAfter.setAfterBills(bills);
        });
    }
    public void observeAfterSplit() {
        expenseViewModel.getAfterBillsEndMo().observe(this, bills -> {
            upAfter.setAfterBillsEndMo(bills);
        });

        expenseViewModel.getAfterBillsBegMo().observe(this, bills -> {
            upAfter.setAfterBillsBegMo(bills);
        });
    }
}


