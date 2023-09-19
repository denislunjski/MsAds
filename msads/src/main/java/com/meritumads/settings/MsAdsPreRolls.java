package com.meritumads.settings;

import android.view.View;
import android.widget.ScrollView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public abstract class MsAdsPreRolls {

    private static PreRollBanner preRollBanner;

    public static void getInstance(String bannerId, ViewPager layoutHolder){
        runBanners(bannerId, layoutHolder);
    }

    private static void runBanners(String bannerId, ViewPager view){
        if(preRollBanner == null){
            preRollBanner = new PreRollBanner();
            preRollBanner.init(bannerId, view);
        }else{
            preRollBanner.init(bannerId, view);
        }
    }

}
