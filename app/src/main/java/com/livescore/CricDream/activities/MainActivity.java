package com.livescore.CricDream.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.livescore.CricDream.Models.Banner;
import com.livescore.CricDream.R;
import com.livescore.CricDream.Utils.Constants;
import com.livescore.CricDream.Utils.RequestHandler;
import com.livescore.CricDream.Utils.Utillity;
import com.livescore.CricDream.adapters.BannerAdapter;
import com.livescore.CricDream.fragments.Home;
import com.livescore.CricDream.fragments.LiveListing;
import com.livescore.CricDream.fragments.Schedule;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.livescore.CricDream.fragments.Setting;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static String TAGC = MainActivity.class.getName();

    FrameLayout frameContainer;
    BottomNavigationView navigation;

    ArrayList<Banner> bannerArrayList = new ArrayList<>();
    BannerAdapter bannerAdapter;
    RecyclerView rvBanner;
    private ImageView mImageView;
    RelativeLayout adMobView;
    AdView adView;
    String whatsAppurl, imageUrl;
    final static String url = "http://75.101.138.121:5000/v1/getGames/";
    ACProgressFlower acProgressFlower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = findViewById(R.id.navigation);
        rvBanner = findViewById(R.id.rvBanner);
        adView = findViewById(R.id.adView);
        adMobView = findViewById(R.id.adMobView);
        mImageView = findViewById(R.id.ivbanner);

        frameContainer = findViewById(R.id.fragment_container);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new Home());
        mFetchListFromAPI();
        // mFetchListFromAPI();
        new BannerAsyncTask().execute();

        AdView mAdView = new AdView(MainActivity.this);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(MainActivity.this.getResources().getString(R.string.banner_score_home));
        ((RelativeLayout) adMobView).addView(mAdView);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                // Check the LogCat to get your test device ID
                .addTestDevice("465C1AEBF81716612A169070B98DA602")
                .build();

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }


            @Override
            public void onAdClosed() {
                // Toast.makeText(getActivity(), "Ad is closed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Toast.makeText(getActivity(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                // Toast.makeText(getActivity(), "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });
        mAdView.loadAd(adRequest);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {

                case R.id.navigation_home:
                    fragment = new Home();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_schedule:
                    Utillity.p(TAGC + " ", " navigation_schedule ----");
                    fragment = new Schedule();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_live:
                    fragment = new LiveListing();
                    loadFragment(fragment);
                    return true;

                case R.id.setting:
                    fragment = new Setting();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private class BannerAsyncTask extends AsyncTask<String, String, String> {
        private String resp = "";

        @Override
        protected String doInBackground(String... params) {
            Utillity.p(TAGC + " ", " BANNER_API Calling =" + Constants.BANNER_API);
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(Constants.BANNER_API + "")
                    .get()
                    .build();

            try {
                Response response = client.newCall(request).execute();
                resp = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String response) {
            acProgressFlower.dismiss();
            try {
                final JSONObject response_Json = new JSONObject(response);

                if (response_Json.getString("status").equals("Success")) {
                    if (bannerArrayList.size() > 0) {
                        bannerArrayList.clear();
                    }

                    JSONArray data_JArray = response_Json.getJSONArray("data");

                    Banner banner;

                    JSONObject jsonObject;

                    for (int count = 0; count < data_JArray.length(); count++) {
                        jsonObject = data_JArray.getJSONObject(count);

                        banner = new Banner(
                                jsonObject.getString("name") + "",
                                jsonObject.getString("number") + "",
                                jsonObject.getString("URL") + "",
                                jsonObject.getString("banner") + ""
                        );

                        if (jsonObject.getString("name").equals("Famous exchange")) {
                            bannerArrayList.add(banner);
                        }
                    }

                    LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                    rvBanner.setLayoutManager(layoutManager);
                    bannerAdapter = new BannerAdapter(bannerArrayList, MainActivity.this);
                    //   rvBanner.setOnItemClickListner(mlive_list);

                    rvBanner.setAdapter(bannerAdapter);
                }

            } catch (Exception e) {
                acProgressFlower.dismiss();
            }
        }


        @Override
        protected void onPreExecute() {

            if (acProgressFlower != null) {
                acProgressFlower.dismiss();
            }

            acProgressFlower = new ACProgressFlower.Builder(MainActivity.this)
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

    private void mFetchListFromAPI() {

        StringRequest stringDRequest = new StringRequest(
                com.android.volley.Request.Method.GET,
                url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonobjectMain = new JSONObject(response);
                            final JSONArray jsonObject = jsonobjectMain.getJSONArray("data");
                            Log.d("dataApi", jsonObject.toString());
                            getData(jsonObject);
                            Glide.with(getApplicationContext())
                                    .load("http://75.101.138.121:5000/" + imageUrl)
                                    .into(mImageView);
                            final String urlWhatsapp = whatsAppurl;
                            mImageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlWhatsapp));
                                    startActivity(browserIntent);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("MainError", e.getMessage());
                            Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  mFetchListFromAPI();
                    }
                }
        );

        RequestHandler.getInstance(this).addToRequestQueue(stringDRequest);

    }

    private void getData(JSONArray jsonArray) {

        if (jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                final JSONObject obj;

                try {
                    obj = jsonArray.getJSONObject(i);
                    if (obj.getString("name").equals("Guru exchange")) {
                        Log.d("dataApi", obj.toString());
                        imageUrl = obj.getString("banner");
                        whatsAppurl = obj.getString("url");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        } else {
            //Toast.makeText(getContext(), "News not found !", Toast.LENGTH_SHORT).show();
        }
    }
}
