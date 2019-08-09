package com.example.biweeklybudget;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "bill_table")
public class Bill {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @NonNull
    @ColumnInfo(name = "label")
    private String label;

    @NonNull
    @ColumnInfo(name = "due")
    private int due;

    @NonNull
    @ColumnInfo(name = "cost")
    private double cost;

    public Bill(@NonNull String label, int due, double cost) {
        this.label = label;
        this.due = due;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getLabel() {
        return label;
    }

    @NonNull
    public int getDue() {
        return due;
    }

    @NonNull
    public double getCost() {
        return cost;
    }
}
