package com.example.biweeklybudget;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WeeklyDao {

    @Query("SELECT * from weekly_table ORDER BY label ASC")
    LiveData<List<Weekly>> getWeeklyList();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertWeekly(Weekly weekly);

    @Query("UPDATE weekly_table SET label = :label, cost = :cost , days = :days WHERE id = :id")
    void updateWeekly(int id, String label, double cost, byte days);

    @Query("DELETE FROM weekly_table where id = :id")
    void deleteWeekly(int id);
}
