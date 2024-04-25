package com.meritumads.settings;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidx.recyclerview.widget.RecyclerView;

import com.meritumads.elements.AdsAdapter;
import com.meritumads.R;
import com.meritumads.elements.HeightWrappingViewPager;
import com.meritumads.pojo.Banner;
import com.meritumads.pojo.Position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

class InListBanner {

    public Position currentBanner;
    public String error;
    ViewGroup mainView;
    RecyclerView recyclerView;
    ScrollView scrollView;
    public void init(String developerId, ViewGroup mainView, RecyclerView recyclerView, ScrollView scrollView) {
        this.mainView = mainView;
        this.recyclerView = recyclerView;
        this.scrollView = scrollView;
        ArrayList<Position> inListBanners = MsAdsSdk.getInstance().getInListBanners();
        for(int i = 0; i < inListBanners.size(); i++){
            if(developerId.equals(inListBanners.get(i).getDeveloperId())){
                currentBanner = inListBanners.get(i);
                break;
            }
        }
        if(currentBanner!=null){
            setupBanner();
        }
    }

    private void setupBanner() {

        if(mainView == null){
            return;
        }
        RelativeLayout relativeLayout = new RelativeLayout(mainView.getContext());
        ViewGroup.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeLayout.setLayoutParams(params);
        relativeLayout.setBackgroundColor(Color.parseColor("#000000"));
        mainView.addView(relativeLayout);
        HeightWrappingViewPager heightWrappingViewPager = new HeightWrappingViewPager(mainView.getContext());
        if(heightWrappingViewPager == null){
            return;
        }
        relativeLayout.addView(heightWrappingViewPager);
        params.height = (int)((int)MsAdsSdk.getInstance().getScreenWidth() * currentBanner.getBoxRatio());
        heightWrappingViewPager.setLayoutParams(params);


        if(currentBanner.getBanners().size()>0){
            for(int i = 0; i < currentBanner.getBanners().size(); i++){
                if(!currentBanner.getBanners().get(i).getStates().contains(MsAdsSdk.getInstance().getDeviceState())){
                    currentBanner.getBanners().remove(i);
                    i--;
                }
            }
        }

        Collections.sort(currentBanner.getBanners(), new Comparator<Banner>() {
            @Override
            public int compare(Banner banner1, Banner banner2) {
                return banner1.getOrdnum() - banner2.getOrdnum();
            }
        });

        AdsAdapter adsAdapter = new AdsAdapter(currentBanner.getBanners(), heightWrappingViewPager,
                currentBanner.getRotationDelay(), recyclerView, scrollView, currentBanner.getReplayMode());
        heightWrappingViewPager.setAdapter(adsAdapter);

    }

}
