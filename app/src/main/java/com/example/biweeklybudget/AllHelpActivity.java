package com.example.biweeklybudget;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class AllHelpActivity extends AppCompatActivity{

    ArrayList<HelpItem> helps;
    AllHelp allHelp;
    private RecyclerView mRecyclerView;
    private helpAdapter mHelpAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    int count;
    String originClass = "\0";
    Button btnBack;
    Context context;
    String text;
    byte tags;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_help);
        helps = new ArrayList<>();
        allHelp = AllHelp.getInstance();
        count = allHelp.helpCount();
        context = getApplicationContext();
        for (int i = 0; i < count; i++){
            text = allHelp.getHelpLabel(i);
            tags = allHelp.getHelpByte(i);
            id = allHelp.getHelpID(i);
            helps.add(new HelpItem(id, tags, text));
        }
        mRecyclerView = findViewById(R.id.all_help_view);
        //the new OnHelpListener is garbage code so I can run my app without deleting the class
        mHelpAdapter = new helpAdapter(new helpAdapter.OnHelpListener() {
            @Override
            public void OnHelpClick(int position) {

            }
        });
        mHelpAdapter.setCount(count);
        for (int i = 0; i < count; i++){
            mHelpAdapter.setHelps(helps.get(i).getText());
        }
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mHelpAdapter);
        Bundle Extras = getIntent().getExtras();
        assert Extras != null;
        originClass = Extras.getString("origin_class", "mainActivity");

        btnBack = findViewById(R.id.button_back);
    }
}