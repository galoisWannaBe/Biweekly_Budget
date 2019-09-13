package com.example.biweeklybudget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class helpMainActivity extends AppCompatActivity implements helpAdapter.OnHelpListener{

    public static final byte helpMainActivity = 1;
    public static final String TAG = "helpMainActivity";
    ArrayList<HelpItem> helps;
    AllHelp allHelp;
    private RecyclerView mRecyclerView;
    private helpAdapter mHelpAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    int count;
    int id;
    byte tags;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_main);
        helps = new ArrayList<>();
        allHelp = AllHelp.getInstance();
        count = allHelp.helpCount();
        for (int i = 0; i < count; i++){
            byte currentByte = allHelp.getHelpByte(i);
            Log.d(TAG, "currentByte: " +currentByte);
            Log.d(TAG, "currentByte & helpMainActivity: " +(currentByte & helpMainActivity));
            Log.d(TAG, "bool currentByte & helpMainActivity" +((currentByte & helpMainActivity) == (int) helpMainActivity));
            if ((currentByte & helpMainActivity) == helpMainActivity){
                text = allHelp.getHelpLabel(i);
                tags = allHelp.getHelpByte(i);
                id = allHelp.getHelpID(i);
                helps.add(new HelpItem(id, tags, text));
                Log.d(TAG , "Adding: " +allHelp.getHelpLabel(i));
            }
        }
        Log.d(TAG, "Helps contains " +helps.size() +" elements");
        mRecyclerView = findViewById(R.id.help_view);
        mHelpAdapter = new helpAdapter(this);
        mHelpAdapter.setCount(helps.size());
        for (int i = 0; i < helps.size(); i++){
            mHelpAdapter.setHelps(helps.get(i).getText());
        }
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mHelpAdapter);
        


    }
    public void goToMoreHelp(View view){
        Intent intent = new Intent(this, AllHelpActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("origin_class" , "mainActivity");
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void goBack(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void click4(View view) {
        int pos = 3;
        id = helps.get(pos).getID();
        boolean open = mHelpAdapter.isOpen();
        if (open) {
            Log.d(TAG, "Was open");
            mHelpAdapter.clearList();
            for (int i = 0; i < mHelpAdapter.getCount(); i++) {
                mHelpAdapter.addHelpLabel(i);
            }
        }else{
            mHelpAdapter.clearList();
            Log.d(TAG, "was closed");
            for (int i = 0; i < pos; i++){
                mHelpAdapter.addHelpLabel(i);
            }mHelpAdapter.addHelpText(id);
            for (int i = (pos + 1); i < mHelpAdapter.getCount(); i++){
                mHelpAdapter.addHelpLabel(i);
            }
        }
        if (open) {
            mHelpAdapter.setOpen(false);
        }else{
            mHelpAdapter.setOpen(true);
        }
    }
    public void click1(View view){
            int pos = 0;
            id = helps.get(pos).getID();
            boolean open = mHelpAdapter.isOpen();
            if (open) {
                Log.d(TAG, "Was open");
                mHelpAdapter.clearList();
                for (int i = 0; i < mHelpAdapter.getCount(); i++) {
                    mHelpAdapter.addHelpLabel(i);
                }
            }else{
                mHelpAdapter.clearList();
                Log.d(TAG, "was closed");
                for (int i = 0; i < pos; i++){
                    mHelpAdapter.addHelpLabel(i);
                }mHelpAdapter.addHelpText(id);
                for (int i = (pos + 1); i < mHelpAdapter.getCount(); i++){
                    mHelpAdapter.addHelpLabel(i);
                }
            }
            if (open) {
                mHelpAdapter.setOpen(false);
            }else{
                mHelpAdapter.setOpen(true);
            }
    }
    public void click2(View v) {
        int pos = 1;
        id = helps.get(pos).getID();
        boolean open = mHelpAdapter.isOpen();
        if (open) {
            Log.d(TAG, "Was open");
            mHelpAdapter.clearList();
            for (int i = 0; i < mHelpAdapter.getCount(); i++) {
                mHelpAdapter.addHelpLabel(i);
            }
        }else{
            mHelpAdapter.clearList();
            Log.d(TAG, "was closed");
            for (int i = 0; i < pos; i++){
                mHelpAdapter.addHelpLabel(i);
            }mHelpAdapter.addHelpText(0);
            for (int i = (pos + 1); i < mHelpAdapter.getCount(); i++){
                mHelpAdapter.addHelpLabel(i);
            }
        }
        if (open) {
            mHelpAdapter.setOpen(false);
        }else{
            mHelpAdapter.setOpen(true);
        }
    }
    public void click3(View view) {
        int pos = 2;
        id = helps.get(pos).getID();
        boolean open = mHelpAdapter.isOpen();
        if (open) {
            Log.d(TAG, "Was open");
            mHelpAdapter.clearList();
            for (int i = 0; i < mHelpAdapter.getCount(); i++) {
                mHelpAdapter.addHelpLabel(i);
            }
        }else{
            mHelpAdapter.clearList();
            Log.d(TAG, "was closed");
            for (int i = 0; i < pos; i++){
                mHelpAdapter.addHelpLabel(i);
            }mHelpAdapter.addHelpText(0);
            for (int i = (pos + 1); i < mHelpAdapter.getCount(); i++){
                mHelpAdapter.addHelpLabel(i);
            }
        }
        if (open) {
            mHelpAdapter.setOpen(false);
        }else{
            mHelpAdapter.setOpen(true);
        }
    }

    @Override
    public void OnHelpClick(int position) {
        int pos = position;
        id = helps.get(pos).getID();
        Log.d(TAG, "position: " +pos);
        Log.d(TAG, "ID" +id);
        boolean open = mHelpAdapter.isOpen();
        if (open) {
            Log.d(TAG, "Was open");
            mHelpAdapter.clearList();
            for (int i = 0; i < mHelpAdapter.getCount(); i++) {
                mHelpAdapter.addHelpLabel(i);
            }
        }else{
            mHelpAdapter.clearList();
            Log.d(TAG, "was closed");
            for (int i = 0; i < pos; i++){
                mHelpAdapter.addHelpLabel(i);
            }mHelpAdapter.addHelpText(id);
            for (int i = (pos + 1); i < mHelpAdapter.getCount(); i++){
                mHelpAdapter.addHelpLabel(i);
            }
        }
        if (open) {
            mHelpAdapter.setOpen(false);
        }else{
            mHelpAdapter.setOpen(true);
        }
    }
}
