package com.example.biweeklybudget;

import android.util.Log;

import androidx.lifecycle.LiveData;

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
    public List<Bill> nextBillsBegMo;
    public List<Bill> nextBillsEndMo;
    public List<Weekly> allWeekly;
    public int julianDate = 0;
    public int payMonth;
    public int payDate;
    int[] months = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    boolean splitDue;
    LiveData<List<Bill>> liveBillsDue;

    public static final BudgetData INSTANCE = new BudgetData();

    public static BudgetData getInstance(){
        return INSTANCE;
    }

    private BudgetData() {
        balance = 0;
        ttlbills = 0;
        weeklyTotal = 0;
    }

    public void addBills() {
        ttlbills = 0;
        if (splitDue){
            for (int i = 0; i < nextBillsBegMo.size(); i++){
                ttlbills += nextBillsBegMo.get(i).getCost();
            }
            for (int i = 0; i < nextBillsEndMo.size(); i++){
                ttlbills += nextBillsEndMo.get(i).getCost();
            }
        }else {
            for (int i = 0; i < nextBills.size(); i++) {
                ttlbills += nextBills.get(i).getCost();
            }
        }
        Log.d(TAG, "Bills totaled to " +ttlbills);
    }
    public void addWeekly(){
        weeklyTotal = 0;
        byte days;
        //weekCounter = week;
        for (int i = 0; i < allWeekly.size(); i++){
            days = allWeekly.get(i).getDays();
            weekCounter = week;
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
        splitDue = false;
        Log.d(TAG, "Set NextBills");
        if (nextBills.size() == 0){
            System.out.println("No Bills");
        }else{
            for (int i = 0; i < nextBills.size(); i++){
                System.out.println(nextBills.get(i).getLabel() +", " +nextBills.get(i).getDue());
            }
            System.out.println("End");
        }
    }
    public void setNextSplitEndMo(List<Bill> bills){
        nextBillsEndMo = bills;
        splitDue = true;
    }
    public void setNextBillsBegMo(List<Bill> bills){
        nextBillsBegMo = bills;
        splitDue = true;
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

    public void setLiveBillsDue(LiveData<List<Bill>> liveBillsDue) {
        this.liveBillsDue = liveBillsDue;
    }
}
