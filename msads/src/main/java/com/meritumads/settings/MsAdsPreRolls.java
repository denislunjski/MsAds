package com.meritumads.settings;

import android.widget.ScrollView;

import androidx.recyclerview.widget.RecyclerView;

import com.meritumads.elements.MsAdsPreRollHolder;

public abstract class MsAdsPreRolls {

    private static MsAdsPreRollBanner preRollBanner;

    public static void getInstance(String developerId, MsAdsPreRollHolder layoutHolder){
        runBanners(developerId, layoutHolder, null, null, null);
    }
    public static void getInstance(String developerId, MsAdsPreRollHolder layoutHolder, MsAdsPreRollService preRollService){
        runBanners(developerId, layoutHolder, preRollService, null, null);
    }

    public static void getInstance(String developerId, MsAdsPreRollHolder layoutHolder, RecyclerView recyclerView){
        runBanners(developerId, layoutHolder, null, recyclerView, null);
    }

    public static void getInstance(String developerId, MsAdsPreRollHolder layoutHolder, ScrollView scrollView){
        runBanners(developerId, layoutHolder, null, null, scrollView);
    }

    public static void getInstance(String developerId, MsAdsPreRollHolder layoutHolder, RecyclerView recyclerView, MsAdsPreRollService preRollService){
        runBanners(developerId, layoutHolder, preRollService, recyclerView, null);
    }

    public static void getInstance(String developerId, MsAdsPreRollHolder layoutHolder, ScrollView scrollView, MsAdsPreRollService preRollService){
        runBanners(developerId, layoutHolder, preRollService,null, scrollView);
    }

    private static void runBanners(String bannerId, MsAdsPreRollHolder view, MsAdsPreRollService preRollService, RecyclerView recyclerView, ScrollView scrollView){
            preRollBanner = new MsAdsPreRollBanner();
            preRollBanner.init(bannerId, view, preRollService, recyclerView, scrollView);
    }

}
