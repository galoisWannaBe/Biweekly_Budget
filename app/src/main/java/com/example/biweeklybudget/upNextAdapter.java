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


public class upNextAdapter extends RecyclerView.Adapter<upNextAdapter.upNextViewHolder> {


    public static class upNextViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;

        private final Context context;

        public upNextViewHolder(View itemView) {
            super(itemView);
            context  = itemView.getContext();
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
            mTextView3 = itemView.findViewById(R.id.textView3);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    int id = data.findDue(position);

                    System.out.println(position);
                    AddToList.setPosition(true, id, 0);
                    Bundle bundle = new Bundle();
                    Intent nIntent = new Intent(context, AddToList.class);
                    startActivity(context, nIntent, bundle);


                }
            });
        }
    }

    public upNextAdapter() {
    }
    private static Context context;
    @Override
    public upNextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item, parent, false);
        upNextViewHolder evh = new upNextViewHolder(v);
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

    @Override
    public int getItemCount() {
        return data.dueSize();
    }

}