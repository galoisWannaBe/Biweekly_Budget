package com.example.biweeklybudget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Hashtable;

public class AllHelpActivity extends AppCompatActivity {

    ArrayList<String> helps;
    AllHelp allHelp;
    private RecyclerView mRecyclerView;
    private helpAdapter mHelpAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    int count;
    String originClass;
    Button btnBack;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_help);
        helps = new ArrayList<>();
        allHelp = AllHelp.getInstance();
        count = allHelp.helpCount();
        context = getApplicationContext();
        for (int i = 0; i < count; i++){
            helps.add(allHelp.getHelpLabel(i));
        }
        mRecyclerView = findViewById(R.id.all_help_view);
        mHelpAdapter = new helpAdapter();
        mHelpAdapter.setCount(count);
        for (int i = 0; i < count; i++){
            mHelpAdapter.setHelps(helps.get(i));
        }
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mHelpAdapter);

        btnBack = findViewById(R.id.button_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                Bundle bundle = new Bundle();
                bundle.putString("origin_class", originClass);
                Bundle extraBundle = getIntent().getExtras();
                Bundle priorBundle;
                Hashtable<String, String> priorHash;
                originClass = extraBundle.getString("origin_class");
                switch (originClass) {
                    case "mainActivity":
                        intent = new Intent(context, helpMainActivity.class);
                        break;
                    case "upNext":
                    case "upAfter":
                    case "viewAll":
                    case "WeeklyExpenses":
                        intent = new Intent(context, viewsHelp.class);
                        break;
                    case "AddToList":
                        priorBundle = getIntent().getExtras();
                        priorHash = (Hashtable<String, String>) priorBundle.getSerializable("prior_bundle");
                        intent = new Intent(context, AddToList.class);
                        bundle.putSerializable("prior_bundle" , priorHash);
                        break;
                    case "AddWeekly":
                        priorBundle = getIntent().getExtras();
                        priorHash = (Hashtable<String, String>) priorBundle.getSerializable("prior_bundle");
                        intent = new Intent(context, AddWeekly.class);
                        bundle.putSerializable("prior_hash" , priorHash);
                        break;
                    default:
                        intent = new Intent(context, MainActivity.class);
                        break;
                }
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}