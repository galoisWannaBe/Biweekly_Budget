package com.example.biweeklybudget;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class upAfter extends AppCompatActivity implements upAfterAdapter.OnBillListener{
    private RecyclerView mRecyclerView;
    upAfterAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static int q;
    public final int ADD_REQUEST = 0;
    public final int EDIT_REQUEST = 1;
    public final int RESULT_DELETED = 2;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent Data) {
        super.onActivityResult(requestCode, resultCode, Data);
        Bundle bundle = Data.getExtras();
        String label;
        String due;
        String cost;
        int ID = 0;
        if(resultCode == RESULT_OK){
            label = bundle.getString("Label");
            due = bundle.getString("Due");
            cost = bundle.getString("Cost");
        } else{
            label = "\0";
            due = "\0";
            cost = "\0";
        }mAdapter = new upAfterAdapter();
        if(requestCode == ADD_REQUEST){
            if(resultCode == RESULT_OK){
                data.addItem(label, due, cost);
            }else if(resultCode == RESULT_CANCELED){
                //make toast; add failed
            }
        }else if (requestCode == EDIT_REQUEST){
            ID = bundle.getInt("ID");
            if (resultCode == RESULT_OK){
                data.addItem(label, due, cost, ID);
            }else if (resultCode == RESULT_DELETED){
                data.removeItem(ID);
            }else if (resultCode == RESULT_CANCELED){
                //make toast; edit failed
            }
        }
        budgetData.upNextGen();
        budgetData.upAfterGen();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_after);

        mRecyclerView = findViewById(R.id.recyclerView);
        mAdapter = new upAfterAdapter(this);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);



    }
    public void goToAdd(View view) {
        Intent intent = new Intent(this, AddToList.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("fromList", false);
        bundle.putString("origin_class", "upAfter");
        intent.putExtras(bundle);
        startActivityForResult(intent, ADD_REQUEST);
    }


    public void gotoMain(View view){
        Intent mIntent = new Intent(upAfter.this, MainActivity.class);
        startActivity(mIntent);
    }
    public void gotoUpNext(View view){
        Intent nIntent = new Intent(upAfter.this, upNext.class);
        startActivity(nIntent);
    }    public void gotoViewAll(View view){
        Intent vIntent = new Intent(upAfter.this, viewAll.class);
        startActivity(vIntent);
    }

    @Override
    public void OnBillclick(int position) {
        Intent intent = new Intent(this, AddToList.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("fromList", true);
        bundle.putInt("index", position);
        bundle.putString("origin_class", "upAfter");
        intent.putExtras(bundle);
        startActivityForResult(intent, EDIT_REQUEST);
    }
}
