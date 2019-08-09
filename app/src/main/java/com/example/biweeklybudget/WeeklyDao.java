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

    @Update
    void updateWeekly(Weekly weekly);

    @Delete
    void deleteWeekly(Weekly weekly);
}
