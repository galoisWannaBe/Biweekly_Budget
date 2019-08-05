package com.example.biweeklybudget;

import android.app.DownloadManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
        String balanceStr;
        String projBalanceStr;
        int seed;
        int julDate;
        int dd;
        int MM;
        int yy;
        int ee;
    public static int q;
    public static boolean firstRun = true;
    //Database vars

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            //helps distinguish between an add and an edit
            if (firstRun == true) {
                seed = 112;

                clockStuff.init();
                julDate = clockStuff.getJulDate();
                dd = clockStuff.getDay();
                MM = clockStuff.getMonth();
                yy = clockStuff.getYear();
                ee = clockStuff.getWeek();
                budgetData.init(seed, julDate, dd, MM, yy, ee);
                q = ListData.listDataInit();
                for (int i = 0; i < q; i++) {
                    data.addItem(ListData.getBillElement(i), ListData.getDueElement(i), ListData.getCostElement(i));

                }
                q = ListData.weeklyInit();
                for (int i = 0; i < q; i++) {
                    data.addWeekly(ListData.getWeeklyLabel(i), ListData.getWeeklyCost(i), ListData.getWeeklyDays(i));
                }
                budgetData.upNextGen();
                budgetData.upAfterGen();
                firstRun = false;
            }
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent Data) {
        super.onActivityResult(requestCode, resultCode, Data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Bundle bundle = Data.getExtras();
            int seed = bundle.getInt("seed");
            budgetData.setSeedPay(seed);
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
                projBalanceStr = budgetData.calculate(balanceStr);
                TextView textView = findViewById(R.id.projBalance_box);
                textView.setText(projBalanceStr);
            }
        }
        public void goToSettings(View view){
            Intent intent = new Intent(this, Settings.class);
            startActivityForResult(intent, 0);
        }

    }


