package com.example.biweeklybudget;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class upAfter extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static int q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_after);
        //helps distinguish between an add and an edit

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new upAfterAdapter();
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);



    }
    public void goToAdd(View view) {

        Intent nIntent = new Intent(upAfter.this, AddToList.class);
        startActivity(nIntent);
    }


    public void gotoMain(View view){
        Intent mIntent = new Intent(upAfter.this, MainActivity.class);
        startActivity(mIntent);
    }
    public void gotoUpNext(View view){
        Intent nIntent = new Intent(upAfter.this, upNext.class);
        startActivity(nIntent);
    }    public void gotoViewAll(View view){
        Intent vIntent = new Intent(upAfter.this, viewAll.class);
        startActivity(vIntent);
    }

}
