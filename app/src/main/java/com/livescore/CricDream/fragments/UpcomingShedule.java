package com.livescore.CricDream.fragments;

import android.content.Context;
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
import com.livescore.CricDream.Models.UpcomingItem;
import com.livescore.CricDream.R;
import com.livescore.CricDream.Utils.Constants;
import com.livescore.CricDream.Utils.RequestHandler;
import com.livescore.CricDream.Utils.Utillity;
import com.livescore.CricDream.adapters.UpcomingAdapter;
import com.livescore.CricDream.api.CommonMethod;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class UpcomingShedule extends Fragment implements UpcomingAdapter.OnDetailClickedListener {
    private static String TAGC = UpcomingShedule.class.getName();
    ArrayList<UpcomingItem> upcomingList = new ArrayList<>();
    ListView upcoming_list_lv;
    private ACProgressFlower acProgressFlower,acProgressFlower2;
    UpcomingAdapter upcoming_ada;
    UpcomingAdapter upcoming_list_ada;
    private ImageView info_button;

    private boolean show = false;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {
            if (CommonMethod.isNetworkAvailable(getActivity()))
            {
              //  new AsyncTaskAllSeasons().execute();
             //   new NewUpcomingDetail().execute();
            getMatchesList();
            }else {
                CommonMethod.showAlert("Internet Connectivity Failure", getActivity());
            }
        }
        else {
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);

        Utillity.p(TAGC+" " , " onCreateView --- ");


        upcoming_list_lv = view.findViewById(R.id.upcoming_list_lv);
        info_button=view.findViewById(R.id.info_btn);

        info_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater = (LayoutInflater)getActivity(). getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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


        upcoming_list_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
//                Intent i=new Intent(getActivity(), UpcomingDetail.class);
//                i.putExtra("match_key",upcomingList.get(arg2).getMatch_key());
//                startActivity(i);
            }

        });
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

                            if(upcomingList!= null)
                            {
                                upcomingList.clear();
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

                                                    if (!obj.getString("status").equals("completed")) {
                                                        upcomingList.add(
                                                                new UpcomingItem(
                                                                        title+"",
                                                                        match_details+"",
                                                                        date_time+"",
                                                                        "",
                                                                        location+"",
                                                                        key+""
                                                                )
                                                        );
                                                    }
                                                    upcoming_ada = new UpcomingAdapter(getActivity(),R.layout.upcoming_item,upcomingList);
                                                    upcoming_list_lv.setAdapter(upcoming_ada);
                                                    /*
                                                    recent_ada = new RecentAdapter(getActivity(), R.layout.upcoming_item, recentList);
                                                    recent_list_lv.setAdapter(recent_ada);

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


    /*
    private class AsyncTaskAllSeasons extends AsyncTask<String, String, String> {
        private String resp;

        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(Constants.RECENT_SEASON_API + Constants.getAccessToken(getActivity()))
                    .get()
                    .build();

            try {
                Response response = client.newCall(request).execute();
                resp = response.body().string();

            } catch (Exception e) {
                e.printStackTrace();
                acProgressFlower.dismiss();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String res) {
            // execution of result of Long time consuming operation

            ////System.out.println("all_season*************" + res);
            try {
                final JSONObject job = new JSONObject(res);
                final JSONArray data_arr=job.getJSONArray("data");

                if (job.getBoolean("status")) {


                    try {
                        for(int i=0;i<data_arr.length();i++){
                            try{
                                JSONObject season_obj=data_arr.getJSONObject(i);
                                if (CommonMethod.isNetworkAvailable(getActivity())) {
                                    new AsyncTaskDetailSeasons().execute(season_obj.getString("key"));
                                }else {
                                    CommonMethod.showAlert("Internet Connectivity Failure", getActivity());
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                                acProgressFlower.dismiss();
                            }
                        }
                        acProgressFlower.dismiss();

                    } catch (Exception e) {
                        e.printStackTrace();
                        acProgressFlower.dismiss();
                    }

                } else {
                    String statusMsg = job.getString("status_msg");
                    if (statusMsg.equalsIgnoreCase("Invalid Access Token")) {

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
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

*/

    /*
    private class NewUpcomingDetail extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected String doInBackground(String... params)
        {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(Constants.FULL_MATCH_API)
                    .get()
                    .build();
            try {
                Response response = client.newCall(request).execute();
                resp = response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String res) {


            try {
                final JSONObject response = new JSONObject(res);

                JSONObject liveLineJson = response.getJSONObject("LiveLine");
                JSONObject dataliveJson = liveLineJson.getJSONObject("data");
                JSONObject currentScoreJson = dataliveJson.getJSONObject("current_score");
                JSONObject teamBet = currentScoreJson.getJSONObject("team_bet");

                if(upcomingList!= null)
                {
                    upcomingList.clear();
                }

                upcomingList.add(
                        new UpcomingItem(
                                liveLineJson.getString("title")+"",
                                dataliveJson.getString("title")+"",
                                teamBet.getString("match_date_time")+"",
                                "",
                                "",
                                ""
                        )
                );


//                    for (Iterator<String> iter = job.keys(); iter.hasNext();)
//                    {
//                        String key = iter.next();
//
//                        JSONObject match_detail = job.getJSONObject(key);
//
//                        if (match_detail.getString("status").equals(Constants.MATCH_UPCOMMING+""))
//                        {
//                            //System.out.println("sizeofupcomiing"+match_detail.getJSONObject("start_date").getString("iso"));
//                            upcomingList.add(
//                                    new UpcomingItem(
//                                            match_detail.getString("title"),
//                                            match_detail.getString("series_name"),
//                                            match_detail.getString("date"),
//                                            match_detail.getString("series_name"),
//                                            match_detail.getString("venue"),
//                                            key
//                                    )
//                            );
//                        }
//                    }

                    upcoming_ada = new UpcomingAdapter(getActivity(),R.layout.upcoming_item,upcomingList);
                    upcoming_list_lv.setAdapter(upcoming_ada);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        protected void onPreExecute() {
            // progressDialog = ProgressDialog.show(getActivity(),"","");
        }


        @Override
        protected void onProgressUpdate(String... text) {
        }
    }

=
     */
/*
    private class UpcomingDetail extends AsyncTask<String, String, String> {

        private String resp;
        //ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params)
        {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(Constants.MATCH_API)
                    .get()
                    .build();
            try {
                Response response = client.newCall(request).execute();
                resp = response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String res) {
            // execution of result of Long time consuming operation
            // progressDialog.dismiss();

            try {
                final JSONObject job = new JSONObject(res);


                    for (Iterator<String> iter = job.keys(); iter.hasNext();)
                    {
                        String key = iter.next();

                        JSONObject match_detail = job.getJSONObject(key);

                        if (match_detail.getString("status").equals(Constants.MATCH_UPCOMMING+""))
                        {
                            //System.out.println("sizeofupcomiing"+match_detail.getJSONObject("start_date").getString("iso"));
                            upcomingList.add(
                                    new UpcomingItem(
                                            match_detail.getString("title"),
                                            match_detail.getString("series_name"),
                                            match_detail.getString("date"),
                                            match_detail.getString("series_name"),
                                            match_detail.getString("venue"),
                                            key
                                    )
                            );
                        }
                    }

                    upcoming_ada = new UpcomingAdapter(getActivity(),R.layout.upcoming_item,upcomingList);
                    upcoming_list_lv.setAdapter(upcoming_ada);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        protected void onPreExecute() {
            // progressDialog = ProgressDialog.show(getActivity(),"","");
        }


        @Override
        protected void onProgressUpdate(String... text) {
        }
    }

*/
/*
    private class AsyncTaskDetailSeasons extends AsyncTask<String, String, String> {

        private String resp;
        //ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params)
        {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()

                    .url(Constants.SEASON_DETAIL_API.replace("season_key",params[0]) + Constants.getAccessToken(getActivity()))
                    .get()
                    .build();
            try {
                Response response = client.newCall(request).execute();
                resp = response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String res) {
            // execution of result of Long time consuming operation
            // progressDialog.dismiss();

            //  //System.out.println("detail_season*************" + res);
            try {
                final JSONObject job = new JSONObject(res);

                if (job.getBoolean("status")) {
                    try {
                        JSONObject matches=job.getJSONObject("data").getJSONObject("season").getJSONObject("matches");

                        Iterator iterator = matches.keys();
                        while(iterator.hasNext()){
                            String key = (String)iterator.next();

                            JSONObject match_detail = matches.getJSONObject(key);
                            //System.out.println("match_detaaaaaaaaaaaaaa "+match_detail.toString());

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault());
                            Date matchDateTime = sdf.parse(match_detail.getJSONObject("start_date").getString("iso").split("//+")[0]);

                            Calendar cal1 = Calendar.getInstance();
                            Calendar cal2 = Calendar.getInstance();
                            cal1.setTime(new Date());
                            cal2.setTime(matchDateTime);
                            boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                                    cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);


                            if (new Date().before(matchDateTime) || sameDay) {
                                //System.out.println("sizeofupcomiing"+match_detail.getJSONObject("start_date").getString("iso"));
                                upcomingList.add(new UpcomingItem(match_detail.getString("title"),match_detail.getString("name"),match_detail.getJSONObject("start_date").getString("iso").split("\\+")[0],match_detail.getString("related_name"),match_detail.getString("venue"),match_detail.getString("key")));
                            }

                            ////System.out.println("match_keyyyyyyyyyyyyy"+_pubKey);

                            //match_keys.add(issue);
                            /*for(int j=0;j<match_keys.size();j++){
                                JSONObject match_detail=match_keys.get(j);
                                recentList.add(new RecentItem(match_detail.getString("title"),match_detail.getString("name"),match_detail.getJSONObject("start_date").getString("iso"),match_detail.getString("related_name"),match_detail.getString("venue")));
                                //System.out.println("hhhhhhh"+recentList.get(j).getMatch_name());
                            }*/

                  /*          upcoming_ada = new UpcomingAdapter(getActivity(),R.layout.upcoming_item,upcomingList);
                            upcoming_list_lv.setAdapter(upcoming_ada);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                    // 18/08/2020 by kk

//                    String statusMsg = job.getString("status_msg");
//                    if (statusMsg.equalsIgnoreCase("Invalid Access Token"))
//                    {
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
//                                    AsyncTaskAllSeasons asyn_run = new AsyncTaskAllSeasons();
//                                    asyn_run.execute();
//                                    //System.out.println("************" + ACCESS_TOKEN.replaceAll("\"", ""));
//                                }
//
//                                @Override
//                                public void onFailure(Call<JsonObject> call, Throwable t) {
//                                    t.printStackTrace();
//                                }
//                            });
//
//                        } else {
//                            //System.out.println("invaliddddddddddelse");
//                            CommonMethod.showAlert("Internet Connectivity Failure", getActivity());
//                        }
//                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        protected void onPreExecute() {
            // progressDialog = ProgressDialog.show(getActivity(),"","");

        }


        @Override
        protected void onProgressUpdate(String... text) {


        }
    }

*/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void DetailClicked(String url) {
        Log.e("Test", url);
       // startActivity(new Intent(getActivity(), UpcomingDetail.class));
    }


}