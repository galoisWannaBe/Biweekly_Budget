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

public class helpAddBill extends AppCompatActivity {

    byte Tag = (byte) (0 | R.integer.add_bill);
    public static final byte helpAddBill = (byte) R.integer.add_bill;
    ArrayList<String> helps;
    AllHelp allHelp;
    private RecyclerView mRecyclerView;
    private helpAdapter mHelpAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    int count;
    Hashtable<String, String> priorBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_add_bill);
        priorBundle = new Hashtable<>();
        helps = new ArrayList<>();
        allHelp = AllHelp.getInstance();
        count = allHelp.helpCount();
        for (int i = 0; i < count; i++){
            if ((allHelp.getHelpByte(i) & helpAddBill) == helpAddBill){
                helps.add(allHelp.getHelpLabel(i));
            }
        }
        mRecyclerView = findViewById(R.id.add_bill_help_view);
        mLayoutManager = new LinearLayoutManager(this);
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
        Intent priorIntent = getIntent();
        Bundle bundle1 = priorIntent.getExtras();
        Intent intent = new Intent(this, AllHelpActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("origin_class" , "AddToList");
        bundle.putSerializable("prior_bundle" , priorBundle);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    // TODO: 9/9/19 Finish dragging from helpAddBill through allHelpActivity; do same with helpAddWeekly 
    public void goBack(View view){
        Intent intent = new Intent(this, AddToList.class);
        Bundle bundle = new Bundle();
        startActivity(intent);
    }
}
