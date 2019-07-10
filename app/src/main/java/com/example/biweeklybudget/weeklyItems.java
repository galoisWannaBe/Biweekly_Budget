package com.example.biweeklybudget;

public class weeklyItems {

    private  String label;
    private  String cost;
    private  String days;

    public weeklyItems(String labelling, String costing, String daying){
        label = labelling;
        cost = costing;
        days = daying;
    }
    public String getLabel(){
        return label;
    }
    public String getCost(){
        return cost;
    }
    public String getDays(){
        return days;
    }
}
