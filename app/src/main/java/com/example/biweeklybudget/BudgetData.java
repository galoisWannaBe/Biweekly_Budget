package com.example.biweeklybudget;

import android.util.Log;

import java.lang.annotation.Target;
import java.util.List;

public class BudgetData {

    private static final String TAG = "BudgetData";

    public double balance;
    public double ttlbills;
    public double projBalance;
    public int daysRemain;
    public int day;
    public int week;
    public int weekCounter;
    public double cost;
    public double weeklyTotal;
    public final byte SUNDAY = 1;
    public final byte MONDAY = 2;
    public final byte TUESDAY = 4;
    public final byte WEDNESDAY = 8;
    public final byte THURSDAY = 16;
    public final byte FRIDAY = 32;
    public final byte SATURDAY = 64;
    public final byte[] weekArr = new byte[]{SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY};
    public List<Bill> nextBills;
    public List<Weekly> allWeekly;

    BudgetData() {
        balance = 0;
        ttlbills = 0;
        weeklyTotal = 0;
    }

    public void addBills() {
        ttlbills = 0;
        for(int i = 0; i < nextBills.size(); i++){
            ttlbills += nextBills.get(i).getCost();
        }
        Log.d(TAG, "Bills totaled to " +ttlbills);
    }
    public void addWeekly(){
        weeklyTotal = 0;
        byte days;
        weekCounter = week;
        for (int i = 0; i < allWeekly.size(); i++){
            days = allWeekly.get(i).getDays();
            Log.d(TAG, "Current Weekly" +allWeekly.get(i).getLabel());
            Log.d(TAG, "Days Remaining: " +daysRemain);
            for (int j = 0; j < daysRemain; j++){
                if((days & weekArr[weekCounter]) == weekArr[weekCounter]){
                    weeklyTotal += allWeekly.get(i).getCost();
                    Log.d(TAG, "Added " +allWeekly.get(i).getCost() +" for " +weekCounter);
                    Log.d(TAG, "Weekly Total: " +weeklyTotal);
                }
                Log.d(TAG, j +"Days into the pay");
                weekCounter++;
                weekCounter = weekCounter % 7;
                Log.d(TAG, "WeekCounter" +weekCounter);
            }
        }
        Log.d(TAG, "Weekly expenses totaled to " +weeklyTotal);
    }


    public String calculate(double getBalance) {
        if (nextBills.isEmpty() && allWeekly.isEmpty()){
            return "Error; monthly bills and weekly expenses are empty";
        }else {
            addBills();
            addWeekly();
            ttlbills = ttlbills + weeklyTotal;
            projBalance = getBalance - ttlbills;
            return String.valueOf(projBalance);
        }
    }
   public void updatePPDInfo(int daysRemain){
        this.daysRemain = daysRemain;
   }

    public void setNextBills(List<Bill> nextBills) {
        this.nextBills = nextBills;
        Log.d(TAG, "Set NextBills");
    }

    public void setAllWeekly(List<Weekly> allWeekly) {
        this.allWeekly = allWeekly;
        Log.d(TAG, "Set Weeklies");
    }

    public void setDaysRemain(int daysRemain) {
        this.daysRemain = daysRemain;
    }

    public void setWeek(int week) {
        this.week = week;
    }
}
