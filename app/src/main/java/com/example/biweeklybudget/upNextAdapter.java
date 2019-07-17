package com.example.biweeklybudget;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.support.v4.content.ContextCompat.startActivity;


public class upNextAdapter extends RecyclerView.Adapter<upNextAdapter.upNextViewHolder>{

    private OnBillListener mOnBillListener;

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
        String temp1;
        String temp2;
        String temp3;
        int pos = position;
        temp1 = data.getDue(pos, 0);
        temp2 = data.getDue(pos, 1);
        temp3 = data.getDue(pos, 2);
        //BillItem currentItem = new BillItem(temp1, temp2, temp3);

        holder.mTextView1.setText(temp1);
        holder.mTextView2.setText(temp2);
        holder.mTextView3.setText(temp3);


    }
    public interface OnBillListener{
        void OnBillClick(int position);
    }

    @Override
    public int getItemCount() {
        return data.dueSize();
    }

}