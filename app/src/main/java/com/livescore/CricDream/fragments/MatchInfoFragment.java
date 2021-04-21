package com.livescore.CricDream.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.livescore.CricDream.R;
import com.livescore.CricDream.Utils.Constants;
import com.livescore.CricDream.Utils.RequestHandler;
import com.livescore.CricDream.api.CommonMethod;

import org.json.JSONObject;

import cc.cloudist.acplibrary.ACProgressFlower;

public class MatchInfoFragment extends Fragment {
    String match_key;
    ACProgressFlower acProgressFlower;
    TextView match_info,match_name, math_type, match_refree, match_toss, match_umpire, match_three_umpire, match_status, match_series, match_date, match_desc;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match_info, container, false);
        init(view);
        return view;
    }

    public void init(View view) {
        match_name = view.findViewById(R.id.match_name);
        match_info = view.findViewById(R.id.match_infos);
        match_date = view.findViewById(R.id.match_date);
        match_desc = view.findViewById(R.id.match_desc);
        match_date = view.findViewById(R.id.match_date);
        match_series = view.findViewById(R.id.match_series);
        match_three_umpire = view.findViewById(R.id.match_three_umpire);
        match_refree = view.findViewById(R.id.match_refree);
        match_toss = view.findViewById(R.id.match_toss);
        match_umpire = view.findViewById(R.id.match_umpire);
        math_type = view.findViewById(R.id.match_type);
        match_status = view.findViewById(R.id.match_status);


        if (CommonMethod.isNetworkAvailable(getActivity())) {
                fetchMatchInfo();
        } else {
            CommonMethod.showAlert("Internet Connectivity Failure", getActivity());
        }
    }

    public MatchInfoFragment(String match_key) {
        this.match_key = match_key;
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

                            String key = String.valueOf(match_key);
                            match_info.setText(location);
                            match_name.setText(title);
                            math_type.setText(matchTyepe.toUpperCase());
                            match_refree.setText("-");
                            match_toss.setText(obj.getJSONObject("toss").getString("str"));
                            match_umpire.setText("-");
                            match_three_umpire.setText("-");
                            match_status.setText(match_s);
                            match_series.setText(series_n);
                            match_date.setText(date_time);
                            match_desc.setText(match_details);
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