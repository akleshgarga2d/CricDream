package com.livescore.CricDream.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.livescore.CricDream.R;
import com.livescore.CricDream.Models.FeatureVideoItem;

import java.util.List;

public class FeatureVideoAdapter extends PagerAdapter {

    private Context context;
    private List<FeatureVideoItem> data;
    private ProgressDialog dialog;

    private static final String TAG = "Adapter";

    public FeatureVideoAdapter(List<FeatureVideoItem> image_arraylist, Context context) {
        this.context = context;
        this.data = image_arraylist;
        
    }

   

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.featured_video_item, container, false);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    @Override
    public float getPageWidth(int position) {
        return(0.5f);
    }

}