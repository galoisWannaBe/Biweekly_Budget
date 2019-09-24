package com.example.biweeklybudget;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.Hashtable;

public class helpAddWeekly extends AppCompatActivity implements helpAdapter.OnHelpListener {

    public static final byte helpAddWeekly = 16;
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

    public final static String TAG = "helpAddWeekly";
    public final int ADD_REQUEST = 0;
    public final int EDIT_REQUEST = 1;
    public final int RESULT_DELETED = 2;

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
        intent.putExtras(bundle);
        startActivityForResult(intent, ADD_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        if (requestCode == ADD_REQUEST) {
            if (resultCode == RESULT_OK) {
                extras = data.getExtras();
                String label = extras.getString("Label");
                double cost = extras.getDouble("Cost");
                byte days = extras.getByte("Days");
                priorOrigin = extras.getString("origin_class");
                bundle.putString("Label", label);
                bundle.putDouble("Cost", cost);
                bundle.putByte("Days", days);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);

            } else if (resultCode == RESULT_CANCELED) {
                //toast cancelled
            }
        } else if (requestCode == EDIT_REQUEST) {
            if (resultCode == RESULT_OK) {
                extras = data.getExtras();
                int id = extras.getInt("ID");
                String label = extras.getString("Label");
                double cost = extras.getDouble("Cost");
                byte days = extras.getByte("Days");
                intent = new Intent(this, WeeklyExpenses.class);
                bundle.putString("Label", label);
                bundle.putDouble("Cost", cost);
                bundle.putByte("Days", days);
                bundle.putInt("ID", id);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            } else if (resultCode == RESULT_DELETED) {
                extras = data.getExtras();
                int id = extras.getInt("ID");
                intent = new Intent(this, WeeklyExpenses.class);
            } else if (resultCode == RESULT_CANCELED) {
                extras = data.getExtras();
            }

        }
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
