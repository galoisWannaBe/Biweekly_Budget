package com.example.biweeklybudget;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

class ExpenseRepository {
    private static final String TAG = "ExpenseRepository";

    private LiveData<List<Bill>> allBills;
    private LiveData<List<Bill>> nextBills;
    private LiveData<List<Bill>> afterBills;
    private LiveData<List<Weekly>> allWeekly;
    private LiveData<Integer> billCount;
    private BillDao billDao;
    private WeeklyDao weeklyDao;
    private clockStuff mClockStuff;
    private int today;
    private int finPPD;
    private int daysRemain;
    private int dayOfWeek;
    private int begNext;
    private int finNext;

    public ExpenseRepository(Application application) {
        ExpensesDatabase db = ExpensesDatabase.getDatabase(application);
        billDao = db.billDao();
        weeklyDao = db.weeklyDao();
        mClockStuff = clockStuff.getInstance();
        today = mClockStuff.getDay();
        finPPD = mClockStuff.getFinPPD();
        begNext = finPPD + 1;
        finNext =finPPD + 14;
        daysRemain = mClockStuff.getDaysRemain();
        allBills = billDao.getAllBills();
        nextBills = billDao.getNext(today, finPPD);
        afterBills = billDao.getAfter(begNext, finNext);
        allWeekly = weeklyDao.getWeeklyList();
        dayOfWeek = mClockStuff.getWeek();
        billCount = billDao.getBillCount();
        Log.d(TAG, "constructer ran");
    }

    void setSeedPay(int seed){
        mClockStuff.setSeedPay(seed);
        today = mClockStuff.getDay();
        finPPD = mClockStuff.getFinPPD();
        begNext = finPPD + 1;
        finNext = finPPD + 14;
        daysRemain = mClockStuff.getDaysRemain();
        nextBills = billDao.getNext(today, finPPD);
        afterBills = billDao.getAfter(begNext, finNext);
        Log.d(TAG, "Seed pay set");
        Log.d(TAG, daysRemain +"days remaining");
        Log.d(TAG, "Today: " +today +" EndPay: " +finPPD +" begNext: " +begNext +" finNext: " +finNext);
    }

    public LiveData<List<Bill>> getAllBills(){
        Log.d(TAG, "getAllBills ran");
        return allBills;
    }
    public LiveData<List<Weekly>> getAllWeekly(){
        Log.d(TAG, "getAllWeekly ran");
        return allWeekly;
    }
    public LiveData<List<Bill>> getNextBills() {
        return nextBills;
    }
    public Integer getDaysRemain() {
        return daysRemain;
    }
    public LiveData<List<Bill>> getAfterBill(){
        return afterBills;
    }
    public LiveData<Integer> getBillCount(){
        return billCount;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    void insertBill(Bill bill){
        Log.d(TAG, "Bill insertion started");
        new insertBillAsyncTask(billDao).execute(bill);
    }
    void deleteBill(int ID){
        new deleteByIDAsyncTask(billDao).execute(ID);
    }
    void updateBill(int ID, String label, int due, double cost){
        updateParams mUpdateParams = new updateParams(ID, label, due, cost);

        new updateBillByAsync(billDao).execute(mUpdateParams);
    }
    void insertWeekly(Weekly weekly){
        Log.d(TAG, "Weekly insertion started");
        new insertWeeklyAsync(weeklyDao).execute(weekly);
    }
    void updateWeeklyAsync(int id, String label, double cost, byte days){
        updateWeekly mUpdateWeekly= new updateWeekly(id, label, cost, days);
        new updateWeeklyAsync(weeklyDao).execute(mUpdateWeekly);
    }
    void deleteWeekly(int id){
        new deleteWeeklyAsync(weeklyDao).execute(id);
    }

    private static class insertBillAsyncTask extends AsyncTask<Bill, Void, Void>{

        private BillDao mBillAsync;

        public insertBillAsyncTask(BillDao mBillAsync) {
            this.mBillAsync = mBillAsync;
        }

        @Override
        protected Void doInBackground(final Bill... bills) {
            mBillAsync.insertBill(bills[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG,"Bill inserted");
        }
    }

    private static class deleteByIDAsyncTask extends AsyncTask<Integer, Void, Void>{

        private BillDao mBillAsync;

        public deleteByIDAsyncTask(BillDao mBillAsync) {
            this.mBillAsync = mBillAsync;
        }

        @Override
        protected Void doInBackground(final Integer... integers) {
            mBillAsync.deleteBill(integers[0]);
            return null;
        }
    }

    public static class updateParams{

        int ID;
        String label;
        int due;
        double cost;

        public updateParams(int ID, String label, int due, double cost) {
            this.ID = ID;
            this.label = label;
            this.due = due;
            this.cost = cost;
        }
    }

    private static class updateBillByAsync extends AsyncTask<updateParams, Void, Void>{

        private BillDao mBillAsync;

        public updateBillByAsync(BillDao mBillAsync) {
            this.mBillAsync = mBillAsync;
        }

        @Override
        protected Void doInBackground(updateParams... updateParams) {

            int ID = updateParams[0].ID;
            String label = updateParams[0].label;
            int due = updateParams[0].due;
            double cost = updateParams[0].cost;

            mBillAsync.updateBill(ID, label, due, cost);
            return null;


        }
    }

    private static class insertWeeklyAsync extends AsyncTask<Weekly, Void, Void>{

        private WeeklyDao mWeeklyAsyncDao;

        public insertWeeklyAsync(WeeklyDao mWeeklyAsyncDao) {
            this.mWeeklyAsyncDao = mWeeklyAsyncDao;
        }

        @Override
        protected Void doInBackground(Weekly... weeklies) {
            mWeeklyAsyncDao.insertWeekly(weeklies[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG, "Weekly insertion finished");
        }
    }

    private static class deleteWeeklyAsync extends AsyncTask<Integer, Void, Void>{

        WeeklyDao asyncWeekly;

        public deleteWeeklyAsync(WeeklyDao asyncWeekly) {
            this.asyncWeekly = asyncWeekly;
        }

        @Override
        protected Void doInBackground(Integer... integers) {

            asyncWeekly.deleteWeekly(integers[0]);

            return null;
        }

    }

    private static class updateWeekly{

        int ID;
        String label;
        double cost;
        byte days;

        public updateWeekly(int ID, String label, double cost, byte days) {
            this.ID = ID;
            this.label = label;
            this.cost = cost;
            this.days = days;
        }
    }

    private static class updateWeeklyAsync extends AsyncTask<updateWeekly, Void, Void>{

        WeeklyDao weekSyncDao;

        public updateWeeklyAsync(WeeklyDao weekSyncDao) {
            this.weekSyncDao = weekSyncDao;
        }

        @Override
        protected Void doInBackground(updateWeekly... updateWeeklies) {
            int ID = updateWeeklies[0].ID;
            String label = updateWeeklies[0].label;
            double cost = updateWeeklies[0].cost;
            byte days = updateWeeklies[0].days;

            weekSyncDao.updateWeekly(ID, label, cost, days);
            return null;
        }
    }


}
