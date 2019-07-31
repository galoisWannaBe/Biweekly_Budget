package com.example.biweeklybudget;

import android.content.Intent;
import android.net.http.SslCertificate;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

public class WeeklyExpenses extends AppCompatActivity implements weeklyAdapter.OnWeeklyListener {
    private RecyclerView wRecyclerView;
    private  RecyclerView.Adapter wAdapter;
    private RecyclerView.LayoutManager wLayoutManager;
    public final int ADD_REQUEST = 0;
    public final int EDIT_REQUEST = 1;
    public final int RESULT_DELETED = 2;
    weeklyAdapter wAdatper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_expenses);

        wRecyclerView = this.findViewById(R.id.weekliesRecyclerView);
        wAdapter = new com.example.biweeklybudget.weeklyAdapter(this);
        wRecyclerView.setHasFixedSize(true);
        wLayoutManager = new LinearLayoutManager(this);
        wRecyclerView.setLayoutManager(wLayoutManager);
        wRecyclerView.setAdapter(wAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent Data) {
        super.onActivityResult(requestCode, resultCode, Data);
        String label = " ";
        String cost = " ";
        String days = " ";
        int position = 0;

        if(resultCode == RESULT_CANCELED){
            Toast.makeText(
                    getApplicationContext(),
                    "Cancelled",
                    Toast.LENGTH_LONG).show();
        }else if (resultCode == RESULT_OK){
            Bundle bundle = Data.getExtras();
            label = bundle.getString("Label");
            cost = bundle.getString("Cost");
            days = bundle.getString("Days");
        } else{
          label = "\0";
          cost = "\0";
          days = "\0";
        }
        if (requestCode == ADD_REQUEST) {
            if (resultCode == RESULT_OK) {
                data.addWeekly(label, cost, days);
            }
        }else if(requestCode == EDIT_REQUEST){

            if (resultCode == RESULT_OK){
                Bundle bundle = Data.getExtras();
                position = bundle.getInt("position");
                data.addWeekly(label, cost, days, position);
            }else if(resultCode == RESULT_DELETED){
                Bundle bundle = Data.getExtras();
                position = bundle.getInt("position");
                data.removeWeekly(position);
            }
        }
        wAdapter.notifyDataSetChanged();
    }

    public void backToMain(View view){
        Intent mIntent = new Intent(this, MainActivity.class);
        startActivity(mIntent);
    }
    public void goToAddWeeklies(View view){
        Intent nintent = new Intent(this, AddWeekly.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("fromList", false);
        nintent.putExtras(bundle);
        startActivityForResult(nintent, ADD_REQUEST);
    }

    @Override
    public void OnWeeklyClick(int position) {
        Intent intent = new Intent(this, AddWeekly.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("fromList", true);
        bundle.putInt("position", position);
        intent.putExtras(bundle);
        startActivityForResult(intent, EDIT_REQUEST);
    }
}
