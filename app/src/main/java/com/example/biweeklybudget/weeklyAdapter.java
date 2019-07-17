package com.example.biweeklybudget;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biweeklybudget.AddToList;
import com.example.biweeklybudget.R;
import com.example.biweeklybudget.data;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.getSystemService;
import static android.support.v4.content.ContextCompat.startActivity;


public class weeklyAdapter extends RecyclerView.Adapter<weeklyAdapter.WeeklyViewHolder> {

    public static class WeeklyViewHolder extends RecyclerView.ViewHolder {
        public TextView wTextView1;
        public TextView wTextView2;
        public TextView wTextView3;

        private final Context context;

        public WeeklyViewHolder(View itemView) {
            super(itemView);
            context  = itemView.getContext();
            wTextView1 = itemView.findViewById(R.id.weeklyLabels);
            wTextView2 = itemView.findViewById(R.id.weeklyCosts);
            wTextView3 = itemView.findViewById(R.id.weeklyDays);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();

                    //onClick Shit
                    AddWeekly.setPosition(position, true);
                    Bundle bundle = new Bundle();
                    Intent nIntent = new Intent(context, AddWeekly.class);
                    startActivity(context, nIntent, bundle);

                }
            });
        }
    }

    public weeklyAdapter() {
    }
    private static Context context;
    @Override
    public WeeklyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.weekly_item, parent, false);
        WeeklyViewHolder wvh = new WeeklyViewHolder(v);
        return wvh;
    }

    @Override
    public void onBindViewHolder(WeeklyViewHolder holder, int wPosition) {
        String temp1;
        String temp2;
        String temp3;
        int wPos = wPosition;
        temp1 = data.getWeekly(wPos, 0);
        temp2 = data.getWeekly(wPos, 1);
        temp3 = data.getWeekly(wPos, 2);

        holder.wTextView1.setText(temp1);
        holder.wTextView2.setText(temp2);
        holder.wTextView3.setText(temp3);



    }

    @Override
    public int getItemCount() {
        return data.getWeeklySize();
    }

}