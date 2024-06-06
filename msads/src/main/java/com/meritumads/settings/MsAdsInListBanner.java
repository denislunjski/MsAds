package com.meritumads.settings;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meritumads.elements.MsAdsAdapter;
import com.meritumads.elements.MsAdsHeightWrappingViewPager;
import com.meritumads.pojo.MsAdsBanner;
import com.meritumads.pojo.MsAdsPosition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class MsAdsInListBanner {

    public MsAdsPosition currentBanner;
    public String error;
    ViewGroup mainView;
    RecyclerView recyclerView;
    ScrollView scrollView;

    MsAdsAdapter adsAdapter;
    public void init(String developerId, ViewGroup mainView, RecyclerView recyclerView, ScrollView scrollView) {
        this.mainView = mainView;
        this.recyclerView = recyclerView;
        this.scrollView = scrollView;
        ArrayList<MsAdsPosition> inListBanners = MsAdsSdk.getInstance().getInListBanners();
        if(inListBanners!=null && inListBanners.size()>0) {
            for (int i = 0; i < inListBanners.size(); i++) {
                if (developerId.equals(inListBanners.get(i).getDeveloperId())) {
                    currentBanner = inListBanners.get(i);
                    break;
                }
            }
        }else{
            return;
        }
        if(currentBanner!=null){
            setupBanner();
        }
    }

    private void setupBanner() {

        if(mainView == null){
            return;
        }
        if(currentBanner.getBanners().size()>0) {

            RelativeLayout relativeLayout = new RelativeLayout(mainView.getContext());
            ViewGroup.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            relativeLayout.setLayoutParams(params);
            relativeLayout.setBackgroundColor(Color.parseColor("#000000"));
            mainView.addView(relativeLayout);
            final MsAdsHeightWrappingViewPager[] heightWrappingViewPager = {new MsAdsHeightWrappingViewPager(mainView.getContext())};
            if (heightWrappingViewPager[0] == null) {
                return;
            }
            relativeLayout.addView(heightWrappingViewPager[0]);
            params.height = (int) ((int) MsAdsSdk.getInstance().getScreenWidth() * currentBanner.getBoxRatio());
            heightWrappingViewPager[0].setLayoutParams(params);

            Collections.sort(currentBanner.getBanners(), new Comparator<MsAdsBanner>() {
                @Override
                public int compare(MsAdsBanner banner1, MsAdsBanner banner2) {
                    return banner1.getOrdnum() - banner2.getOrdnum();
                }
            });

            adsAdapter = new MsAdsAdapter(currentBanner.getBanners(), heightWrappingViewPager[0],
                    currentBanner.getRotationDelay(), recyclerView, scrollView, currentBanner.getReplayMode());
            heightWrappingViewPager[0].setAdapter(adsAdapter);

            heightWrappingViewPager[0].addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(@NonNull View v) {

                }

                @Override
                public void onViewDetachedFromWindow(@NonNull View v) {
                    if(adsAdapter!=null)
                        adsAdapter = null;

                }
            });
        }

    }

}
