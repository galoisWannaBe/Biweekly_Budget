package com.example.biweeklybudget;

import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

public class upAfter extends AppCompatActivity implements upAfterAdapter.OnBillListener{
    private RecyclerView mRecyclerView;
    upAfterAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static int q;
    public final int ADD_REQUEST = 0;
    public final int EDIT_REQUEST = 1;
    public final int RESULT_DELETED = 2;

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

        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent Data){
            super.onActivityResult(requestCode, resultCode, Data);
            String label = "\0";
            String due = "\0";
            String cost = "\0";
            int ID = 0;
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(
                        getApplicationContext(),
                        "Cancelled",
                        Toast.LENGTH_LONG).show();
            }else if(resultCode == RESULT_OK){
                Bundle bundle = Data.getExtras();
                label = bundle.getString("Label");
                due = bundle.getString("Due");
                cost = bundle.getString("Cost");
            }
            else{
                label = "\0";
                due = "\0";
                cost = "\0";
            }
            if(requestCode == ADD_REQUEST){
                if(resultCode == RESULT_OK){
                    data.addItem(label, due, cost);
                }
                } else if(requestCode == EDIT_REQUEST){
                if(resultCode == RESULT_OK){
                    Bundle bundle = Data.getExtras();
                    ID = bundle.getInt("ID");
                    data.addItem(label, due, cost, ID);
                }else if(resultCode == RESULT_DELETED) {
                    Bundle bundle = Data.getExtras();
                    ID = bundle.getInt("ID");
                    data.removeItem(ID);
                }
            }budgetData.upNextGen();
            budgetData.upAfterGen();
            mAdapter.notifyDataSetChanged();
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
