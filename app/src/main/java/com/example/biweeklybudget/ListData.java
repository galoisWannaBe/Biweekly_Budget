package com.example.biweeklybudget;

import java.util.ArrayList;
import java.util.Arrays;

public class ListData {

    //this entire class should have been burned before publishing

    static ArrayList<String> billS= new ArrayList<>(Arrays.asList("rent","electric","YouTube","Citizens","Hulu","FiOS","SYF","PayPal","Amazon","T-Mobile","Netflix","Navient"));
    static ArrayList<String> dueS = new ArrayList<>(Arrays.asList("1", "1", "2", "4", "4", "6", "15", "17", "18", "19" ,"22", "22"));
    static ArrayList<String> costS = new ArrayList<>(Arrays.asList("570", "100", "10.59", "71.71", "8.46", "68", "48.95", "60", "28", "70", "13.9", "222"));
    static ArrayList<String> weeklyLabels = new ArrayList<>(Arrays.asList("pizza", "booze", "grocery"));
    static ArrayList<String> weeklyCosts = new ArrayList<>(Arrays.asList("10", "50" , "70"));
    static ArrayList<String> weeklyDays = new ArrayList<>(Arrays.asList("MTWRF" , "F" , "U"));

    public static int listDataInit(){
        return dueS.size();
    }
    public static String getBillElement(int i){
        return billS.get(i);
    }
    public static String getDueElement(int i){
        return dueS.get(i);
    }
    public static String  getCostElement(int i){
        return costS.get(i);
    }
    public static int weeklyInit(){
        int q = weeklyCosts.size();
        System.out.println("The weekly expenses as are stored in the list are");
        for (int j = 0; j < q; j++){
            System.out.println(weeklyLabels.get(j) +" " +weeklyCosts.get(j) +" " +weeklyDays.get(j));
        }
        return weeklyCosts.size();
    }
    public static String getWeeklyLabel(int i){
        System.out.println("Currently getting:");
        System.out.println(weeklyLabels.get(i));
        return weeklyLabels.get(i);
    }
    public static String getWeeklyCost(int i){
        return weeklyCosts.get(i);
    }
    public static String getWeeklyDays(int i){
        return weeklyDays.get(i);
    }



}
