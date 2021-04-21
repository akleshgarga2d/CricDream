package com.livescore.CricDream.adapters;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.livescore.CricDream.Models.LiveListItem;
import com.livescore.CricDream.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LiveLstAdapter extends RecyclerView.Adapter<LiveLstAdapter.ViewHolder> {

    private Context context;
    private final List<ViewHolder> lstHolders;
    public List<LiveListItem> lst;

    private Handler mHandler = new Handler();
    private Runnable updateRemainingTimeRunnable = new Runnable() {
        @Override
        public void run() {
            synchronized (lstHolders) {
                long currentTime = System.currentTimeMillis();
                for (ViewHolder holder : lstHolders) {
                    holder.updateTimeRemaining(currentTime);
                }
            }
        }
    };

    public LiveLstAdapter(List<LiveListItem> lst, Context context){
        super();
        this.lst = lst;
        this.context = context;
        lstHolders = new ArrayList<>();
        startUpdateTimer();
    }

    private void startUpdateTimer() {
        Timer tmr = new Timer();
        tmr.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(updateRemainingTimeRunnable);
            }
        }, 1000, 1000);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.livelist_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(lst.get(position));
        synchronized (lstHolders) {
            lstHolders.add(holder);
        }
        holder.updateTimeRemaining(System.currentTimeMillis());
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewName;
        public TextView tvTimeRemaining;
        LiveListItem mLiveListItem;

        public void setData(LiveListItem item) {
            mLiveListItem = item;
          //  textViewName.setText(item.name);
            updateTimeRemaining(System.currentTimeMillis());
        }

        public void updateTimeRemaining(long currentTime) {
            try{
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                Date futureDate = dateFormat.parse(mLiveListItem.getDateTime());
                long timeDiff = futureDate.getTime() - currentTime;
                if (timeDiff > 0) {
                    int seconds = (int) (timeDiff / 1000) % 60;
                    int minutes = (int) ((timeDiff / (1000 * 60)) % 60);
                    int hours = (int) ((timeDiff / (1000 * 60 * 60)) % 24);

                    tvTimeRemaining.setText(hours + " hrs " + minutes + " mins " + seconds + " sec");
                } else {
                    tvTimeRemaining.setText("Expired!!");
                }
            }catch (Exception e){

            }

        }

        public ViewHolder(View itemView) {
            super(itemView);
            tvTimeRemaining = (TextView) itemView.findViewById(R.id.countdown_text);

        }
    }
}