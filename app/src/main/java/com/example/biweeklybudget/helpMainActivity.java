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

public class helpMainActivity extends AppCompatActivity {

    public static final byte helpMainActivity = (byte) R.integer.main_activity;
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
    Button btnTest;
    Button btnTest1;
    Button btnTest2;
    Button btnTest3;
    int tempPos;
    // TODO: 9/11/19 Finish converting list for ids to load from master list
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_main);
        helps = new ArrayList<>();
        allHelp = AllHelp.getInstance();
        count = allHelp.helpCount();
        for (int i = 0; i < count; i++){
            if ((allHelp.getHelpByte(i) & helpMainActivity) == helpMainActivity){
                text = allHelp.getHelpLabel(i);
                tags = allHelp.getHelpByte(i);
                id = allHelp.getHelpID(i);
                helps.add(new HelpItem(id, tags, text));
                Log.d(TAG , "Adding: " +allHelp.getHelpLabel(i));
            }
        }
        Log.d(TAG, "Helps contains " +helps.size() +" elements");
        mRecyclerView = findViewById(R.id.help_view);
        mHelpAdapter = new helpAdapter();
        mHelpAdapter.setCount(helps.size());
        for (int i = 0; i < helps.size(); i++){
            mHelpAdapter.setHelps(helps.get(i).getText());
        }
        //mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mHelpAdapter);
        
        btnTest = findViewById(R.id.button_test_3);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = helps.get(3).getID();
                boolean open = mHelpAdapter.isOpen();
                    if (open) {
                        mHelpAdapter.clearList();
                        for (int i = 0; i < mHelpAdapter.getCount(); i++) {
                            mHelpAdapter.addHelpLabel(i);
                            mHelpAdapter.setOpen(false);
                        }
                    }else{
                        for (int i = 0; i < 3; i++){
                            mHelpAdapter.addHelpLabel(i);
                        }mHelpAdapter.addHelpText(3);
                        for (int i = (3 + 1); i < mHelpAdapter.getCount(); i++){
                            mHelpAdapter.addHelpText(i);
                        }
                    }mHelpAdapter.setOpen(true);
                    mHelpAdapter.printList();
                }

        });
        btnTest1 = findViewById(R.id.button_test_0);
        btnTest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = helps.get(0).getID();
                boolean open = mHelpAdapter.isOpen();
                if (open) {
                    mHelpAdapter.clearList();
                    for (int i = 0; i < mHelpAdapter.getCount(); i++) {
                        mHelpAdapter.addHelpLabel(i);
                        mHelpAdapter.setOpen(false);
                    }
                }else{
                    for (int i = 0; i < 0; i++){
                        mHelpAdapter.addHelpLabel(i);
                    }mHelpAdapter.addHelpText(0);
                    for (int i = (0 + 1); i < mHelpAdapter.getCount(); i++){
                        mHelpAdapter.addHelpText(i);
                    }
                }mHelpAdapter.setOpen(true);
                mHelpAdapter.printList();
                }

        });
        btnTest2 = findViewById(R.id.button_test_1);
        btnTest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = helps.get(1).getID();
                boolean open = mHelpAdapter.isOpen();
                if (open) {
                    mHelpAdapter.clearList();
                    for (int i = 0; i < mHelpAdapter.getCount(); i++) {
                        mHelpAdapter.addHelpLabel(i);
                        mHelpAdapter.setOpen(false);
                    }
                }else{
                    for (int i = 0; i < 1; i++){
                        mHelpAdapter.addHelpLabel(i);
                    }mHelpAdapter.addHelpText(1);
                    for (int i = (1 + 1); i < mHelpAdapter.getCount(); i++){
                        mHelpAdapter.addHelpText(i);
                    }
                }mHelpAdapter.setOpen(true);
                mHelpAdapter.printList();
            }
        });
        btnTest3 = findViewById(R.id.button_test_2);
        btnTest3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = helps.get(2).getID();
                boolean open = mHelpAdapter.isOpen();
                if (open) {
                    mHelpAdapter.clearList();
                    for (int i = 0; i < mHelpAdapter.getCount(); i++) {
                        mHelpAdapter.addHelpLabel(i);
                        mHelpAdapter.setOpen(false);
                    }
                }else{
                    for (int i = 0; i < 2; i++){
                        mHelpAdapter.addHelpLabel(i);
                    }mHelpAdapter.addHelpText(2);
                    for (int i = (2 + 1); i < mHelpAdapter.getCount(); i++){
                        mHelpAdapter.addHelpText(i);
                    }
                }mHelpAdapter.setOpen(true);
                mHelpAdapter.printList();
            }
        });


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
    //force reinstall
}