package com.meritumads.elements;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.meritumads.R;
import com.meritumads.pojo.Banner;
import com.meritumads.settings.MsAdsSdk;
import com.meritumads.settings.Util;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class PreRollAdapter extends PagerAdapter {

    private ArrayList<Banner> banners;

    public PreRollAdapter(ArrayList<Banner> banners) {
        this.banners = banners;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = null;

        if(banners.get(position).getMediaType().equals("2")){
            view = LayoutInflater.from(container.getContext()).inflate(R.layout.sponsor_home_video_layout, container, false);
            VideoSponsorView videoSponsorView = view.findViewById(R.id.pvSponsor);
            videoSponsorView.loadVideo(container.getContext(), banners.get(position), null, null);
        }

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return banners.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
