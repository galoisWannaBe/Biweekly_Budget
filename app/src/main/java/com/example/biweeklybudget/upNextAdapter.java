package com.example.biweeklybudget;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;


public class upNextAdapter extends RecyclerView.Adapter<upNextAdapter.upNextViewHolder>{

    private OnBillListener mOnBillListener;
    private List<Bill> nextBills = Collections.emptyList();
    private boolean splitDue;
    private List<Bill> nextSplitEnds;
    private List<Bill> nextSplitBegins;

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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item, parent, false);
        upNextViewHolder evh = new upNextViewHolder(v, mOnBillListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(upNextViewHolder holder, int position) {
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
        holder.mTextView3.setText(String.valueOf(cost));


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

    public void setNextBills(List<Bill> nextBills) {
        this.nextBills = nextBills;
    }
    public void isSplitDue(boolean split){
        splitDue = split;
    }

    public void setNextSplitEnds(List<Bill> nextSplitEnds) {
        this.nextSplitEnds = nextSplitEnds;
    }

    public void setNextSplitBegins(List<Bill> nextSplitBegins) {
        this.nextSplitBegins = nextSplitBegins;
    }
}