package com.example.biweeklybudget;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "bills_table")
public class Bills {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    public int id;

    @NonNull int getId(){
        return  this.id;
    }
    @NonNull
    @ColumnInfo(name = "label")
    private String mLabel;

    @NonNull String getLabel(){
        return this.mLabel;
    }

    @NonNull
    @ColumnInfo(name = "due")
    private int mDue;

    @NonNull int getDue(){
        return this.mDue;
    }

    @NonNull
    @ColumnInfo
    private double mCost;

    @NonNull double getCost(){
        return this.mCost;
    }


    public Bills(@NonNull String lable, @NonNull int due, @NonNull double cost){
        this.mLabel = lable;
        this.mDue = due;
        this.mCost = cost;
    }
}
