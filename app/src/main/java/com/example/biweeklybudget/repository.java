package com.example.biweeklybudget;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

class repository {

    private BillDao mBillDao;
    private LiveData<List<Bills>> mAllBills;

    repository(Application application){
        TheDatabase db = TheDatabase.getDatabase(application);
        mBillDao = db.billDao();
        mAllBills = mBillDao.getAlphabetizedBills();
    }

    LiveData<List<Bills>> getAllBills(){
        return mAllBills;
    }
    void insert(Bills bill){
        new insertAsyncTask(mBillDao).execute(bill);
    }
    private  static class insertAsyncTask extends AsyncTask<Bills, Void, Void>{

        private BillDao mAsyncTaskDao;

        insertAsyncTask(BillDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected  Void doInBackground(final Bills... params){
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
