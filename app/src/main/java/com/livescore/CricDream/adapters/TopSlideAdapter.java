package com.livescore.CricDream.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.livescore.CricDream.Interfaces.OnItemClickListner;
import com.livescore.CricDream.Models.TopSlideItem;
import com.livescore.CricDream.R;
import com.livescore.CricDream.Utils.Constants;
import com.livescore.CricDream.Utils.Utillity;
import com.livescore.CricDream.activities.CompletedActivity;
import com.livescore.CricDream.activities.LiveLineActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.livescore.CricDream.Utils.Constants.teams_colors_list;

public class TopSlideAdapter extends PagerAdapter {

    private Context context;
    private List<TopSlideItem> data;
    private ProgressDialog dialog;
    private RelativeLayout live_lay;
    private LinearLayout upcoming_lay;
    private static final String TAG = "Adapter";

    private OnItemClickListner onItemClickListner;

    public OnItemClickListner getOnItemClickListner() {
        return onItemClickListner;
    }

    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    public TopSlideAdapter(List<TopSlideItem> topslide_arraylist, Context context) {
        this.context = context;
        this.data = topslide_arraylist;
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.topside_item, container, false);

        container.addView(itemView);
        RelativeLayout recent_item = (RelativeLayout) itemView.findViewById(R.id.recent_item);
        LinearLayout bg_gradient = (LinearLayout) itemView.findViewById(R.id.bg_gradient);
        TextView tvShortNames1 = (TextView) itemView.findViewById(R.id.tvShortNames1);
        TextView tvShortNames2 = (TextView) itemView.findViewById(R.id.tvShortNames2);
        TextView team1_name = (TextView) itemView.findViewById(R.id.team1_name);
        TextView team2_name = (TextView) itemView.findViewById(R.id.team2_name);
        TextView next_live_tv = (TextView) itemView.findViewById(R.id.next_live_tv);
        TextView venue = (TextView) itemView.findViewById(R.id.venue);
        TextView team1_runs = (TextView) itemView.findViewById(R.id.team1_runs);
        TextView team2_runs = (TextView) itemView.findViewById(R.id.team2_runs);
        TextView team1_overs = (TextView) itemView.findViewById(R.id.team1_overs);
        TextView team2_overs = (TextView) itemView.findViewById(R.id.team2_overs);
        TextView match_abonded = (TextView) itemView.findViewById(R.id.match_abond);
        ImageView live_indicator = (ImageView) itemView.findViewById(R.id.live_indicator);
        ImageView team1_logo = (ImageView) itemView.findViewById(R.id.team1_logo);
        ImageView team2_logo = (ImageView) itemView.findViewById(R.id.team2_logo);
        RelativeLayout live_lay = (RelativeLayout) itemView.findViewById(R.id.score_layout);
        LinearLayout upcoming_lay = (LinearLayout) itemView.findViewById(R.id.timer_l);

    /*    for (int i = 0; i < temas_short_name.length; i++) {
            flag_list.put(temas_short_name[i], Constants.teams_flag[i]);
            teams_colors_list.put(temas_short_name[i], Constants.teams_colors[i]);
        }*/
//
//        if(flag_list.containsKey(data.get(position).getTeam1_short_name())){
//            team1_logo.setImageResource(flag_list.get(data.get(position).getTeam1_short_name()));
//        }
//        if(flag_list.containsKey(data.get(position).getTeam2_short_name())){
//            team2_logo.setImageResource(flag_list.get(data.get(position).getTeam2_short_name()));
//        }


        Glide.with(context).load(data.get(position).getflag1())
                .centerCrop()
                .placeholder(context.getResources().getDrawable(R.mipmap.ic_launchernew_round))
                .into(team1_logo);

        Glide.with(context).load(data.get(position).getflag2())
                .centerCrop()
                .placeholder(context.getResources().getDrawable(R.mipmap.ic_launchernew_round))
                .into(team2_logo);

//        if(data.get(position).getMatch_status().equalsIgnoreCase("completed")&&data.get(position).getMatch_status_overview().equalsIgnoreCase("result")){
//            live_lay.setVisibility(View.VISIBLE);
//            upcoming_lay.setVisibility(View.GONE);
//            match_abonded.setVisibility(View.GONE);
//            next_live_tv.setVisibility(View.GONE);
//            live_indicator.setVisibility(View.VISIBLE);
//        }else if(data.get(position).getMatch_status().equalsIgnoreCase("notstarted")){
//            live_lay.setVisibility(View.GONE);
//            upcoming_lay.setVisibility(View.VISIBLE);
//            next_live_tv.setVisibility(View.VISIBLE);
//            live_indicator.setVisibility(View.GONE);
//            match_abonded.setVisibility(View.GONE);
//        }else if(data.get(position).getMatch_status().equalsIgnoreCase("completed")&&data.get(position).getMatch_status_overview().equalsIgnoreCase("abandoned")){
//            live_lay.setVisibility(View.GONE);
//            upcoming_lay.setVisibility(View.GONE);
//            match_abonded.setVisibility(View.VISIBLE);
//            next_live_tv.setVisibility(View.GONE);
//            live_indicator.setVisibility(View.GONE);
//        }

//        Utillity.p(TAG+ " "," position ="+position);
//        Utillity.p(TAG+ " "," getMatch_status ="+data.get(position).getMatch_status());
//        Utillity.p(TAG+ " "," AA getTeam1_runs ="+data.get(position).getTeam1_runs());
//        Utillity.p(TAG+ " "," AA getTeam2_runs ="+data.get(position).getTeam2_runs());


        if (data.get(position).getMatch_status().equals(Constants.MATCH_LIVE + ""))//0
        {
            live_indicator.setVisibility(View.VISIBLE);
            next_live_tv.setVisibility(View.GONE);
        } else if (data.get(position).getMatch_status().equals(Constants.MATCH_UPCOMMING + ""))//1
        {
            next_live_tv.setVisibility(View.VISIBLE);
            //    upcoming_lay.setVisibility(View.VISIBLE);
            live_indicator.setVisibility(View.GONE);
            //      live_lay.setVisibility(View.GONE);
        } else if (data.get(position).getMatch_status().equals(Constants.MATCH_FINISHED + ""))//2
        {
            //   live_lay.setVisibility(View.GONE);
            next_live_tv.setVisibility(View.GONE);
            live_indicator.setVisibility(View.GONE);

//                live_lay.setVisibility(View.VISIBLE);
//                upcoming_lay.setVisibility(View.GONE);
//                match_abonded.setVisibility(View.GONE);
//                next_live_tv.setVisibility(View.GONE);
//                live_indicator.setVisibility(View.VISIBLE);
        }

        final TextView countdown_text = (TextView) itemView.findViewById(R.id.countdown_text);
        String team1_color, team2_color;
        if (teams_colors_list.containsKey(data.get(position).getflag1())) {

            team1_color = teams_colors_list.get(data.get(position).getflag1());

            if (teams_colors_list.containsKey(data.get(position).getflag2())) {
                team2_color = teams_colors_list.get(data.get(position).getflag2());
                final int[] colors = {Color.parseColor(team1_color), Color.parseColor(team2_color)};

                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.LEFT_RIGHT, colors);

                gd.setCornerRadius(0f);

                bg_gradient.setBackground(gd);
            }
        } else {
            if (data.get(position).getStatus().equals("started")) {

              /*  recent_item.setBackground(context.getResources().getDrawable(R.drawable.top_bg_line));*/

            } else {
               // recent_item.setBackground(context.getResources().getDrawable(R.drawable.top_bg_line));
                final int[] colors = {Color.parseColor("#331919"), Color.parseColor("#331919")};

                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.LEFT_RIGHT, colors);

                gd.setCornerRadius(0f);

                bg_gradient.setBackground(gd);
            }
        }
/*        if (data.get(position).getT1().length() > 2) {
            team1_name.setText(data.get(position).getT1().toUpperCase());
        } else {
            team1_name.setText("-");
        }

        if (data.get(position).getT2().length() > 2) {
            team2_name.setText(data.get(position).getT2().toUpperCase());
        } else {
            team2_name.setText("");
        }*/

        tvShortNames1.setText(data.get(position).getT1());
        tvShortNames2.setText(data.get(position).getT2());


        team1_name.setText(data.get(position).getT1().toUpperCase());
        team2_name.setText(data.get(position).getT2().toUpperCase());
     /*   tvShortNames1.setText(data.get(position).getTeam1_name());
        tvShortNames2.setText(data.get(position).getTeam2_name());*/
        Utillity.p(TAG + " ", " getTeam1_runs =" + data.get(position).getTeam1_runs());
        Utillity.p(TAG + " ", " getTeam2_runs =" + data.get(position).getTeam2_runs());

        if (!data.get(position).getTeam1_runs().equals("") & !data.get(position).getTeam1_runs().equals("0")) {
            team1_runs.setText(data.get(position).getTeam1_runs() + "/" + data.get(position).getWicket1());
        }

        if (!data.get(position).getTeam2_runs().equals("")) {
            team2_runs.setText(data.get(position).getTeam2_runs() + "/" + data.get(position).getWicket2());
        }

        if (!data.get(position).getTeam1_overs().equals("") & !data.get(position).getTeam1_overs().equals("0")) {

            team1_overs.setText(data.get(position).getTeam1_overs() + " Overs");
        }

        if (!data.get(position).getTeam2_overs().equals("") & !data.get(position).getTeam2_overs().equals("0")) {
            team2_overs.setText(data.get(position).getTeam2_overs() + " Overs");
        }


        venue.setText(data.get(position).getVenue() + "|" + data.get(position).getRelated_name());
        try {

            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

            Date futureDate = inputFormat.parse(data.get(position).getTime_str() + "");
            Date currentDate = new Date();

            if (!currentDate.after(futureDate)) {
                live_indicator.setVisibility(View.GONE);
                next_live_tv.setVisibility(View.VISIBLE);
                upcoming_lay.setVisibility(View.VISIBLE);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (data.get(position).getStatus().equals("started")) {
            //countdown_text.setText("LIVE NOW");
            live_indicator.setVisibility(View.VISIBLE);
        }
        if (data.get(position).getStatus().equals("notstarted")) {
            final Handler handler;
            Runnable runnable;

            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {
                    handler.postDelayed(this, 1000);
                    try {
                   /* SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd");*/
                        //          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");


                        /// "2020-08-28T18:30"
                        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                        Date futureDate = inputFormat.parse(data.get(position).getTime_str() + "");


                        String formattedDate = outputFormat.format(futureDate);

                        //     Utillity.p(TAG+" "," formattedDate ="+formattedDate);
                        //    System.out.println(formattedDate); // prints 10-04-2018


                        //        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, h:mm a");
                        //dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
                        // Please here set your event date//YYYY-MM-DD

                        //       Utillity.p(TAG+" "," getTime_str ="+data.get(position).getTime_str());

                        //    Date futureDate = dateFormat.parse(data.get(position).getTime_str());


                        Date currentDate = new Date();

                        //      futureDate.setYear((int) currentDate.getYear());

//                    Utillity.p(TAG+" "," futureDate ="+futureDate);
//                    Utillity.p(TAG+" "," currentDate ="+currentDate);


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
                            countdown_text.setText(String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));

                            //     Utillity.p(TAG+" "," countdown_text ="+String.format("%02d", hours)+":"+ String.format("%02d", minutes)+":"+String.format("%02d", seconds));

                        }
                    } catch (Exception e) {
                        // handler.removeCallbacksAndMessages(null);

                        e.printStackTrace();
                    }
                }
            };
            handler.postDelayed(runnable, 1 * 1000);

        }

        recent_item.setTag(position);
        recent_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                Log.d("Card", String.valueOf(position));
                // Toast.makeText(context, data.get(position).getMatchkey(), Toast.LENGTH_SHORT).show();

                if (data.get(position).getStatus().equals("started")) {
                    Intent intent = new Intent(itemView.getContext(), LiveLineActivity.class);
                    intent.putExtra("match_key", data.get(position).getMatchkey());
                    intent.putExtra("team1_name", data.get(position).getTeam1_name());
                    intent.putExtra("team2_name", data.get(position).getTeam2_name());
                    intent.putExtra("team1_logo", data.get(position).getflag1());
                    intent.putExtra("team2_logo", data.get(position).getflag2());
                    intent.putExtra("score", data.get(position).getTeam1_runs());
                    intent.putExtra("score2", data.get(position).getTeam2_runs());
                    intent.putExtra("team1_overs", data.get(position).getTeam1_overs());
                    intent.putExtra("team2_overs", data.get(position).getTeam2_overs());
                    intent.putExtra("wicket1", data.get(position).getWicket1());
                    intent.putExtra("wicket2", data.get(position).getWicket2());
                    intent.putExtra("type", data.get(position).getType());
                    intent.putExtra("status", data.get(position).getStatus());
                    context.startActivity(intent);
                }

                else {
                    Intent intent = new Intent(itemView.getContext(), CompletedActivity.class);
                    intent.putExtra("match_key", data.get(position).getMatchkey());
                    intent.putExtra("team1_name", data.get(position).getTeam1_name());
                    intent.putExtra("team2_name", data.get(position).getTeam2_name());
                    intent.putExtra("team1_logo", data.get(position).getflag1());
                    intent.putExtra("team2_logo", data.get(position).getflag2());
                    intent.putExtra("score", data.get(position).getTeam1_runs());
                    intent.putExtra("score2", data.get(position).getTeam2_runs());
                    intent.putExtra("team1_overs", data.get(position).getTeam1_overs());
                    intent.putExtra("team2_overs", data.get(position).getTeam2_overs());
                    intent.putExtra("wicket1", data.get(position).getWicket1());
                    intent.putExtra("wicket2", data.get(position).getWicket2());
                    intent.putExtra("type", data.get(position).getType());
                    intent.putExtra("status", data.get(position).getStatus());
                    context.startActivity(intent);
                }
            }
        });

        return itemView;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

   /* private void startTimer(int noOfMinutes) {
        CountDownTimer countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                countdown_text.setText(hms);//set text
            }
            public void onFinish() {
                countdown_text.setText("TIME'S UP!!"); //On finish change timer text
            }
        }.start();

    }*/
}