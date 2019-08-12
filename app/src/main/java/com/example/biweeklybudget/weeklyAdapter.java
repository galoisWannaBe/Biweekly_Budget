package com.example.biweeklybudget;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import static androidx.core.content.ContextCompat.getSystemService;
import static androidx.core.content.ContextCompat.startActivity;


public class weeklyAdapter extends RecyclerView.Adapter<weeklyAdapter.WeeklyViewHolder> {

    public final byte SUNDAY = 1;
    public final byte MONDAY = 2;
    public final byte TUESDAY = 4;
    public final byte WEDNESDAY = 8;
    public final byte THURSDAY = 16;
    public final byte FRIDAY = 32;
    public final byte SATURDAY = 64;
    public final byte[] weekArr = new byte[]{SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY};
    public final String[] weekStr = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    List<Weekly> allWeekly = Collections.emptyList();

    private OnWeeklyListener mOnWeeklyListener;

    public static class WeeklyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView wTextView1;
        public TextView wTextView2;
        public TextView wTextView3;

        private final Context context;
        OnWeeklyListener onWeeklyListener;

        public WeeklyViewHolder(View itemView, OnWeeklyListener onWeeklyListener) {
            super(itemView);
            context  = itemView.getContext();
            wTextView1 = itemView.findViewById(R.id.weeklyLabels);
            wTextView2 = itemView.findViewById(R.id.weeklyCosts);
            wTextView3 = itemView.findViewById(R.id.weeklyDays);
            this.onWeeklyListener = onWeeklyListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onWeeklyListener.OnWeeklyClick(getAdapterPosition());
        }
    }

    public weeklyAdapter(OnWeeklyListener onWeeklyListener) {
        this.mOnWeeklyListener = onWeeklyListener;
    }
    private static Context context;
    @Override
    public WeeklyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.weekly_item, parent, false);
        WeeklyViewHolder wvh = new WeeklyViewHolder(v, mOnWeeklyListener);
        return wvh;
    }

    @Override
    public void onBindViewHolder(WeeklyViewHolder holder, int position) {
        Weekly weekly = allWeekly.get(position);
        String label = weekly.getLabel();
        double cost = weekly.getCost();
        byte days = weekly.getDays();
        StringBuilder sb = new StringBuilder("\0");
        int bitcount = 0;
        for(int i = 0; i < 7; i++){
            if ((days & weekArr[i]) == weekArr[i]){
                bitcount++;
            }
        }
        for(int i = 0; i < 7; i++){
            if ((days & weekArr[i]) == weekArr[i]){
                sb = sb.append(weekStr[i]);
                if (bitcount > 2){
                    sb = sb.append(", ");
                }
                if (bitcount == 2 ){
                    sb = sb.append(" & ");
                }
                bitcount--;
            }
        }
        holder.wTextView1.setText(label);
        holder.wTextView2.setText(String.valueOf(cost));
        holder.wTextView3.setText(sb.toString());



    }

    @Override
    public int getItemCount() {
        return allWeekly.size();
    }

    void setAllWeekly(List<Weekly> allWeekly) {
        this.allWeekly = allWeekly;
        notifyDataSetChanged();
    }

    public interface OnWeeklyListener{
        void OnWeeklyClick(int position);
    }


}