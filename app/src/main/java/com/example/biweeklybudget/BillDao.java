package com.example.biweeklybudget;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

@Dao
public interface BillDao {

    //Queries here

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Bills bill);

    @Query("DELETE FROM bills_table")
    void deleteAll();

}
