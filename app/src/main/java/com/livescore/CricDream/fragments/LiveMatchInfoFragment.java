package com.livescore.CricDream.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.livescore.CricDream.R;
import com.livescore.CricDream.Utils.Constants;
import com.livescore.CricDream.Utils.RequestHandler;
import com.livescore.CricDream.Utils.Utillity;
import com.livescore.CricDream.api.CommonMethod;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class LiveMatchInfoFragment extends Fragment {
    private static String TAGC = LiveMatchInfoFragment.class.getName();
    String match_key = "";
    ACProgressFlower acProgressFlower;
    RelativeLayout info_lay;
    View view;
    TextView reson, match_name, math_type, match_refree, match_toss, match_umpire, match_three_umpire, match_status, match_series, match_date, match_desc;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_match_info, container, false);
        Utillity.p(TAGC + " ", " onCreateView --- ");
        init(view);
        return view;
    }

    public void init(View view) {
        info_lay = (RelativeLayout) view.findViewById(R.id.info_lay);
        match_name = view.findViewById(R.id.match_name);
        match_date = view.findViewById(R.id.match_date);
        match_desc = view.findViewById(R.id.match_desc);
        match_series = view.findViewById(R.id.match_series);
        match_umpire = view.findViewById(R.id.match_umpire);
        match_three_umpire = view.findViewById(R.id.match_three_umpire);
        match_refree = view.findViewById(R.id.match_refree);
        match_toss = view.findViewById(R.id.match_toss);
        reson = view.findViewById(R.id.match_infos);
        math_type = view.findViewById(R.id.match_type);
        match_status = view.findViewById(R.id.match_status);
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
        resultsIntent.putExtra("height_s", "1940");
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());

        localBroadcastManager.sendBroadcast(resultsIntent);

        if (CommonMethod.isNetworkAvailable(getActivity())) {
            //     new AsyncTaskDetailMatch().execute(this.match_key);
            acProgressFlower = new ACProgressFlower.Builder(getActivity())
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE)
                    .text("")
                    .fadeColor(Color.DKGRAY).build();
            acProgressFlower.show();
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

    public LiveMatchInfoFragment(String match_key) {
        this.match_key = match_key;
    }

    private void fetchMatchInfo() {

        String url = Constants.MATCH_DETAIL_API + match_key + "/?access_token=" + Constants.getAccessToken(getContext());
        Log.d("URL_MAIN", url);
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
                            String match_s = obj.getString("status");
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
                                    wicket = "00";
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
                            reson.setText(location);
                            match_name.setText(title);
                            math_type.setText(matchTyepe.toUpperCase());
                            match_refree.setText("-");
                            match_toss.setText(obj.getJSONObject("toss").getString("str"));
                            match_umpire.setText("-");
                            match_three_umpire.setText("-");
                            match_status.setText(match_s);
                            match_series.setText(series_n);

                            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                            Date sourceDate = null;
                            try {
                                sourceDate = inputFormat.parse(date_time);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            SimpleDateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a");
                            match_date.setText(targetFormat.format(sourceDate));
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