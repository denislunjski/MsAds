package com.meritumads.elements;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.meritumads.R;
import com.meritumads.elements.HeightWrappingViewPager;
import com.meritumads.elements.VideoSponsorView;
import com.meritumads.pojo.Banner;
import com.meritumads.settings.MsAdsSdk;
import com.meritumads.settings.Util;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class AdsAdapter extends PagerAdapter {

    private ArrayList<Banner> banners;
    private Timer timer;
    private TimerTask task;
    private android.os.Handler handler;
    private ViewPager viewPager;
    private int temp = 0;
    private float scrollTime;
    private RecyclerView recyclerView;
    private ScrollView scrollView;

    public AdsAdapter(ArrayList<Banner> banners, ViewPager viewPager, String scrollTime, RecyclerView recyclerView, ScrollView scrollView) {
        this.banners = banners;
        this.viewPager = viewPager;
        this.recyclerView = recyclerView;
        this.scrollView = scrollView;
        try {
            this.scrollTime = Float.parseFloat(scrollTime);
        }catch (Exception e){
            this.scrollTime = 3;
        }
        startAutomaticScroll();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = null;


        if(banners.get(position).getMediaType().equals("2")){
            view = LayoutInflater.from(container.getContext()).inflate(R.layout.sponsor_home_video_layout, container, false);
            VideoSponsorView videoSponsorView = view.findViewById(R.id.pvSponsor);
            videoSponsorView.loadVideo(container.getContext(), banners.get(position), recyclerView, scrollView);
        }else {
            view = LayoutInflater.from(container.getContext()).inflate(R.layout.sponsor_home_layout, container, false);
            ImageView sponsorImage = view.findViewById(R.id.sponsor_image);

            Glide.with(container.getContext())
                    .load(banners.get(position).getUrlSource() + "?=" + banners.get(position).getUuidTs())
                    .signature(new ObjectKey(banners.get(position).getUuidTs()))
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(sponsorImage);

            sponsorImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.startAnimation(new AlphaAnimation(1.0f, 0.4f));
                    //Util.sendSponsorClick(container.getContext(), sponsoTypeId, sponsorBannerArrayList.get(position).getSponsorId(), campaignId);
                    Util.openWebView(!banners.get(position).getUrlTarget().equals("") ?
                                    banners.get(position).getUrlTarget() :
                                    banners.get(position).getMainCampaignUrl(),
                            MsAdsSdk.getInstance().getWebviewDroid());
                }
            });
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

    private void startAutomaticScroll() {

        if(banners.size()>1) {
            if (timer == null) {
                timer = new Timer();
                handler = new android.os.Handler();
                task = new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (viewPager.getCurrentItem() + 1 < viewPager.getAdapter().getCount()) {
                                    temp++;
                                    viewPager.setCurrentItem(temp, true);
                                } else if (viewPager.getCurrentItem() + 1 == viewPager.getAdapter().getCount()) {
                                    temp = 0;
                                    viewPager.setCurrentItem(temp, true);
                                }
                            }
                        });
                    }
                };
            }
            timer.schedule(task, (int)scrollTime * 1000, (int)scrollTime * 1000);

            viewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (timer != null) {
                        timer.cancel();
                        timer.purge();
                        timer = null;
                    }
                    if (task != null) {
                        task.cancel();
                    }
                    if (handler != null) {
                        handler.removeCallbacks(null);
                        handler = null;
                    }
                    return false;
                }
            });

        }
    }
}
