package com.example.biweeklybudget;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BillDao {

    @Query("SELECT * from bill_table ORDER BY due ASC")
    LiveData<List<Bill>> getAllBills();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertBill(Bill bill);

    @Query("DELETE FROM bill_table where id = :id")
    void deleteBill(int id);

    @Query("UPDATE bill_table SET label = :label, due = :due, cost = :cost WHERE id = :id")
    void updateBill(int id, String label, int due, double cost);

    @Query("SELECT * from bill_table WHERE due >= :today AND due < :fin ORDER BY due ASC")
    LiveData<List<Bill>> getNext(int today, int fin);
}
