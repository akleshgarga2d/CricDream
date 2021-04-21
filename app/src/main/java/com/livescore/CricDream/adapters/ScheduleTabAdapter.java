package com.livescore.CricDream.adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.livescore.CricDream.fragments.RecentSchedule;
import com.livescore.CricDream.fragments.UpcomingShedule;

public class ScheduleTabAdapter extends FragmentStatePagerAdapter {

    int totalTabs;
    private int mCurrentPosition = -1;

    public ScheduleTabAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);

        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                UpcomingShedule upcomingFragment = new UpcomingShedule();
                return upcomingFragment;
            case 0:
                RecentSchedule recentFragment = new RecentSchedule();
                return recentFragment;
            default:
                return null;
        }
    }

    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }


//
//    @Override
//    public void setPrimaryItem(ViewGroup container, int position, Object object) {
//        super.setPrimaryItem(container, position, object);
//        if (position != mCurrentPosition) {
//            Fragment fragment = (Fragment) object;
//            CustomPager pager = (CustomPager) container;
//            if (fragment != null && fragment.getView() != null) {
//                mCurrentPosition = position;
//                pager.measureCurrentView(fragment.getView());
//            }
//        }
//    }


//    @Override
//    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//        boolean wrapHeight = View.MeasureSpec.getMode(heightMeasureSpec) == View.MeasureSpec.AT_MOST;
//
//        final View tab = getChildAt(0);
//        int width = getMeasuredWidth();
//        int tabHeight = tab.getMeasuredHeight();
//
//        if (wrapHeight) {
//            // Keep the current measured width.
//            widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
//        }
//
//        int fragmentHeight = measureFragment(((Fragment) getAdapter().instantiateItem(this, getCurrentItem())).getView());
//        heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(tabHeight + fragmentHeight + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()), MeasureSpec.AT_MOST);
//
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }

}
