package com.example.biweeklybudget;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

public class upNext extends AppCompatActivity implements upNextAdapter.OnBillListener{

    private RecyclerView nRecyclerView;
    private RecyclerView.LayoutManager nLayoutManager;
    public static int q;
    public final int ADD_REQUEST = 0;
    public final int EDIT_REQUEST = 1;
    public final int RESULT_DELETED = 2;
    upNextAdapter nAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_next);

        //helps distinguish between an add and an edit

        nRecyclerView = findViewById(R.id.recyclerView);
        nAdapter = new upNextAdapter(this);
        nRecyclerView.setHasFixedSize(true);
        nLayoutManager = new LinearLayoutManager(this);
        nRecyclerView.setLayoutManager(nLayoutManager);
        nRecyclerView.setAdapter(nAdapter);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent Data) {
        super.onActivityResult(requestCode, resultCode, Data);
        String label;
        String due;
        String cost;
        int ID = 0;
        if (resultCode == RESULT_OK) {
            Bundle bundle = Data.getExtras();
            label = bundle.getString("Label");
            due = bundle.getString("Due");
            cost = bundle.getString("Cost");
        } else {
            label = "\0";
            due = "\0";
            cost = "\0";
        }
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(
                    getApplicationContext(),
                    "Cancelled",
                    Toast.LENGTH_LONG).show();
        } else if (requestCode == ADD_REQUEST) {
            if (resultCode == RESULT_OK) {
                data.addItem(label, due, cost);
            }
        } else if (requestCode == EDIT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = Data.getExtras();
                ID = bundle.getInt("ID");
                data.addItem(label, due, cost, ID);
            } else if (resultCode == RESULT_DELETED) {
                Bundle bundle = Data.getExtras();
                ID = bundle.getInt("ID");
                data.removeItem(ID);

            }
            budgetData.upNextGen();
            budgetData.upAfterGen();
            nAdapter.notifyDataSetChanged();
        }
    }

    public void goToAdd(View view) {
        Intent intent = new Intent(this, AddToList.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("fromList", false);
        bundle.putString("origin_class", "upNext");
        intent.putExtras(bundle);
        startActivityForResult(intent, ADD_REQUEST);
    }

    public void gotoMain(View view){
        Intent mIntent = new Intent(this, MainActivity.class);
        startActivity(mIntent);
    }
    public void gotoUpAfter(View view){
        Intent nIntent = new Intent(this, upAfter.class);
        startActivity(nIntent);
    }    public void gotoviewAll(View view){
        Intent vIntent = new Intent(this, viewAll.class);
        startActivity(vIntent);
    }

    @Override
    public void OnBillClick(int position) {
        Intent intent = new Intent(this, AddToList.class);
        Bundle bundle = new Bundle();
        bundle.putInt("index", position);
        bundle.putString("origin_class", "upNext");
        bundle.putBoolean("fromList", true);
        intent.putExtras(bundle);
        startActivityForResult(intent, EDIT_REQUEST);
    }
}
