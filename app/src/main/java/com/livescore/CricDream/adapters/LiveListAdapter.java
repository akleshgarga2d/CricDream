package com.livescore.CricDream.adapters;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.livescore.CricDream.Interfaces.OnItemClickListner;
import com.livescore.CricDream.Models.LiveListItem;
import com.livescore.CricDream.R;
import com.livescore.CricDream.Utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.livescore.CricDream.Utils.Constants.flag_list;
import static com.livescore.CricDream.Utils.Constants.temas_short_name;

public class LiveListAdapter extends RecyclerView.Adapter<LiveListAdapter.MyViewHolder> {
    private static String TAGC = LiveListAdapter.class.getName();

    private ArrayList<LiveListItem> liveList;
    private Context context;
    private OnItemClickListner onItemClickListner;

    public OnItemClickListner getOnItemClickListner() {
        return onItemClickListner;
    }

    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }


    public LiveListAdapter(ArrayList<LiveListItem> Data, Context context) {
        liveList = Data;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.livelist_item, parent, false);

        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (liveList.get(position).getTeam1_name().equalsIgnoreCase("")) {
            holder.team1_name.setVisibility(View.GONE);
        } else {
            holder.team1_name.setVisibility(View.VISIBLE);
            holder.team1_name.setText(liveList.get(position).getTeam1_name());
        }


        if (liveList.get(position).getTeam2_name().equalsIgnoreCase("")) {
            holder.team2_name.setVisibility(View.GONE);
        } else {
            holder.team2_name.setVisibility(View.VISIBLE);
            holder.team2_name.setText(liveList.get(position).getTeam2_name());
        }


        if (liveList.get(position).getTeam1_runs().equalsIgnoreCase("") | liveList.get(position).getTeam1_runs().equalsIgnoreCase("0")) {
            holder.team1_runs.setVisibility(View.GONE);
        } else {
            holder.team1_runs.setVisibility(View.VISIBLE);
            holder.team1_runs.setText(liveList.get(position).getTeam1_runs() + "/" + liveList.get(position).getWicket1());
        }


        if (liveList.get(position).getTeam2_runs().equalsIgnoreCase("") | liveList.get(position).getTeam2_runs().equalsIgnoreCase("0")) {
            holder.team2_runs.setVisibility(View.GONE);
        } else {
            holder.team2_runs.setVisibility(View.VISIBLE);
            holder.team2_runs.setText(liveList.get(position).getTeam2_runs() + "/" + liveList.get(position).getWicket2());
        }


        if (liveList.get(position).getTeam1_overs().equalsIgnoreCase("") | liveList.get(position).getTeam1_overs().equalsIgnoreCase("0")) {
            holder.team1_overs.setVisibility(View.GONE);
        } else {
            holder.team1_overs.setVisibility(View.VISIBLE);
            holder.team1_overs.setText(liveList.get(position).getTeam1_overs() + " Overs");
        }


        if (liveList.get(position).getTeam2_overs().equalsIgnoreCase("") | liveList.get(position).getTeam2_overs().equalsIgnoreCase("0")) {
            holder.team2_overs.setVisibility(View.GONE);
        } else {
            holder.team2_overs.setVisibility(View.VISIBLE);
            holder.team2_overs.setText(liveList.get(position).getTeam2_overs() + " Overs");
        }

        if (liveList.get(position).getMatch_status().equals(Constants.MATCH_UPCOMMING)) {
            holder.team1_overs.setVisibility(View.GONE);
            holder.team2_overs.setVisibility(View.GONE);
            holder.team1_runs.setVisibility(View.GONE);
            holder.team2_runs.setVisibility(View.GONE);
        }


        if (liveList.get(position).getRelated_name().equalsIgnoreCase("")) {

            holder.reated_name.setVisibility(View.GONE);
        } else {
            holder.reated_name.setVisibility(View.VISIBLE);
            holder.reated_name.setText(liveList.get(position).getRelated_name());
        }


        if (liveList.get(position).getVenue().equalsIgnoreCase("")) {
            holder.match_venue.setVisibility(View.GONE);
        } else {
            holder.match_venue.setVisibility(View.VISIBLE);
            holder.match_venue.setText(liveList.get(position).getVenue());
        }

        if (liveList.get(position).getName().equalsIgnoreCase("")) {
            holder.match_name.setVisibility(View.GONE);
        } else {
            holder.match_name.setVisibility(View.VISIBLE);
            holder.match_name.setText(liveList.get(position).getName());
        }

/*
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date sourceDate = null;
        try {
            sourceDate = dateFormat.parse(liveList.get(position).getRelated_name() + " . " + liveList.get(position).getDateTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy");
        //  holder.tv_expiry_date.setText(targetFormat.format(sourceDate));
        holder.match_time.setText(targetFormat.format(sourceDate));*/

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date sourceDate = null;
        try {
            sourceDate = inputFormat.parse(/*liveList.get(position).getRelated_name() + " . " + */liveList.get(position).getDateTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a");
        holder.match_time.setText(targetFormat.format(sourceDate));

        holder.livelist_lay.setTag(position);
        holder.livelist_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                if (onItemClickListner != null) {
                    onItemClickListner.onClick(pos);
                }
            }
        });
        for (int i = 0; i < temas_short_name.length; i++) {
            flag_list.put(temas_short_name[i], Constants.teams_flag[i]);
        }

        Glide.with(context).load(liveList.get(position).getflag1())
                .centerCrop()
                .placeholder(context.getResources().getDrawable(R.mipmap.ic_launchernew_round))
                .into(holder.team1_im);

        Glide.with(context).load(liveList.get(position).getflag2())
                .centerCrop()
                .placeholder(context.getResources().getDrawable(R.mipmap.ic_launchernew_round))
                .into(holder.team2_im);


        //System.out.println("statussssssssssssssss"+liveList.get(position).getMatch_status());

//
//        if (liveList.get(position).getMatch_status().equalsIgnoreCase("completed") && liveList.get(position).getStatus_overview().equalsIgnoreCase("result")) {
//            holder.countdown_text.setVisibility(View.INVISIBLE);
//        } else if (liveList.get(position).getMatch_status().equalsIgnoreCase("notstarted")) {
//            holder.countdown_text.setVisibility(View.VISIBLE);
//        } else if (liveList.get(position).getMatch_status().equalsIgnoreCase("completed") && liveList.get(position).getStatus_overview().equalsIgnoreCase("abandoned")) {
//            holder.countdown_text.setVisibility(View.INVISIBLE);
//            holder.countdown_text.setText("Abandoned");
//        }

        if (liveList.get(position).getMatch_status().equals(Constants.MATCH_LIVE + "")) {
            holder.countdown_text.setVisibility(View.VISIBLE);
        } else if (liveList.get(position).getMatch_status().equals(Constants.MATCH_UPCOMMING + "")) {
            holder.countdown_text.setVisibility(View.VISIBLE);
            holder.countdown_text.setVisibility(View.GONE);
        } else if (liveList.get(position).getMatch_status().equals(Constants.MATCH_FINISHED + "")) {
            holder.countdown_text.setVisibility(View.INVISIBLE);
            holder.countdown_text.setText("Abandoned");
            // holder.countdown_text.setVisibility(View.GONE);
        }

        if (!liveList.get(position).getDateTime().equals("")) {
            final Handler handler;
            Runnable runnable;

            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {
                    handler.postDelayed(this, 1000);
                    try {

                        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                        //       SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                        Date futureDate = inputFormat.parse(liveList.get(position).getDateTime() + "");

                        Date currentDate = new Date();
                        if (!currentDate.after(futureDate)) {
                            long diff = futureDate.getTime()
                                    - currentDate.getTime();
                            long days = diff / (24 * 60 * 60 * 1000);
                            diff -= days * (24 * 60 * 60 * 1000);
                            long hours = diff / (60 * 60 * 1000);
                            diff -= hours * (60 * 60 * 1000);
                            long minutes = diff / (60 * 1000);
                            diff -= minutes * (60 * 1000);
                            long seconds = diff / 1000;
                            holder.countdown_text.setText(String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
                            //     Utillity.p(TAG+" "," countdown_text ="+String.format("%02d", hours)+":"+ String.format("%02d", minutes)+":"+String.format("%02d", seconds));

                        } else {
                            holder.countdown_text.setText("FINISHED MATCH");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            handler.postDelayed(runnable, 1 * 1000);
        }


//
//
//        try {
//
//            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
//            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
//
//            Date futureDate = inputFormat.parse(liveList.get(position).getDateTime() + "");
//            Date currentDate = new Date();
//
//            //    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, h:mm a");
//            // Please here set your event date//YYYY-MM-DD
//
//            //    Date futureDate = dateFormat.parse(liveList.get(position).getDateTime());
//
//            //    futureDate.setYear((int) currentDate.getYear());
//
//
//            if (!currentDate.after(futureDate)) {
//                long diff = futureDate.getTime()
//                        - currentDate.getTime();
//                long days = diff / (24 * 60 * 60 * 1000);
//                diff -= days * (24 * 60 * 60 * 1000);
//                long hours = diff / (60 * 60 * 1000);
//                diff -= hours * (60 * 60 * 1000);
//                long minutes = diff / (60 * 1000);
//                diff -= minutes * (60 * 1000);
//                long seconds = diff / 1000;
//
//                holder.countdown_text.setText(String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
//
//                if (holder.timer != null) {
//                    holder.timer.cancel();
//                }
//                long timer = futureDate.getTime();
//
//                timer = timer * 1000;
//
////                holder.timer = new CountDownTimer(timer, 1000)
////                {
////                    public void onTick(long millisUntilFinished) {
////                        long days = millisUntilFinished / (24 * 60 * 60 * 1000);
////                        millisUntilFinished -= days * (24 * 60 * 60 * 1000);
////                        long hours = millisUntilFinished / (60 * 60 * 1000);
////                        millisUntilFinished -= hours * (60 * 60 * 1000);
////                        long minutes = millisUntilFinished / (60 * 1000);
////                        millisUntilFinished -= minutes * (60 * 1000);
////                        long seconds = millisUntilFinished / 1000;
////
////                        holder.countdown_text.setText(String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
////                    }
////
////                    public void onFinish() {
////                        holder.countdown_text.setText("00:00:00");
////                    }
////                }.start();
//            } else {
//                holder.countdown_text.setText("The event started");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

/*
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                    // Please here set your event date//YYYY-MM-DD

                    Date futureDate = dateFormat.parse(liveList.get(position).getDateTime());
                    Date currentDate = new Date();
                    if (!currentDate.after(futureDate)) {
                        long diff = futureDate.getTime()
                                - currentDate.getTime();
                        long days = diff / (24 * 60 * 60 * 1000);
                        diff -= days * (24 * 60 * 60 * 1000);
                        long hours = diff / (60 * 60 * 1000);
                        diff -= hours * (60 * 60 * 1000);
                        long minutes = diff / (60 * 1000);
                        diff -= minutes * (60 * 1000);
                        long seconds = diff / 1000;
                        holder.countdown_text.setText(String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));

                    } else {
                        holder.countdown_text.setText("The event started");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1 * 1000);*/
    }

    @Override
    public int getItemCount() {
        return liveList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView team1_name, team2_name, team1_runs, team2_runs, team1_overs, team2_overs, reated_name, match_venue, match_time, match_name, countdown_text;
        ImageView team1_im, team2_im;
        RelativeLayout livelist_lay;
        CountDownTimer timer;

        public MyViewHolder(View v) {
            super(v);
            team1_name = v.findViewById(R.id.team1_name);
            team2_name = v.findViewById(R.id.team2_name);
            team1_runs = v.findViewById(R.id.team1_runs);
            team2_runs = v.findViewById(R.id.team2_runs);
            team1_overs = v.findViewById(R.id.team1_overs);
            team2_overs = v.findViewById(R.id.team2_overs);
            reated_name = v.findViewById(R.id.related_name);
            match_venue = v.findViewById(R.id.match_venue);
            match_time = v.findViewById(R.id.match_time);
            match_name = v.findViewById(R.id.match_name);
            countdown_text = v.findViewById(R.id.countdown_text);
            team1_im = v.findViewById(R.id.team1_im);
            team2_im = v.findViewById(R.id.team2_im);
            livelist_lay = v.findViewById(R.id.livelist_lay);
        }
    }
}


