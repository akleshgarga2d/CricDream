package com.livescore.CricDream.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.livescore.CricDream.Models.BowlerItem;
import com.livescore.CricDream.R;

import java.util.ArrayList;

public class BowlingAdapter extends RecyclerView.Adapter<BowlingAdapter.MyViewHolder> {

    private ArrayList<BowlerItem> list;
    private Context ctx;

    public BowlingAdapter(ArrayList<BowlerItem> Data, Context context) {
        list = Data;
        this.ctx=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bowling_item, parent, false);
       
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
    
        holder.bowler_name.setText(list.get(position).getBowler_name());
        holder.bowler_run.setText(list.get(position).getBowler_run());
        holder.bowler_eco.setText(list.get(position).getBowler_eco());
        holder.bowler_maiden_overs.setText(list.get(position).getBowler_maiden_overs());
        holder.bowler_over.setText(list.get(position).getBowler_over());
        holder.bowler_wicket.setText(list.get(position).getBowler_wicket());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
 
    public class MyViewHolder extends RecyclerView.ViewHolder {

     TextView bowler_name,bowler_over,bowler_run,bowler_wicket,bowler_eco,bowler_maiden_overs;

        public MyViewHolder(View v) {
            super(v);
            bowler_eco=(TextView)v.findViewById(R.id.bowler_eco);
            bowler_maiden_overs=(TextView)v.findViewById(R.id.bowler_maiden_overs);
            bowler_name=(TextView)v.findViewById(R.id.bowler_name);
            bowler_over=(TextView)v.findViewById(R.id.bowler_over);
            bowler_run=(TextView)v.findViewById(R.id.bowler_run);
            bowler_wicket=(TextView)v.findViewById(R.id.bowler_wicket);
        }
    }
}
