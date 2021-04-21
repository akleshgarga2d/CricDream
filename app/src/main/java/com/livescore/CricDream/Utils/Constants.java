package com.livescore.CricDream.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;

import com.livescore.CricDream.R;
import com.livescore.CricDream.api.APIClient;
import com.livescore.CricDream.api.APIInterface;
import com.google.gson.JsonObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class Constants {

    public static String ACCESS_TOKEN="";
    public static String MATCH_UPCOMMING ="0";
    public static String MATCH_LIVE ="1";
    public static String MATCH_FINISHED ="2";
    public static String ACCESSKEY="e1f1f18b1841f6bf512e37a564426f30";
    public static String SECRETKEY="ceea1f48113d5e8c24e5a4cd4cef28f3";
    public static String APPID="com.livescore.cricora";

    public static String BASE_URL="https://rest.cricketapi.com/rest/v2/";
    public static String NEWS_API="https://rest.cricketapi.com/rest/v2/news_aggregation/?access_token=";

    public static String RECENT_SEASON_API="https://rest.cricketapi.com/rest/v2/recent_seasons/?access_token=";
    public static String SEASON_DETAIL_API="https://rest.cricketapi.com/rest/v2/season/season_key/?access_token=";

    public static String RECENT_MATCH_API="https://rest.cricketapi.com/rest/v2/recent_matches/?access_token=";
    public static String MATCH_DETAIL_API="https://rest.cricketapi.com/rest/v2/match/";
    public static String MATCH_DETAIL_API_HOME="https://rest.cricketapi.com/rest/v2/match/banzim_2020_one-day_01/?access_token=";
    public static String GENETRATE_AUTH_TOKEN="https://rest.cricketapi.com/rest/v2/auth/?access_key=e1f1f18b1841f6bf512e37a564426f30&secret_key=ceea1f48113d5e8c24e5a4cd4cef28f3&app_id=com.livescore.cricora&device_id=developer";

    public static String BANNER_IMAGE_URI = "https://api.storelyapp.com:5000/cricket/team/";
    public static String BANNER_IMAGE_URI_IPL = "https://api.storelyapp.com:5000/cricket/ipl/";
    public static String BANNER_API = "https://api.storelyapp.com:5000/v1/games/";

    public static String FULL_MATCH_API = "https://cricket-live-line-edbbf.firebaseio.com/.json";

    public static String MATCH_API="https://cricket-exchange.firebaseio.com/liveMatches.json";

    public static String SCOREBOARDS_API = "https://cricket-exchange.firebaseio.com/scoreboards/{ID}.json";

    public static String FULL_SCORECARD_API = "https://cricket-exchange.firebaseio.com/scorecards/{ID}.json";

    public static String CHECK_STRING ="com.cricket.liveline";
    public static String CHECK_IMAGE ="http://server1.livetvindia.co.in:25461/roms/apk/Untitled.png";
    
    public static APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);

    public static String temas_short_name[]={"IND","NZ","AUS","ENG","SA","SL","PAK","WI","BAN","AFG","ZIM","IRE","CSK","DC","KXIP","MI","RCB","RR","SRH","KKR"};
    public static String teams_colors[]={"#0368CD","#111111","#F9EF50","#3DA8DC","#0141130","#47538D","#0E7D56","#650920","#0ACB51","#3A6BE1","#CF0209","#73DF3D","#E9D841","#359ADE","#F1516D","#1d55cc","#BF141D","#d73984","#e63727","#F2C028"};
    public static int teams_flag[]={R.drawable.ind,R.drawable.nz,R.drawable.aus,R.drawable.eng,R.drawable.sa,R.drawable.sl,R.drawable.pak,R.drawable.wi,R.drawable.ban,R.drawable.afg,R.drawable.zim,R.drawable.ire,R.drawable.csk,R.drawable.dc,R.drawable.kxip,R.drawable.mi,R.drawable.rc,R.drawable.rr,R.drawable.srh,R.drawable.kkr};



    public static HashMap<String,Integer> flag_list=new HashMap<>();
    public static HashMap<String,String> teams_colors_list=new HashMap<>();


    public static class GetAuthToken extends Thread{

        Context ctx;

        public GetAuthToken(@NonNull Context ctx_s) {
            this.ctx=ctx_s;
        }

        @Override
        public void run() {

            authRetrofitApi(this.ctx);
        }

    }
    public static void authRetrofitApi(final Context ctx) {
        //System.out.println("************inauth");
        String device_id = Settings.Secure.getString(ctx.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Call<JsonObject> call1 = apiInterface.getAccessToken(ACCESSKEY,SECRETKEY,APPID,device_id);
        call1.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject object = response.body();
                ACCESS_TOKEN=String.valueOf(object.getAsJsonObject("auth").get("access_token"));
                setAccessToken(ctx,ACCESS_TOKEN.replaceAll("\"", ""));
                Log.d("ACCESS_TOKEN",ACCESS_TOKEN);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("ACCESS_TOKEN","Failed");
            }

    });

    }

    public static void setAccessToken(Context ctx,String accessToken){
        SharedPreferences preferences = ctx.getSharedPreferences("AccessToken", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("acces_token",accessToken);
        editor.apply();
    }

  public static String getAccessToken(Context ctx){
      SharedPreferences prfs = ctx.getSharedPreferences("AccessToken", Context.MODE_PRIVATE);
      return prfs.getString("acces_token", "");
   }
}
