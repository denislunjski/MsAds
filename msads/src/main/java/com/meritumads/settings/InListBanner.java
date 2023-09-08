package com.meritumads.settings;

import android.view.View;
import android.widget.ScrollView;

import androidx.recyclerview.widget.RecyclerView;

import com.meritumads.elements.AdsAdapter;
import com.meritumads.R;
import com.meritumads.elements.HeightWrappingViewPager;
import com.meritumads.pojo.Position;

import java.util.ArrayList;

class InListBanner {

    Position currentBanner;
    View mainView;
    RecyclerView recyclerView;
    ScrollView scrollView;
    public void init(String bannerId, View mainView, RecyclerView recyclerView, ScrollView scrollView) {
        this.mainView = mainView;
        this.recyclerView = recyclerView;
        this.scrollView = scrollView;
        ArrayList<Position> inListBanners = MsAdsSdk.getInstance().getInListBanners();
        for(int i = 0; i < inListBanners.size(); i++){
            if(bannerId.equals(inListBanners.get(i).getPositionId())){
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
        HeightWrappingViewPager heightWrappingViewPager = mainView.findViewById(R.id.main_banner_holder);
        if(heightWrappingViewPager == null){
            return;
        }

        AdsAdapter adsAdapter = new AdsAdapter(currentBanner.getBanners(), heightWrappingViewPager, currentBanner.getRotationDelay(), recyclerView, scrollView);
        heightWrappingViewPager.setAdapter(adsAdapter);

    }

}
