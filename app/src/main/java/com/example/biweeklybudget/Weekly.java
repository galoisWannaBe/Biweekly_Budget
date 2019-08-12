package com.example.biweeklybudget;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weekly_table")
public class Weekly {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @NonNull
    public int getId() {
        return id;
    }

    @NonNull
    @ColumnInfo(name = "label")
    private String label;

    @NonNull
    @ColumnInfo(name = "cost")
    private double cost;

    @NonNull
    @ColumnInfo(name = "days")
    private byte days;

    public Weekly(@NonNull String label, @NonNull double cost, @NonNull byte days) {
        this.label = label;
        this.cost = cost;
        this.days = days;
    }

    @NonNull
    public String getLabel() {
        return label;
    }

    @NonNull
    public double getCost() {
        return cost;
    }

    @NonNull
    public byte getDays(){
        return days;
    }
}
