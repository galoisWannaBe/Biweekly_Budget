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


public class upNextAdapter extends RecyclerView.Adapter<upNextAdapter.upNextViewHolder>{

    private OnBillListener mOnBillListener;
    private static List<Bill> nextBills = Collections.emptyList();
    private static boolean splitDue;
    private static List<Bill> nextSplitEnds;
    private static List<Bill> nextSplitBegins;

    upNextAdapter(OnBillListener onBillListener){
        this.mOnBillListener = onBillListener;
    }

    public static class upNextViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;

        private final Context context;
        OnBillListener onBillListener;

        public upNextViewHolder(View itemView, OnBillListener onBillListener) {
            super(itemView);
            context  = itemView.getContext();
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
            mTextView3 = itemView.findViewById(R.id.textView3);
            this.onBillListener = onBillListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onBillListener.OnBillClick(getAdapterPosition());

        }
    }

    public upNextAdapter() {
    }
    private static Context context;
    @Override
    public upNextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_item, parent, false);
        upNextViewHolder evh = new upNextViewHolder(v, mOnBillListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(upNextViewHolder holder, int position) {
        NumberFormat formatter;
        formatter = NumberFormat.getCurrencyInstance();
        String label = "\0";
        int due = 0;
        double cost = 0;

        int pos = position;

        if(splitDue){
            if(pos < nextSplitEnds.size()){
                Bill bill = nextSplitEnds.get(pos);
                label = bill.getLabel();
                due = bill.getDue();
                cost = bill.getCost();
            }else{
                int i = pos - nextSplitEnds.size();
                Bill bill = nextSplitBegins.get(i);
                label = bill.getLabel();
                due = bill.getDue();
                cost = bill.getCost();
            }
        }else{
            Bill bill = nextBills.get(pos);
            label = bill.getLabel();
            due = bill.getDue();
            cost = bill.getCost();
        }

        holder.mTextView1.setText(label);
        holder.mTextView2.setText(String.valueOf(due));
        holder.mTextView3.setText(formatter.format(cost));


    }
    public interface OnBillListener{
        void OnBillClick(int position);
    }

    @Override
    public int getItemCount() {
        if(splitDue){
            return (nextSplitEnds.size() + nextSplitBegins.size());
        }else {
            return nextBills.size();
        }
    }

    public static void setNextBills(List<Bill> NextBills) {
        splitDue = false;
        nextBills = NextBills;
    }
    public void isSplitDue(boolean split){
        splitDue = split;
    }

    public static void setNextSplitEnds(List<Bill> NextSplitEnds) {
        splitDue = true;
        nextSplitEnds = NextSplitEnds;
    }

    public static void setNextSplitBegins(List<Bill> NextSplitBegins) {
        nextSplitBegins = NextSplitBegins;
    }
}