package com.example.biweeklybudget;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class ExpenseRepository {

    private LiveData<List<Bill>> allBills;
    private LiveData<List<Bill>> nextBills;
    private LiveData<List<Weekly>> allWeekly;
    private BillDao billDao;
    private WeeklyDao weeklyDao;
    private clockStuff mClockStuff;
    private int today;
    private int finPPD;
    private int daysRemain;

    public ExpenseRepository(Application application) {
        ExpensesDatabase db = ExpensesDatabase.getDatabase(application);
        billDao = db.billDao();
        weeklyDao = db.weeklyDao();
        mClockStuff = clockStuff.getInstance();
        today = mClockStuff.getDay();
        finPPD = mClockStuff.getFinPPD();
        daysRemain = mClockStuff.getDaysRemain();
        allBills = billDao.getAllBills();
        nextBills = billDao.getNext(today, finPPD);
        allWeekly = weeklyDao.getWeeklyList();
    }

    void setSeedPay(int seed){
        mClockStuff.setSeedPay(seed);
        today = mClockStuff.getDay();
        finPPD = mClockStuff.getFinPPD();
        daysRemain = mClockStuff.getDaysRemain();
        nextBills = billDao.getNext(today, finPPD);
        //getNextList();
    }

    // TODO: 8/9/19 code repo and viewmodel
}
