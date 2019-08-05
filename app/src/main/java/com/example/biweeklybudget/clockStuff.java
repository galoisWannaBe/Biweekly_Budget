package com.example.biweeklybudget;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;

import java.util.Calendar;

public class clockStuff {

    static int day;
    static int month;
    static int year;
    static int week;
    static int julDate;
    static int[] months;
    static DateFormat dd = new SimpleDateFormat("dd");
    static DateFormat MM = new SimpleDateFormat("MM");
    static DateFormat yy = new SimpleDateFormat("yy");
    static DateFormat ee = new SimpleDateFormat("E");
    static String dayS = dd.format(Calendar.getInstance().getTime());
    static String monS = MM.format(Calendar.getInstance().getTime());
    static String yearS = yy.format(Calendar.getInstance().getTime());
    static String weekS = ee.format(Calendar.getInstance().getTime());

    static public void init(){
        months = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        day = Integer.valueOf(dayS);
        month = Integer.valueOf(monS);
        julDate = 0;
        for(int i = 0; i <(month - 1); i++){
            julDate += months[i];
        }julDate += day;

    }

    static public int getDay(){
        day = Integer.valueOf(dayS);
        return day;
    }
    static public int getMonth(){
        month = Integer.valueOf(monS);
        return month;
    }
    static public int getYear(){
        year = Integer.valueOf(yearS);
        return year;
    }
    static public int getWeek(){
        switch (weekS){
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
        return week;
    }
    static public int getJulDate(){
        return julDate;
    }

}
