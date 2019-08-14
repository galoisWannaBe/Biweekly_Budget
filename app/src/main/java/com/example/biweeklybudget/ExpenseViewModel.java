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
    private LiveData<List<Bill>> afterBillsEndMo;
    private LiveData<List<Bill>> afterBillsBegMo;
    private LiveData<List<Bill>> nextBillsEndMo;
    private LiveData<List<Bill>> nexBillsBegMo;
    private LiveData<List<Weekly>> allWeekly;
    private LiveData<Integer> billCount;
    private boolean splitMo;
    private boolean splitDue;
    private int today;
    private int daysRemain;
    private int dayOfWeek;

    public LiveData<List<Bill>> getNextBillsEndMo() {
        return nextBillsEndMo;
    }

    public LiveData<List<Bill>> getNexBillsBegMo() {
        return nexBillsBegMo;
    }

    public boolean isSplitDue() {
        return splitDue;
    }

    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ExpenseRepository(application);
        allBills = mRepository.getAllBills();
        nextBills = mRepository.getNextBills();
        afterBills = mRepository.getAfterBill();
        allWeekly = mRepository.getAllWeekly();
        daysRemain = mRepository.getDaysRemain();
        billCount = mRepository.getBillCount();
        splitMo = mRepository.isSplitMo();
        splitDue = mRepository.isSplitDue();
        Log.d(TAG, "Constructor ran");
    }

    public boolean isSplitMo() {
        return splitMo;
    }

    public LiveData<List<Bill>> getAllBills() {
        return allBills;
    }

    public LiveData<List<Bill>> getNextBills() {
        return nextBills;
    }

    public LiveData<List<Bill>> getAfterBills() {
        return afterBills;
    }
    public LiveData<List<Bill>> getAfterBillsEndMo() {
        return afterBillsEndMo;
    }

    public LiveData<List<Bill>> getAfterBillsBegMo() {
        return afterBillsBegMo;
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
    public LiveData<List<Bill>> getAfterEndMo(){
        afterBillsEndMo = mRepository.getAfterBillsEndMonth();
        return afterBillsEndMo;
    }
    public LiveData<List<Bill>> getAfterBegMo(){
        afterBillsBegMo = mRepository.getAfterBillsBeginningMonth();
        return afterBillsBegMo;
    }
}