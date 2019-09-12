package com.example.biweeklybudget;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class helpAdapter  extends RecyclerView.Adapter<helpAdapter.HelpViewHolder>{
    public static final String TAG = "helpAdapter";
    ArrayList<String> Helps;
    boolean open = false;
    int tempItemPos;
    String tempHelp;
    AllHelp allHelp;

    public helpAdapter(){
        Helps = new ArrayList<>();
        Log.d(TAG, "Ran " +TAG);
        allHelp = AllHelp.getInstance();
    }

    @NonNull
    @Override
    public HelpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.help_item, parent, false);
        HelpViewHolder hvh = new HelpViewHolder(v);
        return hvh;
    }

    @Override
    public void onBindViewHolder(@NonNull HelpViewHolder holder, int position) {
        holder.textView.setText(Helps.get(position));
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public class HelpViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        private final Context context;

        public HelpViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            textView = itemView.findViewById(R.id.help_view);
        }
    }
    public void setHelps(String helpText){
        Helps.add(helpText);
        Log.d(TAG, "Added " +helpText);
        notifyDataSetChanged();
    }
    int count;
    public void setCount(int mCount){
        count = mCount;
    }

    public int getCount() {
        return count;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean Open) {
        open = Open;
        //notifyDataSetChanged();
    }
    public void clearList() {
        Helps.clear();
    }
    public void addHelpText(int id){
        Helps.add(allHelp.getHelpText(id));
    }
    public void addHelpLabel(int id){
        Helps.add(allHelp.getHelpLabel(id));
            notifyDataSetChanged();
    }
    public void printList(){

                for (int i = 0; i < Helps.size(); i++) {
                    System.out.println();
        }
    }
}