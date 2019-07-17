package com.example.biweeklybudget;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class upNext extends AppCompatActivity implements upNextAdapter.OnBillListener{

    private RecyclerView nRecyclerView;
    private RecyclerView.Adapter nAdapter;
    private RecyclerView.LayoutManager nLayoutManager;
    public static int q;
    public String label;
    public String due;
    public String cost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_next);

        //helps distinguish between an add and an edit

        nRecyclerView = findViewById(R.id.recyclerView);
        nRecyclerView.setHasFixedSize(true);
        nLayoutManager = new LinearLayoutManager(this);
        nAdapter = new upNextAdapter();
        nRecyclerView.setLayoutManager(nLayoutManager);
        nRecyclerView.setAdapter(nAdapter);


    }
    public void goToAdd(View view) {
    }

    public void gotoMain(View view){
        Intent mIntent = new Intent(this, MainActivity.class);
        startActivity(mIntent);
    }
    public void gotoUpAfter(View view){
        Intent nIntent = new Intent(this, upAfter.class);
        startActivity(nIntent);
    }    public void gotoviewAll(View view){
        Intent vIntent = new Intent(this, viewAll.class);
        startActivity(vIntent);
    }

    @Override
    public void OnBillClick(int position) {
        Intent intent = new Intent(this, AddToList.class);
        Bundle bundle = new Bundle();
        //finish onBillClick and set intents/REQUESTS/etc
    }
}
