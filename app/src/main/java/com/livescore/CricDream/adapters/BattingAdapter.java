package com.livescore.CricDream.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.livescore.CricDream.Models.BattingItem;
import com.livescore.CricDream.R;

import java.util.ArrayList;

public class BattingAdapter extends RecyclerView.Adapter<BattingAdapter.MyViewHolder> {

    private ArrayList<BattingItem> list;
    private Context ctx;

    public BattingAdapter(ArrayList<BattingItem> Data, Context context) {
        list = Data;
        this.ctx=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.batting_item, parent, false);
       
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
    
        holder.bat_name.setText(list.get(position).getBat_name());
        holder.bat_four.setText(list.get(position).getBat_four());
        holder.bat_six.setText(list.get(position).getBat_six());
        holder.bat_run.setText(list.get(position).getBat_run());
        holder.bat_strike_rate.setText(list.get(position).getBat_strike_rate());
        holder.batman_ball.setText(list.get(position).getBalls());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
 
    public class MyViewHolder extends RecyclerView.ViewHolder {

     TextView bat_name,bat_run,bat_four,bat_six,bat_strike_rate,batman_ball;

        public MyViewHolder(View v) {
            super(v);
            bat_name=(TextView)v.findViewById(R.id.batman_name);
            bat_run=(TextView)v.findViewById(R.id.batman_run);
            bat_four=(TextView)v.findViewById(R.id.batman_four);
            bat_six=(TextView)v.findViewById(R.id.batman_six);
            batman_ball=(TextView)v.findViewById(R.id.batman_ball);
            bat_strike_rate=(TextView)v.findViewById(R.id.batman_strike_rate);
        }
    }
}
