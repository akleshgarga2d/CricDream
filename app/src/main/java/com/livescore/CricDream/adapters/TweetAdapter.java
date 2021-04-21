package com.livescore.CricDream.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.livescore.CricDream.R;
import com.livescore.CricDream.Models.TweeterItem;

import java.util.ArrayList;

public class TweetAdapter extends ArrayAdapter<TweeterItem> {

    ArrayList<TweeterItem> tweetList = new ArrayList<>();

    public TweetAdapter(Context context, int textViewResourceId, ArrayList<TweeterItem> objects) {
        super(context, textViewResourceId, objects);
        tweetList = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.feed_item, null);
        TextView textViewtitle = (TextView) v.findViewById(R.id.feed_title);
        TextView textViewurl = (TextView) v.findViewById(R.id.feed_url);
        TextView textViewtime = (TextView) v.findViewById(R.id.feed_time);

        textViewtitle.setText(tweetList.get(position).getTitle());
        textViewurl.setText(tweetList.get(position).getUrl());
        textViewtime.setText(tweetList.get(position).getTime());

        return v;

    }

}
