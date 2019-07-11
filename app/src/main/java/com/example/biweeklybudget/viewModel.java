package com.example.biweeklybudget;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class viewModel extends AndroidViewModel {

    private repository repo;
    private LiveData<List<Bills>> mAllBills;

    public viewModel(Application application){
        super(application);
        repo = new repository(application);
        mAllBills = repo.getAllBills();
    }
    LiveData<List<Bills>> getAllBillsFromVM(){
        return mAllBills;
    }
    void insert(Bills bill){
        repo.insert(bill);
    }
    public LiveData<List<Bills>> getAllBillsFromRepo(){
        return repo.getAllBills();
    }
}
