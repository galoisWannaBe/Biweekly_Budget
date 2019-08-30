package com.example.biweeklybudget;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import java.lang.annotation.Target;
import java.security.PublicKey;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class BudgetData {

    private static final String TAG = "BudgetData";

    public double balance;
    public double ttlbills;
    public double projBalance;
    double endTtl;
    double begTtl;
    double nextTTl;
    double afterTtl;
    double afterEnd;
    double afterBeg;
    double allAfterTtl;
    double allBillsTtl;
    public int daysRemain;
    public int day;
    public int week;
    public int weekCounter;
    public double cost;
    public double weeklyTotal;
    public double weeklyWholePay;
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
    public List<Bill> afterBills;
    public List<Bill> afterBillsEnd;
    public List<Bill> afterBillsBegin;
    public List<Bill> allBills;
    public List<Weekly> allWeekly;
    public int julianDate = 0;
    public int payMonth;
    public int payDate;
    int[] months = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    boolean splitDue;
    LiveData<List<Bill>> liveBillsDue;
    ExpenseViewModel expenseViewModel;
    NumberFormat formatter;

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
        afterBills = Collections.emptyList();
        afterBillsEnd = Collections.emptyList();
        afterBillsBegin = Collections.emptyList();
        allWeekly = Collections.emptyList();
        formatter = NumberFormat.getCurrencyInstance();

    }

    public double getTtlbills() {
        ttlbills = nextTTl + endTtl + begTtl;
        Log.d(TAG, nextBills +" + " +endTtl +" + " +begTtl +" = " +ttlbills);
        return ttlbills;
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
            projBalance = getBalance - ttlbills;
            Log.d(TAG, "ttl bills: " +ttlbills);
            String a = formatter.format(projBalance);
            return a;
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

    public void setAfterBills(List<Bill> afterBills) {
        this.afterBills = afterBills;
        afterTtl = 0;
        afterEnd = 0;
        afterBeg = 0;
        for (int i = 0; i < afterBills.size(); i++){
            afterTtl += afterBills.get(i).getCost();
        }
    }

    public void setAfterBillsEnd(List<Bill> afterBillsEnd) {
        this.afterBillsEnd = afterBillsEnd;
        afterTtl = 0;
        afterEnd = 0;
        for (int i = 0; i < afterBillsEnd.size(); i++) {
            afterEnd += afterBillsEnd.get(i).getCost();
        }
    }

    public void setAfterBillsBegin(List<Bill> afterBillsBegin) {
        this.afterBillsBegin = afterBillsBegin;
        afterTtl = 0;
        afterBeg = 0;
        for (int i = 0; i < afterBillsBegin.size(); i++){
            afterBeg += afterBillsBegin.get(i).getCost();
        }
    }

    public void setAllWeekly(List<Weekly> allWeekly) {
        this.allWeekly = allWeekly;
        weeklyWholePay = 0;
        for (int i = 0; i < allWeekly.size(); i++){
            Weekly weekly = allWeekly.get(i);
            byte days = weekly.getDays();
            for (int j = 0; j < 7; j++) {
                if ((days & weekArr[j]) == weekArr[j]) {
                    weeklyWholePay += (weekly.getCost() * 2);
                }
            }
        }

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
        Log.d(TAG, "Set Weeklies");
        Log.d(TAG, "Whole pay: $" +weeklyWholePay);
    }

    public String getWeeklyTotal() {
        Log.d(TAG, "There are $" +weeklyTotal +" worth of expenses for the rest of the pay");
        return formatter.format(weeklyTotal);
    }

    public String getWeeklyWholePay() {
        Log.d(TAG, "There a total of $" +weeklyWholePay +" in weekly expenses paid for a whole pay");
        return formatter.format(weeklyWholePay);
    }

    public double getAfterTtl(){
        allAfterTtl = afterTtl + afterEnd + afterBeg;
        return allAfterTtl;
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

    public void setAllBills(List<Bill> allBills) {
        this.allBills = allBills;
        allBillsTtl = 0;
        Log.d(TAG, allBills.size() +" bills are being added");
        for (int i = 0; i < allBills.size(); i++){
            allBillsTtl += allBills.get(i).getCost();
        }
        Log.d(TAG, "You pay $" +allBillsTtl +" each month in bills");
    }

    public double getAllBillsTtl() {
        Log.d(TAG, "All Bills totalted to: " +allBillsTtl);
        return allBillsTtl;
    }
}
