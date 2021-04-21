package com.livescore.CricDream.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.livescore.CricDream.Models.BattingItem;
import com.livescore.CricDream.Models.BowlerItem;
import com.livescore.CricDream.R;
import com.livescore.CricDream.Utils.Constants;
import com.livescore.CricDream.Utils.RequestHandler;
import com.livescore.CricDream.Utils.Utillity;
import com.livescore.CricDream.adapters.BattingAdapter;
import com.livescore.CricDream.adapters.BowlingAdapter;
import com.livescore.CricDream.api.CommonMethod;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class LiveScorecardFragment extends Fragment {
    private static String TAGC = LiveScorecardFragment.class.getName();
    String match_key;
    ACProgressFlower acProgressFlower;
    public ViewTreeObserver observer;
    RecyclerView bat_list, ball_list;
    RecyclerView bat_list_b, ball_list_b;
    NestedScrollView score_lay;
    private View view;
    private TextView current_partenership_tv, target_tv, extra_a_tv, total_a_tv, yet_to_bat_a, team1_name, team2_name;
    private TextView current_partenership_tvb, target_tvb, extra_b_tv, total_b_tv, yet_to_bat_b;
    private ArrayList<BattingItem> batlist = new ArrayList<>();
    private ArrayList<BowlerItem> balllist = new ArrayList<>();
    private ArrayList<BattingItem> batlistb = new ArrayList<>();
    private ArrayList<BowlerItem> balllistb = new ArrayList<>();

    TextView match_name, math_type, match_refree, match_toss, match_umpire, match_three_umpire, match_status, match_series, match_date, match_desc;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_scorecard, container, false);
        Utillity.p(TAGC + " ", " onCreateView --- ");
        init(view);
        return view;
    }

    public void init(View v) {

        score_lay = (NestedScrollView) v.findViewById(R.id.score_lay);
        bat_list = (RecyclerView) v.findViewById(R.id.bat_list);
        ball_list = (RecyclerView) v.findViewById(R.id.ball_list);
        bat_list_b = (RecyclerView) v.findViewById(R.id.bat_list_b);
        ball_list_b = (RecyclerView) v.findViewById(R.id.ball_list_b);
        current_partenership_tv = (TextView) v.findViewById(R.id.current_partnership_tv);
        extra_a_tv = (TextView) v.findViewById(R.id.extra_a);
        total_a_tv = (TextView) v.findViewById(R.id.total_a);
        yet_to_bat_a = (TextView) v.findViewById(R.id.yet_to_bat_a);
        target_tv = (TextView) v.findViewById(R.id.target_tv);
        team1_name = (TextView) v.findViewById(R.id.team1_name);
        team2_name = (TextView) v.findViewById(R.id.team2_name);

        current_partenership_tvb = (TextView) v.findViewById(R.id.current_partnership_tv_b);
        extra_b_tv = (TextView) v.findViewById(R.id.extra_b);
        total_b_tv = (TextView) v.findViewById(R.id.total_b);
        yet_to_bat_b = (TextView) v.findViewById(R.id.yet_to_bat_b);
        target_tvb = (TextView) v.findViewById(R.id.target_tv_b);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }
       /* ViewTreeObserver observer;
        observer= info_lay.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                    }
                });*/
        Intent resultsIntent = new Intent("heightViewpagerInfo");
        resultsIntent.putExtra("height_s", "1960");
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());

        localBroadcastManager.sendBroadcast(resultsIntent);

        if (CommonMethod.isNetworkAvailable(getActivity())) {
            //   new AsyncTaskDetailMatch().execute(this.match_key);
        //    new NewLiveMatchDetail().execute(this.match_key);
            fetchMatchInfo();
        } else {
            CommonMethod.showAlert("Internet Connectivity Failure", getActivity());
        }
        //do your stuff here
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && view != null) {
            onResume();
        }
    }

    public LiveScorecardFragment(String match_key) {
        this.match_key = match_key;
    }


    private void fetchMatchInfo(){
        acProgressFlower = new ACProgressFlower.Builder(getActivity())
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("")
                .fadeColor(Color.DKGRAY).build();
        acProgressFlower.show();

        String url = Constants.MATCH_DETAIL_API + match_key + "/?access_token=" + Constants.getAccessToken(getContext());
        StringRequest stringDRequest = new StringRequest(
                com.android.volley.Request.Method.GET,
                url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            if(batlist!=null & batlist.size()>0){
                                batlist.clear();
                                balllistb.clear();
                                balllist.clear();
                                batlistb.clear();
                            }
                            JSONObject jsonobject = new JSONObject(response);

                            JSONObject objDetails = jsonobject.getJSONObject("data");
                            JSONObject obj = objDetails.getJSONObject("card");

                            JSONObject team = obj.getJSONObject("teams");
                            JSONObject team_1_json = team.getJSONObject("a");
                            JSONObject team_2_json = team.getJSONObject("b");
                            String series_n = obj.getString("title");
                            String location = obj.getString("venue");

                            String match_details = obj.getString("description");
                            String title = obj.getString("name");
                            String matchTyepe = obj.getString("format");
                            String date_time = obj.getJSONObject("start_date").getString("iso");
                            String match_s=obj.getString("status");
                            String team1_nam = team_1_json.getString("name");
                            String team2_nam = team_2_json.getString("name");

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
                                    wicket = "-";
                                    team1_runs = "-";
                                    team1_overs = "-";

                                }

                                if (details.has("b_1")) {
                                    JSONObject teamTwoDetails = details.getJSONObject("b_1");
                                    wicket2 = teamTwoDetails.getString("wickets");
                                    team2_runs = teamTwoDetails.getString("runs");
                                    team2_overs = teamTwoDetails.getString("overs");
                                } else {
                                    wicket2 = "-";
                                    team2_runs = "-";
                                    team2_overs = "-";

                                }
                            } else {
                                wicket = "-";
                                team1_runs = "-";
                                team1_overs = "-";
                                wicket2 = "-";
                                team2_runs = "-";
                                team2_overs = "-";
                            }
                            JSONObject teamPlayersInfo=obj.getJSONObject("players");
                            team1_name.setText(team1_nam + "");
                            team2_name.setText(team2_nam + "");

                            JSONArray jsonArrayT1=team_1_json.getJSONObject("match").getJSONArray("playing_xi");
                            for (int i = 0; i < jsonArrayT1.length(); i++) {
                                String objs = jsonArrayT1.getString(i);

                                String name=teamPlayersInfo.getJSONObject(objs).getString("fullname");

                                JSONObject batsManDetails=teamPlayersInfo.getJSONObject(objs).getJSONObject("match").getJSONObject("innings").getJSONObject("1").getJSONObject("batting");

                                if (batsManDetails.has("runs")){
                                    String run=batsManDetails.getString("runs");
                                    String s4=batsManDetails.getString("fours");
                                    String s6=batsManDetails.getString("sixes");
                                    String sr=batsManDetails.getString("strike_rate");
                                    String balls=batsManDetails.getString("balls");
                                    batlist.add(
                                            new BattingItem(
                                                    name + "",
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

                                JSONObject bowlerDetails=teamPlayersInfo.getJSONObject(objs).getJSONObject("match").getJSONObject("innings").getJSONObject("1").getJSONObject("bowling");
                                if (bowlerDetails.has("overs")){
                                    String over=bowlerDetails.getString("overs");
                                    String run=bowlerDetails.getString("runs");
                                    String wic=bowlerDetails.getString("wickets");
                                    String eco=bowlerDetails.getString("economy");
                                    String m=bowlerDetails.getString("maiden_overs");
                                    balllist.add(
                                            new BowlerItem(
                                                    name + "",
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

                            }

                            JSONArray jsonArrayT2=team_2_json.getJSONObject("match").getJSONArray("playing_xi");
                            for (int i = 0; i < jsonArrayT2.length(); i++) {

                                String objs = jsonArrayT2.getString(i);

                                String name=teamPlayersInfo.getJSONObject(objs).getString("fullname");
                                JSONObject batsManDetails=teamPlayersInfo.optJSONObject(objs).optJSONObject("match").optJSONObject("innings").optJSONObject("1").optJSONObject("batting");
                                if (batsManDetails.has("runs")){
                                    String run=batsManDetails.getString("runs");
                                    String s4=batsManDetails.getString("fours");
                                    String s6=batsManDetails.getString("sixes");
                                    String sr=batsManDetails.getString("strike_rate");
                                    String balls=batsManDetails.getString("balls");
                                    batlistb.add(
                                            new BattingItem(
                                                    name + "",
                                                    run + "",
                                                    s4 + "",
                                                    s6 + "",
                                                    sr + "",
                                                    balls + "")
                                    );
                                    LinearLayoutManager manager_b = new LinearLayoutManager(getActivity());
                                    bat_list_b.setLayoutManager(manager_b);
                                    bat_list_b.setHasFixedSize(true);
                                    BattingAdapter bat_Ada_b = new BattingAdapter(batlistb, getActivity());
                                    bat_list_b.setAdapter(bat_Ada_b);
                                }

                                JSONObject bowlerDetails=teamPlayersInfo.optJSONObject(objs).optJSONObject("match").optJSONObject("innings").optJSONObject("1").optJSONObject("bowling");
                                if (bowlerDetails.has("overs")){
                                    String over=bowlerDetails.getString("overs");
                                    String run=bowlerDetails.getString("runs");
                                    String wic=bowlerDetails.getString("wickets");
                                    String eco=bowlerDetails.getString("economy");
                                    String m=bowlerDetails.getString("maiden_overs");
                                    balllistb.add(
                                            new BowlerItem(
                                                    name + "",
                                                    over + "",
                                                    run + "",
                                                    wic + "",
                                                    eco + "",
                                                    m + ""
                                            )
                                    );
                                    LinearLayoutManager manager_bb = new LinearLayoutManager(getActivity());
                                    ball_list_b.setLayoutManager(manager_bb);
                                    ball_list_b.setHasFixedSize(true);
                                    BowlingAdapter ball_Ada_bb = new BowlingAdapter(balllistb, getActivity());
                                    ball_list_b.setAdapter(ball_Ada_bb);
                                }

                            }
                            acProgressFlower.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                            acProgressFlower.dismiss();
                        }

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        acProgressFlower.dismiss();
                    }
                }
        );
        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringDRequest);
    }

}