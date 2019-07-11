package com.example.biweeklybudget;

import java.util.ArrayList;
import java.util.Arrays;

public class budgetData {

    public static double balance;
    public static String tempStr;
    public static String tempStr2;
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
    public static double food;
    public static double grocery;
    public static double booze;
    public static double ttlExpenses;
    public static int begNextPay;
    public static int endNextPay;
    public static int monthNew;
    public static int due;
    public static double cost;
    public static double weeklyTotal;
    public static String weekDayCounter;
    public static double tempDouble;
    public static ArrayList<String> weekDay = new ArrayList<>(Arrays.asList("U" , "M" , "T" , "W" , "R" , "F" , "S"));
    public static String tempStr3;
    public static double tempTtl;

    public static void init(int seed, int jul, int dd, int MM, int yy, int ee) {
        balance = 0;
        ttlbills = 0;
        seedPay = seed;
        julDate = jul;
        day = dd;
        month = MM;
        year = yy;
        week = ee;
        temp = julDate - seedPay;
        dayOfPay = temp % 14;
        julBegOfPay = julDate - dayOfPay;
        begOfPay = day - dayOfPay;
        endOfPay = begOfPay + 14;
        daysRemain = 14 - dayOfPay;
        begNextPay = begOfPay + 14;
        endNextPay = endOfPay + 14;

    }

    public static void addBills() {
        ttlbills = 0;
        quant = data.getSize();
        cost = 0;

            //do this if the pay straddles months
        if ((begOfPay + 14) > months[(month - 1)]) {
            for (int i = day; i <= months[(month - 1)]; i++) {
                for (int j = 0; j < quant; j++) {
                    due = Integer.parseInt(data.getData(j, 1));
                    if (i == due) {
                        cost = Double.valueOf(data.getData(j, 2));
                        ttlbills += cost;
                    }
                }
            }
            endOfPay = endOfPay - months[(month - 1)];
            for (int i = 1; i < endOfPay; i++) {
                for (int j = 0; j < quant; j++) {
                    due = Integer.parseInt(data.getData(j, 1));
                    if (i == due) {
                        cost = Double.valueOf(data.getData(j, 2));
                        ttlbills += cost;
                    }
                }
            }
            //pick up here
            //replacing arrays with data
        } else {
            for (int i = day; i < endOfPay; i++) {

                System.out.println(i);
                for (int j = 0; j < quant; j++) {
                    due = Integer.parseInt(data.getData(j, 1));
                    if (i == due) {
                        cost = Double.valueOf(data.getData(j, 2));
                        ttlbills += cost;
                    }
                }
            }
        }

    }
    public static void addWeekly(){
            quant = data.getWeeklySize();
            weeklyTotal = 0;
            weekCounter = week;
            tempDouble = 0;
            for (int i = 0; i < daysRemain; i++){
                tempStr = weekDay.get(weekCounter);
                for (int j = 0; j < quant; j++){
                    tempStr2 = data.getWeekly(j , 2);
                    if (tempStr2.contains(tempStr)){
                        tempDouble = Double.valueOf(data.getWeekly(j, 1));
                        weeklyTotal = weeklyTotal + tempDouble;
                    }

                }
                weekCounter++;
                weekCounter = weekCounter % 7;
            }



    }

    public static void upNextGen() {
        data.clearIsDue();
        due = 0;
        //begOfPay = day - dayOfPay;
        //endOfPay = begOfPay + 14;
        quant  = data.getSize();
        System.out.println(" ");
        System.out.println("started upNextGen");
        System.out.println(" ");
        //do this if the pay straddles months
        if ((begOfPay + 14) > months[(month - 1)]) {
            for (int i = day; i <= months[(month - 1)]; i++) {
                for (int j = 0; j < quant; j++) {
                    due = Integer.parseInt(data.getData(j, 1));
                    if (i == due) {
                        data.addDue(j);
                    }
                }
            }
            endOfPay = endOfPay - months[(month - 1)];
            for (int i = 1; i < endOfPay; i++) {
                for (int j = 0; j < quant; j++) {
                    due = Integer.parseInt(data.getData(j, 1));
                    if (i == due) {
                        data.addDue(j);
                    }
                }
            }
        } else {
            for (int i = day; i < endOfPay; i++) {
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
        due = 0;
        quant = data.getSize();
        System.out.println(" ");
        System.out.println("Started upAfterGen");
        System.out.println(" ");
        System.out.println("Begnextpay = " +begNextPay);
        System.out.println("EndnextPay = " +endNextPay);
        //Checking if the next pay starts in the next month
        if (begNextPay > months[(month - 1)]) {
            monthNew = month;
            begNextPay = begNextPay - months[month];
            begNextPay++;
            endNextPay = endNextPay - months[month];
            endNextPay++;
            System.out.println("MonthNew: " +monthNew);
        } else {
            //monthNew is an index; month is the counting number month
            monthNew = month - 1;
            System.out.println("MonthNew: " +monthNew);
        }
        if (endNextPay > months[monthNew]) {
            for (int i = begNextPay; i <= months[monthNew]; i++) {
                System.out.println("Comparing day" +i +" from calendar");
                for (int j = 0; j < quant; j++) {
                    due = Integer.parseInt(data.getData(j, 1));
                    System.out.println("Comparing date " +due +" from bills");
                    if (due == i) {
                        data.addAfter(j);
                        System.out.println("Added to After Straddles Month");
                    }
                }
            }
            endNextPay = endNextPay - months[monthNew];
            for (int i = 1; i < endNextPay; i++) {
                System.out.println("Comparing date " +i +"from calendar");
                for (int j = 0; j < quant; j++) {
                    due = Integer.parseInt(data.getData(j, 1));
                    System.out.println("Comparing date " +due +" from bills");
                    if (due == i) {
                        data.addAfter(j);
                        System.out.println("Added to After Straddled Month");
                    }
                }
            }
        } else {
            for (int i = begNextPay; i < endNextPay; i++) {
                System.out.println("Comparing day " +i +"from calendar");
                for (int j = 0; j < quant; j++) {
                    due = Integer.parseInt(data.getData(j, 1));
                    System.out.println("Comparing day " +due +" from due dates");
                    if (due == i) {
                        data.addAfter(j);
                        System.out.println("Added to After Didn't straddle month");
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

        tempTtl = ttlbills + weeklyTotal;
        projBalance = balance - tempTtl;
        System.out.println("Total bills: " +ttlbills);
        System.out.println("Total weekly: " +weeklyTotal);
        System.out.println("TempTtl is " +tempTtl);
        System.out.println("Bank Balance " +balance);
        System.out.println("Projected Balance " +projBalance);
        returned = String.valueOf(projBalance);
        return returned;

    }
}