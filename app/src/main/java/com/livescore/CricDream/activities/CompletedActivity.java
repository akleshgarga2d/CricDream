package com.livescore.CricDream.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.livescore.CricDream.R;
import com.livescore.CricDream.Utils.Constants;
import com.livescore.CricDream.Utils.Utillity;
import com.livescore.CricDream.api.CommonMethod;
import com.livescore.CricDream.fragments.LiveFragment;
import com.livescore.CricDream.fragments.LiveMatchInfoFragment;
import com.livescore.CricDream.fragments.LiveScorecardFragment;
import com.livescore.CricDream.fragments.SquadsFragment;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class CompletedActivity extends AppCompatActivity {
    private static String TAGC = CompletedActivity.class.getName();

    public ViewPager viewPager;
    private TabLayout tabLayout;
    public String status, match_key = "", team1_name, team2_name, team1_logo, team2_logo, score = "", score2 = "", wicket1 = "", wicket2 = "", team1_oversstr = "", team2_oversstr = "";
    private ImageView back_btn, imageView_team1, imageView_team2;
    private ACProgressFlower acProgressFlower;
    private String frag_name = "top_frag";
    private TextView team1_runs, team2_runs, team1_overs, team2_overs, team1_short_name, team2_short_name, current_run_rate, need_run, remain_balls, run_rate;
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed);


        Utillity.p(TAGC + " ", " onCreate --- ");

        if (getIntent() != null) {
            match_key = getIntent().getExtras().getString("match_key");

            team1_name = getIntent().getExtras().getString("team1_name");
            team2_name = getIntent().getExtras().getString("team2_name");
            team1_logo = getIntent().getExtras().getString("team1_logo");
            team2_logo = getIntent().getExtras().getString("team2_logo");
            score = getIntent().getExtras().getString("score");
            score2 = getIntent().getExtras().getString("score2");
            wicket1 = getIntent().getExtras().getString("wicket1");
            wicket2 = getIntent().getExtras().getString("wicket2");
            team1_oversstr = getIntent().getExtras().getString("team1_overs");
            team2_oversstr = getIntent().getExtras().getString("team2_overs");
            status = getIntent().getExtras().getString("status");
            Utillity.p(TAGC + " ", " match_key " + match_key);
            type = getIntent().getExtras().getInt("type");
            //System.out.println("matchkeyyyyyyyy********"+match_key);

            try {
                if (getIntent().getExtras().getString("frag_name") != null) {
                    frag_name = getIntent().getExtras().getString("frag_name");
                } else {
                    frag_name = "top_frag";
                }
            } catch (Exception e) {
                frag_name = "top_frag";
                e.printStackTrace();
            }
        }
        initUI();

      NewInfoReceiver receiver_info = new NewInfoReceiver();
        //the intent filter will be action = "com.example.demo_service.action.SERVICE_FINISHED"
        IntentFilter filterInfo = new IntentFilter("heightViewpagerInfo");
        // register the receiver:
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver_info, filterInfo);
    }


    private void initUI() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (frag_name.equalsIgnoreCase("recent")) {
            setupViewPagerRecent(viewPager);

        } else if (frag_name.equalsIgnoreCase("top_frag")) {

            setupViewPager(viewPager);
        }

        team1_runs = findViewById(R.id.team1_runs);
        team2_runs = findViewById(R.id.team2_runs);
        team1_overs = findViewById(R.id.team1_overs);
        team2_overs = findViewById(R.id.team2_overs);
        back_btn = findViewById(R.id.back_btn);
        imageView_team1 = findViewById(R.id.imageView_team1);
        imageView_team2 = findViewById(R.id.imageView_team2);
        team1_short_name = findViewById(R.id.team1_short_name);
        team2_short_name = findViewById(R.id.team2_short_name);
        current_run_rate = findViewById(R.id.current_run_rate);
        need_run = findViewById(R.id.need_runs);
        remain_balls = findViewById(R.id.remaining_balls);
        run_rate = findViewById(R.id.run_rate);

        Glide.with(this).load(team1_logo)
                .centerCrop()
                .placeholder(getResources().getDrawable(R.mipmap.ic_launchernew_round))
                .into(imageView_team1);

        Glide.with(this).load(team2_logo)
                .centerCrop()
                .placeholder(getResources().getDrawable(R.mipmap.ic_launchernew_round))
                .into(imageView_team2);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }


    private class AsyncTaskDetailMatch extends AsyncTask<String, String, String> {

        private String resp;
        //ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            Utillity.p(TAGC + " ", " AsyncTaskDetailMatch ---");
            Utillity.p(TAGC + " ", " API calling" + Constants.SCOREBOARDS_API.replace("{ID}", match_key));

            OkHttpClient client = new OkHttpClient();
            String urlString = Constants.SCOREBOARDS_API.replace("{ID}", match_key);
            Request request = new Request.Builder()
                    .url(urlString)
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
//            OkHttpClient client = new OkHttpClient();
//            String urlString = Constants.MATCH_DETAIL_API.replace("matchKey",params[0]) + Constants.getAccessToken(LiveLineActivity.this);
//            Request request = new Request.Builder()
//                    .url(urlString)
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
            ///    //System.out.println("ddddddddd"+res);

            try {
                final JSONObject response = new JSONObject(res);

                Utillity.p(TAGC + " ", " onPostExecuteresponse =" + response);

                acProgressFlower.dismiss();

                try {
                    team1_short_name.setText(team1_name);
                    team2_short_name.setText(team2_name);


                    if (score.equals("")) {
                        team1_runs.setText("00" + "/" + "0");
                    } else {

                        team1_runs.setText(score + "/" + wicket1);
                    }
                    if (score2.equals("")) {
                        team2_runs.setText("00" + "/" + "0");
                    } else {

                        team2_runs.setText(score2 + "/" + wicket2);
                    }

                    if (!team1_oversstr.equals("") & !team1_oversstr.equals("0")) {
                        team1_overs.setText(team1_oversstr);
                    }
                    if (!team2_oversstr.equals("") & !team2_oversstr.equals("0")) {
                        team2_overs.setText(team2_oversstr);
                    }

                    remain_balls.setText(response.getInt("remainingballs") + "");
                    need_run.setText(response.getInt("needrun") + "");

                    // Required Run Rate = (Runs required to win / Balls Remaining ) x 6
                    run_rate.setText("" + (response.getInt("target") / response.getInt("remainingballs") * 6));


                    //If a team has scored 227 runs and has faced 5 overs in that time, then:
                    //Run Rate (runs per over) = 227 / 5
                    //Run Rate (runs per over) = 45.4

                    current_run_rate.setText("");

                    acProgressFlower.dismiss();

                } catch (Exception e) {
                    e.printStackTrace();
                    acProgressFlower.dismiss();
                }
            } catch (Exception e) {
                acProgressFlower.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            acProgressFlower = new ACProgressFlower.Builder(CompletedActivity.this)
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE)
                    .text("Please Wait")
                    .fadeColor(Color.DKGRAY).build();
            acProgressFlower.show();
            // progressDialog = ProgressDialog.show(getActivity(),"","");
        }


        @Override
        protected void onProgressUpdate(String... text) {
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        CompletedActivity.ViewPagerAdapter adapter = new CompletedActivity.ViewPagerAdapter(getSupportFragmentManager());
        LiveFragment live_frag = new LiveFragment(match_key);
        live_frag.setUserVisibleHint(false);

        if (type == 1) {
            if (status.equals("started")) {
                //  adapter.addFragment(new LiveFragment(match_key), "Live");
                adapter.addFragment(live_frag, "Live");
            }

            adapter.addFragment(new LiveScorecardFragment(match_key), "Scorecard");
            adapter.addFragment(new LiveMatchInfoFragment(match_key), "Info");
            adapter.addFragment(new SquadsFragment(match_key), "Squads");
        } else {

            adapter.addFragment(new LiveScorecardFragment(match_key), "Scorecard");
            adapter.addFragment(new LiveMatchInfoFragment(match_key), "Info");
            adapter.addFragment(new SquadsFragment(match_key), "Squads");
        }

        viewPager.setAdapter(adapter);
    }

    private void setupViewPagerRecent(ViewPager viewPager) {
        CompletedActivity.ViewPagerAdapter adapter = new CompletedActivity.ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new LiveScorecardFragment(match_key), "Scorecard");
        adapter.addFragment(new LiveMatchInfoFragment(match_key), "Info");
        adapter.addFragment(new SquadsFragment(match_key), "Squads");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (CommonMethod.isNetworkAvailable(CompletedActivity.this)) {
            new CompletedActivity.AsyncTaskDetailMatch().execute(this.match_key);
        } else {
            CommonMethod.showAlert("Internet Connectivity Failure", CompletedActivity.this);
        }
    }


    private class NewInfoReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //System.out.println("*******gggg"+intent.getExtras().getString("height_s"));

            ViewGroup.LayoutParams params = viewPager.getLayoutParams();
            params.height = Integer.parseInt(intent.getExtras().getString("height_s"));    //500px
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;    //500px
            viewPager.requestLayout();

        }
    }
}