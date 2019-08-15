package com.example.biweeklybudget;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.util.Log;
import android.widget.TextView;

import java.util.Calendar;

public class clockStuff {

    public static final String TAG = "clockStuff";

    int julDate;
    int seedPay;
    int startPPD;
    int daysRemain;
    int finPPD;
    int month;
    int day;
    int[] months;
    int week;
    int begNext;
    int finNext;
    int payMonth;
    int payDate;
    DateFormat dd = new SimpleDateFormat("dd");
    DateFormat MM = new SimpleDateFormat("MM");
    DateFormat ee = new SimpleDateFormat("E");
    String dayStr = dd.format(Calendar.getInstance().getTime());
    String monStr = MM.format(Calendar.getInstance().getTime());
    String weekStr = ee.format(Calendar.getInstance().getTime());

    private static final clockStuff INSTANCE = new clockStuff();

    public static clockStuff getInstance() {
        return INSTANCE;
    }

    private clockStuff(){
        months = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        day = Integer.parseInt(dayStr);
        month = Integer.parseInt(monStr);
        julDate = 0;
        for(int i = 0; i < (month -1); i++){
            julDate += months[i];
        }
        julDate += day;

        switch (weekStr){
            case "Sun":
                week = 0;
                break;
            case "Mon":
                week = 1;
                break;
            case "Tue":
                week = 2;
                break;
            case "Wed":
                week = 3;
                break;
            case "Thu":
                week = 4;
                break;
            case "Fri":
                week = 5;
                break;
            case "Sat":
                week = 6;
                break;
        }
        Log.d(TAG, "Julian Date " +julDate);
        Log.d(TAG, "Date: " +day);
    }

    public void setSeedPay(int seedPay) {
        this.seedPay = seedPay;
        seedPay = seedPay % 14;
        int dayOfPay = (julDate % 14);
        daysRemain = 14 - dayOfPay;
        daysRemain += seedPay;//currently number of days into pay period
        finPPD = (day + daysRemain) -1;
        Log.d(TAG, "FinPPD: " +finPPD);
        Log.d(TAG, "month: " +month);
        Log.d(TAG, "Days in Month: " +months[month]);
        Log.d(TAG, "FinPPD: " +finPPD);
        if (finPPD > months[(month - 1)]){
            finPPD -= months[month];
        }
        begNext = finPPD + 1;
        if(begNext > months[(month - 1)]){
            begNext -= months[month];
        }
        finNext = finPPD + 13;
        if(finNext > months[(month - 1)]){
            finNext -= months[(month - 1)];
        }
        finNext = finNext % months[month];
        Log.d(TAG, "Julian pay " +julDate);

    }

    public int getDay(){
        return day;
    }
    public int getMonth(){
        return month;
    }
    public int getFinPPD(){
        return finPPD;
    }
    public Integer getDaysRemain(){
        return daysRemain;
    }
    public int getWeek(){
        return week;
    }
    public int getJulDate(){
        return julDate;
    }

    public int getBegNext() {
        return begNext;
    }

    public int getFinNext() {
        return finNext;
    }
    public void calculateMostRecentPay(int seed){
        int payDay = 0;
        while (payDay <= julDate){
            payDay += 14;
        }
        payDay -= 14;
        payDay += seed;
        int i = 0;
        while (payDay > 0){
            payDay -= months[i];
            i++;
        }
        payMonth = i + 1;
        payDate = payDay + months[i] + 1;
    }
    public void setJulianDate(int julianDate){
        this.julDate = julianDate;
    }

    public int getPayMonth() {
        return payMonth;
    }

    public int getPayDate() {
        return payDate;
    }
}
