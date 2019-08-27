package com.example.biweeklybudget;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.List;

public class ExpenseViewModel extends AndroidViewModel implements LifecycleOwner {

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
    private LiveData<Boolean> liveSplitDue;
    private int today;
    private int daysRemain;
    private int dayOfWeek;
    BudgetData budgetData;

    public boolean isSplitDue() {
        return mRepository.isSplitDue();
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
        liveSplitDue = mRepository.isSplitDueLive();
        splitMo = mRepository.isSplitMo();
        splitDue = mRepository.isSplitDue();
        budgetData = BudgetData.getInstance();
        observeAfter();
        observeAfterBeg();
        observeAfterEnd();
        observeAll();
        observeNext();
        observeNextBeg();
        observNextEnd();
        observeWeekly();
        /*
        mRepository.getNextBills().observe(this, new Observer<List<Bill>>() {
            @Override
            public void onChanged(List<Bill> bills) {
                budgetData.setNextBills(bills);
            }
        });
        Log.d(TAG, "Constructor ran");
        */
         }

    public LiveData<List<Bill>> getNextBillsEndMo() {
        Log.d(TAG, "EndMo");
        return mRepository.getNextBillsEnd();
    }

    public LiveData<List<Bill>> getNexBillsBegMo() {
        Log.d(TAG, "BegMo");
        return mRepository.getNextBillsBegin();
    }

    public boolean isSplitMo() {
        return splitMo;
    }

    public LiveData<Boolean> getLiveSplitDue(){
        return liveSplitDue;
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
        return mRepository.getAllWeekly();
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
        Log.d(TAG, "getNextAsink ran in viewModel");
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
    public void upDateNextSplit(){
        mRepository.getNextBillsEnd();
    }
    public void updateNextSplitBegin(){
        mRepository.getNextSplitBegins();
    }

    public void observNextEnd(){
        getNextBillsEndMo().observe(this, bills -> {
            budgetData.setNextSplitEndMo(bills);
        });
    }
    public void observeNextBeg(){
        getNexBillsBegMo().observe(this, bills -> {
            budgetData.setNextBillsBegMo(bills);
        });
    }

    public void observeNext(){
        getNextBills().observe(this, bills -> {
            budgetData.setNextBills(bills);
        });
    }

    public void observeWeekly(){
        getAllWeekly().observe(this, weeklies -> {
            budgetData.setAllWeekly(weeklies);
        });
    }

    public void observeAfter(){
        getAfterBills().observe(this, bills -> {
            upAfter.setAfterBills(bills);

        });
    }
    public void observeAfterEnd(){
        getAfterBillsEndMo().observe(this, bills -> {
            upAfter.setAfterBillsEndMo(bills);
        });
    }
    public void observeAfterBeg(){
        getAfterBillsBegMo().observe(this, bills -> {
            upAfter.setAfterBillsBegMo(bills);
        });
    }
    public void observeAll(){
        getAllBills().observe(this, bills -> {
            viewAll.setAllBills(bills);
        });
    }

    // TODO: 8/26/19 Finish putting observers into the ExpenseViewModel Class and rip them out everywhere else
    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return null;
    }
}