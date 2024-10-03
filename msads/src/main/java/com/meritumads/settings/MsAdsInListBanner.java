package com.meritumads.settings;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.meritumads.elements.MsAdsHeightWrappingViewPager;
import com.meritumads.pojo.MsAdsBanner;
import com.meritumads.pojo.MsAdsPosition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

class MsAdsInListBanner {

    public MsAdsPosition currentBanner;
    public String error;
    ViewGroup mainView;
    RecyclerView recyclerView;
    ScrollView scrollView;
    int currentPage = 0;
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

        if(currentBanner.getBanners().size()>0){
            if(MsAdsSdk.getInstance().getListOfActiveFilters().size()>0){
                for(int i = 0; i < currentBanner.getBanners().size(); i++){
                    if(currentBanner.getBanners().get(i).getFilters().length()>0){
                        boolean isFilterActive = false;
                        ArrayList<String> filteredFilters = new ArrayList<>();
                        for(Map.Entry<String, String> entry: MsAdsSdk.getInstance().getListOfActiveFilters().entrySet()) {
                            String[] temp = entry.getKey().split("-");
                            if(!currentBanner.getBanners().get(i).getFilters().equals("")) {
                                if (currentBanner.getBanners().get(i).getFilters().contains(temp[0] + "," + entry.getValue())) {
                                    filteredFilters.add(temp[0] +"," + entry.getValue());
                                    isFilterActive = true;
                                }
                            }
                        }
                        if(!isFilterActive){
                            currentBanner.getBanners().remove(i);
                            i--;
                        }else{
                            currentBanner.getBanners().get(i).setFiltersForStats(filteredFilters);
                        }
                    }
                }
            }
        }

        if(currentBanner.getBanners().size()>0) {

            float boxRatio = 0f;
            for(int i = 0; i < currentBanner.getBanners().size(); i++){
                if((float)currentBanner.getBanners().get(i).getHeight()/(float) currentBanner.getBanners().get(i).getWidth()> boxRatio){
                    boxRatio = (float)currentBanner.getBanners().get(i).getHeight()/(float) currentBanner.getBanners().get(i).getWidth();
                }
            }
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
            params.height = (int) ((int) MsAdsSdk.getInstance().getScreenWidth() * boxRatio);
            heightWrappingViewPager[0].setLayoutParams(params);

            Collections.sort(currentBanner.getBanners(), new Comparator<MsAdsBanner>() {
                @Override
                public int compare(MsAdsBanner banner1, MsAdsBanner banner2) {
                    return banner1.getOrdnum() - banner2.getOrdnum();
                }
            });

            adsAdapter = new MsAdsAdapter(currentBanner.getDeveloperId(), currentBanner.getBanners(), heightWrappingViewPager[0],
                    currentBanner.getRotationDelay(), recyclerView, scrollView, currentBanner.getReplayMode(), null);
            heightWrappingViewPager[0].setAdapter(adsAdapter);

            heightWrappingViewPager[0].addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    MsAdsSdk.getInstance().setActivePositionInViewPager(heightWrappingViewPager[0].getCurrentItem());
                    for(int i = 0; i < heightWrappingViewPager[0].getChildCount(); i++){
                        if(heightWrappingViewPager[0].getChildAt(i).getTag().toString().contains("video")) {
                            if (heightWrappingViewPager[0].getChildAt(i).getTag().equals("video-" + String.valueOf(position))) {
                                if (heightWrappingViewPager[0].getChildAt(i) instanceof RelativeLayout) {
                                    if (((RelativeLayout) heightWrappingViewPager[0].getChildAt(i)).getChildAt(0) instanceof MsAdsVideoSponsorView) {
                                        ((MsAdsVideoSponsorView) ((RelativeLayout) heightWrappingViewPager[0].getChildAt(i)).getChildAt(0)).resumeVideo();
                                    }
                                }
                            }else{
                                if (heightWrappingViewPager[0].getChildAt(i) instanceof RelativeLayout) {
                                    if (((RelativeLayout) heightWrappingViewPager[0].getChildAt(i)).getChildAt(0) instanceof MsAdsVideoSponsorView) {
                                        ((MsAdsVideoSponsorView) ((RelativeLayout) heightWrappingViewPager[0].getChildAt(i)).getChildAt(0)).pauseVideo();
                                    }
                                }
                            }
                        }
                    }
                    /*
                    if(heightWrappingViewPager[0].getChildAt(heightWrappingViewPager[0].getChildCount()-1).getTag().equals("video")){
                        if(heightWrappingViewPager[0].getChildAt(heightWrappingViewPager[0].getChildCount()-1) instanceof RelativeLayout){
                           if(((RelativeLayout)heightWrappingViewPager[0].getChildAt(heightWrappingViewPager[0].getChildCount()-1)).getChildAt(0) instanceof MsAdsVideoSponsorView){
                               ((MsAdsVideoSponsorView)((RelativeLayout)heightWrappingViewPager[0].getChildAt(heightWrappingViewPager[0].getChildCount()-1)).getChildAt(0)).resumeVideo();
                           }
                        }
                    }

                     */
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

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
