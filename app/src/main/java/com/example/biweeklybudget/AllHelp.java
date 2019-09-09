package com.example.biweeklybudget;

import android.util.Log;

import java.util.ArrayList;

public class AllHelp {

    private ArrayList<HelpItem> Helps = new ArrayList<>();
    private byte currentTags;
    private String helpLabel;
    int mainSize;

    private static final AllHelp INSTANCE = new AllHelp();
    private static final String TAG = "AllHelp";

    public static AllHelp getInstance(){
        return INSTANCE;
    }

    private AllHelp() {
        //Explain Calculate
        currentTags = (byte) R.integer.no_tags;
        currentTags |= R.integer.main_activity;
        Log.d(TAG, "tags are " +currentTags);
        Helps.add(new HelpItem(0, currentTags, "How to calculate your expected balance"));

        //Explain Viewing Lists
        currentTags = (byte) R.integer.no_tags;
        currentTags |= R.integer.main_activity;
        currentTags |= R.integer.lists;

        Log.d(TAG, "tags are " +currentTags);
        Helps.add(new HelpItem(1, currentTags, "How to view lists"));

        //Explain how to add items
        currentTags = (byte) R.integer.no_tags;
        currentTags |= R.integer.main_activity;
        Log.d(TAG, "tags are " +currentTags);
        Helps.add(new HelpItem(2, currentTags, "How to add bills and weekly expenses"));

        //Explains changing payday
        currentTags = (byte) R.integer.no_tags;
        currentTags |= R.integer.main_activity;
        Log.d(TAG, "tags are " +currentTags);
        Helps.add(new HelpItem(3, currentTags, "Change you're pay day"));

        //Explain choosing a payperiod
        currentTags = (byte) R.integer.no_tags;
        currentTags |= R.integer.settings;
        Helps.add(new HelpItem(4, currentTags, "Setting your pay period information"));

        //Explain list views
        currentTags = (byte) R.integer.no_tags;
        currentTags |= R.integer.lists;
        Helps.add(new HelpItem(5, currentTags, "Editing and deleting your expenses"));

        //Explain the totals
        currentTags = (byte) R.integer.no_tags;
        currentTags |= (byte) R.integer.lists;
        Helps.add(new HelpItem(6, currentTags, "Explanation for totals"));

        //Explain Label
        currentTags = (byte) R.integer.no_tags;
        currentTags |= R.integer.add_bill;
        currentTags |= R.integer.add_weekly;
        Helps.add(new HelpItem(7, currentTags, "What's a label?"));

        //Explain Due date limitations

        currentTags = (byte) R.integer.no_tags;
        currentTags |= R.integer.add_bill;
        Helps.add(new HelpItem(8, currentTags, "Explanation of due dates"));

        //Explain costs... because why not
        currentTags = (byte) R.integer.no_tags;
        currentTags |= R.integer.add_bill;
        currentTags |= R.integer.add_weekly;
        Helps.add(new HelpItem(9, currentTags, "Cost?"));

        //Explain days... literally just for completeness
        currentTags = (byte) R.integer.no_tags;
        currentTags |= R.integer.add_weekly;
        Helps.add(new HelpItem(10, currentTags, "Days"));
    }
    public int helpCount(){
        Log.d(TAG, "counted Help");
        return Helps.size();
    }
    public int getHelpID(int index){
        return Helps.get(index).getID();
    }
    public byte getHelpByte(int index){
        return Helps.get(index).getTags();
    }

    public String getHelpLabel(int index){
        return Helps.get(index).getText();
    }
    /*
    public void setMainSize(int size){
        mainSize = size;
    }

     */
}
