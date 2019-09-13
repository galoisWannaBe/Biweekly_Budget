package com.example.biweeklybudget;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.Hashtable;

public class helpAddWeekly extends AppCompatActivity implements helpAdapter.OnHelpListener {

    public static final byte helpAddWeekly = (byte) R.integer.add_weekly;
    ArrayList<HelpItem> helps;
    AllHelp allHelp;
    private RecyclerView mRecyclerView;
    private helpAdapter mHelpAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    int count;
    Bundle extras;
    int id;
    byte tags;
    String text;
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
                text = allHelp.getHelpLabel(i);
                tags = allHelp.getHelpByte(i);
                id = allHelp.getHelpID(i);
                helps.add(new HelpItem(id, tags, text));
            }
        }
        mRecyclerView = findViewById(R.id.add_weekly_help_view);
        mHelpAdapter = new helpAdapter(this);
        mHelpAdapter.setCount(helps.size());
        for (int i = 0; i < helps.size(); i++){
            mHelpAdapter.setHelps(helps.get(i).getText());
        }
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mHelpAdapter);
    }

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

    @Override
    public void OnHelpClick(int position) {
        int pos = position;
        id = helps.get(pos).getID();
        boolean open = mHelpAdapter.isOpen();
        if (open) {
            mHelpAdapter.clearList();
            for (int i = 0; i < mHelpAdapter.getCount(); i++) {
                mHelpAdapter.addHelpLabel(helps.get(i).getID());
            }
        }else{
            mHelpAdapter.clearList();
            for (int i = 0; i < pos; i++){
                mHelpAdapter.addHelpLabel(helps.get(i).getID());
            }mHelpAdapter.addHelpText(id);
            for (int i = (pos + 1); i < mHelpAdapter.getCount(); i++){
                mHelpAdapter.addHelpLabel(helps.get(i).getID());
            }
        }
        if (open) {
            mHelpAdapter.setOpen(false);
        }else{
            mHelpAdapter.setOpen(true);
        }
    }
}
