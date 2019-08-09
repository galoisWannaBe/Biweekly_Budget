package com.example.biweeklybudget;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Context;

@Database(entities = {Bill.class, Weekly.class}, version = 1, exportSchema = false)
public abstract class ExpensesDatabase extends RoomDatabase {

    public abstract BillDao billDao();
    public abstract WeeklyDao weeklyDao();

    private static volatile ExpensesDatabase INSTANCE;

    static ExpensesDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ExpensesDatabase.class) {
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ExpensesDatabase.class, "expenses_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}
