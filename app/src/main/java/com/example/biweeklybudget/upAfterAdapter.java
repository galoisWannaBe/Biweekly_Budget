package com.example.biweeklybudget;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static androidx.core.content.ContextCompat.startActivity;


public class upAfterAdapter extends RecyclerView.Adapter<upAfterAdapter.upAfterViewHolder> {

    private OnBillListener mOnBillListener;

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
        String temp1;
        String temp2;
        String temp3;
        int pos = position;
        temp1 = data.getAfter(pos, 0);
        temp2 = data.getAfter(pos, 1);
        temp3 = data.getAfter(pos, 2);

        holder.mTextView1.setText(temp1);
        holder.mTextView2.setText(temp2);
        holder.mTextView3.setText(temp3);



    }
    public interface OnBillListener{
        void OnBillclick(int position);
    }

    @Override
    public int getItemCount() {
        return data.afterSize();
    }

}