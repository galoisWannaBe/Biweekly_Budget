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


public class viewAllAdapter extends RecyclerView.Adapter<viewAllAdapter.ViewAllViewHolder> {

    private List<Bill> allBills = Collections.emptyList();
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

    @Override
    public ViewAllViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item, parent, false);
        ViewAllViewHolder evh = new ViewAllViewHolder(v, mOnBillListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(ViewAllViewHolder holder, int position) {
        String label;
        int due;
        double cost;

        int pos = position;
        Bill bill = allBills.get(pos);
        label = bill.getLabel();
        due = bill.getDue();
        cost = bill.getCost();

        holder.mTextView1.setText(label);
        holder.mTextView2.setText(String.valueOf(due));
        holder.mTextView3.setText(String.valueOf(cost));



    }

    public interface OnBillListener{
        void OnBillClick(int position);
    }

    @Override
    public int getItemCount() {
        return allBills.size();
    }

    public void setAllBills(List<Bill> allBills) {
        this.allBills = allBills;
    }
}