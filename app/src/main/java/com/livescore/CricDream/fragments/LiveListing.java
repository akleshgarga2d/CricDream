package com.livescore.CricDream.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.livescore.CricDream.Interfaces.OnItemClickListner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.livescore.CricDream.Models.LiveListItem;
import com.livescore.CricDream.R;
import com.livescore.CricDream.Utils.Constants;
import com.livescore.CricDream.Utils.RequestHandler;
import com.livescore.CricDream.Utils.Utillity;
import com.livescore.CricDream.activities.CompletedActivity;
import com.livescore.CricDream.activities.LiveLineActivity;
import com.livescore.CricDream.activities.UpcomingDetail;
import com.livescore.CricDream.adapters.LiveListAdapter;
import com.livescore.CricDream.api.CommonMethod;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class LiveListing extends Fragment implements OnItemClickListner {
    private static String TAGC = LiveListing.class.getName();
    ArrayList<LiveListItem> liveList = new ArrayList<>();
    RecyclerView live_list_lv;
    LiveListAdapter live_list_ada;
    ACProgressFlower acProgressFlower;
    private LiveListing mlive_list;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_livestream, container, false);
        live_list_lv = view.findViewById(R.id.live_list_lv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        live_list_lv.setLayoutManager(layoutManager);
        mlive_list = this;

        if (CommonMethod.isNetworkAvailable(getActivity())) {
            //     AsyncTaskRunner asyn_run = new AsyncTaskRunner();
            //     NewAsyncTaskRunner asyn_run = new NewAsyncTaskRunner();
            //     asyn_run.execute();
            getMatchesList();
        } else {
            CommonMethod.showAlert("Internet Connectivity Failure", getActivity());
        }
        return view;
    }


    private void getMatchesList() {

        acProgressFlower = new ACProgressFlower.Builder(getActivity())
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("")
                .fadeColor(Color.DKGRAY).build();
        acProgressFlower.show();

        String url = Constants.RECENT_MATCH_API + Constants.getAccessToken(getContext());

        StringRequest stringDRequest = new StringRequest(
                com.android.volley.Request.Method.GET,
                url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonobject = new JSONObject(response);

                            if (liveList != null & liveList.size() > 0) {
                                liveList.clear();
                                live_list_ada.notifyDataSetChanged();

                                /* different thread / object */
                                synchronized (liveList) {
                                    live_list_ada.notify();
                                }
                                //   topside_pager.notifyAll();
                                live_list_ada = null;
                            }

                            JSONObject jsonObject = jsonobject.getJSONObject("data");
                            JSONArray jsonArray = jsonObject.getJSONArray("intelligent_order");

                            Log.d("HOME_DEBUG", jsonArray.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {

                                String objs = jsonArray.getString(i);
                                String url = Constants.MATCH_DETAIL_API + objs + "/?access_token=" + Constants.getAccessToken(getContext());
                                Log.d("URL_MAIN", url);
                                int finalI = i;
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

                                                    String match_details = obj.getString("name");
                                                    String title = obj.getString("name");
                                                    String location = obj.getString("venue");
                                                    String date_time = obj.getJSONObject("start_date").getString("iso");

                                                    String team1_name = team_1_json.getString("name");
                                                    String team2_name = team_2_json.getString("name");
                                                    Log.d("DEBUG_HOME", team1_name + " V/S " + team2_name);

                                                    String flag1 = Constants.BANNER_IMAGE_URI + team_1_json.getString("key") + ".png";
                                                    String flag2 = Constants.BANNER_IMAGE_URI + team_2_json.getString("key") + ".png";

                                                    JSONObject details = obj.getJSONObject("innings");

                                                    String wicket, team1_runs, team1_overs;
                                                    String wicket2, team2_runs, team2_overs;
                                                    String status = obj.getString("status");
                                                    if (!obj.getString("status").equals("started")) {
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

                                                    String key = String.valueOf(objs);

                                                    LiveListItem t;
                                                    t = new LiveListItem(key + "", team1_name + "", team2_name + "", "",
                                                            team1_runs + "", team2_runs + "", team1_overs + "",
                                                            team2_overs + "",
                                                            "", "", date_time + "", "", Constants.MATCH_LIVE + "",
                                                            flag1 + "", flag2 + "",
                                                            wicket + "", wicket2 + "", status);
                                                    if (!obj.getString("status").equals("started")) {
                                                        liveList.add(t);
                                                    }
                                                    live_list_ada = new LiveListAdapter(liveList, getActivity());
                                                    live_list_ada.setOnItemClickListner(mlive_list);
                                                    live_list_lv.setAdapter(live_list_ada);
                                                    /*
                                                    topslideList.add(new TopSlideItem(true, location + "", key, team1_name, team2_name, "", team1_runs, team2_runs, team1_overs, team2_overs, Constants.MATCH_FINISHED + "", match_details + "", date_time + "", flag1, flag2, wicket, wicket2, team1_name + "", team2_name + ""));
                                                    top_ada=new TopSlideAdapter(topslideList,getActivity());
                                                    topside_pager.setAdapter(top_ada);
*/
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
                            //     Log.d("DEBUG_ERROR", String.valueOf(topslideList.size()));
                            acProgressFlower.dismiss();
                            //   top_ada.notifyDataSetChanged();


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

    @Override
    public void onClick(int position) {
        Utillity.p(TAGC + " ", " getMatch_status = " + liveList.get(position).getMatch_status());
        Utillity.p(TAGC + " ", " getMatch_key = " + liveList.get(position).getMatch_key());

        if (liveList.get(position).getMatch_status().equals(Constants.MATCH_UPCOMMING)) {
            Intent intent = new Intent(getActivity(), UpcomingDetail.class);
            intent.putExtra("match_key", liveList.get(position).getMatch_key());
            getActivity().startActivity(intent);
        } else {
            Intent intent = new Intent(getActivity(), CompletedActivity.class);

            intent.putExtra("match_key", liveList.get(position).getMatch_key());
            intent.putExtra("team1_name", liveList.get(position).getTeam1_name());
            intent.putExtra("team2_name", liveList.get(position).getTeam2_name());
            intent.putExtra("team1_logo", liveList.get(position).getflag1());
            intent.putExtra("team2_logo", liveList.get(position).getflag2());
            intent.putExtra("score", liveList.get(position).getTeam1_runs());
            intent.putExtra("score2", liveList.get(position).getTeam2_runs());
            intent.putExtra("team1_overs", liveList.get(position).getTeam1_overs());
            intent.putExtra("team2_overs", liveList.get(position).getTeam2_overs());
            intent.putExtra("wicket1", liveList.get(position).getWicket1());
            intent.putExtra("wicket2", liveList.get(position).getWicket2());
            intent.putExtra("status", liveList.get(position).getStatus());

            getActivity().startActivity(intent);
        }
    }
}