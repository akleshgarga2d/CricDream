package com.livescore.CricDream.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.livescore.CricDream.Models.BattingItem;
import com.livescore.CricDream.Models.BowlerItem;
import com.livescore.CricDream.Models.RunModel;
import com.livescore.CricDream.R;
import com.livescore.CricDream.Utils.Constants;
import com.livescore.CricDream.Utils.RequestHandler;
import com.livescore.CricDream.Utils.Utillity;
import com.livescore.CricDream.adapters.BattingAdapter;
import com.livescore.CricDream.adapters.BowlingAdapter;
import com.livescore.CricDream.api.CommonMethod;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import cc.cloudist.acplibrary.ACProgressFlower;

public class LiveFragment extends Fragment {
    private static String TAGC = LiveFragment.class.getName();
    public String match_key;
    private ACProgressFlower acProgressFlower;
    RecyclerView bat_list, ball_list;
    public RelativeLayout live_lay;
    private View view;
    private ArrayList<BattingItem> batlist = new ArrayList<>();
    private TextView last_run, now_overs, ball1_tv, ball2_tv, ball3_tv, ball4_tv, ball5_tv, ball6_tv, tvFavoriteteam, tvMarketrate1, tvMarketrate2, tvSession1, tvSession2, current_partenership_tv, target_tv;
    private FrameLayout ball1_lay, ball2_lay, ball3_lay, ball4_lay, ball5_lay, ball6_lay;
    private ImageView ball1_im, ball2_im, ball3_im, ball4_im, ball5_im, ball6_im;
    private ArrayList<BowlerItem> balllist = new ArrayList<>();
    private ArrayList<RunModel> runModelArrayList=new ArrayList<>();

    public LiveFragment(String match_key) {
        // Required empty public constructor
        this.match_key = match_key;
        Utillity.p(TAGC + "", " match_key =" + match_key);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_live, container, false);
        init(view);
        return view;
    }


    public void init(View v) {

        now_overs = (TextView) v.findViewById(R.id.now_overs);
        tvFavoriteteam = (TextView) v.findViewById(R.id.tvFavoriteteam);
        tvMarketrate1 = (TextView) v.findViewById(R.id.tvMarketrate1);
        tvMarketrate2 = (TextView) v.findViewById(R.id.tvMarketrate2);
        tvSession1 = (TextView) v.findViewById(R.id.tvSession1);
        tvSession2 = (TextView) v.findViewById(R.id.tvSession2);
        last_run=(TextView) v.findViewById(R.id.last_run);

        live_lay = (RelativeLayout) v.findViewById(R.id.live_lay);
        bat_list = (RecyclerView) v.findViewById(R.id.bat_list);
        ball_list = (RecyclerView) v.findViewById(R.id.ball_list);
        ball1_im = (ImageView) v.findViewById(R.id.ball1_im);
        ball2_im = (ImageView) v.findViewById(R.id.ball2_im);
        ball3_im = (ImageView) v.findViewById(R.id.ball3_im);
        ball4_im = (ImageView) v.findViewById(R.id.ball4_im);
        ball5_im = (ImageView) v.findViewById(R.id.ball5_im);
        ball6_im = (ImageView) v.findViewById(R.id.ball6_im);
        ball1_lay = (FrameLayout) v.findViewById(R.id.ball1);
        ball2_lay = (FrameLayout) v.findViewById(R.id.ball2);
        ball3_lay = (FrameLayout) v.findViewById(R.id.ball3);
        ball4_lay = (FrameLayout) v.findViewById(R.id.ball4);
        ball5_lay = (FrameLayout) v.findViewById(R.id.ball5);
        ball6_lay = (FrameLayout) v.findViewById(R.id.ball6);
        ball1_tv = (TextView) v.findViewById(R.id.ball1_tv);
        ball2_tv = (TextView) v.findViewById(R.id.ball2_tv);
        ball3_tv = (TextView) v.findViewById(R.id.ball3_tv);
        ball4_tv = (TextView) v.findViewById(R.id.ball4_tv);
        ball5_tv = (TextView) v.findViewById(R.id.ball5_tv);
        ball6_tv = (TextView) v.findViewById(R.id.ball6_tv);
        current_partenership_tv = (TextView) v.findViewById(R.id.current_partnership);
        target_tv = (TextView) v.findViewById(R.id.target);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && view != null) {
            onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }
        Intent resultsIntent = new Intent("heightViewpagerInfo");
        resultsIntent.putExtra("height_s", "960");
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());

        localBroadcastManager.sendBroadcast(resultsIntent);
        if (CommonMethod.isNetworkAvailable(getActivity())) {
//            new AsyncTaskDetailMatch().execute(this.match_key);
        //    new NewLiveMatchDetail().execute(this.match_key);
            fetchMatchInfo();
        } else {
            CommonMethod.showAlert("Internet Connectivity Failure", getActivity());
        }

    }

    private void fetchMatchInfo(){

        String url = Constants.MATCH_DETAIL_API + match_key + "/?access_token=" + Constants.getAccessToken(getContext());
        Log.d("URL_MAIN",url);
        StringRequest stringDRequest = new StringRequest(
                com.android.volley.Request.Method.GET,
                url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonobject = new JSONObject(response);

                            JSONObject objDetails = jsonobject.getJSONObject("data");
                            JSONObject obj = objDetails.getJSONObject("card");

                            JSONObject team = obj.getJSONObject("teams");
                            Log.d("DEBUG_HOME", team.getJSONObject("a").getString("name"));
                            JSONObject team_1_json = team.getJSONObject("a");
                            JSONObject team_2_json = team.getJSONObject("b");
                            String series_n = obj.getString("title");
                            String location = obj.getString("venue");

                            String match_details = obj.getString("description");
                            String title = obj.getString("name");
                            String matchTyepe = obj.getString("format");
                            String date_time = obj.getJSONObject("start_date").getString("iso");
                            String match_s=obj.getString("status");
                            String team1_name = team_1_json.getString("name");
                            String team2_name = team_2_json.getString("name");
                            Log.d("DEBUG_HOME", team1_name + " V/S " + team2_name);

                            String flag1 = Constants.BANNER_IMAGE_URI + team_1_json.getString("key") + ".png";
                            String flag2 = Constants.BANNER_IMAGE_URI + team_2_json.getString("key") + ".png";

                            JSONObject details = obj.getJSONObject("innings");

                            String wicket, team1_runs, team1_overs;
                            String wicket2, team2_runs, team2_overs;

                            if (obj.getString("status").equals("completed")) {
                                if (details.has("a_1")) {
                                    JSONObject teamOneDetails = details.getJSONObject("a_1");
                                    wicket = teamOneDetails.getString("wickets");
                                    team1_runs = teamOneDetails.getString("runs");
                                    team1_overs = teamOneDetails.getString("overs");

                                } else {
                                    wicket = "0";
                                    team1_runs = "00";
                                    team1_overs = "00";

                                }

                                if (details.has("b_1")) {
                                    JSONObject teamTwoDetails = details.getJSONObject("b_1");
                                    wicket2 = teamTwoDetails.getString("wickets");
                                    team2_runs = teamTwoDetails.getString("runs");
                                    team2_overs = teamTwoDetails.getString("overs");
                                } else {
                                    wicket2 = "0";
                                    team2_runs = "00";
                                    team2_overs = "00";

                                }
                            } else {
                                wicket = "0";
                                team1_runs = "00";
                                team1_overs = "00";
                                wicket2 = "0";
                                team2_runs = "00";
                                team2_overs = "00";
                            }

                            String key = String.valueOf(match_key);
                            if (batlist!=null & batlist.size()>0){
                                batlist.clear();
                                balllist.clear();
                                runModelArrayList.clear();
                            }
                            //-----------------GETTING DATA FROM LIVE FRAGMENT--------------------------
                            JSONObject liveObject=obj.getJSONObject("now");
                            String strikerKey=liveObject.getJSONObject("last_ball").getString("striker");
                            String nonStrikerKey=liveObject.getJSONObject("last_ball").getString("nonstriker");

                            JSONObject teamPlayersInfo=obj.getJSONObject("players");

                            JSONObject batsManDetails=teamPlayersInfo.getJSONObject(strikerKey).getJSONObject("match").getJSONObject("innings").getJSONObject("1").getJSONObject("batting");
                            String Strikername=teamPlayersInfo.getJSONObject(strikerKey).getString("fullname");
                            if (batsManDetails.has("runs")){
                                String run=batsManDetails.getString("runs");
                                String s4=batsManDetails.getString("fours");
                                String s6=batsManDetails.getString("sixes");
                                String sr=batsManDetails.getString("strike_rate");
                                String balls=batsManDetails.getString("balls");
                                batlist.add(
                                        new BattingItem(
                                                Strikername + "",
                                                run + "",
                                                s4 + "",
                                                s6 + "",
                                                sr + "",
                                                balls + "")
                                );
                                LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                                bat_list.setLayoutManager(manager);
                                bat_list.setHasFixedSize(true);
                                BattingAdapter bat_Ada = new BattingAdapter(batlist, getActivity());
                                bat_list.setAdapter(bat_Ada);
                            }

                            JSONObject batsNManDetails=teamPlayersInfo.getJSONObject(nonStrikerKey).getJSONObject("match").getJSONObject("innings").getJSONObject("1").getJSONObject("batting");
                            String nonStrikername=teamPlayersInfo.getJSONObject(nonStrikerKey).getString("fullname");
                            if (batsManDetails.has("runs")){
                                String run=batsNManDetails.getString("runs");
                                String s4=batsNManDetails.getString("fours");
                                String s6=batsNManDetails.getString("sixes");
                                String sr=batsNManDetails.getString("strike_rate");
                                String balls=batsNManDetails.getString("balls");
                                batlist.add(
                                        new BattingItem(
                                                nonStrikername + "",
                                                run + "",
                                                s4 + "",
                                                s6 + "",
                                                sr + "",
                                                balls + "")
                                );
                                LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                                bat_list.setLayoutManager(manager);
                                bat_list.setHasFixedSize(true);
                                BattingAdapter bat_Ada = new BattingAdapter(batlist, getActivity());
                                bat_list.setAdapter(bat_Ada);
                            }

                            String bowlerKey=liveObject.getJSONObject("last_ball").getJSONObject("bowler").getString("key");
                            JSONObject bowlerDetails=teamPlayersInfo.getJSONObject(bowlerKey).getJSONObject("match").getJSONObject("innings").getJSONObject("1").getJSONObject("bowling");
                            String nameB=teamPlayersInfo.getJSONObject(bowlerKey).getString("fullname");

                            if (bowlerDetails.has("overs")){
                                String over=bowlerDetails.getString("overs");
                                String run=bowlerDetails.getString("runs");
                                String wic=bowlerDetails.getString("wickets");
                                String eco=bowlerDetails.getString("economy");
                                String m=bowlerDetails.getString("maiden_overs");
                                balllist.add(
                                        new BowlerItem(
                                                nameB + "",
                                                over + "",
                                                run + "",
                                                wic + "",
                                                eco + "",
                                                m + ""
                                        )
                                );
                                LinearLayoutManager ball_manager = new LinearLayoutManager(getActivity());
                                ball_list.setLayoutManager(ball_manager);
                                ball_list.setHasFixedSize(true);
                                BowlingAdapter ball_Ada = new BowlingAdapter(balllist, getActivity());
                                ball_list.setAdapter(ball_Ada);
                            }

                            JSONObject ballsDetails=obj.getJSONObject("balls");
                            Iterator iteratorObj = ballsDetails .keys();
                            while (iteratorObj.hasNext()) {
                                final JSONObject objc;
                                String getJsonObj = (String) iteratorObj.next();
                                objc = ballsDetails.getJSONObject(getJsonObj);

                                RunModel runModel=new RunModel();
                                String over=String.valueOf(objc.getInt("over"));
                                String ball=String.valueOf(Integer.valueOf(objc.getString("ball")));
                                String run=objc.getString("wicket").equals("")?String.valueOf(objc.getJSONObject("batsman").getInt("runs")):"w";
                                runModel.setOver(over);
                                runModel.setBall(ball);
                                runModel.setRun(run);
                                runModelArrayList.add(runModel);
                            }

                            Collections.sort(runModelArrayList, new Comparator<RunModel>() {
                                @Override
                                public int compare(RunModel lhs, RunModel rhs) {
                                    return String.valueOf(lhs.getOver()).compareTo(String.valueOf(rhs.getOver()));
                                }
                            });
                            for(int i=0;i<6;i++){
                                Toast.makeText(getContext(),String.valueOf(runModelArrayList.get(i).getRun()),Toast.LENGTH_LONG).show();
                                if(i==0){
                                    if(runModelArrayList.get(i).getRun().toString().equalsIgnoreCase("w")){
                                        ball1_im.setImageResource(R.drawable.wkt_ball);
                                        ball1_tv.setText("wkt");
                                    }else{
                                        ball1_tv.setText(""+String.valueOf(runModelArrayList.get(i).getRun()));
                                        ball1_im.setImageResource(R.drawable.runs);
                                    }
                                    ball1_tv.setVisibility(View.VISIBLE);

                                }
                                if(i==1){
                                    if(runModelArrayList.get(i).getRun().toString().equalsIgnoreCase("w")){
                                        ball2_im.setImageResource(R.drawable.wkt_ball);
                                        ball2_tv.setText("wkt");
                                    }else{
                                        ball2_tv.setText(""+String.valueOf(runModelArrayList.get(i).getRun()));
                                        ball2_im.setImageResource(R.drawable.runs);
                                    }
                                    ball2_tv.setVisibility(View.VISIBLE);

                                }
                                if(i==2){
                                    if(runModelArrayList.get(i).getRun().toString().equalsIgnoreCase("w")){
                                        ball3_im.setImageResource(R.drawable.wkt_ball);
                                        ball3_tv.setText("wkt");
                                    }else{
                                        ball3_tv.setText(""+String.valueOf(runModelArrayList.get(i).getRun()));
                                        ball3_im.setImageResource(R.drawable.runs);
                                    }
                                    ball3_tv.setVisibility(View.VISIBLE);

                                }
                                if(i==3){
                                    if(runModelArrayList.get(i).getRun().toString().equalsIgnoreCase("w")){
                                        ball4_im.setImageResource(R.drawable.wkt_ball);
                                        ball4_tv.setText("wkt");
                                    }else{
                                        ball4_tv.setText(""+String.valueOf(runModelArrayList.get(i).getRun()));
                                        ball4_im.setImageResource(R.drawable.runs);
                                    }
                                    ball4_tv.setVisibility(View.VISIBLE);

                                }
                                if(i==4){
                                    if(runModelArrayList.get(i).getRun().toString().equalsIgnoreCase("w")){
                                        ball5_im.setImageResource(R.drawable.wkt_ball);
                                        ball5_tv.setText("wkt");
                                    }else{
                                        ball5_tv.setText(""+String.valueOf(runModelArrayList.get(i).getRun()));
                                        ball5_im.setImageResource(R.drawable.runs);
                                    }
                                    ball5_tv.setVisibility(View.VISIBLE);

                                }
                                if(i==5){
                                    if(runModelArrayList.get(i).getRun().toString().equalsIgnoreCase("w")){
                                        ball6_im.setImageResource(R.drawable.wkt_ball);
                                        ball6_tv.setText("wkt");
                                        last_run.setText("W");

                                    }else{
                                        ball6_tv.setText(""+String.valueOf(runModelArrayList.get(i).getRun()));
                                        ball6_im.setImageResource(R.drawable.runs);
                                        last_run.setText(""+String.valueOf(runModelArrayList.get(i).getRun()));

                                    }
                                    ball6_tv.setVisibility(View.VISIBLE);

                                }
                            }
                            acProgressFlower.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("DEBUG_ERROR", e.getMessage());

                        }

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("DEBUG_ERROR", error.getMessage());
                    }
                }
        );
        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringDRequest);
    }


}
