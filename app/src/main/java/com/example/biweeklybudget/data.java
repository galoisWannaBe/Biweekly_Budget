package com.example.biweeklybudget;
import java.util.ArrayList;
import java.util.Collection;

public class data {

    public static ArrayList<BillItem> toSort = new ArrayList<>();
    public static ArrayList<BillItem> isSort = new ArrayList<>();
    public static ArrayList<Integer> isDue = new ArrayList<>();
    public static ArrayList<Integer> isAfter = new ArrayList<>();
    public static String temp;
    public static String temp2;
    public static String temp3;
    public static int i = 0;
    public static int index = 0;
    public static int indexTwo = 0;
    public static int size;
    public static int tempInt;
    public static int tempInt2;
    public static int nextID = 0;
    public static ArrayList<Integer> unusedID = new ArrayList<>();
    public static int tempID;
    public static ArrayList<Integer> posIDDue = new ArrayList<>();
    public static ArrayList<Integer> posIDAfter = new ArrayList<>();
    public static ArrayList<weeklyItems> weeklies = new ArrayList<>();


    public static void addItem(String tempStr1, String tempStr2, String tempStr3) {

        temp = tempStr1;
        temp2 = tempStr2;
        temp3 = tempStr3;
        toSort.add(new BillItem(temp, temp2, temp3, nextID));
        nextID++;
        //now sort
        tempInt = 1000;
        i = 0;
        index = 0;
        size = toSort.size();
        while (size > 0) {
            while (i < size) {
                tempInt2 = Integer.parseInt(toSort.get(i).getText2());
                if (tempInt2 < tempInt) {
                    tempInt = tempInt2;
                    index = i;
                }
                i++;
            }
            temp = toSort.get(index).getText1();
            temp2 = toSort.get(index).getText2();
            temp3 = toSort.get(index).getText3();
            tempID = toSort.get(index).getId();
            isSort.add(new BillItem(temp, temp2, temp3, tempID));
            toSort.remove(index);
            tempInt = 1000;
            index = 0;
            size = toSort.size();

            i = 0;
        }
        //switch the list to the original list
        size = isSort.size();
        for (int j = 0; j < size; j++) {
            temp = isSort.get(j).getText1();
            temp2 = isSort.get(j).getText2();
            temp3 = isSort.get(j).getText3();
            tempID = isSort.get(i).getId();
            toSort.add(new BillItem(temp, temp2, temp3, tempID));
        }
        //get isSort ready for next time
        isSort.clear();
    }

    public static void addItem(String tempStr1, String tempStr2, String tempStr3, int rIndex) {
        //Remove the entry before it becomes a duplicate entry and is shuffled in
        indexTwo = rIndex;
        toSort.remove(rIndex);
        temp = tempStr1;
        temp2 = tempStr2;
        temp3 = tempStr3;
        //add ability to get IDs from due and after lists
        tempID = rIndex;
        toSort.add(new BillItem(temp, temp2, temp3, tempID));
        //now sort
        tempInt = 1000;
        i = 0;
        index = 0;
        size = toSort.size();
        while (size > 0) {
            while (i < size) {
                tempInt2 = Integer.parseInt(toSort.get(i).getText2());
                if (tempInt2 < tempInt) {
                    tempInt = tempInt2;
                    index = i;
                }
                i++;
            }
            temp = toSort.get(index).getText1();
            temp2 = toSort.get(index).getText2();
            temp3 = toSort.get(index).getText3();
            tempID = toSort.get(index).getId();
            isSort.add(new BillItem(temp, temp2, temp3, tempID));
            toSort.remove(index);
            tempInt = 1000;
            index = 0;
            size = toSort.size();

            i = 0;
        }
        //move list to initial list
        size = isSort.size();

        for (int j = 0; j < size; j++) {
            temp = isSort.get(j).getText1();
            temp2 = isSort.get(j).getText2();
            temp3 = isSort.get(j).getText3();
            tempID = isSort.get(j).getId();
            toSort.add(new BillItem(temp, temp2, temp3, tempID));
        }
        //get isSort ready for next use
        isSort.clear();
    }

    public static void removeItem(int position) {
        //update to allow delete from due and after lists
        toSort.remove(position);
    }

    public static int getSize() {
        tempInt = toSort.size();
        int lnm = 0;
        while (lnm < tempInt) {
            System.out.println(toSort.get(lnm).getText1() + " " + toSort.get(lnm) + " " + toSort.get(lnm));
            lnm++;
        }
        return tempInt;
    }

    public static String getData(int a, int b) {
        //index is the position within the lists
        //indexTwo is which of the items in BillItem are chosen
        index = a;
        indexTwo = b;
        switch (indexTwo) {
            case 0:
                return toSort.get(index).getText1();
            case 1:
                return toSort.get(index).getText2();
            case 2:
                return toSort.get(index).getText3();
            default:
                return "default";
        }

    }
    public static int getIDByPosition(int pos){
        return toSort.get(pos).getId();
    }

    public static void addDue(int identifier) {
        isDue.add(identifier);
    }

    public static String getDue(int a, int b) {
        index = a;
        indexTwo = b;
        switch (indexTwo) {
            case 0:
                return toSort.get(isDue.get(index)).getText1();
            case 1:
                return toSort.get(isDue.get(index)).getText2();
            case 2:
                return toSort.get(isDue.get(index)).getText3();
            default:
                return "default";
        }
    }

    public static int findDue(int pos) {
        return isDue.get(pos);
    }

    public static int dueSize() {
        return isDue.size();
    }

    public static int afterSize() {
        return isAfter.size();
    }

    public static int findAfter(int pos) {
        return isAfter.get(pos);
    }

    public static String getAfter(int a, int b) {
        index = a;
        indexTwo = b;
        switch (indexTwo) {
            case 0:
                return toSort.get(isAfter.get(index)).getText1();
            case 1:
                return toSort.get(isAfter.get(index)).getText2();
            case 2:
                return toSort.get(isAfter.get(index)).getText3();
            default:
                return "default";
        }
    }

    public static void addAfter(int identifier) {
        isAfter.add(identifier);
    }

    public static void clearIsDue() {
        size = isDue.size();
        while (size > 0) {
            isDue.remove(0);
            size = isDue.size();
        }
    }

    public static void clearIsAfter() {
        size = isAfter.size();
        while (size > 0) {
            isAfter.remove(0);
            size = isAfter.size();
        }
    }
    //toSort.add(new BillItem(temp, temp2, temp3, tempID));

    public static void addWeekly(String label, String cost, String day) {
        temp = label;
        temp2 = cost;
        temp3 = day;
        weeklies.add(new weeklyItems(temp, temp2, temp3));
        int q = weeklies.size();
        q--;
    }
    public static void addWeekly(String label, String cost, String day, int rIndex){
        temp = label;
        temp2 = cost;
        temp3 = day;
        weeklies.remove(rIndex);
        weeklies.add(new weeklyItems(temp, temp2, temp3));
    }

    public static void removeWeekly(int a) {
        weeklies.remove(a);
    }

    public static String getWeekly(int a, int b) {
        index = a;
        indexTwo = b;
        //System.out.println("Getting weekly item at :" +index +", " +indexTwo);
        switch (indexTwo) {
            case 0:
                return weeklies.get(index).getLabel();
            case 1:
                return weeklies.get(index).getCost();
            case 2:
                return weeklies.get(index).getDays();
            default:
                return "default";
        }

    }
    public static int getWeeklySize(){
        return weeklies.size();
    }
}