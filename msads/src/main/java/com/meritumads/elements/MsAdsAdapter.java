package com.meritumads.elements;

import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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
import com.meritumads.pojo.MsAdsBanner;
import com.meritumads.settings.MsAdsSafeClickListener;
import com.meritumads.settings.MsAdsSdk;
import com.meritumads.settings.MsAdsUtil;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MsAdsAdapter extends PagerAdapter {

    private ArrayList<MsAdsBanner> banners;
    private Timer timer;
    private TimerTask task;
    private android.os.Handler handler;
    private ViewPager viewPager;
    private int temp = 0;
    private float scrollTime;
    private RecyclerView recyclerView;
    private ScrollView scrollView;
    private String replayMode = "";

    private MsAdsFullScreenPopup msAdsFullScreenPopup;

    public MsAdsAdapter(ArrayList<MsAdsBanner> banners, ViewPager viewPager, float scrollTime, RecyclerView recyclerView, ScrollView scrollView, String replayMode, MsAdsFullScreenPopup msAdsFullScreenPopup) {
        this.banners = banners;
        this.viewPager = viewPager;
        this.recyclerView = recyclerView;
        this.scrollView = scrollView;
        this.replayMode = replayMode;
        this.msAdsFullScreenPopup = msAdsFullScreenPopup;
        try {
            this.scrollTime = scrollTime;
            if(this.scrollTime < 0.1){
                this.scrollTime = 3;
            }
        }catch (Exception e){
            this.scrollTime = 3;
        }
        startAutomaticScroll();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = null;


        if(banners.get(position).getBannerType().equals("video")){
            view = LayoutInflater.from(container.getContext()).inflate(R.layout.msads_sponsor_home_video_layout, container, false);
            MsAdsVideoSponsorView videoSponsorView = view.findViewById(R.id.pvSponsor);
            videoSponsorView.loadVideo(container.getContext(), banners.get(position), recyclerView, scrollView, null, replayMode, msAdsFullScreenPopup);
        }else {
            view = LayoutInflater.from(container.getContext()).inflate(R.layout.msads_sponsor_home_layout, container, false);
            ImageView sponsorImage = view.findViewById(R.id.sponsor_image);

            Glide.with(container.getContext())
                    .load(banners.get(position).getMediaUrl() + "?=" + banners.get(position).getMediaTs())
                    .signature(new ObjectKey(banners.get(position).getMediaTs()))
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(sponsorImage);

            sponsorImage.setOnClickListener(new MsAdsSafeClickListener() {
                @Override
                public void onSingleClick(View v) {
                    MsAdsUtil.collectUserStats(banners.get(position).getBannerId(), "click", MsAdsSdk.getInstance().getUserId(),banners.get(position).getFiltersForStats());
                    if(banners.get(position).getApiActiveNonActive().equals("1")){
                        String response = MsAdsSdk.getInstance().getApiLinkService().openApiLink(banners.get(position).getAndroidSubLink());
                        MsAdsUtil.openWebView(response);
                    }else {
                        MsAdsUtil.openWebView(banners.get(position).getAndroidSubLink());
                    }
                    if(msAdsFullScreenPopup!=null){
                        msAdsFullScreenPopup.removeDialog();
                    }
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

        if(banners.size()>0) {
            viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (getVisibilitPercentHeight(viewPager)) {
                        MsAdsUtil.collectUserStats(banners.get(0).getBannerId(), "impression", MsAdsSdk.getInstance().getUserId(), banners.get(0).getFiltersForStats());
                        viewPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        Log.i("ms_ads_counter", banners.get(0).getBannerId());
                    }
                }
            });


            ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    if (position < banners.size()) {
                        if (getVisibilitPercentHeight(viewPager)) {
                            MsAdsUtil.collectUserStats(banners.get(position).getBannerId(), "impression",
                                    MsAdsSdk.getInstance().getUserId(), banners.get(position).getFiltersForStats());
                            Log.i("ms_ads_counter", banners.get(position).getBannerId());
                        }
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            };

            viewPager.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(@NonNull View v) {
                    if (viewPager != null) {
                        viewPager.addOnPageChangeListener(onPageChangeListener);
                        Log.i("ms_ads_viewpager", "attached");
                    }
                }

                @Override
                public void onViewDetachedFromWindow(@NonNull View v) {
                    if (viewPager != null) {
                        viewPager.removeOnPageChangeListener(onPageChangeListener);
                        Log.i("ms_ads_viewpager", "dettached");
                    }

                }
            });
        }


    }

    public boolean getVisibilitPercentHeight(View view) {
        boolean visible = false;

        Rect globalVisibilityRectangle = new Rect();
        view.getGlobalVisibleRect(globalVisibilityRectangle);

        int visibleHeight = globalVisibilityRectangle.bottom - globalVisibilityRectangle.top;
        int actualHeight = view.getMeasuredHeight();

        if(visibleHeight <= actualHeight && globalVisibilityRectangle.top > 0 && !MsAdsSdk.getInstance().isAppInBackground()){
            visible = true;
        }

        return visible;
    }

}
