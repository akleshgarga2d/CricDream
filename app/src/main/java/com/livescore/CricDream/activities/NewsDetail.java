package com.livescore.CricDream.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.livescore.CricDream.Models.NewsItem;
import com.livescore.CricDream.R;
import com.livescore.CricDream.Utils.Utillity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewsDetail extends Activity {
    private static String TAGC = NewsDetail.class.getName();
    WebView web_news;
    TextView tvDate, tvtag, tvTitle, tvContent;
    ImageView back_btn, ivTopImage;
    CardView cardView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);
        web_news = findViewById(R.id.web_news);
        cardView = findViewById(R.id.cardView);

        ivTopImage = findViewById(R.id.ivTopImage);
        back_btn = findViewById(R.id.back_btn);

        tvDate = findViewById(R.id.tvDate);
        tvtag = findViewById(R.id.tvtag);
        tvTitle = findViewById(R.id.tvTitle);
        tvContent = findViewById(R.id.tvContent);

        cardView.setCardBackgroundColor(Color.TRANSPARENT);
        cardView.setCardElevation(0);

        NewsItem newsItem = (NewsItem) getIntent().getSerializableExtra("newsObj");

        if (newsItem != null) {
            Utillity.p(TAGC + " ", " newsItem.getImage() =" + newsItem.getImage());
            Glide.with(this).load(newsItem.getImage())
                    .placeholder(getResources().getDrawable(R.drawable.player_icon))
                    .centerCrop()
                    .into(ivTopImage);

            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");

            try {
                Date futureDate = inputFormat.parse(newsItem.getTime() + "");
                String dateString = outputFormat.format(futureDate);
                tvDate.setText(dateString + "");

            } catch (ParseException e) {
                e.printStackTrace();
            }

            tvtag.setText(newsItem.getAut() + "");
            tvTitle.setText(newsItem.getTitle() + "");
            tvContent.setText(newsItem.getDescription() + "");
        }

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


//
//        WebSettings webSettings = web_news.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setUseWideViewPort(true);
//        webSettings.setLoadWithOverviewMode(true);
//
//        final Activity activity = this;
//
//        web_news.setWebViewClient(new WebViewClient() {
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
//            }
//
//
//        });
//
//        if(getIntent().getExtras().get("newsUrl")!=null)
//        {
//            web_news.getSettings().setJavaScriptEnabled(true);
//            web_news.loadUrl(getIntent().getExtras().get("newsUrl").toString().replace("http","https"));
//        }
    }
}
