package com.example.biweeklybudget;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

public class viewModel extends AndroidViewModel {

    private repository repo;
    private LiveData<Bills> mAllBills;

    public viewModel(Application application){
        super(application);

    }
}
