package com.livescore.CricDream.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.livescore.CricDream.R;
import com.livescore.CricDream.activities.NewsDetail;
import com.livescore.CricDream.Models.NewsItem;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<NewsItem> {

    ArrayList<NewsItem> tweetList = new ArrayList<>();
    Context ctx;
    ImageView im_news;
    //    WebView web_news;


    public NewsAdapter(Context context, int textViewResourceId, ArrayList<NewsItem> objects) {
        super(context, textViewResourceId, objects);
        tweetList = objects;
        ctx = context;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.news_item, null);
        TextView textViewtitle = (TextView) v.findViewById(R.id.news_title);
        RelativeLayout relNewsItem = v.findViewById(R.id.relNewsItem);
        TextView textViewtime = (TextView) v.findViewById(R.id.news_time);
        TextView textViewurl = (TextView) v.findViewById(R.id.news_url);
        TextView textViewdesc = (TextView) v.findViewById(R.id.news_desc);
        im_news = (ImageView) v.findViewById(R.id.news_im);
        //       web_news=(WebView) v.findViewById(R.id.web_item) ;

        textViewtitle.setText(tweetList.get(position).getTitle());
        textViewdesc.setText(tweetList.get(position).getDescription());
        textViewurl.setText(tweetList.get(position).getNews_url());
        //    textViewtime.setText("05 March,2020  .  11:50 PM, Thu");

        Glide.with(ctx).load(tweetList.get(position).getImage())
                .centerCrop()
                .placeholder(ctx.getResources().getDrawable(R.mipmap.ic_launchernew_round))
                .into(im_news);

        relNewsItem.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent i = new Intent(ctx, NewsDetail.class);
                i.putExtra("newsUrl", tweetList.get(position).getNews_url());
                i.putExtra("newsObj", tweetList.get(position));
                ctx.startActivity(i);
            }
        });


//
//        WebSettings webSettings = web_news.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setUseWideViewPort(true);
//        webSettings.setLoadWithOverviewMode(true);
//
//
//        web_news.setWebViewClient(new WebViewClient() {
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                Toast.makeText(ctx, description, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url)
//            {
//                super.onPageFinished(view, url);
//                im_news.setVisibility(View.GONE);
//                web_news.loadUrl(tweetList.get(position).getNews_url().replace("http","https"));
//            }
//        });
        return v;
    }
}
