package com.example.biweeklybudget;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

@Database(entities = {Bills.class}, version  = 1, exportSchema = false)

public abstract class TheDatabase extends RoomDatabase{

        public abstract BillDao billDao();

        //marking the instance as volatile to ensure atomic access to the variable

        private static volatile TheDatabase INSTANCE;

        static TheDatabase getDatabase(final Context context){
            if (INSTANCE == null){
                synchronized (TheDatabase.class){
                    if (INSTANCE == null){
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TheDatabase.class, "the_database").fallbackToDestructiveMigration().addCallback(sRoomDatabaseCallback).build();
                    }
                }
            }
            return INSTANCE;
        }

        private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db){

                super.onOpen(db);

            }

        };

}

