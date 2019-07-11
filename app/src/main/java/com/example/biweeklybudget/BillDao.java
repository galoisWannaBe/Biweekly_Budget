package com.example.biweeklybudget;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface BillDao {

    //Queries here

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Bills bill);

    @Query("DELETE FROM bills_table")
    void deleteAll();

    @Query("SELECT * from bills_table ORDER by due ASC")
    LiveData<List<Bills>> getAlphabetizedBills();
}
