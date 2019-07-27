package com.example.biweeklybudget;

public class BillItem {
    private String mLabel;
    private String mDue;
    private String mCost;

    public BillItem(String label, String due, String cost) {
        mLabel = label;
        mDue = due;
        mCost = cost;
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

}
