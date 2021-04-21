package com.livescore.CricDream.adapters;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.livescore.CricDream.Models.PlayerItem;
import com.livescore.CricDream.R;

import java.util.ArrayList;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.MyViewHolder> {

    private ArrayList<PlayerItem> list;
    private Context ctx;

    public PlayerAdapter(ArrayList<PlayerItem> Data, Context context) {
        list = Data;
        this.ctx=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_item, parent, false);
        view.getLayoutParams().width = (int) (getScreenWidth() / 3); /// THIS LINE WILL DIVIDE OUR VIEW INTO NUMBERS OF PARTS

        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.player_item.setText(list.get(position).getPlayer_name());

        if(list.get(position).getPlayer_role().equalsIgnoreCase("allrounder"))
            holder.player_item.setChipIcon(ctx.getResources().getDrawable(R.drawable.bat_ball));
        if(list.get(position).getPlayer_role().equalsIgnoreCase("bowler"))
            holder.player_item.setChipIcon(ctx.getResources().getDrawable(R.drawable.ball));
        if(list.get(position).getPlayer_role().equalsIgnoreCase("batsman"))
            holder.player_item.setChipIcon(ctx.getResources().getDrawable(R.drawable.bats));
        if(list.get(position).getPlayer_role().equalsIgnoreCase("keeper"))
        holder.player_item.setChipIcon(ctx.getResources().getDrawable(R.drawable.wkt));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public int getScreenWidth() {

        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size.x;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

      Chip player_item;

        public MyViewHolder(View v) {
            super(v);
            player_item = (Chip) v.findViewById(R.id.player_chip);


        }
    }
}
