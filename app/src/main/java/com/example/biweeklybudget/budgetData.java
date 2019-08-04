package com.example.biweeklybudget;

import java.util.ArrayList;
import java.util.Arrays;

public class budgetData {

    public static double balance;
    public static String tempStr;
    public static int quant;
    public static double ttlbills;
    public static double projBalance;
    public static String returned;
    public static int seedPay;
    public static int julDate;
    public static int begOfPay;
    public static int endOfPay;
    public static int dayOfPay;
    public static int daysRemain;
    public static int temp;
    public static int day;
    public static int month;
    public static int year;
    public static int week;
    public static int weekCounter;
    public static int[] months = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    public static int julBegOfPay;
    public static int begNextPay;
    public static int endNextPay;
    public static int monthNew;
    public static int due;
    public static double cost;
    public static double weeklyTotal;
    public static double tempDouble;
    public static double tempTtl;
    public static int x;

    public static final byte SUNDAY = 1;
    public static final byte MONDAY = 2;
    public static final byte TUESDAY = 4;
    public static final byte WEDNESDAY = 8;
    public static final byte THURSDAY = 16;
    public static final byte FRIDAY = 32;
    public static final byte SATURDAY = 64;
    public static final byte[] weekArr = new byte[]{SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY};
    public static final String[] weekStr = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    public static int tempBeginning;
    public static int tempEnding;

    public static void init(int seed, int jul, int dd, int MM, int yy, int ee) {
        balance = 0;
        ttlbills = 0;
        seedPay = seed % 14;
        julDate = jul;
        day = dd;
        month = MM;
        year = yy;
        week = ee;
        x = 0;
        dayOfPay = (julDate % 14) + seedPay;
        begOfPay = julDate - dayOfPay;
        while (begOfPay >= months[(MM - 1)]){
            begOfPay -= months[x];
            x++;
        }
        endOfPay = begOfPay + 14;
        daysRemain = 14 - dayOfPay;
        begNextPay = (day - dayOfPay)+ 14;
        endNextPay = (day - dayOfPay) + 28;

    }

    public static void addBills() {
        ttlbills = 0;
        upNextGen();
        quant = data.dueSize();
        cost = 0;
            //do this if the pay straddles months

        for (int i = 0; i < quant; i++){
            cost += Double.parseDouble(data.getDue(i, 2));
        }
        System.out.println("budgetData ln 84 cost: " +cost);

    }
    public static void addWeekly(){
            quant = data.getWeeklySize();
            weeklyTotal = 0;
            weekCounter = week;
            tempDouble = 0;
            byte tempByte = 0;
            for (int i = 0; i < daysRemain; i++){
                for (int j = 0; j < quant; j++){
                    tempByte = Byte.parseByte(data.getWeekly(j , 2));
                    if ((tempByte & weekArr[weekCounter]) == weekArr[weekCounter]){
                        tempDouble = Double.valueOf(data.getWeekly(j, 1));
                        weeklyTotal = weeklyTotal + tempDouble;
                    }
                }
                weekCounter++;
                weekCounter = weekCounter % 7;
            }
            System.out.println("budgetData ln 104 Weekly: " +weeklyTotal);
    }

    public static void upNextGen() {
        System.out.println("budgetData ln 132 upNextGen Ran");
        data.clearIsDue();
        due = 0;
        quant  = data.getSize();
        tempBeginning = begOfPay;
        tempEnding = endOfPay;
        daysRemain = 14 - dayOfPay;
        //do this if the pay straddles months
        if (endOfPay - daysRemain <= 0) {
            for (int i = day; i <= months[(month - 2)]; i++) {
                for (int j = 0; j < quant; j++) {
                    due = Integer.parseInt(data.getData(j, 1));
                    if (i == due) {
                        data.addDue(j);
                    }
                }
            }
            tempEnding = tempEnding - months[(month - 2)];
            for (int i = 1; i < tempEnding; i++) {
                for (int j = 0; j < quant; j++) {
                    due = Integer.parseInt(data.getData(j, 1));
                    if (i == due) {
                        data.addDue(j);
                    }
                }
            }
        } else {
            tempEnding = tempEnding - months[(month - 2)];
            for (int i = day; i < tempEnding; i++) {
                for (int j = 0; j < quant; j++) {
                    due = Integer.parseInt(data.getData(j, 1));
                    if (i == due) {
                        data.addDue(j);
                    }
                }
            }
        }

    }

    public static void upAfterGen() {
        data.clearIsAfter();
        System.out.println("budgetData ln 174 upAfterGen ran");
        due = 0;
        quant = data.getSize();
        tempBeginning = begNextPay;
        tempEnding = endNextPay;
        //Checking if the next pay starts in the next month
        if (tempEnding > months[(month - 1)]) {
            monthNew = month;
            tempBeginning = tempBeginning - months[month];
            tempBeginning++;
            tempEnding = tempEnding- months[month];
            tempEnding++;
        } else {
            //monthNew is an index; month is the counting number month
            monthNew = month - 1;
        }
        if (tempEnding > months[monthNew]) {
            for (int i = tempBeginning; i <= months[monthNew]; i++) {
                for (int j = 0; j < quant; j++) {
                    due = Integer.parseInt(data.getData(j, 1));
                    if (due == i) {
                    }
                }
            }
            tempEnding = tempEnding - months[monthNew];
            for (int i = 1; i < tempEnding; i++) {
                for (int j = 0; j < quant; j++) {
                    due = Integer.parseInt(data.getData(j, 1));
                    if (due == i) {
                        data.addAfter(j);
                    }
                }
            }
        } else {
            for (int i = tempBeginning; i < tempEnding; i++) {
                for (int j = 0; j < quant; j++) {
                    due = Integer.parseInt(data.getData(j, 1));
                    if (due == i) {
                        data.addAfter(j);
                    }
                }
            }
        }
    }

    public static String calculate(String getBalance) {
        projBalance = 0;
        balance = 0;
        tempStr = getBalance;
        balance = Double.valueOf(tempStr);
        tempTtl = 0;
        addBills();
        addWeekly();
        System.out.println("Budget Data Calculate:");
        System.out.println("Beginning of pay: " +begOfPay);
        System.out.println("End of pay: " +endOfPay);
        System.out.println("Seed Pay: " +seedPay);
        System.out.println("Days remaining: " +daysRemain);
        System.out.println("Day of pay: " +dayOfPay);
        tempTtl = cost + weeklyTotal;
        projBalance = balance - tempTtl;
        returned = String.valueOf(projBalance);
        return returned;

    }
    public static void setSeedPay(int seed){
        int currentSeed =  0;
        currentSeed = seed;
        seedPay = currentSeed;
        System.out.println("budgetData ln 218 seedPay: " +seedPay);
        seedPay = seedPay % 14;
        System.out.println("budgetData ln 220 seedPay after modulus: " +seedPay);
        temp = julDate - seedPay;
        dayOfPay = temp % 14;
        System.out.println("budgetData ln 223 dayOfPay: " +dayOfPay);
        julBegOfPay = julDate - dayOfPay;
        begOfPay = julBegOfPay;
        x = 0;
        while (begOfPay > months[x]){
            begOfPay -= months[x];
            x++;
        }
        System.out.println("budgetData ln 227 begOfPay: " +begOfPay);
        endOfPay = begOfPay + 14;
        System.out.println("budgetData ln 231 endOfPay: " +endOfPay);
        daysRemain = 14 - dayOfPay;
        begNextPay = (day - dayOfPay)+ 14;
        endNextPay = (day - dayOfPay) + 28;
        daysRemain = 14 - dayOfPay;
        System.out.println("budgetData ln 233 daysRemain: " +daysRemain);
        upNextGen();
        upAfterGen();
    }
}
