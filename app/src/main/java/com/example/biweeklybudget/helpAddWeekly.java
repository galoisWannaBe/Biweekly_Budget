package com.example.biweeklybudget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Hashtable;

public class helpAddWeekly extends AppCompatActivity {

    public static final byte helpAddWeekly = (byte) R.integer.add_weekly;
    ArrayList<String> helps;
    AllHelp allHelp;
    private RecyclerView mRecyclerView;
    private helpAdapter mHelpAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    int count;
    Hashtable<String, String> priorHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_add_weekly);
        priorHash = new Hashtable<>();
        helps = new ArrayList<>();
        allHelp = AllHelp.getInstance();
        count = allHelp.helpCount();
        for (int i = 0; i < count; i++){
            if ((allHelp.getHelpByte(i) & helpAddWeekly) == helpAddWeekly){
                helps.add(allHelp.getHelpLabel(i));
            }
        }
        mRecyclerView = findViewById(R.id.add_weekly_help_view);
        mHelpAdapter = new helpAdapter();
        mHelpAdapter.setCount(helps.size());
        for (int i = 0; i < helps.size(); i++){
            mHelpAdapter.setHelps(helps.get(i));
        }
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mHelpAdapter);
    }
    public void goToMoreHelp(View view){
        Bundle priorBundle = getIntent().getExtras();
        Intent intent = new Intent(this, AllHelpActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(priorBundle);
        startActivity(intent);
    }
    public void goBack(View view){
        Bundle bundle = getIntent().getExtras();
        Intent intent = new Intent(this, AddWeekly.class);
        //extract and reformat bundle contents for AddWeekly
        priorHash = (Hashtable<String, String>) bundle.getSerializable("prior_hash");
        String temp;
        temp = priorHash.get("origin_class");
        bundle.putString("origin_class", temp);
        temp = priorHash.get("fromList");
        if (temp.equals("true")){
            bundle.putBoolean("fromList" , true);
            temp = priorHash.get("index");
            bundle.putInt("index" , Integer.parseInt(temp));
        }else{
            bundle.putBoolean("fromList" , false);
        }
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
