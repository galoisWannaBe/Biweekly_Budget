package com.example.biweeklybudget;

import android.content.Intent;
import android.net.http.SslCertificate;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class WeeklyExpenses extends AppCompatActivity {
    private RecyclerView wRecyclerView;
    private  RecyclerView.Adapter wAdapter;
    private RecyclerView.LayoutManager wLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_expenses);
        wRecyclerView = this.findViewById(R.id.weekliesRecyclerView);
        wRecyclerView.setHasFixedSize(true);
        wLayoutManager = new LinearLayoutManager(this);
        wAdapter = new com.example.biweeklybudget.weeklyAdapter();
        wRecyclerView.setLayoutManager(wLayoutManager);
        wRecyclerView.setAdapter(wAdapter);

    }
    public void backToMain(View view){
        Intent mIntent = new Intent(this, MainActivity.class);
        startActivity(mIntent);
    }
    public void goToAddWeeklies(View view){
        Intent nintent = new Intent(this, AddWeekly.class);
        startActivity(nintent);
    }
    
}
