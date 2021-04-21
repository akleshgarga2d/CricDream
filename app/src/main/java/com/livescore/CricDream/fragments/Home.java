package com.livescore.CricDream.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.livescore.CricDream.Interfaces.OnItemClickListner;
import com.livescore.CricDream.Models.Banner;
import com.livescore.CricDream.Models.FeatureVideoItem;
import com.livescore.CricDream.Models.NewsItem;
import com.livescore.CricDream.Models.TopSlideItem;
import com.livescore.CricDream.Models.TweeterItem;
import com.livescore.CricDream.R;
import com.livescore.CricDream.Utils.Constants;
import com.livescore.CricDream.Utils.RequestHandler;
import com.livescore.CricDream.Utils.Utillity;
import com.livescore.CricDream.activities.LiveLineActivity;
import com.livescore.CricDream.activities.UpcomingDetail;
import com.livescore.CricDream.adapters.BannerAdapter;
import com.livescore.CricDream.adapters.FeatureVideoAdapter;
import com.livescore.CricDream.adapters.NewsAdapter;
import com.livescore.CricDream.adapters.TopSlideAdapter;
import com.livescore.CricDream.api.CommonMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cc.cloudist.acplibrary.ACProgressFlower;

public class Home extends Fragment implements OnItemClickListner {
    private static String TAGC = Home.class.getName();
    ImageView moreNews;
    JSONArray jsonArray2;
    ArrayList<TweeterItem> tweetList = new ArrayList<>();
    ViewPager topside_pager, feature_video_pager;
    ArrayList<NewsItem> newsList = new ArrayList<>();
    ArrayList<TopSlideItem> topslideList = new ArrayList<>();
    ArrayList<FeatureVideoItem> featureVideoList = new ArrayList<>();
    boolean click_more_news = false;
    ListView news_lv;
    TextView feature_video_title;
    TopSlideAdapter top_ada;
    private Home mHome;
    ACProgressFlower acProgressFlower;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    private View view;
    ProgressBar progresBar;
    ImageView ivHome, ivLive, ivShedule;
    ArrayList<Banner> bannerArrayList = new ArrayList<>();
    BannerAdapter bannerAdapter;
    RecyclerView rvBanner;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // //System.out.println("aceeeeeeeeeee "+getAccessToken(getActivity()));
        view = inflater.inflate(R.layout.fragment_home, container, false);

        init(view);
        Constants.GetAuthToken mgetAuthToken = new Constants.GetAuthToken(getActivity());
        mgetAuthToken.run();

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }
        if (CommonMethod.isNetworkAvailable(getActivity())) {
            getMatchesList();
            mFetchListNewsFromAPI();

        } else {
            CommonMethod.showAlert("Internet Connectivity Failure", getActivity());
        }

        Utillity.p(TAGC + " ", " topside_pager " + topside_pager);

        topside_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                try {
                    Utillity.p(TAGC + " ", " in loop dotscount " + dotscount);
                    for (int i = 0; i < dotscount; i++) {
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.non_active_dot));
                    }
                    Utillity.p(TAGC + " ", " after loop position " + position);

                    dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));
                } catch (Exception e) {
                    Utillity.p(TAGC + " ", " Error on addOnPageChangeListener ---");
                    e.printStackTrace();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && view != null) {
            onResume();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void init(View view) {
        top_ada = new TopSlideAdapter(topslideList, getActivity());
        topside_pager = view.findViewById(R.id.topside_v);
        news_lv = view.findViewById(R.id.latest_news_lv);
        sliderDotspanel = view.findViewById(R.id.SliderDots);
        feature_video_pager = view.findViewById(R.id.featured_v);
        moreNews = view.findViewById(R.id.more_news);
        feature_video_title = view.findViewById(R.id.feature_video_title);
        ivHome = view.findViewById(R.id.ivHome);
        ivLive = view.findViewById(R.id.ivLive);
        ivShedule = view.findViewById(R.id.ivShedule);
        progresBar = view.findViewById(R.id.progresBar);
        // //System.out.println("aceeeeeeeeeee "+getAccessToken(getActivity()));
        mHome = this;

        moreNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click_more_news) {
                    click_more_news = false;
                    FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(news_lv.getWidth(), 400);
                    news_lv.setLayoutParams(lp);

                } else {
                    click_more_news = true;
                    ListAdapter listadp = news_lv.getAdapter();

                    if (listadp != null) {
                        int totalHeight = 0;
                        for (int i = 0; i < listadp.getCount(); i++) {
                            View listItem = listadp.getView(i, null, news_lv);
                            listItem.measure(0, 0);
                            totalHeight += listItem.getMeasuredHeight();
                        }
                        ViewGroup.LayoutParams params = news_lv.getLayoutParams();
                        params.height = totalHeight + (news_lv.getDividerHeight() * (listadp.getCount() - 1));
                        news_lv.setLayoutParams(params);
                        news_lv.requestLayout();
                    }
                }
            }
        });
        featureVideoList.add(new FeatureVideoItem());
        featureVideoList.add(new FeatureVideoItem());
        featureVideoList.add(new FeatureVideoItem());
        featureVideoList.add(new FeatureVideoItem());
        featureVideoList.add(new FeatureVideoItem());

        FeatureVideoAdapter feature_v_ada = new FeatureVideoAdapter(featureVideoList, getActivity());
        feature_video_pager.setAdapter(feature_v_ada);

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(int position) {
        if (topslideList.get(position).isNow()) {
            Intent intent = new Intent(getActivity(), LiveLineActivity.class);
            intent.putExtra("match_key", topslideList.get(position).getMatchkey());
            intent.putExtra("team1_name", topslideList.get(position).getTeam1_name());
            intent.putExtra("team2_name", topslideList.get(position).getTeam2_name());
            intent.putExtra("team1_logo", topslideList.get(position).getflag1());
            intent.putExtra("team2_logo", topslideList.get(position).getflag2());
            intent.putExtra("score", topslideList.get(position).getTeam1_runs());
            intent.putExtra("score2", topslideList.get(position).getTeam2_runs());
            intent.putExtra("team1_overs", topslideList.get(position).getTeam1_overs());
            intent.putExtra("team2_overs", topslideList.get(position).getTeam2_overs());
            intent.putExtra("wicket1", topslideList.get(position).getWicket1());
            intent.putExtra("wicket2", topslideList.get(position).getWicket2());


            getActivity().startActivityForResult(intent, 121);
        } else {
            Intent intent2 = new Intent(getActivity(), UpcomingDetail.class);
            intent2.putExtra("match_key", topslideList.get(position).getMatchkey());
            intent2.putExtra("team1_name", topslideList.get(position).getTeam1_name());
            intent2.putExtra("team2_name", topslideList.get(position).getTeam2_name());
            intent2.putExtra("team1_logo", topslideList.get(position).getflag1());
            intent2.putExtra("team2_logo", topslideList.get(position).getflag2());
            intent2.putExtra("score", topslideList.get(position).getTeam1_runs());
            intent2.putExtra("score2", topslideList.get(position).getTeam2_runs());
            getActivity().startActivityForResult(intent2, 122);
        }

    }

    private void getMatchesList() {
        String url = Constants.RECENT_MATCH_API + Constants.getAccessToken(getContext());

        StringRequest stringDRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonobject = new JSONObject(response);
                            if (topslideList != null & topslideList.size() > 0) {
                                topslideList.clear();
                                top_ada.notifyDataSetChanged();

                                // different thread / object
                                synchronized (topside_pager) {
                                    topside_pager.notify();
                                }
                                //   topside_pager.notifyAll();
                                top_ada = null;
                            }

                            JSONObject jsonObject = jsonobject.getJSONObject("data");
                            JSONArray jsonArray = jsonObject.getJSONArray("intelligent_order");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                String objs = jsonArray.getString(i);
                                String url = Constants.MATCH_DETAIL_API + objs + "/?access_token=" + Constants.getAccessToken(getContext());
                                int finalI = i;
                                StringRequest stringDRequest = new StringRequest(
                                        Request.Method.GET,
                                        url,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {

                                                try {

                                                    JSONObject jsonobject = new JSONObject(response);

                                                    JSONObject objDetails = jsonobject.getJSONObject("data");
                                                    JSONObject obj = objDetails.getJSONObject("card");

                                                    JSONObject team = obj.getJSONObject("teams");
                                                    JSONObject team_1_json = team.getJSONObject("a");
                                                    JSONObject team_2_json = team.getJSONObject("b");

                                                    String match_details = obj.getString("name");
                                                    String title = obj.getString("name");
                                                    String location = obj.getString("venue");
                                                    String date_time = obj.getJSONObject("start_date").getString("iso");

                                                    String team1_name = team_1_json.getString("name");
                                                    String team2_name = team_2_json.getString("name");

                                                    String flag1 = Constants.BANNER_IMAGE_URI + team_1_json.getString("key") + ".png";
                                                    String flag2 = Constants.BANNER_IMAGE_URI + team_2_json.getString("key") + ".png";

                                                    JSONObject details = obj.getJSONObject("innings");

                                                    String wicket, team1_runs, team1_overs;
                                                    String wicket2, team2_runs, team2_overs;
                                                    String status = obj.getString("status");

                                                    if (!status.equals("notstarted")) {
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
                                                    topslideList.add(new TopSlideItem(true, location + "", key, team1_name, team2_name, "", team1_runs, team2_runs, team1_overs, team2_overs, Constants.MATCH_FINISHED + "", match_details + "", date_time + "", flag1, flag2, wicket, wicket2, team1_name + "", team2_name + "", status + ""));
                                                    top_ada = new TopSlideAdapter(topslideList, getActivity());
                                                    topside_pager.setAdapter(top_ada);

                                                } catch (Exception e) {
                                                    e.printStackTrace();

                                                }

                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                            }
                                        }
                                );
                                RequestHandler.getInstance(getActivity()).addToRequestQueue(stringDRequest);

                            }

                            //   top_ada.notifyDataSetChanged();


                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringDRequest);
    }

    private void mFetchListNewsFromAPI() {

        StringRequest stringDRequest = new StringRequest(
                Request.Method.GET,
                "https://livescore6.p.rapidapi.com/news/list?category=cricket",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progresBar.setVisibility(View.GONE);
                            JSONObject jsonobject = new JSONObject(response);
                            jsonArray2 = jsonobject.getJSONArray("arts");

                            getData(jsonArray2);

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progresBar.setVisibility(View.GONE);
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("content-type", "application/json");
                params.put("x-rapidapi-host", "livescore6.p.rapidapi.com");
                params.put("x-rapidapi-key", "d8dd056a73msh8a5ffee0891991dp1ddae9jsna9f688347177");
                return params;
            }
        };

        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringDRequest);

    }

    private void getData(JSONArray jsonArray) {
        if (jsonArray.length() > 0) {
            progresBar.setVisibility(View.GONE);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject newsObj = null;
                try {
                    newsObj = jsonArray.getJSONObject(i);
                    newsList.add(
                            new NewsItem(
                                    "",
                                    newsObj.getString("tit") + "",
                                    newsObj.getString("des") + "",
                                    "",
                                    newsObj.getString("img") + "",
                                    newsObj.getString("img") + "",
                                    newsObj.getString("cap") + "",
                                    newsObj.getString("aut") + ""
                            )

                    );
                    NewsAdapter myAdapter2 = new NewsAdapter(getActivity(), R.layout.news_item, newsList);
                    news_lv.setAdapter(myAdapter2);
                } catch (JSONException e) {
                    e.printStackTrace();
                    acProgressFlower.dismiss();
                }
                //System.out.println(newsObj.toString());

            }
        } else {
            //    Toast.makeText(getContext(), "News not found !", Toast.LENGTH_SHORT).show();
        }
    }


}


//    private class RecentData extends Thread {
//        @Override
//        public void run() {
//
//            OkHttpClient client = new OkHttpClient();
//
//            Request request = new Request.Builder()
//
//                    .url(Constants.RECENT_MATCH_API + Constants.getAccessToken(getActivity()))
//                    .get()
//                    .build();
//            try {
//                Response response = client.newCall(request).execute();
//                String res = response.body().string();
//                //System.out.println("recent*************" + res);
//
//                final JSONObject job = new JSONObject(res);
//
//                Utillity.p(TAGC + " ", " resres ==" + res);
//
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            JSONObject recentData = job.getJSONObject("data");
//                            JSONArray jarr = recentData.getJSONArray("cards");
//                            if (!topslideList.isEmpty()) {
//                                topslideList.clear();
//                            }
//                            for (int i = 0; i < jarr.length(); i++) {
//                                JSONObject recentObj = jarr.getJSONObject(i);
//
//                                MatchDetail matchDetail_load = new MatchDetail(Constants.MATCH_DETAIL_API.replace("matchKey", recentObj.getString("key")) + Constants.getAccessToken(getActivity()));
//                                if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
//                                    Executor executor = Executors.newSingleThreadExecutor();
//                                    executor.execute(matchDetail_load);
//                                }
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//    private class MatchDetail extends Thread {
//
//        private String url;
//
//        public MatchDetail(String url) {
//            this.url = url;
//
//        }
//
//        @Override
//        public void run() {
//            //System.out.println("matchkeyurl..." + this.url);
//
//            OkHttpClient client = new OkHttpClient();
//
//            Request request = new Request.Builder()
//
//                    .url(this.url)
//                    .get()
//                    .build();
//            try {
//                Response response = client.newCall(request).execute();
//                String res = response.body().string();
//
//                //System.out.println("matchdetail*************" + res);
//
//                final JSONObject job = new JSONObject(res);
//
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//
//                            String venue = "", team1_runs = "", matchkey = "", team2_runs = "", team1_overs = "", team2_overs = "", match_status_overview = "", time_tv = "", short_team1 = "", short_team2 = "";
//                            JSONObject matchData = job.getJSONObject("data");
//                            boolean now = false;
//
//                            if (matchData.getJSONObject("card").getJSONObject("innings").toString().equals("{}")) {
//
//                                now = false;
//                            } else {
//
//                                now = true;
//                            }
//                            String related_name = matchData.getJSONObject("card").getString("related_name");
//                            venue = matchData.getJSONObject("card").getString("venue");
//                            String team1_name = matchData.getJSONObject("card").getJSONObject("teams").getJSONObject("a").getString("name");
//                            String team2_name = matchData.getJSONObject("card").getJSONObject("teams").getJSONObject("b").getString("name");
//                            String match_status = matchData.getJSONObject("card").getString("status");
//                            match_status_overview = matchData.getJSONObject("card").getString("status_overview");
//                            time_tv = matchData.getJSONObject("card").getJSONObject("start_date").getString("iso").split("\\+")[0];
//                            short_team1 = matchData.getJSONObject("card").getJSONObject("teams").getJSONObject("a").getString("short_name");
//                            short_team2 = matchData.getJSONObject("card").getJSONObject("teams").getJSONObject("b").getString("short_name");
//                            matchkey = matchData.getJSONObject("card").getString("key");
//
//                            if (matchData.getJSONObject("card").getJSONObject("innings").has("a_1")) {
//                                team1_runs = matchData.getJSONObject("card").getJSONObject("innings").getJSONObject("a_1").getString("run_str").split("in")[0];
//                                team2_runs = matchData.getJSONObject("card").getJSONObject("innings").getJSONObject("b_1").getString("run_str").split("in")[0];
//                                team1_overs = matchData.getJSONObject("card").getJSONObject("innings").getJSONObject("a_1").getString("run_str").split("in")[1];
//                                team2_overs = matchData.getJSONObject("card").getJSONObject("innings").getJSONObject("b_1").getString("run_str").split("in")[1];
//                            }
//                            //System.out.println("short***********"+short_team1+short_team2);
//                            topslideList.add(new TopSlideItem(now, venue, matchkey, team1_name, team2_name, related_name, team1_runs, team2_runs, team1_overs, team2_overs, match_status, match_status_overview, time_tv, short_team1, short_team2));
//
//                            top_ada = new TopSlideAdapter(topslideList, getActivity());
//                            top_ada.setOnItemClickListner(mHome);
//                            topside_pager.setAdapter(top_ada);
//
//                            dotscount = top_ada.getCount();
//                            //System.out.println("dotcountttttttttttt"+dotscount);
//                            dots = new ImageView[dotscount];
//                            sliderDotspanel.removeAllViews();
//                            for (int i = 0; i < dotscount; i++) {
//
//                                dots[i] = new ImageView(getActivity());
//                                dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.non_active_dot));
//
//                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//                                params.setMargins(8, 0, 8, 0);
//
//                                sliderDotspanel.addView(dots[i], params);
//
//                            }
//
//                            dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//
//                        }
//                    }
//                });
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//    }



/*
                Log.d("HOME_DATA",liveLineJson.toString());
                JSONObject dataliveJson = liveLineJson.getJSONObject("data");
                int status = liveLineJson.getInt("status");
                String playing_innings = dataliveJson.getString("playing_innings");
                String match_title = dataliveJson.getString("title");
                JSONObject currentScoreJson = dataliveJson.getJSONObject("current_score");
                JSONObject teamBet = currentScoreJson.getJSONObject("team_bet");
*/
//  "ball_left":"6",
//               "bowling_team_title":"St Lucia Zouks",
//               "current_overs":"8",
//               "current_r":"75",
//               "current_w":"4",
//               "full_over":"6,1,NB,0,1,4",
//               "match_date_time":"2020-08-26T19:30",
//               "required_rate":"0",
//               "run_left":"-3",
//               "run_rate":"9.38",
//               "scoreboardteam1name":"St Lucia Zouks",
//               "scoreboardteam2name":"Trinbago Knight Riders  ",
//               "target":"72",
//               "team1_logo_url":"https://staticg.sportskeeda.com/cricket_widget/trinbago-knight-riders.png",
//               "team2_current_overs":"17.1",
//               "team2_current_r":"111",
//               "team2_current_w":"6",
//               "team2_logo_url":"https://staticg.sportskeeda.com/cricket_widget/st-lucia-zouks.png",
//               "team2_run_rate":"6.47",
//               "title":"Trinbago Knight Riders  ",
//               "total_overs":"9"
/*
                TopSlideItem t;
                if (playing_innings.equals("1")) {
                    t = new TopSlideItem(
                            true, match_title + "", "", Utillity.checkString(teamBet.getString("scoreboardteam1name")), Utillity.checkString(teamBet.getString("scoreboardteam2name")),
                            "", teamBet.getString("current_r") + "", "", teamBet.getString("current_overs") + "", "",
                            status + "", teamBet.getString("title") + "", teamBet.getString("match_date_time") + "",
                            Utillity.checkImage(teamBet.getString("team1_logo_url")) + "", Utillity.checkImage(teamBet.getString("team2_logo_url") )+ "",
                            teamBet.getString("current_w") + "", "",
                            Utillity.checkString( teamBet.getString("scoreboardteam1name")) + "", Utillity.checkString(teamBet.getString("scoreboardteam2name")) + "");

                } else {
                    t = new TopSlideItem(
                            true, match_title + "", "", Utillity.checkString(teamBet.getString("scoreboardteam1name")),Utillity.checkString( teamBet.getString("scoreboardteam2name")),
                            "", teamBet.getString("team2_current_r") + "", "", teamBet.getString("team2_current_overs") + "",
                            "",
                            status + "", teamBet.getString("title") + "", teamBet.getString("match_date_time") + "",
                            Utillity.checkImage( teamBet.getString("team1_logo_url")) + "",Utillity.checkImage( teamBet.getString("team2_logo_url")) + "",
                            teamBet.getString("team2_current_w") + "", "",
                            Utillity.checkString(teamBet.getString("scoreboardteam1name")) + "", Utillity.checkString(teamBet.getString("scoreboardteam2name") )+ "");
                }


                topslideList.add(t);
*/

/*
                JSONObject recentMatchesJson = response.getJSONObject("RecentMatches");
                JSONObject recentMatches_DataJson = recentMatchesJson.getJSONObject("data");

                for (Iterator<String> iter = recentMatches_DataJson.keys(); iter.hasNext(); )
                {
                    String key = iter.next();

                    Utillity.p(TAGC + " ", " key name =" + key);

                    JSONObject jsonObjectM = new JSONObject(recentMatches_DataJson.getJSONObject(key + "") + "");

                    //            Utillity.p(TAGC + " ", " jsonObjectM =" + jsonObjectM);

                    JSONObject team_1_json = jsonObjectM.getJSONObject("team_1");
                    JSONObject team_2_json = jsonObjectM.getJSONObject("team_2");

                    String date_time = jsonObjectM.getString("date_time");
                    String match_details = jsonObjectM.getString("match_details");
                    String title = jsonObjectM.getString("title");
                    String location = jsonObjectM.getString("location");


                    String team1_name = Utillity.checkString(team_1_json.getString("team_name"));
                    String team2_name = Utillity.checkString(team_2_json.getString("team_name"));

                    String flag1 = team_1_json.getString("team_logo");
                    String flag2 = team_2_json.getString("team_logo");

                    String wicket = team_1_json.getString("team1_wicket");
                    String wicket2 = team_2_json.getString("team2_wicket");

                    String team1_runs = team_1_json.getString("team1_run");
                    String team2_runs = team_2_json.getString("team2_run");

                    String team1_overs = team_1_json.getString("team1_overs");
                    String team2_overs = team_2_json.getString("team2_overs");


                    topslideList.add(new TopSlideItem(true, location + "", key, team1_name, team2_name, "", team1_runs, team2_runs, team1_overs, team2_overs, Constants.MATCH_FINISHED + "", match_details + "", date_time + "", flag1, flag2, wicket, wicket2, team1_name + "", team2_name + ""));

                    //  topslideList.add(new TopSlideItem(true, "", "", team1_name, team2_name, "", "", "", "", "", "", "", "", short_team1, short_team2));

                    if(topslideList.size() > 5)
                    {
                        topslideList.remove(1);
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            top_ada = new TopSlideAdapter(topslideList, getActivity());
                            top_ada.setOnItemClickListner(mHome);
                            topside_pager.setAdapter(top_ada);

                            dotscount = top_ada.getCount();
                            //System.out.println("dotcountttttttttttt"+dotscount);
                            dots = new ImageView[dotscount];
                            sliderDotspanel.removeAllViews();
                            for (int i = 0; i < dotscount; i++)
                            {
                                dots[i] = new ImageView(getActivity());
                                dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.non_active_dot));

                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                                params.setMargins(8, 0, 8, 0);

                                sliderDotspanel.addView(dots[i], params);
                            }

                            dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));
                        }
                    });
                }
                */
//                if (topslideList != null & topslideList.size() > 0) {
//                    topslideList.clear();
//                    top_ada.notifyDataSetChanged();
//
//                    /* different thread / object */
//                    synchronized (topside_pager) {
//                        topside_pager.notify();
//                    }
//                    //   topside_pager.notifyAll();
//                    top_ada = null;
//                }
//
//                for (Iterator<String> iter = jsonObject.keys(); iter.hasNext(); )
//                {
//                    String key = iter.next();
//
//                    //           Utillity.p(TAGC + " ", " key name =" + key);
//
//                    JSONObject jsonObjectM = new JSONObject(jsonObject.getJSONObject(key + "") + "");
//
//                    //            Utillity.p(TAGC + " ", " jsonObjectM =" + jsonObjectM);
//
//                    String team1_name = jsonObjectM.getString("team1");
//                    String team2_name = jsonObjectM.getString("team2");
//                    String flag1 = jsonObjectM.getString("flag1");
//                    String flag2 = jsonObjectM.getString("flag2");
//                    String venue = jsonObjectM.getString("venue");
//                    String related_name = jsonObjectM.getString("series_name");
//                    String match_status = jsonObjectM.getString("status");
//                    String match_status_overview = jsonObjectM.getString("title");
//                    String time_tv = jsonObjectM.getString("date");
//                    String wicket = jsonObjectM.getString("wicket");
//                    String wicket2 = jsonObjectM.getString("wicket2");
//
//                    String t1 = jsonObjectM.getString("t1");
//                    String t2 = jsonObjectM.getString("t2");
//
//                    //          if (matchData.getJSONObject("card").getJSONObject("innings").has("a_1"))
//
//                    String team1_runs = "";
//                    String team2_runs = "";
//                    if (!jsonObjectM.isNull("score")) {
//                        team1_runs = jsonObjectM.getInt("score") + "";
//                    }
//
//                    if (!jsonObjectM.isNull("score2")) {
//                        team2_runs = jsonObjectM.getInt("score2") + "";
//                    }
//
//                    String team1_overs = ((jsonObjectM.getInt("ballsdone") / 6) + "." + (jsonObjectM.getInt("ballsdone") % 6) + " Overs");
//                    String team2_overs = ((jsonObjectM.getInt("ballsdone2") / 6) + "." + (jsonObjectM.getInt("ballsdone2") % 6) + " Overs");
//
//
//                    //System.out.println("short***********"+short_team1+short_team2);
//                    topslideList.add(new TopSlideItem(true, venue, key, team1_name, team2_name, related_name, team1_runs, team2_runs, team1_overs, team2_overs, match_status, match_status_overview, time_tv, flag1, flag2, wicket, wicket2, t1, t2));
//                    //  topslideList.add(new TopSlideItem(true, "", "", team1_name, team2_name, "", "", "", "", "", "", "", "", short_team1, short_team2));
//
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            top_ada = new TopSlideAdapter(topslideList, getActivity());
//                            top_ada.setOnItemClickListner(mHome);
//                            topside_pager.setAdapter(top_ada);
//
//                            dotscount = top_ada.getCount();
//                            //System.out.println("dotcountttttttttttt"+dotscount);
//                            dots = new ImageView[dotscount];
//                            sliderDotspanel.removeAllViews();
//                            for(int i = 0; i < dotscount; i++)
//                            {
//                                dots[i] = new ImageView(getActivity());
//                                dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.non_active_dot));
//
//                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//                                params.setMargins(8, 0, 8, 0);
//
//                                sliderDotspanel.addView(dots[i], params);
//                            }
//
//                            dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));
//
//                        }
//                    });
//                }

//                if (job.getBoolean("status"))
//                {
//                    try {
//                        JSONObject newsData = job.getJSONObject("data");
//                        JSONArray jarr = newsData.getJSONArray("news");
//                        if (!newsList.isEmpty())
//                        {
//                            newsList.clear();
//                        }
//                        for (int i = 0; i < jarr.length(); i++)
//                        {
//                            JSONObject newsObj = jarr.getJSONObject(i);
//                            //System.out.println(newsObj.toString());
//                            newsList.add(
//                                    new NewsItem(newsObj.getString("title"),
//                                            newsObj.getString("description"),
//                                            newsObj.getString("link")
//                                    )
//                            );
//                        }
//
//                        NewsAdapter myAdapter2 = new NewsAdapter(getActivity(), R.layout.news_item, newsList);
//                        news_lv.setAdapter(myAdapter2);
//
//                        MatchDetail matchDetail = new MatchDetail();
//                       matchDetail.execute();
//
//
////                        NewMatchDetail newMatchDetail = new NewMatchDetail();
////                        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
////                            Executor executor = Executors.newSingleThreadExecutor();
////                            executor.execute(newMatchDetail);
////                        }
////
////                        RecentData recent_load = new RecentData();
////                        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
////                            Executor executor = Executors.newSingleThreadExecutor();
////                            executor.execute(recent_load);
////                        }
//                        acProgressFlower.dismiss();
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        acProgressFlower.dismiss();
//                        moreNews.setEnabled(false);
//                    }
//
//                }
//                else
//                    {
//                    String statusMsg = job.getString("status_msg");
//                    if (statusMsg.equalsIgnoreCase("Invalid Access Token")) {
//
//                        //System.out.println("invalidddddddddd");
//                        if (CommonMethod.isNetworkAvailable(getActivity())) {
//                            //authRetrofitApi(getActivity());
//                            String device_id = Settings.Secure.getString(getActivity().getContentResolver(),
//                                    Settings.Secure.ANDROID_ID);
//
//                            Utillity.p(TAGC+" "," ACCESSKEY ="+ACCESSKEY);
//                            Utillity.p(TAGC+" "," SECRETKEY ="+SECRETKEY);
//                            Utillity.p(TAGC+" "," APPID ="+APPID);
//                            Utillity.p(TAGC+" "," device_id ="+device_id);
//
//                            Call<JsonObject> call1 = apiInterface.getAccessToken(ACCESSKEY, SECRETKEY, APPID, device_id);
//                            call1.enqueue(new Callback<JsonObject>()
//                            {
//                                @Override
//                                public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response)
//                                {
//                                    Utillity.p(TAGC+" "," response.body() "+response.body() );
//                                    JsonObject object = response.body();
//                                    ACCESS_TOKEN = String.valueOf(object.getAsJsonObject("auth").get("access_token"));
//                                    setAccessToken(getActivity(), ACCESS_TOKEN.replaceAll("\"", ""));
//                                    AsyncTaskRunner asyn_run = new AsyncTaskRunner();
//                                    asyn_run.execute();
//                                    //System.out.println("************" + ACCESS_TOKEN.replaceAll("\"", ""));
//                                }
//
//                                @Override
//                                public void onFailure(Call<JsonObject> call, Throwable t)
//                                {
//                                    t.printStackTrace();
//                                }
//                            });
//
//                        } else {
//                            //System.out.println("invaliddddddddddelse");
//                            CommonMethod.showAlert("Internet Connectivity Failure", getActivity());
//                        }
//                    }
//                }

/*
    private class FullMatchDetail extends AsyncTask<String, String, String> {
        private String url = Constants.RECENT_MATCH_API+Constants.getAccessToken(getContext());
        @Override
        protected String doInBackground(String... strings) {
         //   Utillity.p(TAGC + "", " API Calling = " + Constants.FULL_MATCH_API);
            String res = "";
            OkHttpClient client = new OkHttpClient();
            Log.d("API_URL",url);
            Request request = new Request.Builder()
                    .url(this.url)
                    .get()
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
                res = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String res) {
            acProgressFlower.dismiss();

            JSONObject response = null;
            try {
                response = new JSONObject(res + "");


                if (topslideList != null & topslideList.size() > 0) {
                    topslideList.clear();
                    top_ada.notifyDataSetChanged();

                    /* different thread / object */
                 /*   synchronized (topside_pager) {
                        topside_pager.notify();
                    }
                    //   topside_pager.notifyAll();
                    top_ada = null;
                }
                JSONObject jsonObject = response.getJSONObject("data");
                JSONArray jsonArray = jsonObject.getJSONArray("intelligent_order");
                Log.d("HOME_DEBUG",jsonArray.toString());

                for (int i=0;i<jsonArray.length();i++){
                    try {
                        TopSlideItem t=new TopSlideItem();
                        String objs = jsonArray.getString(i);

                        String matcheDetails = "";
                        OkHttpClient client = new OkHttpClient();

                        Request request = new Request.Builder()
                                .url(Constants.MATCH_DETAIL_API+objs.toString()+"?access_token="+Constants.getAccessToken(getActivity()))
                                .get()
                                .build();

                        Response respons = null;

                        try {
                            respons = client.newCall(request).execute();
                            matcheDetails = respons.body().string();
                            Log.d("HOME_DEBUG",matcheDetails);

                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d("HOME_DEBUG",e.getMessage());
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                      //  Log.d("DEBUG_HOME",e.getMessage());

                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        protected void onPreExecute() {
            if (acProgressFlower != null) {
                acProgressFlower.dismiss();
            }

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

// this below is old code
/*
    private class NewsAsyncTaskRunner extends AsyncTask<String, String, String> {
        private String resp = "";

        @Override
        protected String doInBackground(String... params) {

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("")
                    .get()
                    .addHeader("x-rapidapi-host", "livescore6.p.rapidapi.com")
                    .addHeader("x-rapidapi-key", "daf8a9c97amsh41f45f554b04b11p14c733jsn1711f0afb7ec")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                resp = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }


//            OkHttpClient client = new OkHttpClient();
//
//            Request request = new Request.Builder()
//                    .url(Constants.NEWS_API + Constants.getAccessToken(getActivity()))
//                    .get()
//                    .build();
//            try {
//                Response response = client.newCall(request).execute();
//                resp = response.body().string();
//                //System.out.println("news*************" + resp);
//
//            } catch (Exception e) {
//                acProgressFlower.dismiss();
//                e.printStackTrace();
//            }
            return resp;
        }


        @Override
        protected void onPostExecute(String res) {
            // execution of result of Long time consuming operation
            //System.out.println(res);
            try {
                final JSONObject response = new JSONObject(res);
                Utillity.p(TAGC+" "," News API response ="+res );

                JSONArray arts = response.getJSONArray("arts");

                if (!newsList.isEmpty()) {
                    newsList.clear();
                }
                for (int i = 0; i < arts.length(); i++) {
                    JSONObject newsObj = arts.getJSONObject(i);
                    //System.out.println(newsObj.toString());
                    newsList.add(
                            new NewsItem(
                                    response.getString("bld")+"",
                                    newsObj.getString("tit")+"",
                                    newsObj.getString("des")+"",
                                    "",
                                    newsObj.getString("img")+"",
                                    newsObj.getString("img")+"",
                                    newsObj.getString("cap")+"",
                                    newsObj.getString("aut")+""
                            )
                    );
                }

                NewsAdapter myAdapter2 = new NewsAdapter(getActivity(), R.layout.news_item, newsList);
                news_lv.setAdapter(myAdapter2);

            } catch (Exception e) {
                acProgressFlower.dismiss();
            }
        }


        @Override
        protected void onPreExecute() {

            if (acProgressFlower != null) {
                acProgressFlower.dismiss();
            }

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
