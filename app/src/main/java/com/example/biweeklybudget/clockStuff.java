package com.example.biweeklybudget;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;

import java.util.Calendar;

public class clockStuff {

    int julDate;
    int seedPay;
    int startPPD;
    int daysRemain;
    int finPPD;
    int month;
    int day;
    int[] months;
    int week;
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
    }

    public void setSeedPay(int seedPay) {
        this.seedPay = seedPay;
        seedPay = seedPay % 14;
        daysRemain = (julDate % 14);
        daysRemain = 14 - daysRemain;
        daysRemain += seedPay;//currently number of days into pay period
        finPPD = day + daysRemain;
        daysRemain = 14 - daysRemain;//actually the amount of days remaining in the pay period
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
    public int getDaysRemain(){
        return daysRemain;
    }
    public int getWeek(){
        return week;
    }
    public int getJulDate(){
        return julDate;
    }

}
