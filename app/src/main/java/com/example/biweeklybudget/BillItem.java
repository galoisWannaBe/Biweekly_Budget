package com.example.biweeklybudget;

public class BillItem {
    private String mLabel;
    private String mDue;
    private String mCost;
    private int id;

    public BillItem(String label, String due, String cost, int identifier) {
        mLabel = label;
        mDue = due;
        mCost = cost;
        id = identifier;
        System.out.println("Saved ID = " +id);
    }


    public String getLabel() {
        return mLabel;
    }

    public String getDue() {
        return mDue;
    }

    public String getCost() {
        return mCost;
    }

    public int getId(){
        return id;
    }
}
