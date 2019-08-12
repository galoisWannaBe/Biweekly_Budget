package com.example.biweeklybudget;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
        String balanceStr;
        String projBalanceStr;
        int daysRemain;
        int dayOfWeek;
        int seedPay = 0;
        LiveData<List<Bill>> nextBills;
        List<Bill> dueBills;

        private static final String TAG = "MainActivity";

    ExpenseViewModel expenseViewModel;
    BudgetData budgetData;
    Context context;
    //Database vars

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            expenseViewModel = ViewModelProviders.of(this).get(ExpenseViewModel.class);
            SharedPreferences prefs = getApplication().getSharedPreferences("prefs", context.MODE_PRIVATE);
            seedPay = prefs.getInt("seedPay", 0);
            Log.d(TAG, "Seedpay" +seedPay);
            expenseViewModel.setSeedPay(seedPay);
            daysRemain = expenseViewModel.getDaysRemain();
            dayOfWeek = expenseViewModel.getDayOfWeek();
            nextBills = expenseViewModel.getNextBills();
            Log.d(TAG, "Day of the week: " +dayOfWeek);
            budgetData = new BudgetData();
            budgetData.setWeek(dayOfWeek);
            budgetData.setDaysRemain(daysRemain);
            expenseViewModel.getAfterASink();
            expenseViewModel.getAllWeekly();
            expenseViewModel.getAllBills();
            observeNextBills();
            observeAllWeekly();
            observeAllBills();

        }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent Data) {
        super.onActivityResult(requestCode, resultCode, Data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Bundle bundle = Data.getExtras();
            int seed = bundle.getInt("seed");
            SharedPreferences prefs = getApplication().getSharedPreferences("prefs", context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("seedPay", seed);
            editor.commit();
            expenseViewModel.setSeedPay(seed);
            daysRemain = expenseViewModel.getDaysRemain();
            budgetData.setDaysRemain(daysRemain);
            budgetData.setWeek(dayOfWeek);
            expenseViewModel.getAllBills();
            expenseViewModel.getNextBills();
        }
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
            EditText editText = findViewById(R.id.balance);
            balanceStr = editText.getText().toString();
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
            startActivityForResult(intent, 0);
        }

        public void observeNextBills(){
            expenseViewModel.getNextBills().observe(this, new Observer<List<Bill>>() {
                @Override
                public void onChanged(List<Bill> bills) {
                    budgetData.setNextBills(bills);
                    dueBills = bills;
                    Log.d(TAG, "There are " + dueBills.size() + " bills due");
                    Log.d(TAG, "Change to next bills observed");
                }
            });
        }
        public void observeAllWeekly(){
            expenseViewModel.getAllWeekly().observe(this, weeklies -> {
                budgetData.setAllWeekly(weeklies);

                Log.d(TAG, "Change to Weeklies observed");
            });
        }
        public void observeAllBills(){
            expenseViewModel.getAllBills().observe(this, bills -> {AddToList.setBills(bills);
            Log.d(TAG, "Change to all bills observed");
            });
        }
}


