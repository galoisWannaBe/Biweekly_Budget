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


public class upAfterAdapter extends RecyclerView.Adapter<upAfterAdapter.upAfterViewHolder> {

    private OnBillListener mOnBillListener;
    private List<Bill> upAfter = Collections.emptyList();

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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item, parent, false);
        upAfterViewHolder avh = new upAfterViewHolder(v, mOnBillListener);
        return avh;
    }

    @Override
    public void onBindViewHolder(upAfterViewHolder holder, int position) {
        String label;
        int due;
        double cost;

        int pos = position;
        Bill bill = upAfter.get(pos);
        label = bill.getLabel();
        due = bill.getDue();
        cost = bill.getCost();

        holder.mTextView1.setText(label);
        holder.mTextView2.setText(String.valueOf(due));
        holder.mTextView3.setText(String.valueOf(cost));



    }
    public interface OnBillListener{
        void OnBillclick(int position);
    }

    @Override
    public int getItemCount() {
        return upAfter.size();
    }

    public void setUpAfter(List<Bill> upAfter) {
        this.upAfter = upAfter;
    }
}