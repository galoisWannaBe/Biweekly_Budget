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


public class viewAllAdapter extends RecyclerView.Adapter<viewAllAdapter.ViewAllViewHolder> {

    private OnBillListener mOnBillListener;

    viewAllAdapter(OnBillListener onBillListener){
        this.mOnBillListener = onBillListener;
    }

    public static class ViewAllViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;

        private final Context context;
        OnBillListener onBillListener;

        public ViewAllViewHolder(View itemView, OnBillListener onBillListener) {
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

    public viewAllAdapter() {
    }
    private static Context context;
    @Override
    public ViewAllViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item, parent, false);
        ViewAllViewHolder evh = new ViewAllViewHolder(v, mOnBillListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(ViewAllViewHolder holder, int position) {
        String Label;
        String Due;
        String Cost;
        int pos = position;
        Label = data.getData(pos, 0);
        Due = data.getData(pos, 1);
        Cost = data.getData(pos, 2);

        holder.mTextView1.setText(Label);
        holder.mTextView2.setText(Due);
        holder.mTextView3.setText(Cost);



    }

    public interface OnBillListener{
        void OnBillClick(int position);
    }

    @Override
    public int getItemCount() {
        return data.getSize();
    }

}