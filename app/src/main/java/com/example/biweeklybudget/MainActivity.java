package com.example.biweeklybudget;

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

import java.util.List;

public class MainActivity extends AppCompatActivity {
        String balanceStr;
        String projBalanceStr;
        String listedBills;
        String date;
        int seed;
        int julDate;
        int dd;
        int MM;
        int yy;
        int ee;
    public static int q;
    public static boolean upDated = false;
    public static boolean firstRun = true;
    //Database vars
    public static final int ADD_TO_BILLS = 1;
    public static final int ADD_TO_WEEKLIES = 2;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            seed = 112;

            clockStuff.init();
            julDate = clockStuff.getJulDate();
            dd = clockStuff.getDay();
            MM = clockStuff.getMonth();
            yy = clockStuff.getYear();
            ee = clockStuff.getWeek();
            budgetData.init(seed, julDate, dd, MM, yy, ee);
            //helps distinguish between an add and an edit
            System.out.println(firstRun);
            if (firstRun == true) {
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
            //startActivityForResult(vIntent, ADD_TO_BILLS);
        }
        public void gotoWeeklyExpenses(View view){
            Intent pintent = new Intent(this, WeeklyExpenses.class);
            startActivity(pintent);
        }

        public void calculate(View view) {
            EditText editText = findViewById(R.id.balance);
            balanceStr = editText.getText().toString();
            projBalanceStr = budgetData.calculate(balanceStr);
            TextView textView = findViewById(R.id.projBalance_box);
            textView.setText(projBalanceStr);
        }

        public void testGetting(View view) {
            date = String.valueOf(clockStuff.getDay());
            System.out.println("Day : " + date);
            date = String.valueOf(clockStuff.getMonth());
            System.out.println("Month " + date);
            date = String.valueOf(clockStuff.getYear());
            System.out.println("Year " + date);
            System.out.println("Julian date: " + julDate);
            System.out.println("Number of bills: " +data.getSize());
            System.out.println("Number of upcoming bills: " +data.dueSize());
            System.out.println("Number of weekly expenses: " +data.getWeeklySize());
            TextView textView = findViewById(R.id.projBalance_box);
            textView.setText("Check Terminal");


        }
    public static void update(){
        upDated = true;
    }

    }


