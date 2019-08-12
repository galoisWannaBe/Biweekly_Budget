package com.example.biweeklybudget;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ExpenseViewModel extends AndroidViewModel {

    private static final String TAG = "ExpenseViewModel";

    private ExpenseRepository mRepository;

    private LiveData<List<Bill>> allBills;
    private LiveData<List<Bill>> nextBills;
    private LiveData<List<Bill>> afterBills;
    private LiveData<List<Weekly>> allWeekly;
    private LiveData<Integer> billCount;
    private int today;
    private int daysRemain;
    private int dayOfWeek;
    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ExpenseRepository(application);
        allBills = mRepository.getAllBills();
        nextBills = mRepository.getNextBills();
        afterBills = mRepository.getAfterBill();
        allWeekly = mRepository.getAllWeekly();
        daysRemain = mRepository.getDaysRemain();
        billCount = mRepository.getBillCount();
        Log.d(TAG, "Constructor ran");
    }

    public LiveData<List<Bill>> getAllBills() {
        return allBills;
    }

    public LiveData<List<Bill>> getNextBills() {
        return nextBills;
    }

    public LiveData<List<Bill>> getAfterBills(){
        return afterBills;
    }

    public LiveData<List<Weekly>> getAllWeekly() {
        return allWeekly;
    }

    public void setSeedPay(int seedPay){
        mRepository.setSeedPay(seedPay);
    }

    public int getDaysRemain() {
        return mRepository.getDaysRemain();
    }

    public int getDayOfWeek() {
        return mRepository.getDayOfWeek();
    }

    void insertBill(Bill bill){
        mRepository.insertBill(bill);
    }
    void deleteBill(int ID){
        mRepository.deleteBill(ID);
    }
    void updateBill(int ID, String label, int due, double cost){
        mRepository.updateBill(ID, label, due, cost);
    }
    void insertWeekly(Weekly weekly){
        mRepository.insertWeekly(weekly);
    }
    void deleteWeekly(int id){
        mRepository.deleteWeekly(id);
    }
    void updateWeekly(int id, String label, double cost, byte days){
        mRepository.updateWeeklyAsync(id, label, cost, days);
    }
    public LiveData<List<Bill>> getNextASink(){
        nextBills = mRepository.getNextBills();
        return nextBills;
    }
    public LiveData<List<Bill>> getAfterAsync(){
        afterBills = mRepository.getAfterBill();
        return afterBills;
    }
}