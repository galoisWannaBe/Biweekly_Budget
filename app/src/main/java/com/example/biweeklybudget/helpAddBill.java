package com.example.biweeklybudget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Hashtable;

public class helpAddBill extends AppCompatActivity implements helpAdapter.OnHelpListener {

    byte Tag = (byte) (0 | R.integer.add_bill);
    public static final byte helpAddBill = (byte) R.integer.add_bill;
    ArrayList<HelpItem> helps;
    AllHelp allHelp;
    private RecyclerView mRecyclerView;
    private helpAdapter mHelpAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    int count;
    int id;
    String text;
    byte tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_add_bill);
        helps = new ArrayList<>();
        allHelp = AllHelp.getInstance();
        count = allHelp.helpCount();
        for (int i = 0; i < count; i++){
            if ((allHelp.getHelpByte(i) & helpAddBill) == helpAddBill){
                text = allHelp.getHelpText(i);
                tags = allHelp.getHelpByte(i);
                id = allHelp.getHelpID(i);
                helps.add(new HelpItem(id, tags, text));
            }
        }
        mRecyclerView = findViewById(R.id.add_bill_help_view);
        mLayoutManager = new LinearLayoutManager(this);
        mHelpAdapter = new helpAdapter(this);
        mHelpAdapter.setCount(helps.size());
        for (int i = 0; i < helps.size(); i++){
            mHelpAdapter.setHelps(helps.get(i).getText());
        }
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mHelpAdapter);
    }

    public void goToMoreHelp(View view){
        Intent priorIntent = getIntent();
        Bundle bundle1 = priorIntent.getExtras();
        Intent intent = new Intent(this, AllHelpActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("origin_class" , "AddToList");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void goBack(View view){
        Intent intent = new Intent(this, AddToList.class);
        Bundle bundle = new Bundle();
        startActivity(intent);
    }

    @Override
    public void OnHelpClick(int position) {
        int pos = position;
        id = helps.get(pos).getID();
        int tempID;
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
