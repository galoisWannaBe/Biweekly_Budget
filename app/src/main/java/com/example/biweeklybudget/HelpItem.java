package com.example.biweeklybudget;

public class HelpItem {

    private byte tags;
    private String text;
    private int ID;

    public HelpItem(int ID, byte tags, String text) {
        this.ID = ID;
        this.tags = tags;
        this.text = text;
    }
    public int getID() {
        return ID;
    }

    public byte getTags() {
        return tags;
    }

    public String getText() {
        return text;
    }
}