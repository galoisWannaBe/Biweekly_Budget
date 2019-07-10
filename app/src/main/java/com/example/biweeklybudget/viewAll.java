package com.example.biweeklybudget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class viewAll extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static int q;
    public static boolean upDated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new com.example.biweeklybudget.viewAllAdapter();
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


    }
    public void goToAdd(View view) {
        AddToList.resetFromList();
        AddToList.setList(1);
        Intent nIntent = new Intent(viewAll.this, AddToList.class);
        startActivity(nIntent);
    }

    public static void update(){
        upDated = true;
    }

    public void gotoMain(View view){
        Intent mIntent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(mIntent);
    }
    public void gotoUpNext(View view){
        Intent nIntent = new Intent(getBaseContext(), upNext.class);
        startActivity(nIntent);
    }    public void gotoviewAll(View view){
        Intent vIntent = new Intent(getBaseContext(), viewAll.class);
        startActivity(vIntent);
    }
}
