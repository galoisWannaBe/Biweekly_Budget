package com.example.biweeklybudget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class viewsHelp extends AppCompatActivity {

    byte Tag = (byte) (0 | R.integer.lists);
    public static final byte helpLists = (byte) R.integer.lists;
    ArrayList<String> helps;
    AllHelp allHelp;
    private RecyclerView mRecyclerView;
    private helpAdapter mHelpadapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String originClass;
    Context context;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_views_help);
        Bundle bundle = getIntent().getExtras();
        originClass = bundle.getString("origin_class" , "mainActivity");
        helps = new ArrayList<>();
        allHelp = AllHelp.getInstance();
        count = allHelp.helpCount();
        context = getApplicationContext();
        for (int i = 0; i < count; i++){
            if ((allHelp.getHelpByte(i) & helpLists) == helpLists){
                helps.add(allHelp.getHelpLabel(i));
            }
        }
        mRecyclerView = findViewById(R.id.view_help_view);
        mHelpadapter = new helpAdapter();
        mHelpadapter.setCount(helps.size());
        for (int i = 0; i < helps.size(); i++){
            mHelpadapter.setHelps(helps.get(i));
        }
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mHelpadapter);
    }
    public void goToMoreHelp(View view){
        Intent intent = new Intent(this, AllHelpActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("origin_class" , originClass);
        startActivity(intent);
    }
    public void goBack(View view){
        Intent intent;
        switch (originClass){
            case "upNext":
                intent = new Intent(context, upNext.class);
                break;
            case "upAfter":
                intent = new Intent(context, upAfter.class);
                break;
            case "viewAll":
                intent = new Intent(context, viewAll.class);
                break;
            case "WeeklyExpenses":
                intent = new Intent(context, WeeklyExpenses.class);
                break;
            default:
                intent = new Intent(context, MainActivity.class);
                break;
        }
        startActivity(intent);
    }
}
