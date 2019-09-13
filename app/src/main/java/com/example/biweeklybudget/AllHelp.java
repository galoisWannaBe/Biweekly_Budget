package com.example.biweeklybudget;

import android.util.Log;

import java.util.ArrayList;

public class AllHelp {

    public final static byte NO_TAGS = 0;
    public final static byte MAIN_ACTIVITY = 1;
    public final static byte SETTINGS = 2;
    public final static byte LISTS = 4;
    public final static byte ADD_BILL = 8;
    public final static byte ADD_WEEKLY = 16;


    private ArrayList<HelpItem> Helps = new ArrayList<>();
    private byte currentTags;
    private String helpLabel;
    private ArrayList<String> HelpText = new ArrayList<>();
    int mainSize;

    private static final AllHelp INSTANCE = new AllHelp();
    private static final String TAG = "AllHelp";

    public static AllHelp getInstance(){
        return INSTANCE;
    }

    private AllHelp() {
        //Explain Calculate
        currentTags = (byte) NO_TAGS;
        currentTags |= MAIN_ACTIVITY;
        Log.d(TAG, "tags are " +currentTags);
        Helps.add(new HelpItem(0, currentTags, "How to calculate your expected balance"));

        //Explain Viewing Lists
        currentTags = NO_TAGS;
        currentTags |= MAIN_ACTIVITY;
        currentTags |= LISTS;

        Log.d(TAG, "tags are " +currentTags);
        Helps.add(new HelpItem(1, currentTags, "How to view lists"));

        //Explain how to add items
        currentTags = NO_TAGS;
        currentTags |= MAIN_ACTIVITY;
        currentTags |= LISTS;
        Log.d(TAG, "tags are " +currentTags);
        Helps.add(new HelpItem(2, currentTags, "How to add bills and weekly expenses"));

        //Explains changing payday
        currentTags = NO_TAGS;
        currentTags |= MAIN_ACTIVITY;
        Log.d(TAG, "tags are " +currentTags);
        Helps.add(new HelpItem(3, currentTags, "Change your pay day"));

        //Explain choosing a payperiod
        currentTags = NO_TAGS;
        currentTags |= MAIN_ACTIVITY;
        Helps.add(new HelpItem(4, currentTags, "Setting your pay period information"));

        //Explain list views
        currentTags = (byte) NO_TAGS;
        currentTags |= LISTS;
        Helps.add(new HelpItem(5, currentTags, "Editing and deleting your expenses"));

        //Explain the totals
        currentTags = NO_TAGS;
        currentTags |= LISTS;
        Helps.add(new HelpItem(6, currentTags, "Explanation for totals"));

        //Explain Label
        currentTags = NO_TAGS;
        currentTags |= ADD_BILL;
        currentTags |= ADD_WEEKLY;
        Helps.add(new HelpItem(7, currentTags, "What's a label?"));

        //Explain Due date limitations

        currentTags = NO_TAGS;
        currentTags |= ADD_BILL;
        Helps.add(new HelpItem(8, currentTags, "Explanation of due dates"));

        //Explain costs... because why not
        currentTags = NO_TAGS;
        currentTags |= ADD_BILL;
        currentTags |= ADD_WEEKLY;
        Helps.add(new HelpItem(9, currentTags, "Cost?"));

        //Explain days... literally just for completeness
        currentTags = NO_TAGS;
        currentTags |= ADD_WEEKLY;
        Helps.add(new HelpItem(10, currentTags, "Days?"));

        HelpText.add(Helps.get(0).getText() +":\nEnter your current balance into the current balance text box");
        HelpText.add(Helps.get(1).getText() +":\nView Lists by tapping the Upcoming , View All, or Weekly buttons, or if you're viewing the upcoming bills, tap the Up After button");
        HelpText.add(Helps.get(2).getText() +":\nTo add bills and weekly expenses, tap the Add Bill button to add bills or the Add Weekly button to add weekly expenses");
        HelpText.add(Helps.get(3).getText() +":\nTo change your payday, tap the change payday button then select a day that you were paid, and tap save");
        HelpText.add(Helps.get(4).getText() +":\nTo set your pay period information, tap a day you were paid to select it, then tap save");
        HelpText.add(Helps.get(5).getText() +":\nTo edit or delete an expense, tap the item you want to change, then either make the changes you want to make and tap save to save or tap delete to just delete the item");
        HelpText.add(Helps.get(6).getText() +":\nThe total under the lists of bills is the total cost of that list. The upper total under the weekly list displays the total your weekly expenses are going to cost until the end of the pay period and the bottom total is the total your weekly expenses cost for an entire pay period.");
        HelpText.add(Helps.get(7).getText() +":\nJust call the bill whatever you like... although I recommend something you'll remember and something that you can explain to someone who may come into contact with your list");
        HelpText.add(Helps.get(8).getText() +":\nDue date the day of the month your bills are due; the due date field only accepts numbers from 1 - 28, as most bills fall between those dates");
        HelpText.add(Helps.get(9).getText() +"\nCost!");
        HelpText.add(Helps.get(10).getText() +":\nCheck 'em!");
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
        Log.d(TAG, "index" +index);
        return Helps.get(index).getText();
    }
    /*
    public void setMainSize(int size){
        mainSize = size;
    }

     */
    public String getHelpText(int id){
        Log.d(TAG, "id" +id);
        return HelpText.get(id);
    }

}
