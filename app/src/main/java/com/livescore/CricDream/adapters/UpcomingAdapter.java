package com.livescore.CricDream.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.livescore.CricDream.Models.UpcomingItem;
import com.livescore.CricDream.R;
import com.livescore.CricDream.activities.UpcomingDetail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UpcomingAdapter extends ArrayAdapter<UpcomingItem> implements View.OnClickListener {

    ArrayList<UpcomingItem> upcomingList = new ArrayList<>();
    Context ctx;
    OnDetailClickedListener mCallback;

    public UpcomingAdapter(Context context, int textViewResourceId, ArrayList<UpcomingItem> objects) {
        super(context, textViewResourceId, objects);
        upcomingList = objects;
        ctx = context;
    }

    public void setOnDetailClickedListener(OnDetailClickedListener mCallback) {
        this.mCallback = mCallback;
    }

    public interface OnDetailClickedListener {
        public void DetailClicked(String url);
    }

    @Override
    public void onClick(View v) {
        this.mCallback.DetailClicked("Detail this text.");
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.upcoming_item, null);
        RelativeLayout relativeLayout = v.findViewById(R.id.upcoming_card);
        TextView match_title = v.findViewById(R.id.match_title);
        TextView match_name = v.findViewById(R.id.series_name);
        TextView match_relatedname = v.findViewById(R.id.related_name);
        TextView match_date = v.findViewById(R.id.match_time);
        TextView match_venue = v.findViewById(R.id.match_venue);
        match_title.setText(upcomingList.get(position).getMatch_title());
        match_name.setText(upcomingList.get(position).getMatch_name());
/*
        try{

            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Date futureDate = inputFormat.parse(upcomingList.get(position).getMatch_date()+"");

            SimpleDateFormat simpleDateFormat_output = new SimpleDateFormat("dd MMM yyyy");
            String dateString = simpleDateFormat_output.format(futureDate);
            SimpleDateFormat simpleDateFormat_time = new SimpleDateFormat("HH:mm aaa");
            String time_str=simpleDateFormat_time.format(futureDate);
            match_date.setText(dateString+" | "+time_str);
        }catch (Exception e){
            e.printStackTrace();
        }*/


        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date sourceDate = null;
        try {
            sourceDate = inputFormat.parse(upcomingList.get(position).getMatch_date() + "");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a");
        match_date.setText(targetFormat.format(sourceDate));
        match_relatedname.setText(upcomingList.get(position).getMatch_related_name());
        match_venue.setText(upcomingList.get(position).getMatch_venue());

        relativeLayout.setTag(position);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                Log.d("Card", String.valueOf(position));
                Intent intent = new Intent(v.getContext(), UpcomingDetail.class);
                intent.putExtra("match_key", upcomingList.get(position).getMatch_key());
                v.getContext().startActivity(intent);
            }
        });
        return v;

    }

}
