package com.livescore.CricDream.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.livescore.CricDream.R;

import com.livescore.CricDream.adapters.UpcomeDetailTabAdapter;


public class UpcomingDetail extends AppCompatActivity {
    private static String TAGC = UpcomingDetail.class.getName();
    TabLayout tab_upcoming;
    ViewPager upcoming_tab_pager;
    ImageView back_btn;
    String match_key;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_upcoming_detail);
        init();
    }

    public void init() {
        back_btn = findViewById(R.id.back_btn);
        tab_upcoming = findViewById(R.id.tabLayout_upcoming);
        upcoming_tab_pager = findViewById(R.id.viewPager_upcoming);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (getIntent() != null)
        {
            match_key = getIntent().getExtras().getString("match_key");
        }

        tab_upcoming.addTab(tab_upcoming.newTab().setText("Match Info"));
        tab_upcoming.addTab(tab_upcoming.newTab().setText("Squads"));

        UpcomeDetailTabAdapter adapter = new UpcomeDetailTabAdapter(this, getSupportFragmentManager(), tab_upcoming.getTabCount(), match_key);
        upcoming_tab_pager.setAdapter(adapter);

        upcoming_tab_pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_upcoming));

        tab_upcoming.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                upcoming_tab_pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}