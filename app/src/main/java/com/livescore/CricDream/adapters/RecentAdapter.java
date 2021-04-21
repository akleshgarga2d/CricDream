package com.livescore.CricDream.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.livescore.CricDream.Models.RecentItem;
import com.livescore.CricDream.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecentAdapter extends ArrayAdapter<RecentItem> {

    ArrayList<RecentItem> RecentList = new ArrayList<>();
    Context ctx;
    public RecentAdapter(Context context, int textViewResourceId, ArrayList<RecentItem> objects) {
        super(context, textViewResourceId, objects);
        RecentList = objects;
        ctx=context;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.upcoming_item, null);
        TextView match_title=v.findViewById(R.id.match_title);
        TextView match_name=v.findViewById(R.id.series_name);
        TextView match_relatedname=v.findViewById(R.id.related_name);
        TextView match_date=v.findViewById(R.id.match_time);
        TextView match_venue=v.findViewById(R.id.match_venue);


        match_title.setText(RecentList.get(position).getMatch_title()+"");
        match_name.setText(RecentList.get(position).getMatch_name()+"");
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date sourceDate = null;
        try {
            sourceDate = inputFormat.parse(RecentList.get(position).getMatch_date() + "");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a");
        match_date.setText(targetFormat.format(sourceDate));

       // match_date.setText(RecentList.get(position).getMatch_date()+"");
       /* SimpleDateFormat format = new SimpleDateFormat("d");
        String date_s = format.format(RecentList.get(position).getMatch_date());

        if(date_s.endsWith("1") && !date_s.endsWith("11"))
            format = new SimpleDateFormat("EE MMM d'st', yyyy");
        else if(date_s.endsWith("2") && !date_s.endsWith("12"))
            format = new SimpleDateFormat("EE MMM d'nd', yyyy");
        else if(date_s.endsWith("3") && !date_s.endsWith("13"))
            format = new SimpleDateFormat("EE MMM d'rd', yyyy");
        else
            format = new SimpleDateFormat("EE MMM d'th', yyyy");

        String date_str = format.format(RecentList.get(position).getMatch_date());
        DateFormat date_f = new SimpleDateFormat("HH:mm");
        date_f.setTimeZone(TimeZone.getTimeZone("GMT"));
        String time_str = date_f.format(RecentList.get(position).getMatch_date());*/

//       try{
//           SimpleDateFormat simpleDateParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
//           Date date = simpleDateParser.parse(RecentList.get(position).getMatch_date());
//           SimpleDateFormat simpleDateFormat_output = new SimpleDateFormat("dd MMM yyyy");
//           String dateString = simpleDateFormat_output.format(date);
//           SimpleDateFormat simpleDateFormat_time = new SimpleDateFormat("HH:mm aaa");
//           String time_str=simpleDateFormat_time.format(date);
//           match_date.setText(dateString+" | "+time_str);
//       }catch (Exception e){
//           e.printStackTrace();
//       }


        match_relatedname.setText(RecentList.get(position).getMatch_related_name());
        match_venue.setText(RecentList.get(position).getMatch_venue());
        return v;

    }

}
