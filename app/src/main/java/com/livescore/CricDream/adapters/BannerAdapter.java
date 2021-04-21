package com.livescore.CricDream.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.livescore.CricDream.Models.Banner;
import com.livescore.CricDream.R;
import com.livescore.CricDream.Utils.Constants;
import com.livescore.CricDream.Utils.Utillity;

import java.net.URLEncoder;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.MyViewHolder> {
    private static String TAGC = BannerAdapter.class.getName();

    private ArrayList<Banner> bannerArrayList;
    private Context context;

    public BannerAdapter(ArrayList<Banner> bannerArrayList, Context context) {
        this.bannerArrayList = bannerArrayList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_banner, parent, false);

        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Glide.with(context)
                .asBitmap()
                .load(Constants.BANNER_IMAGE_URI + bannerArrayList.get(position).getBanner())
                .centerCrop()
                .placeholder(R.mipmap.ic_launchernew_round)

                // .placeholder(context.getResources().getDrawable(R.drawable.top_bg))
                .into(holder.ivBanner);
    }

    @Override
    public int getItemCount() {
        return bannerArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivBanner;

        public MyViewHolder(View v) {
            super(v);
            ivBanner = (ImageView) v.findViewById(R.id.ivBanner);
            ivBanner.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.ivBanner) {
                Utillity.p(TAGC + " ", " ivBanner --- ");

                PackageManager packageManager = context.getPackageManager();
                Intent i = new Intent(Intent.ACTION_VIEW);

                try {
                    //   String url = "https://api.whatsapp.com/send?phone="+ phone +"&text=" + URLEncoder.encode(message, "UTF-8");

                    String url = "https://api.whatsapp.com/send?phone=" + bannerArrayList.get(getAdapterPosition()).getNumber()
                            + "&text=" +
                            URLEncoder.encode(""//bannerArrayList.get(getAdapterPosition()).getName() +"\n" + bannerArrayList.get(getAdapterPosition()).getUrl()
                                    , "UTF-8");
                    i.setPackage("com.whatsapp");
                    i.setData(Uri.parse(url));
                    if (i.resolveActivity(packageManager) != null) {
                        context.startActivity(i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

//              //  String sendString = "Name: " + name + "\nAddress: " + address + "\nProduct: " + product;
//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.putExtra(Intent.EXTRA_TEXT, "");
//                sendIntent.setType("text/plain");
//                sendIntent.setPackage("com.whatsapp");
//                context.startActivity(sendIntent);
            }
        }
    }
}
