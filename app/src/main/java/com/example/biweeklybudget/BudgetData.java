package com.example.biweeklybudget;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import java.lang.annotation.Target;
import java.util.Collections;
import java.util.List;

public class BudgetData {

    private static final String TAG = "BudgetData";

    public double balance;
    public double ttlbills;
    public double projBalance;
    double endTtl;
    double begTtl;
    double nextTTl;
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
    ExpenseViewModel expenseViewModel;

    public static volatile BudgetData INSTANCE = new BudgetData();

    public static BudgetData getInstance(){
        return INSTANCE;
    }

    private BudgetData() {
        balance = 0;
        ttlbills = 0;
        weeklyTotal = 0;
        nextBills = Collections.emptyList();
        nextBillsBegMo = Collections.emptyList();
        nextBillsEndMo = Collections.emptyList();
        allWeekly = Collections.emptyList();

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
            for (int j = 0; j <= daysRemain; j++){
                if((days & weekArr[weekCounter]) == weekArr[weekCounter]){
                    weeklyTotal += allWeekly.get(i).getCost();
                }
                weekCounter++;
                weekCounter = weekCounter % 7;
            }
        }
        Log.d(TAG, "Weekly expenses totaled to " +weeklyTotal);
    }


    public String calculate(double getBalance) {
        if (nextBills.isEmpty() && allWeekly.isEmpty()){
            return "Error; monthly bills and weekly expenses are empty";
        }else {
            addWeekly();
            if(nextBills.size() > 0){
                ttlbills = nextTTl + weeklyTotal;
            }
            if(nextBillsEndMo.size() > 0 || nextBillsBegMo.size() > 0){
                ttlbills = begTtl + endTtl + weeklyTotal;
            }
            Log.d(TAG, "bank balance: " +getBalance);
            Log.d(TAG, "ttl bills: " +ttlbills);
            projBalance = getBalance - ttlbills;
            return String.valueOf(projBalance);
        }
    }
   public void updatePPDInfo(int daysRemain){
        this.daysRemain = daysRemain;
   }

    public void setNextBills(List<Bill> nextBills) {
        this.nextBills = nextBills;
        nextTTl = 0;
        endTtl = 0;
        begTtl = 0;
        for(int i = 0; i < nextBills.size(); i++){
            nextTTl += nextBills.get(i).getCost();
        }
        Log.d(TAG, "NextBills totals to " +nextTTl);
    }
    public void setNextSplitEndMo(List<Bill> bills){
        nextBillsEndMo = bills;
        endTtl = 0;
        nextTTl = 0;
        Log.d(TAG, "Set Bills endmo");
        Log.d(TAG, "There are " +nextBillsEndMo.size() +" bills until the end of the month");
        for (int i = 0; i < nextBillsEndMo.size(); i++){
             endTtl += nextBillsEndMo.get(i).getCost();
        }Log.d(TAG, "Endmo totaled to " +endTtl);
    }
    public void setNextBillsBegMo(List<Bill> bills){
        nextBillsBegMo = bills;
        begTtl = 0;
        nextTTl = 0;
        Log.d(TAG, "Set Bills begmo");
        Log.d(TAG, "There are " +nextBillsBegMo.size() +" bills after the beginning of next month");
        for (int i = 0; i < nextBillsBegMo.size(); i++){
            begTtl += nextBillsBegMo.get(i).getCost();
        }Log.d(TAG, "Begmo totaled to " +begTtl);
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
    public void setSplit(boolean splitDue, boolean splitNext){
        this.splitDue = splitDue;
    }
}
