package com.example.biweeklybudget;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "weekly_table")
public class Weekly {

    @PrimaryKey
    @ColumnInfo(name = "label")
    private String label;


    @ColumnInfo(name = "cost")
    private double cost;

    @ColumnInfo(name = "days")
    private byte days;

    public Weekly(String label, double cost, byte days) {
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
    public byte getDue() {
        return days;
    }
}
