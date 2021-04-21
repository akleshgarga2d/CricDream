package com.livescore.CricDream.adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.livescore.CricDream.fragments.MatchInfoFragment;
import com.livescore.CricDream.fragments.SquadsFragment;

public class UpcomeDetailTabAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;
    String match_key;

    public UpcomeDetailTabAdapter(Context context, FragmentManager fm, int totalTabs,String match_key) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
        this.match_key=match_key;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MatchInfoFragment matchInfoFragment = new MatchInfoFragment(this.match_key);
                return matchInfoFragment;
            case 1:
                SquadsFragment squadFragment = new SquadsFragment(this.match_key);
                return squadFragment;

            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}
