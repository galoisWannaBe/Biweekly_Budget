package com.example.biweeklybudget;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

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
    Bundle extras;
    int id;
    boolean fromList;
    String priorOrigin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_add_weekly);
        extras = getIntent().getExtras();
        assert extras != null;
        fromList = extras.getBoolean("fromList" , false);
        if (fromList){
            id = extras.getInt("ID");
        }
        priorOrigin = extras.getString("origin_class" , "MainActivity");
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
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        Bundle extras = data.getExtras();
        Intent intent;
        assert extras != null;
        priorOrigin = extras.getString("prior_origin");
        switch (priorOrigin) {
            case "mainActivity":
            default:
                intent = new Intent(this, MainActivity.class);
                break;
            case "WeeklyExpenses":
                intent = new Intent(this, WeeklyExpenses.class);
                break;
        }
                intent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                setResult(RESULT_OK, intent);
                startActivity(intent);
        Log.d("addHelpWeekly", "Got to finish but didn't finish");
    }
 */

    public void goToMoreHelp(View view){
        Intent intent = new Intent(this, AllHelpActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("origin_class" , "AddWeekly");
        if (fromList){
            bundle.putBoolean("fromList" , true);
            bundle.putInt("ID" , id);
        }else{
            bundle.putBoolean("fromList" , false);
        }
        bundle.putString("prior_origin" , priorOrigin);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        startActivity(intent);
    }
    public void goBack(View view){
        // TODO: 9/10/19 Make this work both before and after allHelpActivity
        Intent intent = new Intent(this, AddWeekly.class);
        //extract and reformat bundle contents for AddWeekly
        Bundle bundle = new Bundle();
        bundle.putString("origin_class" , priorOrigin);
        bundle.putBoolean("fromList" , fromList);
        intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
