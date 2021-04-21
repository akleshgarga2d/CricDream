package com.livescore.CricDream.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.livescore.CricDream.R;
import com.livescore.CricDream.adapters.ScheduleTabAdapter;

public class Schedule extends Fragment   {
    private static String TAGC = Schedule.class.getName();
    TabLayout tab_shedule;
    ViewPager shedule_tab_pager;
    ScheduleTabAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        tab_shedule = view.findViewById(R.id.tabLayout_shedule);
        shedule_tab_pager = view.findViewById(R.id.viewPager_shedule);

        tab_shedule.addTab(tab_shedule.newTab().setText("Recent"));
        tab_shedule.addTab(tab_shedule.newTab().setText("Upcoming"));

         adapter = new ScheduleTabAdapter(getActivity(), getActivity().getSupportFragmentManager(), tab_shedule.getTabCount());
        shedule_tab_pager.setAdapter(adapter);

        shedule_tab_pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_shedule));

        tab_shedule.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                shedule_tab_pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
    }
}