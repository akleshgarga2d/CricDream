package com.livescore.CricDream.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.livescore.CricDream.Models.RecentItem;
import com.livescore.CricDream.R;
import com.livescore.CricDream.Utils.Constants;
import com.livescore.CricDream.Utils.RequestHandler;
import com.livescore.CricDream.activities.CompletedActivity;
import com.livescore.CricDream.activities.LiveLineActivity;
import com.livescore.CricDream.adapters.RecentAdapter;
import com.livescore.CricDream.api.CommonMethod;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class RecentSchedule extends Fragment {
    private static String TAGC = RecentSchedule.class.getName();
    ArrayList<RecentItem> recentList = new ArrayList<>();
    ListView recent_list_lv;
    private ImageView info_button;
    RecentAdapter recent_ada;
    ArrayList<JSONObject> match_keys = new ArrayList<>();
    ACProgressFlower acProgressFlower;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent, container, false);


        recent_list_lv = view.findViewById(R.id.recent_list_lv);

        recent_list_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getActivity(), CompletedActivity.class);
                intent.putExtra("match_key", recentList.get(arg2).getMatch_key());
                intent.putExtra("frag_name", "recent");
                intent.putExtra("team1_name", recentList.get(arg2).getTeam1_name());
                intent.putExtra("team2_name", recentList.get(arg2).getTeam2_name());
                intent.putExtra("team1_logo", recentList.get(arg2).getFlag1());
                intent.putExtra("team2_logo", recentList.get(arg2).getFlag2());
                intent.putExtra("score", recentList.get(arg2).getScore());
                intent.putExtra("score2", recentList.get(arg2).getScore2());
                intent.putExtra("team1_overs", recentList.get(arg2).getTeam1_overs());
                intent.putExtra("team2_overs", recentList.get(arg2).getTeam2_overs());
                intent.putExtra("wicket1", recentList.get(arg2).getWicket());
                intent.putExtra("wicket2", recentList.get(arg2).getWicket2());
                intent.putExtra("status", recentList.get(arg2).getStatus());
                startActivity(intent);
            }

        });

        info_button = view.findViewById(R.id.info_btn);

        info_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.menu_text_lay, null);

                PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOutsideTouchable(true);

                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        //TODO do sth here on dismiss
                    }
                });

                popupWindow.showAsDropDown(v);
            }
        });
         /*
        recentList.add(new RecentItem());
        recentList.add(new RecentItem());
        recentList.add(new RecentItem());
        recentList.add(new RecentItem());
        recentList.add(new RecentItem());
        RecentAdapter recent_ada=new RecentAdapter(getActivity(),R.layout.upcoming_item,recentList);
        recent_list_lv.setAdapter(recent_ada);*/
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (CommonMethod.isNetworkAvailable(getActivity())) {
            //    new AsyncTaskAllSeasons().execute();
         //   new NewRecentMatch().execute();
            getMatchesList();
        } else {

            CommonMethod.showAlert("Internet Connectivity Failure", getActivity());
        }
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

                            if (recentList != null & recentList.size() > 0) {
                                recentList.clear();
                            }

                            JSONObject jsonObject = jsonobject.getJSONObject("data");
                            JSONArray jsonArray = jsonObject.getJSONArray("intelligent_order");

                            Log.d("HOME_DEBUG", jsonArray.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {

                                String objs = jsonArray.getString(i);
                                String url = Constants.MATCH_DETAIL_API + objs + "/?access_token=" + Constants.getAccessToken(getContext());
                                Log.d("URL_MAIN",url);
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
                                                    String status=obj.getString("status");
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

                                                    String key = String.valueOf(objs);

                                                    if (obj.getString("status").equals("completed")) {
                                                        recentList.add(
                                                                new RecentItem(
                                                                        key + "",
                                                                        title + "",
                                                                        match_details+"",
                                                                        date_time + "",
                                                                        "",
                                                                        location + "",
                                                                        team1_name + "",
                                                                        team2_name + "",
                                                                        flag1 + "",
                                                                        flag2 + "",
                                                                        Constants.MATCH_FINISHED + "",
                                                                        wicket + "",
                                                                        wicket2 + "",
                                                                        team1_name + "",
                                                                        team2_name + "",
                                                                        team1_overs + " Overs",
                                                                        team2_overs + " Overs" + "",
                                                                        team1_runs + "",
                                                                        team2_runs + "",status
                                                                )
                                                        );
                                                    }
                                                    recent_ada = new RecentAdapter(getActivity(), R.layout.upcoming_item, recentList);
                                                    recent_list_lv.setAdapter(recent_ada);
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


}