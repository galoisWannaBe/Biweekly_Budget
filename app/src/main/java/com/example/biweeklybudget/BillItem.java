package com.example.biweeklybudget;

public class BillItem {
    private String mText1;
    private String mText2;
    private String mText3;
    private int id;

    public BillItem(String text1, String text2, String text3, int identifier) {
        mText1 = text1;
        mText2 = text2;
        mText3 = text3;
        id = identifier;
    }


    public String getText1() {
        return mText1;
    }

    public String getText2() {
        return mText2;
    }

    public String getText3() {
        return mText3;
    }

    public int getId(){
        return id;
    }
}
