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

public class helpAdapter  extends RecyclerView.Adapter<helpAdapter.HelpViewHolder> {
    public static final String TAG = "helpAdapter";
    private ArrayList<String> Helps;
    private boolean open = false;
    private int tempItemPos;
    private String tempHelp;
    private AllHelp allHelp;
    private OnHelpListener mOnClickListener;
    private Context context;

    public helpAdapter(OnHelpListener onHelpListener) {
        Helps = new ArrayList<>();
        Log.d(TAG, "Ran " + TAG);
        allHelp = AllHelp.getInstance();
        this.mOnClickListener = onHelpListener;
    }

    @NonNull
    @Override
    public HelpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.help_item, parent, false);
        HelpViewHolder hvh = new HelpViewHolder(v, mOnClickListener);
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

    public class HelpViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textView;
        private final Context context;
        OnHelpListener onHelpListener;

        public HelpViewHolder(@NonNull View itemView, OnHelpListener onHelpListener) {
            super(itemView);
            context = itemView.getContext();
            textView = itemView.findViewById(R.id.help_view);
            this.onHelpListener = onHelpListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onHelpListener.OnHelpClick(getAdapterPosition());
        }
    }

    public void setHelps(String helpText) {
        Helps.add(helpText);
        Log.d(TAG, "Added " + helpText);
        notifyDataSetChanged();
    }

    int count;

    public void setCount(int mCount) {
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
        notifyDataSetChanged();
    }

    public void clearList() {
        Helps.clear();
    }

    public void addHelpText(int id) {
        Helps.add(allHelp.getHelpText(id));
    }

    public void addHelpLabel(int id) {
        Helps.add(allHelp.getHelpLabel(id));
    }

    public interface OnHelpListener {
        void OnHelpClick(int position);

    }
}