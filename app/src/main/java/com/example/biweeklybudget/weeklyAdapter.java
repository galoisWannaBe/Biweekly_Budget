package com.example.biweeklybudget;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    public void onBindViewHolder(WeeklyViewHolder holder, int wPosition) {
        String temp1;
        String temp2;
        String temp3;
        int wPos = wPosition;
        StringBuilder sb = new StringBuilder("\0");
        int bitcount = 0;
        byte weekBin = 0;
        temp1 = data.getWeekly(wPos, 0);
        temp2 = data.getWeekly(wPos, 1);
        temp3 = data.getWeekly(wPos, 2);

        weekBin = Byte.parseByte(temp3);
        for(int i = 0; i < 7; i++){
            if ((weekBin & weekArr[i]) == weekArr[i]){
                bitcount++;
            }
        }
        for(int i = 0; i < 7; i++){
            if ((weekBin & weekArr[i]) == weekArr[i]){
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
        temp3 = sb.toString();

        holder.wTextView1.setText(temp1);
        holder.wTextView2.setText(temp2);
        holder.wTextView3.setText(temp3);



    }

    @Override
    public int getItemCount() {
        return data.getWeeklySize();
    }

    public interface OnWeeklyListener{
        void OnWeeklyClick(int position);
    }

}