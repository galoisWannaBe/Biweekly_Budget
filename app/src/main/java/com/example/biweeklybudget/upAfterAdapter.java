package com.example.biweeklybudget;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;


public class upAfterAdapter extends RecyclerView.Adapter<upAfterAdapter.upAfterViewHolder> {

    private OnBillListener mOnBillListener;
    private static List<Bill> upAfter = Collections.emptyList();
    private static List<Bill> upAfterBegMo = Collections.emptyList();
    private static List<Bill> upAfterEndMo = Collections.emptyList();
    static boolean splitMo = false;

    upAfterAdapter(OnBillListener onBillListener){
        this.mOnBillListener = onBillListener;
    }

    public static class upAfterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;

        private final Context context;
        OnBillListener onBillListener;

        public upAfterViewHolder(View itemView, OnBillListener onBillListener) {
            super(itemView);
            context  = itemView.getContext();
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
            mTextView3 = itemView.findViewById(R.id.textView3);
            this.onBillListener = onBillListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onBillListener.OnBillclick(getAdapterPosition());
        }
    }

    public upAfterAdapter() {
    }
    private static Context context;
    @Override
    public upAfterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_item, parent, false);
        upAfterViewHolder avh = new upAfterViewHolder(v, mOnBillListener);
        return avh;
    }

    @Override
    public void onBindViewHolder(upAfterViewHolder holder, int position) {
        NumberFormat formatter;
        formatter = NumberFormat.getCurrencyInstance();
        String label;
        int due;
        double cost;
        Bill bill;
        int pos = position;

        if (splitMo){
            if (pos < upAfterEndMo.size()) {
                bill = upAfterEndMo.get(pos);
                label = bill.getLabel();
                due = bill.getDue();
                cost = bill.getCost();
            }else{
                int i = pos - upAfterEndMo.size();
                bill = upAfterBegMo.get(i);
                label = bill.getLabel();
                due = bill.getDue();
                cost = bill.getCost();
            }
        }else {
            bill = upAfter.get(pos);
            label = bill.getLabel();
            due = bill.getDue();
            cost = bill.getCost();
            }

        holder.mTextView1.setText(label);
        holder.mTextView2.setText(String.valueOf(due));
        holder.mTextView3.setText(formatter.format(cost));



    }
    public interface OnBillListener{
        void OnBillclick(int position);
    }

    @Override
    public int getItemCount() {
        if(splitMo){
            return (upAfterBegMo.size() + upAfterEndMo.size());
        }else {
            return upAfter.size();
        }
    }

    public static void setUpAfter(List<Bill> UpAfter) {
        splitMo = false;
        upAfter = UpAfter;
    }

    public static void setUpAfterBegMo(List<Bill> UpAfterBegMo) {
        splitMo = true;
        upAfterBegMo = UpAfterBegMo;
    }

    public static void setUpAfterEndMo(List<Bill> UpAfterEndMo) {
        upAfterEndMo = UpAfterEndMo;
    }

    public void setSplitMo(boolean splitMo) {
        this.splitMo = splitMo;
    }
}