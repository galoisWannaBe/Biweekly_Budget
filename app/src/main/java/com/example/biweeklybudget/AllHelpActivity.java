package com.example.biweeklybudget;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class AllHelpActivity extends AppCompatActivity implements helpAdapter.OnHelpListener {

    ArrayList<HelpItem> helps;
    AllHelp allHelp;
    private RecyclerView mRecyclerView;
    private helpAdapter mHelpAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    int count;
    String originClass = "\0";
    Button btnBack;
    Context context;
    String text;
    byte tags;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_help);
        helps = new ArrayList<>();
        allHelp = AllHelp.getInstance();
        count = allHelp.helpCount();
        context = getApplicationContext();
        for (int i = 0; i < count; i++){
            text = allHelp.getHelpLabel(i);
            tags = allHelp.getHelpByte(i);
            id = allHelp.getHelpID(i);
            helps.add(new HelpItem(id, tags, text));
        }
        mRecyclerView = findViewById(R.id.all_help_view);
        mHelpAdapter = new helpAdapter(this);
        mHelpAdapter.setCount(count);
        for (int i = 0; i < count; i++){
            mHelpAdapter.setHelps(helps.get(i).getText());
        }
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mHelpAdapter);
        Bundle Extras = getIntent().getExtras();
        assert Extras != null;
        originClass = Extras.getString("origin_class", "mainActivity");

        btnBack = findViewById(R.id.button_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
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
                        intent = new Intent(context, AddToList.class);
                    case "AddWeekly":
                        intent = new Intent(context, AddWeekly.class);
                    default:
                        intent = new Intent(context, MainActivity.class);
                        break;
                }
                Bundle bundle = new Bundle();
                bundle.putString("origin_class", originClass);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    @Override
    public void OnHelpClick(int position) {
        int pos = position;
        id = helps.get(pos).getID();
        boolean open = mHelpAdapter.isOpen();
        if (open) {
            mHelpAdapter.clearList();
            for (int i = 0; i < mHelpAdapter.getCount(); i++) {
                mHelpAdapter.addHelpLabel(i);
            }
        }else{
            mHelpAdapter.clearList();
            for (int i = 0; i < pos; i++){
                mHelpAdapter.addHelpLabel(i);
            }mHelpAdapter.addHelpText(id);
            for (int i = (pos + 1); i < mHelpAdapter.getCount(); i++){
                mHelpAdapter.addHelpLabel(i);
            }
        }
        if (open) {
            mHelpAdapter.setOpen(false);
        }else{
            mHelpAdapter.setOpen(true);
        }
    }
}