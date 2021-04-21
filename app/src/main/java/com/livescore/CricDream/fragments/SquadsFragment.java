package com.livescore.CricDream.fragments;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.livescore.CricDream.Models.PlayerItem;
import com.livescore.CricDream.R;
import com.livescore.CricDream.Utils.Constants;
import com.livescore.CricDream.Utils.RequestHandler;
import com.livescore.CricDream.Utils.Utillity;
import com.livescore.CricDream.adapters.PlayerAdapter;
import com.livescore.CricDream.api.CommonMethod;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class SquadsFragment extends Fragment {
    private static String TAGC = SquadsFragment.class.getName();
    String match_key;
    ArrayList<PlayerItem> teamA_player_list = new ArrayList<>();
    ArrayList<PlayerItem> teamB_player_list = new ArrayList<>();
    private ACProgressFlower acProgressFlower;
    RecyclerView teamA_lv, teamB_lv;
    TextView team1_name, team2_name;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (getActivity() != null) {
                if (CommonMethod.isNetworkAvailable(getActivity())) {
                    fetchMatchInfo();
                } else {
                    CommonMethod.showAlert("Internet Connectivity Failure", getActivity());
                }
            }
        } else {
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_squads, container, false);
        init(view);
        return view;
    }

    public void init(View v) {
        teamA_lv = (RecyclerView) v.findViewById(R.id.teamA_lv);
        teamB_lv = (RecyclerView) v.findViewById(R.id.teamB_lv);
        team2_name = (TextView) v.findViewById(R.id.team2_name);
        team1_name = (TextView) v.findViewById(R.id.team1_name);

    }

    public SquadsFragment(String match_key) {
        this.match_key = match_key;
    }


    private void fetchMatchInfo() {
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

                            if (teamA_player_list != null & teamA_player_list.size() > 0) {
                                teamA_player_list.clear();
                                teamB_player_list.clear();
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
                            String match_s = obj.getString("status");
                            String team1_nam = team_1_json.getString("name");
                            String team2_nam = team_2_json.getString("name");
                            team1_name.setText(team1_nam);
                            team2_name.setText(team2_nam);

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
                            JSONObject teamPlayersInfo = obj.getJSONObject("players");
                            team1_name.setText(team1_nam + "");
                            team2_name.setText(team2_nam + "");

                            JSONArray jsonArrayT1 = team_1_json.getJSONObject("match").getJSONArray("players");
                            for (int i = 0; i < jsonArrayT1.length(); i++) {
                                String objs = jsonArrayT1.getString(i);

                                if (teamPlayersInfo.getJSONObject(objs).has("fullname")) {
                                    String name = teamPlayersInfo.getJSONObject(objs).getString("fullname");

                                    teamA_player_list.add(
                                            new PlayerItem(
                                                    name,
                                                    ""
                                            ));
                                }

                                //System.out.println("teamAPlayer "+teamA_player_list.get(i).getPlayer_name()+" role "+teamA_player_list.get(i).getPlayer_role());
                                teamA_lv.setHasFixedSize(true);
                                GridLayoutManager MyLayoutManager = new GridLayoutManager(getActivity(), 13);
                                MyLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                                teamA_lv.setAdapter(new PlayerAdapter(teamA_player_list, getActivity()));
                                teamA_lv.setLayoutManager(MyLayoutManager);
                            }

                            JSONArray jsonArrayT2 = team_2_json.getJSONObject("match").getJSONArray("players");
                            for (int i = 0; i < jsonArrayT2.length(); i++) {

                                String objs = jsonArrayT2.getString(i);

                                if (teamPlayersInfo.getJSONObject(objs).has("fullname")) {
                                    String name = teamPlayersInfo.getJSONObject(objs).getString("fullname");

                                    teamB_player_list.add(
                                            new PlayerItem(
                                                    name,
                                                    ""
                                            ));
                                }
                                //System.out.println("teamAPlayer "+teamA_player_list.get(i).getPlayer_name()+" role "+teamA_player_list.get(i).getPlayer_role());
                                teamB_lv.setHasFixedSize(true);
                                GridLayoutManager MyLayoutManagerb = new GridLayoutManager(getActivity(), 13);
                                MyLayoutManagerb.setOrientation(LinearLayoutManager.HORIZONTAL);
                                teamB_lv.setAdapter(new PlayerAdapter(teamB_player_list, getActivity()));

                                teamB_lv.setLayoutManager(MyLayoutManagerb);
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

    private class AsyncTaskDetailMatch extends AsyncTask<String, String, String> {

        private String resp;
        /*  */


        //ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()

                    .url(Constants.FULL_SCORECARD_API.replace("{ID}", params[0]) + Constants.getAccessToken(getActivity()))
                    .get()
                    .build();

            try {
                Response response = client.newCall(request).execute();
                resp = response.body().string();

            } catch (Exception e) {
                e.printStackTrace();
                acProgressFlower.dismiss();
            }

//
//            Request request = new Request.Builder()
//
//                    .url(Constants.MATCH_DETAIL_API.replace("matchKey",params[0]) + Constants.getAccessToken(getActivity()))
//                    .get()
//                    .build();
//
//            try {
//                Response response = client.newCall(request).execute();
//                resp = response.body().string();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                acProgressFlower.dismiss();
//            }
            return resp;
        }


        @Override
        protected void onPostExecute(String res) {
            // execution of result of Long time consuming operation
            // progressDialog.dismiss();

            //  //System.out.println("detail_season*************" + res);
            try {
                final JSONObject response = new JSONObject(res);

                JSONObject jsonObject;

                JSONObject objPlayers;
                JSONObject objBowlers;

                String key;
                int teamFlag = 0;
                for (Iterator<String> iter = response.keys(); iter.hasNext(); ) {

                    key = iter.next();


                    jsonObject = new JSONObject(response.getJSONObject(key + "") + "");

                    objPlayers = jsonObject.getJSONObject("players");
                    objBowlers = jsonObject.getJSONObject("bowlers");
                    //      objFOW = jsonObject.getJSONObject("FOW");

                    JSONObject objplayer;
                    JSONObject player;

                    Utillity.p(TAGC + " ", " key " + key);
                    Utillity.p(TAGC + " ", " teamName " + jsonObject.getString("teamName"));

                    teamFlag++;

                    if (teamFlag == 1) {
                        for (Iterator<String> iterplayers = objPlayers.keys(); iterplayers.hasNext(); ) {
                            String keyplayers = iterplayers.next();
                            objplayer = new JSONObject(objPlayers.getJSONObject(keyplayers + "") + "");
                            player = objplayer.getJSONObject("player");


                            teamA_player_list.add(
                                    new PlayerItem(
                                            player.getString("playerName"),
                                            ""
                                    ));
                            //System.out.println("teamAPlayer "+teamA_player_list.get(i).getPlayer_name()+" role "+teamA_player_list.get(i).getPlayer_role());
                            teamA_lv.setHasFixedSize(true);
                            GridLayoutManager MyLayoutManager = new GridLayoutManager(getActivity(), 3);
                            MyLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                            if (teamA_player_list.size() > 0 & teamA_lv != null) {
                                teamA_lv.setAdapter(new PlayerAdapter(teamA_player_list, getActivity()));
                            }
                            teamA_lv.setLayoutManager(MyLayoutManager);
                        }
                    } else {
                        for (Iterator<String> iterplayers = objPlayers.keys(); iterplayers.hasNext(); ) {
                            String keyplayers = iterplayers.next();
                            objplayer = new JSONObject(objPlayers.getJSONObject(keyplayers + "") + "");
                            player = objplayer.getJSONObject("player");


                        }
                    }

                }

//                if (job.getBoolean("status"))
//                {
//                    try {
//                        JSONObject card_obj=job.getJSONObject("data").getJSONObject("card");
//                        JSONObject teams_obj=card_obj.getJSONObject("teams");
//                        JSONObject players_obj=card_obj.getJSONObject("players");
//                        JSONArray teamA_players_arr=teams_obj.getJSONObject("a").getJSONObject("match").getJSONArray("players");
//                        JSONArray teamB_players_arr=teams_obj.getJSONObject("b").getJSONObject("match").getJSONArray("players");
//                        team1_name.setText(teams_obj.getJSONObject("a").getString("name"));
//                        team2_name.setText(teams_obj.getJSONObject("b").getString("name"));
//                        for(int i=0;i<teamA_players_arr.length();i++)
//                        {
//                            Iterator iterator = players_obj.keys();
//                            while(iterator.hasNext())
//                            {
//                                String key = (String)iterator.next();
//                                if(teamA_players_arr.get(i).toString().equalsIgnoreCase(key))
//                                {
//                                 teamA_player_list.add(
//                                         new PlayerItem(
//                                                 players_obj.getJSONObject(key).getString("fullname"),
//                                                 players_obj.getJSONObject(key).getString("seasonal_role")
//                                         ));
//                                    //System.out.println("teamAPlayer "+teamA_player_list.get(i).getPlayer_name()+" role "+teamA_player_list.get(i).getPlayer_role());
//                                    teamA_lv.setHasFixedSize(true);
//                                    GridLayoutManager MyLayoutManager = new GridLayoutManager(getActivity(),3);
//                                    MyLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//                                    if (teamA_player_list.size() > 0 & teamA_lv != null) {
//                                        teamA_lv.setAdapter(new PlayerAdapter(teamA_player_list,getActivity()));
//                                    }
//                                    teamA_lv.setLayoutManager(MyLayoutManager);
//                                }
//                         }
//                        }
//                        for(int i=0;i<teamB_players_arr.length();i++){
//                            Iterator iterator = players_obj.keys();
//                            while(iterator.hasNext()){
//                                String key = (String)iterator.next();
//                                if(teamB_players_arr.get(i).toString().equalsIgnoreCase(key))
//                                {
//                                    teamB_player_list.add(
//                                            new PlayerItem(players_obj.getJSONObject(key).getString("fullname"),
//                                                    players_obj.getJSONObject(key).getString("seasonal_role")
//                                            ));
//                                    //System.out.println("teamBPlayer "+teamB_player_list.get(i).getPlayer_name()+" role "+teamB_player_list.get(i).getPlayer_role());
//                                    teamB_lv.setHasFixedSize(true);
//                                    GridLayoutManager MyLayoutManager = new GridLayoutManager(getActivity(),3);
//                                    MyLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//                                    if (teamB_player_list.size() > 0 & teamB_lv != null) {
//                                        teamB_lv.setAdapter(new PlayerAdapter(teamB_player_list,getActivity()));
//                                    }
//                                    teamB_lv.setLayoutManager(MyLayoutManager);
//                                }
//                            }
//                        }
//
//                        acProgressFlower.dismiss();
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        acProgressFlower.dismiss();
//                    }
//
//                } else {
//                    String statusMsg = job.getString("status_msg");
//                    if (statusMsg.equalsIgnoreCase("Invalid Access Token")) {
//
//                        //System.out.println("invalidddddddddd");
//                        if (CommonMethod.isNetworkAvailable(getActivity())) {
//                            //authRetrofitApi(getActivity());
//                            String device_id = Settings.Secure.getString(getActivity().getContentResolver(),
//                                    Settings.Secure.ANDROID_ID);
//
//
//                            Call<JsonObject> call1 = apiInterface.getAccessToken(ACCESSKEY, SECRETKEY, APPID, device_id);
//                            call1.enqueue(new Callback<JsonObject>() {
//                                @Override
//                                public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
//                                    JsonObject object = response.body();
//                                    ACCESS_TOKEN = String.valueOf(object.getAsJsonObject("auth").get("access_token"));
//                                    setAccessToken(getActivity(), ACCESS_TOKEN.replaceAll("\"", ""));
//                                    AsyncTaskDetailMatch asyn_run = new AsyncTaskDetailMatch();
//                                    asyn_run.execute();
//                                    //System.out.println("************" + ACCESS_TOKEN.replaceAll("\"", ""));
//                                }
//
//                                @Override
//                                public void onFailure(Call<JsonObject> call, Throwable t) {
//
//                                }
//
//                            });
//
//                        } else {
//                            //System.out.println("invaliddddddddddelse");
//                            CommonMethod.showAlert("Internet Connectivity Failure", getActivity());
//                        }
//
//                    }
//                }


                acProgressFlower.dismiss();


//
//                if (job.getBoolean("status"))
//                {
//                    try {
//                        JSONObject card_obj=job.getJSONObject("data").getJSONObject("card");
//                        JSONObject teams_obj=card_obj.getJSONObject("teams");
//                        JSONObject players_obj=card_obj.getJSONObject("players");
//                        JSONArray teamA_players_arr=teams_obj.getJSONObject("a").getJSONObject("match").getJSONArray("players");
//                        JSONArray teamB_players_arr=teams_obj.getJSONObject("b").getJSONObject("match").getJSONArray("players");
//                        team1_name.setText(teams_obj.getJSONObject("a").getString("name"));
//                        team2_name.setText(teams_obj.getJSONObject("b").getString("name"));
//                        for(int i=0;i<teamA_players_arr.length();i++){
//                            Iterator iterator = players_obj.keys();
//                            while(iterator.hasNext()){
//                                String key = (String)iterator.next();
//                                if(teamA_players_arr.get(i).toString().equalsIgnoreCase(key))
//                                {
//                                 teamA_player_list.add(
//                                         new PlayerItem(
//                                                 players_obj.getJSONObject(key).getString("fullname"),
//                                                 players_obj.getJSONObject(key).getString("seasonal_role")
//                                         ));
//                                    //System.out.println("teamAPlayer "+teamA_player_list.get(i).getPlayer_name()+" role "+teamA_player_list.get(i).getPlayer_role());
//                                    teamA_lv.setHasFixedSize(true);
//                                    GridLayoutManager MyLayoutManager = new GridLayoutManager(getActivity(),3);
//                                    MyLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//                                    if (teamA_player_list.size() > 0 & teamA_lv != null) {
//                                        teamA_lv.setAdapter(new PlayerAdapter(teamA_player_list,getActivity()));
//                                    }
//                                    teamA_lv.setLayoutManager(MyLayoutManager);
//                                }
//                         }
//                        }
//                        for(int i=0;i<teamB_players_arr.length();i++){
//                            Iterator iterator = players_obj.keys();
//                            while(iterator.hasNext()){
//                                String key = (String)iterator.next();
//                                if(teamB_players_arr.get(i).toString().equalsIgnoreCase(key))
//                                {
//                                    teamB_player_list.add(
//                                            new PlayerItem(players_obj.getJSONObject(key).getString("fullname"),
//                                                    players_obj.getJSONObject(key).getString("seasonal_role")
//                                            ));
//                                    //System.out.println("teamBPlayer "+teamB_player_list.get(i).getPlayer_name()+" role "+teamB_player_list.get(i).getPlayer_role());
//                                    teamB_lv.setHasFixedSize(true);
//                                    GridLayoutManager MyLayoutManager = new GridLayoutManager(getActivity(),3);
//                                    MyLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//                                    if (teamB_player_list.size() > 0 & teamB_lv != null) {
//                                        teamB_lv.setAdapter(new PlayerAdapter(teamB_player_list,getActivity()));
//                                    }
//                                    teamB_lv.setLayoutManager(MyLayoutManager);
//                                }
//                            }
//                        }
//
//                        acProgressFlower.dismiss();
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        acProgressFlower.dismiss();
//                    }
//
//                } else {
//                    String statusMsg = job.getString("status_msg");
//                    if (statusMsg.equalsIgnoreCase("Invalid Access Token")) {
//
//                        //System.out.println("invalidddddddddd");
//                        if (CommonMethod.isNetworkAvailable(getActivity())) {
//                            //authRetrofitApi(getActivity());
//                            String device_id = Settings.Secure.getString(getActivity().getContentResolver(),
//                                    Settings.Secure.ANDROID_ID);
//
//
//                            Call<JsonObject> call1 = apiInterface.getAccessToken(ACCESSKEY, SECRETKEY, APPID, device_id);
//                            call1.enqueue(new Callback<JsonObject>() {
//                                @Override
//                                public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
//                                    JsonObject object = response.body();
//                                    ACCESS_TOKEN = String.valueOf(object.getAsJsonObject("auth").get("access_token"));
//                                    setAccessToken(getActivity(), ACCESS_TOKEN.replaceAll("\"", ""));
//                                    AsyncTaskDetailMatch asyn_run = new AsyncTaskDetailMatch();
//                                    asyn_run.execute();
//                                    //System.out.println("************" + ACCESS_TOKEN.replaceAll("\"", ""));
//                                }
//
//                                @Override
//                                public void onFailure(Call<JsonObject> call, Throwable t) {
//
//                                }
//
//                            });
//
//                        } else {
//                            //System.out.println("invaliddddddddddelse");
//                            CommonMethod.showAlert("Internet Connectivity Failure", getActivity());
//                        }
//
//                    }
//                }
            } catch (Exception e) {
                acProgressFlower.dismiss();
            }

        }


        @Override
        protected void onPreExecute() {
            acProgressFlower = new ACProgressFlower.Builder(getActivity())
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE)
                    .text("")
                    .fadeColor(Color.DKGRAY).build();
            acProgressFlower.show();
        }


        @Override
        protected void onProgressUpdate(String... text) {


        }
    }
}